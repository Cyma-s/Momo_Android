package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RecommendDate extends AppCompatActivity{
    private CalendarView calendarView;
    ArrayList<String> meetingTimeStart = new ArrayList<>();
    ArrayList<String> meetingTimeEnd = new ArrayList<>();
    private NumberPicker start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_date);

        initViews();
        setNumberPicker();
        Button nextButton = findViewById(R.id.recommendDayNext);

        calendarView.setSelectionManager(new MultipleSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                selectDates();
            }
        }));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendDate.this, UnableTimeInput.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.makeMeetingCalender);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.MULTIPLE);
        start = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
    }

    private void selectDates() {
        List<Calendar> days = calendarView.getSelectedDates();
        String result = "";
        for(int i = 0; i<days.size(); i++) {
            Calendar calendar = days.get(i);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH);
            final int year = calendar.get(Calendar.YEAR);
            String week = new SimpleDateFormat("EE").format(calendar.getTime());
            result += year + "년 " + month + "월 " + day + "일 " + week + "요일 \n";
        }
        Toast.makeText(RecommendDate.this, result, Toast.LENGTH_LONG).show();
    }

    private void setNumberPicker() {
        for(int i = 0; i<24; i++) {
            meetingTimeStart.add(i + "시");
            meetingTimeEnd.add(i + "시");
        }
        String[] startArr = meetingTimeStart.toArray(new String[0]);
        String[] endArr = meetingTimeEnd.toArray(new String[0]);
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