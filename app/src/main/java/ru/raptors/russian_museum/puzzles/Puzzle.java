package ru.raptors.russian_museum.puzzles;

public class Puzzle {
    private String label;
    private String author;
    private int paintingID;
    private boolean completed;


    public Puzzle(String label, String author, int paintingID) {
        this.label = label;
        this.author = author;
        this.paintingID = paintingID;
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
}
