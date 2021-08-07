package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class MessageInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);

        TextView datatext, testtext;
        datatext = findViewById(R.id.data);

        Intent intent = getIntent();
        if(intent.getAction() == Intent.ACTION_VIEW) {
            String data = intent.getDataString();
            Log.d("TEST", "data : " + data);
            datatext.setText(data);
        }
    }
}