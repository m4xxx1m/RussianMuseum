package ru.raptors.russian_museum.tests;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<String> answerOptions;
    private ArrayList<Integer> pointsCategory;

    public Question(String name, int amountOfAnswers) {
        question = name;
        answerOptions = new ArrayList<>(amountOfAnswers);
        pointsCategory = new ArrayList<>(amountOfAnswers);
    }

    public void addAnswerOption(int category, String answer) {
        answerOptions.add(answer);
        pointsCategory.add(category);
    }

    public String getName() {
        return question;
    }

    public int getOptionsCount() {
        return answerOptions.size();
    }

    public String getAnswerOption(int index) {
        return answerOptions.get(index);
    }

    public int getCategory(int index) {
        return pointsCategory.get(index);
    }

    public int getRightAnswerIndex() {
        if (pointsCategory.contains(0)) {
            return pointsCategory.indexOf(1);
        }
        else return -1;
    }
}
