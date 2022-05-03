package ru.raptors.russian_museum.find_object;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

import ru.raptors.russian_museum.R;

public class FindObjectView extends View {
    private final Paint paint = new Paint();
    private int displayWidth;
    private int displayHeight;
    private Bitmap picture;
    private FindObjectPicture findObjectData;

    private final float placeForText = 0.0f;
    private final float borders = 0.0f;

    private int maxWidth;
    private int maxHeight;
    private int finalWidth;
    private int finalHeight;
    private Point startPoint;

    private long lastTouchTime = -1;
    private final int timeBetweenTouches = 2000;

    public FindObjectView(Context context) {
        super(context);
    }

    public FindObjectView(Context context, FindObjectPicture findObjectData, int taskNum) {
        this(context);
        // int resource = getResources().getIntArray(R.array.find_object_pictures)[taskNum];
        TypedArray typedArray = getResources().obtainTypedArray(R.array.find_object_pictures);
        int resource = typedArray.getResourceId(taskNum, -1);
        picture = BitmapFactory.decodeResource(getResources(), resource);
        typedArray.recycle();
        this.findObjectData = findObjectData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        displayWidth = getWidth();
        displayHeight = getHeight();
        normalizeBitmap();
        startPoint = getStartPoint();
        canvas.drawBitmap(picture, startPoint.x, startPoint.y, paint);
        setOnTouchListener((view, motionEvent) -> {
            performClick();
            onTouch(view, motionEvent);
            return false;
        });
    }

    private void normalizeBitmap() {
        maxWidth = Math.round(displayWidth * (1.0f - 2 * borders));
        maxHeight = Math.round(displayHeight * (1.0f - 2 * borders - placeForText));
        float maxAspectRatio = (float) maxWidth / maxHeight;
        if (maxAspectRatio >= findObjectData.getAspectRatio()) {
            finalHeight = maxHeight;
            finalWidth = Math.round(finalHeight * findObjectData.getAspectRatio());
        }
        else {
            finalWidth = maxWidth;
            finalHeight = Math.round(finalWidth / findObjectData.getAspectRatio());
        }
        picture = Bitmap.createScaledBitmap(picture, finalWidth, finalHeight, false);
    }

    private Point getStartPoint() {
        Point startPoint = new Point(Math.round(displayWidth * borders), Math.round(displayHeight *
                (borders + placeForText)));
        int x = (maxWidth - finalWidth) / 2;
        int y = (maxHeight - finalHeight) / 2;
        startPoint.x += x;
        startPoint.y += y;
        return startPoint;
    }

    private void wrongAnswer() {
        vibrate(250);
        lastTouchTime = Calendar.getInstance().getTime().getTime();
    }

    private void rightAnswer() {
        vibrate(800);
    }

    private void vibrate(int milliseconds) {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.
                    DEFAULT_AMPLITUDE));
        }
        else {
            vibrator.vibrate(300);
        }
    }

    private void onTouch(View view, MotionEvent motionEvent) {
        if (lastTouchTime > 0 && Calendar.getInstance().getTime().getTime() -
                lastTouchTime < timeBetweenTouches) {
            return;
        }
        int x = Math.round(motionEvent.getX());
        int y = Math.round(motionEvent.getY());
        x -= startPoint.x;
        y -= startPoint.y;
        if (x < 0 || y < 0 || x > finalWidth || y > finalHeight)
            return;
        x = Math.round((float)(x) * findObjectData.width / finalWidth);
        y = Math.round((float)(y) * findObjectData.height / finalHeight);
        if (x < findObjectData.minX || x > findObjectData.maxX || y < findObjectData.minY ||
                y > findObjectData.maxY) {
            Log.d("Debuggg", "wrong");
            wrongAnswer();
            return;
        }
        Point a = new Point(x, y);
        Point b = new Point(findObjectData.maxX + 1, y);
        Line line = new Line(a, b);
        int interceptCount = 0;
        for (Line l : findObjectData.getLines()) {
            if (line.doesIntercept(l)) {
                interceptCount++;
            }
        }
        if (interceptCount % 2 == 0) {
            Log.d("Debuggg", "Wrong");
            wrongAnswer();
        }
        else {
            Log.d("Debuggg", "Right!");
            rightAnswer();
        }
    }
}
