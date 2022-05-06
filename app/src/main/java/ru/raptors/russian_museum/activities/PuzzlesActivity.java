package ru.raptors.russian_museum.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.adapters.PuzzleAdapter;
import ru.raptors.russian_museum.puzzles.DifficultyLevel;
import ru.raptors.russian_museum.puzzles.Puzzle;

public class PuzzlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        RecyclerView puzzlesOver14RecyclerView = (RecyclerView) findViewById(R.id.puzzles_14);
        RecyclerView puzzlesUnder14RecyclerView = (RecyclerView) findViewById(R.id.puzzles_under_14);
        RecyclerView puzzlesUnder8RecyclerView = (RecyclerView) findViewById(R.id.puzzles_under_8);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);

        ArrayList<Puzzle> puzzles14 = new ArrayList<Puzzle>();
        puzzles14.add(new Puzzle("Девятый вал", "Иван Айвазовский", R.drawable.puzzle_14_2, DifficultyLevel.Over14));
        puzzles14.add(new Puzzle("Лунная ночь на Днепре", "Архип Куинджи", R.drawable.puzzle_14_1, DifficultyLevel.Over14));
        puzzlesOver14RecyclerView.setLayoutManager(layoutManager);
        PuzzleAdapter puzzleAdapter = new PuzzleAdapter(this, puzzles14);
        puzzlesOver14RecyclerView.setAdapter(puzzleAdapter);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        ArrayList<Puzzle> puzzlesUnder14 = new ArrayList<Puzzle>();
        puzzlesUnder14.add(new Puzzle("Атлеты", "Казимир Малевич", R.drawable.puzzle_under_14_1, DifficultyLevel.Under14));
        puzzlesUnder14.add(new Puzzle("Красная конница", "Казимир Малевич", R.drawable.puzzle_under_14_2, DifficultyLevel.Under14));
        puzzlesUnder14.add(new Puzzle("Композиция VIII", "Василий Кандинский", R.drawable.puzzle_under_14_3, DifficultyLevel.Under14));
        puzzlesUnder14RecyclerView.setLayoutManager(layoutManager);
        PuzzleAdapter puzzleUnder14Adapter = new PuzzleAdapter(this, puzzlesUnder14);
        puzzlesUnder14RecyclerView.setAdapter(puzzleUnder14Adapter);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        ArrayList<Puzzle> puzzlesUnder8 = new ArrayList<Puzzle>();
        puzzlesUnder8.add(new Puzzle("Три радости", "Николай Рерих", R.drawable.puzzle_under_8_1, DifficultyLevel.Under8));
        puzzlesUnder8.add(new Puzzle("Заморские гости", "Николай Рерих", R.drawable.puzzle_under_8_2, DifficultyLevel.Under8));
        puzzlesUnder8RecyclerView.setLayoutManager(layoutManager);
        PuzzleAdapter puzzleUnder8Adapter = new PuzzleAdapter(this, puzzlesUnder8);
        puzzlesUnder8RecyclerView.setAdapter(puzzleUnder8Adapter);

        puzzleAdapter.notifyDataSetChanged();
    }
}