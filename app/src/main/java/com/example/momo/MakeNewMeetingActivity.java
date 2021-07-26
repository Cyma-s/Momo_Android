package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class MakeNewMeetingActivity extends AppCompatActivity {
    NumberPicker numberPicker;
    ArrayList<String> meetingTime = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_meeting);

        for(int i = 1; i<24; i++) {
            meetingTime.add(i + "시간");
        }

        String[] timeArr = meetingTime.toArray(new String[0]);

        numberPicker = findViewById(R.id.datePicker);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(
                ViewGroup.FOCUS_BLOCK_DESCENDANTS
        );
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(23);
        numberPicker.setDisplayedValues(timeArr);
    }
}