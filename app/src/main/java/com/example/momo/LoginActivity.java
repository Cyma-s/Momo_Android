package com.example.momo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "유저";
    private Button kakaoAuth, googleAuth;
    public static Context mContext;
    private SharedPreferences sharedPreferences;
    private User currentUser;
    private String userImageString = "";
    private Bitmap mBitmap;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    // TBD
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                LoginActivity.this.updateKakaoLoginUi();
                return null;
            }
        };
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        kakaoAuth = findViewById(R.id.kakao_auth_button);
        googleAuth = findViewById(R.id.google_auth_button);
        kakaoAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });
        updateKakaoLoginUi();
    }

    public String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        return Base64.getEncoder().encodeToString(b);
    }


    public void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    Log.i(TAG, "id " + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "userimage " + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    try {
                        mBitmap = new LoadImage().execute(user.getKakaoAccount().getProfile().getProfileImageUrl()).get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    userImageString = BitmapToString(mBitmap);
                    editor = sharedPreferences.edit();
                    editor.putLong("userId", user.getId());
                    editor.putString("userNickName", user.getKakaoAccount().getProfile().getNickname());
                    editor.putString("userImage", userImageString);
                    Log.i("userimage", userImageString);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, UserInfoInputActivity.class);
                    //intent.putExtra("userImage", userImageString);
                    startActivity(intent);
                    finish();
                }
                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("이미지 로딩중입니다...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap != null) {
                userImageString = BitmapToString(bitmap);
                Log.i("userImageString", userImageString);
                editor.putString("userImage", userImageString);
                editor.apply();
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "이미지 로딩 중 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                mBitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(strings[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }
    }
}