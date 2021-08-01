package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<Meeting> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button makeNewMeeting = findViewById(R.id.makeNewMeeting);
        recyclerView = findViewById(R.id.meeting_recyclerview);

        makeNewMeeting.setOnClickListener(new View.OnClickListener() {
            // 새로운 모임 만들기 버튼으로 모임 만드는 activity로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MakeNewMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getMeetings() {
        // 현재 유저의 모임들 서버에서 가져오는 메소드
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        String url = getString(R.string.url);
    }


}