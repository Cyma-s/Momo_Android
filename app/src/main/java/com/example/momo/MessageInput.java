package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

public class MessageInput extends AppCompatActivity {
    private String meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);
        SharedPreferences autoLogin = getSharedPreferences("autologin", MODE_PRIVATE);
        Boolean isAuto = autoLogin.getBoolean("autologin", false);

        TextView datatext, testtext;
        Button joinButton = findViewById(R.id.answerInfo_next);



        Intent intent = getIntent();

        if(intent.hasExtra("meetingId")) {
            meetingId = intent.getStringExtra("meetingId");
            Log.d("MessageTest", "this is id after login " + meetingId);
        }
        else if (intent.getAction().equals(Intent.ACTION_VIEW) && isAuto) {
            meetingId = intent.getData().getQueryParameter("message");
            Log.d("TEST", "this meeting id is " + meetingId);
        }

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                Long userId = sharedPreferences.getLong("userId", 0);
                Intent nextIntent = new Intent(MessageInput.this, MeetingTimeAnswer.class);
                nextIntent.putExtra("meetingId", meetingId);
                startActivity(nextIntent);
                finish();
            }
        });

        if(intent.getAction().equals(Intent.ACTION_VIEW) && !isAuto) {  // 자동 로그인 되어 있지 않은 경우 로그인 창으로 보냄
            Intent loginIntent = new Intent(MessageInput.this, LoginActivity.class);
            meetingId = intent.getData().getQueryParameter("message");
            Log.d("TEST", "this meeting id is " + meetingId);
            loginIntent.putExtra("isMessage", true);
            loginIntent.putExtra("meetingId", meetingId);
            startActivity(loginIntent);
        }
    }
}