package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.sdk.user.UserApiClient;

import java.util.Base64;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserInfoInputActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences isComplete;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        isComplete = getSharedPreferences("autologin", MODE_PRIVATE);
        editor = isComplete.edit();

        TextView userName = findViewById(R.id.nickname_edittext);
        Button logoutButton = findViewById(R.id.logout_btn);
        ImageView imageView = findViewById(R.id.userProfileImage);
        Button nextButton = findViewById(R.id.nextButton);

        // 유저 이미지 정보 받아오기
        String userImageString = sharedPreferences.getString("userImage", "");
        Log.i("userInfo", userImageString);
        Bitmap userImage = StringToBitmap(userImageString);
        String id = sharedPreferences.getString("userNickName", "");
        userName.setText(id);
        imageView.setImageBitmap(userImage);

        nextButton.setOnClickListener(new View.OnClickListener() {
            // Home으로 넘어가는 다음 버튼
            @Override
            public void onClick(View v) {
                isComplete.getBoolean("autologin", false);
                editor.putBoolean("autologin", true);
                editor.apply();
                Intent intent = new Intent(UserInfoInputActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            // 로그아웃 버튼
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

    public Bitmap StringToBitmap(String imgString){
        // String을 Bitmap으로 전환하는 메소드
        try {
            byte[] encodeByte = Base64.getDecoder().decode(imgString);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}