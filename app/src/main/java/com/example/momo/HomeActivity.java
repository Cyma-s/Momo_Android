package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<Meeting> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button makeNewMeeting = findViewById(R.id.makeNewMeeting);
        Button logoutButton = findViewById(R.id.logout_btn);
        recyclerView = findViewById(R.id.meeting_recyclerview);


        makeNewMeeting.setOnClickListener(new View.OnClickListener() {
            // 새로운 모임 만들기 버튼으로 모임 만드는 activity로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MakeNewMeetingActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            // 로그아웃 버튼
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("autologin", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                sharedPreferences.getBoolean("autologin", false);
                editor.putBoolean("autologin", false);
                editor.apply();
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        ((LoginActivity)LoginActivity.mContext).updateKakaoLoginUi();
                        return null;
                    }
                });
                Intent backIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }

    public void getMeetings() {
        // 현재 유저의 모임들 서버에서 가져오는 메소드
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        String url = getString(R.string.url);
    }


}