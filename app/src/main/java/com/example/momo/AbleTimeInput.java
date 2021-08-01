package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AbleTimeInput extends AppCompatActivity {
    // 되는 날짜 Activity로 변경 요망

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_able_time_input);

        Button nextButton = findViewById(R.id.unabletimenext);

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
}