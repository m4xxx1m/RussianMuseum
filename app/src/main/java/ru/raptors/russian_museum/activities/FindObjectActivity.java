package ru.raptors.russian_museum.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Objects;
import java.util.Random;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.find_object.FindObjectPicture;
import ru.raptors.russian_museum.find_object.FindObjectView;

public class FindObjectActivity extends AppCompatActivity {
    private int taskNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        if (savedInstanceState != null) {
            taskNum = savedInstanceState.getInt("taskNum", -1);
        }
        if (taskNum < 0)
            taskNum = getTask();
        setContentView(R.layout.activity_find_object);
        String inputData = getResources().getStringArray(R.array.find_object_values)[taskNum];
        FindObjectPicture findObjectData = new FindObjectPicture(inputData);
        ((TextView) findViewById(R.id.title)).setText(findObjectData.title);
        FindObjectView fov = new FindObjectView(this, findObjectData, taskNum);
        ((LinearLayout) findViewById(R.id.place_holder)).addView(fov);
        findViewById(R.id.cross).setOnClickListener(v -> finish());
    }

    private int getTask() {
        int totalTask = getResources().getInteger(R.integer.find_object_total);
        return new Random().nextInt(totalTask);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("taskNum", taskNum);
        super.onSaveInstanceState(outState);
    }
}
