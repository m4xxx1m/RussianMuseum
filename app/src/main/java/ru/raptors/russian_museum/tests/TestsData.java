package ru.raptors.russian_museum.tests;

import java.util.ArrayList;

public class TestsData {

    private static TestsData instance;

    public static TestsData getInstance() {
        if (instance == null) {
            instance = new TestsData();
        }
        return instance;
    }


    private ArrayList<Test> tests;
    private int currentTest = -1;

    public Test getCurrentTest() {
        return tests.get(currentTest);
    }

    public void setCurrentTest(int index) {
        currentTest = index;
    }

    public Test getTest(int index) {
        return tests.get(index);
    }

    public void addTest(Test test) {
        tests.add(test);
    }

    public int getSize() {
        return tests.size();
    }

    public void initializeTests(int capacity) {
        tests = new ArrayList<>(capacity);
    }

    public boolean isInitialized() {
        return tests != null;
    }
}
