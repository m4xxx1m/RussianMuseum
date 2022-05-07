package ru.raptors.russian_museum.guess_genre.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ru.raptors.russian_museum.R;

public class GuessGenreChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_guess_genre);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        findViewById(R.id.card_14).setOnClickListener(view -> {
            navigate(2, view);
        });
        findViewById(R.id.card_8_13).setOnClickListener(view -> {
            navigate(1, view);
        });
        findViewById(R.id.card_6_8).setOnClickListener(view -> {
            navigate(0, view);
        });
    }

    private void navigate(int difficulty, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("difficultyLevel", difficulty);
//        Navigation.findNavController(view).navigate(R.id.action_navigation_guess_genre, bundle);
        Intent intent = new Intent(GuessGenreChooseActivity.this, GuessGenreActivity.class);
        intent.putExtra("difficultyLevel", difficulty);
        startActivity(intent);
    }
}