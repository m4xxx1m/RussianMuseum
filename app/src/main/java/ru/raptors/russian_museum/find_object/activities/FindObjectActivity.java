package ru.raptors.russian_museum.find_object.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.find_object.FindObjectPicture;
import ru.raptors.russian_museum.find_object.FindObjectView;
import ru.raptors.russian_museum.fragments.DialogGameFinished;

public class FindObjectActivity extends AppCompatActivity {
    private int taskNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void showDialog() {
        DialogFragment dialog = DialogGameFinished.newInstance(getString(R.string.you_found_object));
        dialog.show(getFragmentManager(), "dialog");
    }

    public void wrongAnswer() {
        LinearLayout container = findViewById(R.id.place_holder);
        container.setBackgroundColor(getResources().getColor(R.color.red_wrong));
        Handler handler = new Handler();
        handler.postDelayed(() -> container.setBackgroundColor(getResources()
                .getColor(R.color.my_white_dark_grey)), 2000);
    }
}
