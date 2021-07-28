package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_date);

        initViews();

        calendarView.setSelectionManager(new MultipleSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                selectDates();
            }
        }));
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.makeMeetingCalender);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.MULTIPLE);
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
}