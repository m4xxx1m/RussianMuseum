package ru.raptors.russian_museum.puzzles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.adapters.PuzzleAdapter;

public class PuzzlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        ArrayList<Puzzle> puzzles14 = new ArrayList<Puzzle>();
        puzzles14.add(new Puzzle("Девятый вал", "Иван Айвазовский", R.drawable.puzzle_14_2));
        puzzles14.add(new Puzzle("Лунная ночь на Днепре", "Архип Куинджи", R.drawable.puzzle_14_1));
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.puzzles_14);
        recyclerView.setLayoutManager(layoutManager);
        PuzzleAdapter puzzleAdapter = new PuzzleAdapter(this, puzzles14);
        recyclerView.setAdapter(puzzleAdapter);
        puzzleAdapter.notifyDataSetChanged();
    }
}