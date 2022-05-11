package ru.raptors.russian_museum.tests;

public class KnowledgeTestResult extends TestResult {
    int points;

    public KnowledgeTestResult(String text, int points, int res) {
        this.text = text;
        this.points = points;
        pictureRes = res;
    }
}
