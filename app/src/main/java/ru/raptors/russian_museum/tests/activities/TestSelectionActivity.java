package ru.raptors.russian_museum.tests.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.tests.adapters.TestSelectionAdapter;

public class TestSelectionActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TestSelectionAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_test_selection);

        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        String[] ageItems = new String[] { getString(R.string.age_6_8), getString(R.string.age_8_13),
                getString(R.string.age_14_and_more) };
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                R.layout.test_age_selection_item, ageItems);
        ((AutoCompleteTextView)findViewById(R.id.autoComplete)).setAdapter(ageAdapter);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new TestSelectionAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        ((RecyclerView)viewPager.getChildAt(0)).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {

        }).attach();
    }
}