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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONObject;

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
        String userNickName = sharedPreferences.getString("userNickName", "");
        Long userId = sharedPreferences.getLong("userId", 0);
        userName.setText(userNickName);
        imageView.setImageBitmap(userImage);

        nextButton.setOnClickListener(new View.OnClickListener() {
            // Home으로 넘어가는 다음 버튼
            @Override
            public void onClick(View v) {
                sendToServer(userId.toString(), userName.getText().toString());
                isComplete.getBoolean("autologin", false);
                editor.putBoolean("autologin", true);
                editor.apply();
                Intent intent = new Intent(UserInfoInputActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void sendToServer(String id, String userName) {
        String url = getString(R.string.url) + "/users/signup";
        JSONObject userInfo = new JSONObject();
        try {
            userInfo.put("id", id);
            userInfo.put("name", userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(UserInfoInputActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserInfoInputActivity.this, "서버 전송 시 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
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