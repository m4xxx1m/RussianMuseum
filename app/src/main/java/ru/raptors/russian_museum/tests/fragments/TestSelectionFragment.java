package ru.raptors.russian_museum.tests.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.tests.Test;
import ru.raptors.russian_museum.tests.TestsData;
import ru.raptors.russian_museum.tests.activities.TestActivity;
import ru.raptors.russian_museum.tests.activities.TestSelectionActivity;

public class TestSelectionFragment extends Fragment {
    private Test test;
    private int testIndex = -1;
    private ConstraintLayout card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                getLayoutResource(), container, false);
        rootView.findViewById(R.id.button_enter_test).setOnClickListener(v -> enterTest());
        rootView.findViewById(R.id.card_test).setOnClickListener(v -> enterTest());
        card = rootView.findViewById(R.id.card_test);
        ((TextView)rootView.findViewById(R.id.test_name)).setText(test.getName());
        if (test.isSolved()) {
            ((TextView)rootView.findViewById(R.id.result_text)).setText(test.getResult());
            setPicture();
        }
        return rootView;
    }

    private void setPicture() {
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), test.getResultIndex());
        card.setBackgroundResource(test.getResultRes());
    }


    private int getLayoutResource() {
        if (test.isSolved()) {
            return R.layout.fragment_test_selection_solved;
        }
        else {
            return R.layout.fragment_test_selection;
        }
    }

    private void enterTest() {
        Intent intent = new Intent(getActivity(), TestActivity.class);
        // test.setInstance();
        TestsData.getInstance().setCurrentTest(testIndex);
        startActivity(intent);
    }

    public TestSelectionFragment() { }

    public TestSelectionFragment(Test test, int index) {
        this.test = test;
        testIndex = index;
    }
}
