package ru.raptors.russian_museum.puzzles.activities;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.fragments.DialogGameFinished;
import ru.raptors.russian_museum.puzzles.Puzzle;
import ru.raptors.russian_museum.puzzles.PuzzlePiece;
import ru.raptors.russian_museum.puzzles.TouchListener;

public class PuzzleActivity extends AppCompatActivity {

    int rowsCount = 1;
    int colsCount = 1;

    ArrayList<PuzzlePiece> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        final RelativeLayout layout = findViewById(R.id.puzzlesLayout);
        final ImageView imageView = findViewById(R.id.puzzle);
        final TextView label = findViewById(R.id.label);
        final TextView author = findViewById(R.id.author);

        Intent intent = getIntent();
        Puzzle puzzle = (Puzzle) intent.getSerializableExtra("puzzle");

        if (puzzle != null) {
            switch (puzzle.getDifficultyLevel())
            {
                case Under8:
                    rowsCount = 3;
                    colsCount = 4;
                    break;
                case Under14:
                    rowsCount = 5;
                    colsCount = 7;
                    break;
                case Over14:
                    rowsCount = 8;
                    colsCount = 10;
                    break;
            }
            String newLabel = "";
            String[] labelWords = puzzle.getLabel().split(" ");
            for(int i = 0 ; i < labelWords.length ; i++)
            {
                newLabel += labelWords[i];
                if(i == labelWords.length - 1) break;
                if(i != labelWords.length / 2)
                {
                    newLabel += " ";
                }
                else newLabel += "\n";
            }
            label.setText(newLabel);
            author.setText(puzzle.getAuthor());
        }

        // запускаем код, связанный с изображением, после того, как view было создано
        // для расчета всех размеров
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (puzzle != null) {
                    int targetW = imageView.getWidth();
                    int targetH = imageView.getHeight();
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;
                    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), puzzle.getPaintingID(), bmOptions);
                    imageView.setImageBitmap(bitmap);
                }
                pieces = splitImage(rowsCount, colsCount);
                TouchListener touchListener = new TouchListener(PuzzleActivity.this);
                // пазлы в случайном порядке
                Collections.shuffle(pieces);
                for (PuzzlePiece piece : pieces) {
                    piece.setOnTouchListener(touchListener);
                    layout.addView(piece);
                    // случайное положение в нижней части экрана
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
                    lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
                    lParams.topMargin = layout.getHeight() - piece.pieceHeight;
                    piece.setLayoutParams(lParams);
                }
            }
        });
    }

    public void checkGameOver() {
        if (isGameOver()) {
            DialogFragment dialog = DialogGameFinished.newInstance(
                    getString(R.string.you_solved_puzzles));
            dialog.show(getFragmentManager(), "dialog");
        }
    }

    private boolean isGameOver() {
        for (PuzzlePiece piece : pieces) {
            if (piece.canMove) {
                return false;
            }
        }

        return true;
    }

    private ArrayList<PuzzlePiece> splitImage(int rows, int cols) {
        int piecesNumber = rows * cols;

        ImageView imageView = findViewById(R.id.puzzle);
        ArrayList<PuzzlePiece> pieces = new ArrayList<>(piecesNumber);

        // Получаем Картину нужного размера
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimensions = getBitmapPositionInsideImageView(imageView);
        int scaledBitmapLeft = dimensions[0];
        int scaledBitmapTop = dimensions[1];
        int scaledBitmapWidth = dimensions[2];
        int scaledBitmapHeight = dimensions[3];

        int croppedImageWidth = scaledBitmapWidth - 2 * abs(scaledBitmapLeft);
        int croppedImageHeight = scaledBitmapHeight - 2 * abs(scaledBitmapTop);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(scaledBitmapLeft), abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight);

        // Измеряем ширину и высоту пазлов
        int pieceWidth = croppedImageWidth/cols;
        int pieceHeight = croppedImageHeight/rows;

        // Создаём картинки пазлов и добавляем в массив
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                int offsetX = 0;
                int offsetY = 0;
                if (col > 0) {
                    offsetX = pieceWidth / 3;
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3;
                }

                // применяем смещение для каждого пазла
                Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                PuzzlePiece piece = new PuzzlePiece(getApplicationContext());
                piece.setImageBitmap(pieceBitmap);
                piece.xCoord = xCoord - offsetX + imageView.getLeft();
                piece.yCoord = yCoord - offsetY + imageView.getTop();
                piece.pieceWidth = pieceWidth + offsetX;
                piece.pieceHeight = pieceHeight + offsetY;

                // получаем финальное изображение пазлика
                Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);

                // рисуем стыковки с другими пазлами
                int bumpSize = pieceHeight / 4;
                Canvas canvas = new Canvas(puzzlePiece);
                Path path = new Path();
                path.moveTo(offsetX, offsetY);
                if (row == 0) {
                    // верхние пазлы
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                } else {
                    // верхняя стыковка
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3, offsetY);
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, offsetY);
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                }

                if (col == cols - 1) {
                    // правые пазлы
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                } else {
                    // правая стыковка
                    path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.cubicTo(pieceBitmap.getWidth() - bumpSize,offsetY + (pieceBitmap.getHeight() - offsetY) / 6, pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                }

                if (row == rows - 1) {
                    // нижние пазлы
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                } else {
                    // нижняя стыковка
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, pieceBitmap.getHeight());
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5,pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3, pieceBitmap.getHeight());
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                }

                if (col == 0) {
                    // левые пазлы
                    path.close();
                } else {
                    // левая стыковка
                    path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.cubicTo(offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.close();
                }

                // маска для пазла
                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                // рисуем белую границу
                Paint border = new Paint();
                border.setColor(0X80FFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(8.0f);
                canvas.drawPath(path, border);

                // рисуем чёрную границу
                border = new Paint();
                border.setColor(0X80000000);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // устанавливаем полученное изображение на пазл
                piece.setImageBitmap(puzzlePiece);

                pieces.add(piece);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Получаем размер картины
        // Получаем значения матрицы картины и помещаем их в массив
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Извлекаем значения масштаба, используя константы (если соотношение сторон сохраняется, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Получаем картину (можно также получить растровое изображение за рисунком и getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Рассчитываем фактические размеры
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Получаем позицию картины
        // Предполагаем, что изображение центрировано в ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH)/2;
        int left = (int) (imgViewW - actW)/2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }
}