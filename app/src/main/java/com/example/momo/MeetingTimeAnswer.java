package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MeetingTimeAnswer extends AppCompatActivity {
    private CalendarView calendarView;
    ArrayList<String> meetingTimeStart = new ArrayList<>();
    ArrayList<String> meetingTimeEnd = new ArrayList<>();
    private NumberPicker start, end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_time_answer);

        initViews();
        setNumberPicker();

        calendarView.setSelectionManager(new MultipleSelectionManager(new OnDaySelectedListener() {
            // calendarView의 선택 날짜가 달라질 때마다 다시 호출
            @Override
            public void onDaySelected() {
                selectDates();
            }
        }));
    }

    private void initViews() {
        // view 세팅
        calendarView = (CalendarView) findViewById(R.id.meeting_able_time_answer);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.MULTIPLE);
        start = findViewById(R.id.startTime_answer);
        end = findViewById(R.id.endTime_answer);
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
        Toast.makeText(MeetingTimeAnswer.this, result, Toast.LENGTH_LONG).show();
        Calendar[] temp = (Calendar[]) days.toArray(new Calendar[0]);
        ArrayList<Calendar> newDays = new ArrayList<Calendar>(Arrays.asList(temp));
        //setStringArrayPref(newDays);
    }

    // TODO: 서버 통신해서 현재 모임의 서버 최적 시간 가져오는 메소드 필요

    public int[][] findAbleTime(int[][] A, int[][] B) {
        // MeetingInfoActivity가 아니라 답변창에서 해야 하는 부분이었다.
        // 해당 일마다 Time 을 검사한다.
        // 지금 현재 최적 시간 배열만 넣어두면 된다.
        // 즉 최적 시간 배열과 비교하는 것이다.
        // 최적 시간 배열에 포함되어 있지 않으면 추가한다.
        // 최적 시간 배열에 존재하면 확인한다.
        // [j][0] -> startTime, [j][1] -> endTime
        //
        ArrayList<int[]> ans = new ArrayList<>();
        int i = 0, j = 0;

        while (i < A.length && j < B.length) {
            // Let's check if A[i] intersects B[j].
            // lo - the startpoint of the intersection
            // hi - the endpoint of the intersection
            int lo = Math.max(A[i][0], B[j][0]);
            int hi = Math.min(A[i][1], B[j][1]);
            if (lo <= hi)
                ans.add(new int[]{lo, hi});

            // Remove the interval with the smallest endpoint
            if (A[i][1] < B[j][1])
                i++;
            else
                j++;
        }
        Log.i("able", Arrays.deepToString(ans.toArray(new int[ans.size()][])));

        return ans.toArray(new int[ans.size()][]);
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