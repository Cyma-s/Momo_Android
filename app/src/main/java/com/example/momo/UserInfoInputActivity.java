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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_input);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences isComplete;

        TextView userName = findViewById(R.id.nickname_edittext);
        Button logoutButton = findViewById(R.id.logout_btn);
        ImageView imageView = findViewById(R.id.userProfileImage);
        Button nextButton = findViewById(R.id.nextButton);

        String userImageString = sharedPreferences.getString("userImage", "");
        Log.i("userInfo", userImageString);
        Bitmap userImage = StringToBitmap(userImageString);
        String id = sharedPreferences.getString("userNickName", "");
        userName.setText(id);
        imageView.setImageBitmap(userImage);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserInfoInputActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

    public Bitmap StringToBitmap(String imgString){
        try {
            byte[] encodeByte = Base64.getDecoder().decode(imgString);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}