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
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MakeNewMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getMeetings() {
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        String url = getString(R.string.url);
    }


}