package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;

public class AbleTimeInput extends AppCompatActivity {
    // 되는 날짜 Activity로 변경 요망
    private CalendarView calendarView;
    private NumberPicker start, end;
    ArrayList<String> ableTimeStart = new ArrayList<>();
    ArrayList<String> ableTimeEnd = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_able_time_input);

        Button nextButton = findViewById(R.id.unabletimenext);

        initViews();
        setNumberPicker();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다음으로 넘어가는 버튼 (원래는 Home으로 넘어가야 하는데 디버깅 이유로 MeetingInfo로 두었음)
                Intent intent = new Intent(AbleTimeInput.this, MeetingInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        // view 세팅
        calendarView = (CalendarView) findViewById(R.id.makeMeetingCalender);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.SINGLE);
        start = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
    }

    private void setNumberPicker() {
        // numberpicker 세팅
        for(int i = 0; i<24; i++) {
            ableTimeStart.add(i + "시");
            ableTimeEnd.add(i + "시");
        }
        String[] startArr = ableTimeStart.toArray(new String[0]);
        String[] endArr = ableTimeEnd.toArray(new String[0]);
        start.setDisplayedValues(startArr);
        end.setDisplayedValues(endArr);

        start.setWrapSelectorWheel(false);
        start.setDescendantFocusability(
                ViewGroup.FOCUS_BLOCK_DESCENDANTS
        );
        start.setMinValue(0);
        start.setMaxValue(23);
        end.setWrapSelectorWheel(false);
        end.setDescendantFocusability(
                ViewGroup.FOCUS_BLOCK_DESCENDANTS
        );
        end.setMinValue(0);
        end.setMaxValue(23);
    }
}