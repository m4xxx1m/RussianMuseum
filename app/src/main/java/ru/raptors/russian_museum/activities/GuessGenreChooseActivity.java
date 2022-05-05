package ru.raptors.russian_museum.activities;

import androidx.appcompat.app.AppCompatActivity;
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

        });
        findViewById(R.id.card_8_13).setOnClickListener(view -> {

        });
        findViewById(R.id.card_6_8).setOnClickListener(view -> {

        });
    }
}