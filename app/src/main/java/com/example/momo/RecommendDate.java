package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RecommendDate extends AppCompatActivity{
    // 날짜 추천 전에 만날 날짜 정하기 & 대충 어느 시간대에 만날 것인지 정하기 - 주최자의 경우
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
            // calendarView의 선택 날짜가 달라질 때마다 다시 호출
            @Override
            public void onDaySelected() {
                selectDates();
            }
        }));

        nextButton.setOnClickListener(new View.OnClickListener() {
            // 안 되는 시간 표시 activity로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendDate.this, AbleTimeInput.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        // view 세팅
        calendarView = (CalendarView) findViewById(R.id.makeMeetingCalender);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.MULTIPLE);
        start = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
    }

    private void selectDates() {
        // 선택된 날짜들을 days list에 저장 (전역 변수로 선언 필요 - sp에 잠깐 저장해야 함)
        List<Calendar> days = calendarView.getSelectedDates();
        String result = "";
        for(int i = 0; i<days.size(); i++) {
            Calendar calendar = days.get(i);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH) + 1;
            final int year = calendar.get(Calendar.YEAR);
            String week = new SimpleDateFormat("EE", Locale.KOREA).format(calendar.getTime());
            result += year + "년 " + month + "월 " + day + "일 " + week + "요일 \n";
        }
        Toast.makeText(RecommendDate.this, result, Toast.LENGTH_LONG).show();
        Calendar[] temp = (Calendar[]) days.toArray(new Calendar[0]);
        ArrayList<Calendar> newDays = new ArrayList<Calendar>(Arrays.asList(temp));
        setStringArrayPref(newDays);
    }

    private void setStringArrayPref(ArrayList<Calendar> days) {
        // ArrayList를 JSONArray로 바꿔서 SharedPreferences에 저장하기
        SharedPreferences prefs = getSharedPreferences("meetingDays", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray arr = new JSONArray();
        for(int i = 0; i<days.size(); i++) {
            arr.put(days.get(i));
        }
        if(!days.isEmpty()) {
            editor.putString("meetingDays", arr.toString());
        } else {
            editor.putString("meetingDays", null);
        }
        editor.apply();
    }

    private void setNumberPicker() {
        // numberpicker 세팅
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