package ru.raptors.russian_museum.tests.adapters;

import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.tests.Test;
import ru.raptors.russian_museum.tests.TestsData;
import ru.raptors.russian_museum.tests.fragments.TestSelectionFragment;

public class TestSelectionAdapter extends FragmentStateAdapter {

    // private final ArrayList<Test> tests;
    private final TestsData testsData = TestsData.getInstance();

    private final int[] picturesRes = new int[] { R.array.tests_results_pictures_0 };

    public TestSelectionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        int count = fragmentActivity.getResources().getInteger(R.integer.tests_amount);
        //tests = new ArrayList<>(count);
        if (testsData.isInitialized())
            return;
        testsData.initializeTests(count);
        String[] infoArray = fragmentActivity.getResources().getStringArray(R.array.tests_info);
        String[] questionsArray = fragmentActivity.getResources().getStringArray(R.array
                .tests_questions);
        String[] resultsArray = fragmentActivity.getResources().getStringArray(R.array.tests_results);
        for (int i = 0; i < count; ++i) {
            //tests.add(new Test(infoArray[i], questionsArray[i], resultsArray[i]));
            testsData.addTest(new Test(infoArray[i], questionsArray[i], resultsArray[i],
                    fragmentActivity.getResources().obtainTypedArray(picturesRes[i])));
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TestSelectionFragment(testsData.getTest(position), position);
        //return new TestSelectionFragment(tests.get(position));
    }

    @Override
    public int getItemCount() {
        //return tests.size();
        return testsData.getSize();
    }
}
