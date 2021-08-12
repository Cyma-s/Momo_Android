package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MakeNewMeetingActivity extends AppCompatActivity {
    NumberPicker numberPicker;
    ArrayList<String> meetingTime = new ArrayList<>();
    int prevRadio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_meeting);


        Button nextButton;


        nextButton = findViewById(R.id.newMeetingNext);

        nextButton.setOnClickListener(new View.OnClickListener() { // 다음 버튼 클릭 시 추천 여부 activity로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeNewMeetingActivity.this, RecommendOrNot.class);
                startActivity(intent);
            }
        });

        setNumberPicker();
    }

    void setNumberPicker() {
        // numberPicker 초기화
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