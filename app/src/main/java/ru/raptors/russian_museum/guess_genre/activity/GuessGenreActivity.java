package ru.raptors.russian_museum.guess_genre.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.Random;
import ru.raptors.russian_museum.DifficultyLevel;
import ru.raptors.russian_museum.R;

public class GuessGenreActivity extends AppCompatActivity {

    private DifficultyLevel difficultyLevel;
    private int taskNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_genre);
        difficultyLevel = DifficultyLevel.getInstance(getIntent().getIntExtra(
                "difficultyLevel", -1));
        if (taskNum < 0) {
            taskNum = getTask();
        }
        Drawable drawable = getDrawable();
        LinearLayout placeHolder = findViewById(R.id.place_holder);
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageDrawable(drawable);
    }

    private int getTask() {
        int[] resources = new int[] { R.integer.total_6_8, R.integer.total_8_13, R.integer.total_14 };
        int totalTask = getResources().getInteger(resources[difficultyLevel.getValue()]);
        return new Random().nextInt(totalTask);
    }

    private Drawable getDrawable() {
        int[] ageRes = new int[] { R.array.right_answers_14, R.array.right_answers_8_13,
                R.array.right_answers_6_8, };
        int res = getResources().getIntArray(ageRes[difficultyLevel.getValue()])[taskNum];
        return getDrawable(res);
    }
}