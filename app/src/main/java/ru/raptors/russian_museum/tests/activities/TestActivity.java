package ru.raptors.russian_museum.tests.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.ArrayList;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.tests.Test;
import ru.raptors.russian_museum.tests.TestsData;

public class TestActivity extends AppCompatActivity {

    private Test test;
    private TextView questionNumber;
    private LinearProgressIndicator progressIndicator;
    private TextView question;
    private int currentQuestionNum = -1;
    private int currentAnswer = -1;
    private MaterialButton nextButton;
    private ArrayList<MaterialCardView> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //test = Test.getInstance();
        test = TestsData.getInstance().getCurrentTest();
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        ((TextView) findViewById(R.id.textView)).setText(test.getName());
        questionNumber = findViewById(R.id.question_number);
        progressIndicator = findViewById(R.id.progressBar);
        question = findViewById(R.id.question);
        nextButton = findViewById(R.id.next_button);
        setProgress();
        setQuestionName();
        placeOptions();
        setNextButton();
    }

    private void setProgress() {
        currentQuestionNum++;
        questionNumber.setText("Вопрос " + (currentQuestionNum+1) + "/" + test.getQuestionsCount());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressIndicator.setProgress((int) 100.0f * (currentQuestionNum + 1) /
                    test.getQuestionsCount(), true);
        }
        else {
            progressIndicator.setProgress((int) 100.0f * (currentQuestionNum + 1) /
                    test.getQuestionsCount());
        }
    }

    private void setQuestionName() {
        question.setText(test.getQuestion(currentQuestionNum).getName());
    }

    private void placeOptions() {
        LinearLayout placeHolder = findViewById(R.id.place_holder);
        placeHolder.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        cards = new ArrayList<>(test.getQuestion(currentQuestionNum).getOptionsCount());
        for (int i = 0; i < test.getQuestion(currentQuestionNum).getOptionsCount(); ++i) {
            MaterialCardView option = (MaterialCardView) inflater.inflate(R.layout.test_answer_option,
                    placeHolder, false);
            cards.add(option);
            ((TextView) option.findViewById(R.id.text_option)).setText(test.getQuestion(
                    currentQuestionNum).getAnswerOption(i));
            option.setBackgroundResource(0);
            final int finalI = i;
            option.setOnClickListener(v -> {
                if (currentAnswer != -1)
                    return;
                currentAnswer = finalI;
                if (test.getQuestion(currentQuestionNum).getCategory(finalI) > 0) {
                    option.setBackgroundResource(R.drawable.card_right);
                }
                else {
                    vibrate();
                    option.setBackgroundResource(R.drawable.card_wrong);
                    int index = test.getQuestion(currentQuestionNum).getRightAnswerIndex();
                    cards.get(index).setBackgroundResource(R.drawable.card_right);
                }
                nextButton.setVisibility(View.VISIBLE);
            });
            placeHolder.addView(option);
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.
                    DEFAULT_AMPLITUDE));
        }
        else {
            vibrator.vibrate(250);
        }
    }

    private void setNextButton() {
        nextButton.setOnClickListener(v -> {
            nextButton.setVisibility(View.INVISIBLE);
            test.addPoints(currentQuestionNum, currentAnswer);
            currentAnswer = -1;
            if (currentQuestionNum + 1 == test.getQuestionsCount()) {
                test.finish();
                finish();
                return;
            }
            setProgress();
            setQuestionName();
            placeOptions();
        });
    }
}