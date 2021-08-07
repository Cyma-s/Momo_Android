package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class MessageInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);

        TextView datatext, testtext;
        Button joinButton = findViewById(R.id.answerInfo_next);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageInput.this, MeetingTimeAnswer.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if(intent.getAction() == Intent.ACTION_VIEW) {
            String data = intent.getDataString();
            Log.d("TEST", "data : " + data);
        }
    }
}