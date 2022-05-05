package ru.raptors.russian_museum.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.puzzles.Puzzle;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Puzzle> puzzles;

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
        holder.puzzleImage.setImageDrawable(ResourcesCompat.getDrawable(res, puzzle.getPaintingID(),
                null));
    }

    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView puzzleImage;
        final TextView puzzleLabel;
        final TextView puzzleAuthor;
        final ImageButton backButton;
        final View view;
        ViewHolder(View view){
            super(view);
            this.view = view;
            puzzleImage = view.findViewById(R.id.puzzleImage);
            puzzleLabel = view.findViewById(R.id.puzzleLabel);
            puzzleAuthor = view.findViewById(R.id.puzzleAuthor);
            backButton = view.findViewById(R.id.back_button);
        }
    }
}
