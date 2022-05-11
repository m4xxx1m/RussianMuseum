package ru.raptors.russian_museum.tests;

import android.content.res.TypedArray;
import android.graphics.Bitmap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import ru.raptors.russian_museum.DifficultyLevel;

public class Test {

    /*private static Test currentTest;
    public static Test getInstance() {
        return currentTest;
    }
    public void setInstance() {
        currentTest = this;
    }
    public void removeInstance() {
        currentTest = null;
    }*/

    private String name;
    private Bitmap startImage;
    private int categories;
    private boolean isMegaTest;
    private DifficultyLevel minDifficultyLevel;
    private DifficultyLevel maxDifficultyLevel;
    private ArrayList<Question> questions;
    private ArrayList<TestResult> results;
    private boolean isTestSolved = false;
    private int resultIndex = -1;
    private ArrayList<Integer> points;

    public Test(String info_, String questions_, String results_, TypedArray picturesRes) {
        parseInfo(info_);
        parseQuestions(questions_);
        parseResults(results_, picturesRes);
        points = new ArrayList<>(Math.max(1, categories));
        for (int i = 0; i < Math.max(1, categories); ++i) {
            points.add(0);
        }
    }

    public void finish() {
        if (isMegaTest) {
            int max = 0;
            int maxInd = 0;
            for (int i = 0; i < points.size(); ++i) {
                if (points.get(i) > max) {
                    max = points.get(i);
                    maxInd = i;
                }
                points.set(i, 0);
            }
            resultIndex = maxInd;
        }
        else {
            for (int i = 0; i < results.size(); ++i) {
                int point = ((KnowledgeTestResult)results.get(i)).points;
                if (points.get(0) >= point) {
                    resultIndex = i;
                    break;
                }
            }
            points.set(0, 0);
        }
        isTestSolved = true;
    }

    private void parseInfo(String info) {
        Scanner scanner = new Scanner(info);
        categories = scanner.nextInt();
        isMegaTest = categories > 0;
        minDifficultyLevel = DifficultyLevel.getInstance(scanner.nextInt());
        maxDifficultyLevel = DifficultyLevel.getInstance(scanner.nextInt());
        name = scanner.nextLine().substring(1);
    }

    private void parseQuestions(String questions_) {
        String[] data = questions_.split("\\|");
        int amountOfQuestions = Integer.parseInt(data[0]);
        questions = new ArrayList<>(amountOfQuestions);

        int k = 1;
        for (int i = 0; i < amountOfQuestions; ++i) {
            int ansCount = Integer.parseInt(data[k+1]);
            questions.add(new Question(data[k], ansCount));
            k += 2;
            for (int j = 0; j < ansCount; ++j) {
                questions.get(i).addAnswerOption(Integer.parseInt(data[k]), data[k+1]);
                k += 2;
            }
        }
    }

    private void parseResults(String results_, TypedArray picRes) {
        String[] data = results_.split("\\|");
        results = new ArrayList<>(data.length);
        for (int k = 0, i = 0; k < data.length; k+=2, i++) {
            int res = picRes.getResourceId(i, -1);
            if (isMegaTest) {
                results.add(new MegaTestResult(data[k], Integer.parseInt(data[k+1]), res));
            }
            else {
                results.add(new KnowledgeTestResult(data[k], Integer.parseInt(data[k+1]), res));
            }
        }
    }

    public int getResultRes() {
        return results.get(resultIndex).pictureRes;
    }

    public String getResult() {
        return results.get(resultIndex).text;
    }

    public boolean isSolved() {
        return isTestSolved;
    }

    public String getName() {
        return name;
    }

    public int getQuestionsCount() {
        return questions.size();
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public void addPoints(int question, int answer) {
        int index = questions.get(question).getCategory(answer) - 1;
        if (index >= 0) {
            points.set(index, points.get(index) + 1);
        }
    }
}
