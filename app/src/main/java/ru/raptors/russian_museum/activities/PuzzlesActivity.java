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
        ArrayList<Puzzle> puzzles14 = new ArrayList<Puzzle>();
        puzzles14.add(new Puzzle("Девятый вал", "Иван Айвазовский", R.drawable.puzzle_14_2, DifficultyLevel.Over14));
        puzzles14.add(new Puzzle("Лунная ночь на Днепре", "Архип Куинджи", R.drawable.puzzle_14_1, DifficultyLevel.Over14));
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.puzzles_14);
        recyclerView.setLayoutManager(layoutManager);
        PuzzleAdapter puzzleAdapter = new PuzzleAdapter(this, puzzles14);
        recyclerView.setAdapter(puzzleAdapter);

        puzzleAdapter.notifyDataSetChanged();
    }
}