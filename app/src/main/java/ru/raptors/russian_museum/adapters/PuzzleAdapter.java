package ru.raptors.russian_museum.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.activities.PuzzlesActivity;
import ru.raptors.russian_museum.puzzles.Puzzle;
import ru.raptors.russian_museum.puzzles.PuzzleActivity;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Puzzle> puzzles;

    public PuzzleAdapter(Context context, List<Puzzle> puzzles) {
        this.context = context;
        this.puzzles = puzzles;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PuzzleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.puzzle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PuzzleAdapter.ViewHolder holder, int position) {
        Puzzle puzzle = puzzles.get(position);
        Resources res = context.getResources();
        holder.puzzleLabel.setText(puzzle.getLabel());
        holder.puzzleAuthor.setText(puzzle.getAuthor());
        holder.puzzleImage.setImageDrawable(ResourcesCompat.getDrawable(res, puzzle.getPaintingID(), null));
        holder.puzzleLayout.setOnClickListener((par) ->
        {
            Intent intent = new Intent(context, PuzzleActivity.class);
            intent.putExtra("puzzle", puzzle);
            context.startActivity(intent);
            ((PuzzlesActivity) context).finish();
        });
    }


    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView puzzleImage;
        final TextView puzzleLabel;
        final TextView puzzleAuthor;
        final FrameLayout puzzleLayout;
        final ImageButton backButton;
        final View view;
        ViewHolder(View view){
            super(view);
            this.view = view;
            puzzleImage = view.findViewById(R.id.puzzleImage);
            puzzleLabel = view.findViewById(R.id.puzzleLabel);
            puzzleAuthor = view.findViewById(R.id.puzzleAuthor);
            puzzleLayout = view.findViewById(R.id.puzzleLayout);
            backButton = view.findViewById(R.id.back_button);
        }
    }
}