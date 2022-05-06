package ru.raptors.russian_museum.puzzles;

import java.io.Serializable;

import ru.raptors.russian_museum.DifficultyLevel;

public class Puzzle implements Serializable {
    private String label;
    private String author;
    private int paintingID;
    private boolean completed;
    private DifficultyLevel difficultyLevel;


    public Puzzle(String label, String author, int paintingID, DifficultyLevel difficultyLevel) {
        this.label = label;
        this.author = author;
        this.paintingID = paintingID;
        this.difficultyLevel = difficultyLevel;
        completed = false;
    }

    public String getLabel() {
        return label;
    }

    public String getAuthor() {
        return author;
    }

    public int getPaintingID() {
        return paintingID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }
}
