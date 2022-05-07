package ru.raptors.russian_museum.guess_genre.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import ru.raptors.russian_museum.DifficultyLevel;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.fragments.DialogGameFinished;
import ru.raptors.russian_museum.guess_genre.Genre;

public class GuessGenreActivity extends AppCompatActivity {

    private DifficultyLevel difficultyLevel = null;
    private int taskNum = -1;
    private ArrayList<MaterialCardView> cards;
    private ArrayList<Genre> genres;
    private int rightAnswerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_genre);
        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        if (difficultyLevel == null) {
            difficultyLevel = DifficultyLevel.getInstance(getIntent().getIntExtra(
                    "difficultyLevel", -1));
        }
        if (taskNum < 0) {
            taskNum = getTask();
        }
        Drawable drawable = getDrawable();
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageDrawable(drawable);
        cards = new ArrayList<>(getAmountOfTasks());
        if (genres == null) {
            genres = new ArrayList<>(getAmountOfTasks());
            generateGenresArray();
        }
        placeOptions();
    }

    private void restoreData(Bundle savedInstanceState) {
        taskNum = savedInstanceState.getInt("taskNum", -1);
        difficultyLevel = DifficultyLevel.getInstance(savedInstanceState.getInt(
                "difficultyLevel", -1));
        genres = new ArrayList<>(getAmountOfTasks());
        fromIntToGenres(savedInstanceState.getIntegerArrayList("genres"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("taskNum", taskNum);
        outState.putInt("difficultyLevel", difficultyLevel.getValue());
        outState.putIntegerArrayList("genres", fromGenresToInt());
        super.onSaveInstanceState(outState);
    }

    private int getTask() {
        int[] resources = new int[] { R.integer.total_6_8, R.integer.total_8_13, R.integer.total_14 };
        int totalTask = getResources().getInteger(resources[difficultyLevel.getValue()]);
        return new Random().nextInt(totalTask);
    }

    private Drawable getDrawable() {
        int[] ageRes = new int[] { R.array.pictures_6_8, R.array.pictures_8_13,
                R.array.pictures_14, };
        TypedArray typedArray = getResources().obtainTypedArray(ageRes[difficultyLevel.getValue()]);
        int res = typedArray.getResourceId(taskNum, -1);
//        int res = getResources().getIntArray(ageRes[difficultyLevel.getValue()])[taskNum];
        typedArray.recycle();
        return AppCompatResources.getDrawable(this, res);
    }

    private ArrayList<Integer> fromGenresToInt() {
        ArrayList<Integer> genresInt = new ArrayList<>(getAmountOfTasks());
        for (Genre genre : genres) {
            genresInt.add(genre.getValue());
        }
        return genresInt;
    }

    private void fromIntToGenres(ArrayList<Integer> genresInt) {
        for (int genre : genresInt) {
            genres.add(Genre.getInstance(genre));
        }
    }

    private void generateGenresArray() {
        int rightAnswer = getRightAnswer();
        int amount = getAmountOfTasks();
        ArrayList<Integer> genresInt = new ArrayList<>(amount);
        genresInt.add(rightAnswer);
        Random random = new Random();
        while (genresInt.size() < amount) {
            int randomValue = random.nextInt(Genre.getTotal());
            if (!genresInt.contains(randomValue))
                genresInt.add(randomValue);
        }
        Collections.shuffle(genresInt);
        fromIntToGenres(genresInt);
        rightAnswerIndex = genresInt.indexOf(rightAnswer);
    }

    private void placeOptions() {
        LinearLayout placeHolder = findViewById(R.id.place_holder);
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < getAmountOfTasks(); ++i) {
            cards.add((MaterialCardView) layoutInflater.inflate(R.layout.guess_genre_option,
                    placeHolder,false));
            ((TextView)cards.get(i).findViewById(R.id.text_option)).setText((i+1) + ". " +
                    genres.get(i).getString(getResources()));
            int finalI = i;
            cards.get(i).setOnClickListener(v -> {
                if (finalI == rightAnswerIndex) {
                    v.setBackgroundResource(R.drawable.card_right);
                    showDialog();
                }
                else {
                    v.setBackgroundResource(R.drawable.card_wrong);
                    vibrate();
                }
            });
            /*if (difficultyLevel == DifficultyLevel.Over14 && getResources().getConfiguration()
                    .orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ViewGroup.MarginLayoutParams cardParams = (ViewGroup.MarginLayoutParams) cards.get(i)
                        .findViewById(R.id.card).getLayoutParams();
                cardParams.bottomMargin = dpToPx(5);
                cards.get(i).findViewById(R.id.card).setLayoutParams(cardParams);
                ViewGroup.LayoutParams infoParams = cards.get(i).findViewById(R.id.info_button)
                        .getLayoutParams();
                infoParams.height = dpToPx(34);
                infoParams.width = dpToPx(34);
                cards.get(i).findViewById(R.id.info_button).setLayoutParams(infoParams);
            }*/
            placeHolder.addView(cards.get(i));
        }
    }

    public void showDialog() {
        DialogFragment dialog = DialogGameFinished.newInstance(getString(R.string.you_guessed_genre));
        dialog.show(getFragmentManager(), "dialog");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.
                    DEFAULT_AMPLITUDE));
        }
        else {
            vibrator.vibrate(250);
        }
    }

    private int getRightAnswer() {
        int[] res = new int[] { R.array.right_answers_6_8, R.array.right_answers_8_13,
                R.array.right_answers_14 };
        return getResources().getIntArray(res[difficultyLevel.getValue()])[taskNum];
    }

    private int getAmountOfTasks() {
        switch (difficultyLevel) {
            case Under8:
                return 2;
            case Under14:
                return 4;
            default:
                return 6;
        }
    }

    /*public int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }*/
}