package ru.raptors.russian_museum.puzzles.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import ru.raptors.russian_museum.puzzles.activities.PuzzlesActivity;
import ru.raptors.russian_museum.puzzles.activities.PuzzleActivity;
import ru.raptors.russian_museum.puzzles.Puzzle;

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
        switch (puzzle.getDifficultyLevel())
        {
            case Over14:
                holder.gradient.setBackground(res.getDrawable(R.drawable.age_14_gradient));
                break;
            case Under14:
            holder.gradient.setBackground(res.getDrawable(R.drawable.age_8_13_gradient_2));
            break;
            case Under8:
                holder.gradient.setBackground(res.getDrawable(R.drawable.age_6_8_gradient_2));
                break;
        }
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
        final View gradient;
        ViewHolder(View view){
            super(view);
            this.view = view;
            puzzleImage = view.findViewById(R.id.puzzleImage);
            puzzleLabel = view.findViewById(R.id.puzzleLabel);
            puzzleAuthor = view.findViewById(R.id.puzzleAuthor);
            puzzleLayout = view.findViewById(R.id.puzzleLayout);
            backButton = view.findViewById(R.id.back_button);
            gradient = view.findViewById(R.id.gradient);
        }
    }
}