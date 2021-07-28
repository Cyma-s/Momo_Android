package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class RecommendOrNot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_or_not);

        RadioGroup meetingGroup = findViewById(R.id.meetingdayrecommend);
        int selectedDateId = meetingGroup.getCheckedRadioButtonId();
        if(selectedDateId == R.id.day_yes) {
            Intent intent = new Intent(RecommendOrNot.this, RecommendDate.class);
            startActivity(intent);
        }

        Button nextButton = findViewById(R.id.newMeetingRecNext);
        // 다음 버튼 클릭 시
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}