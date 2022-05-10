package ru.raptors.russian_museum.tests;

public class MegaTestResult extends TestResult {
    private int category;

    public MegaTestResult(String text, int category, int res) {
        this.text = text;
        this.category = category;
        pictureRes = res;
    }
}
