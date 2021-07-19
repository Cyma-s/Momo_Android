package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserInfoInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        TextView userName = findViewById(R.id.nickname_edittext);
        Button logoutButton = findViewById(R.id.logout_btn);
        String id = sharedPreferences.getString("userNickName", "");
        userName.setText(id);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}