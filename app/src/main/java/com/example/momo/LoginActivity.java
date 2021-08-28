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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Boolean isTrue = false;
    private Boolean nextIntent = false;
    private String meetingId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        intent = getIntent();
        nextIntent = intent.getBooleanExtra("isMessage", false);
        Toast.makeText(LoginActivity.this, "this is " + String.valueOf(nextIntent), Toast.LENGTH_SHORT).show();
        Toast.makeText(LoginActivity.this, "this is " + meetingId, Toast.LENGTH_SHORT).show();
        meetingId = intent.getStringExtra("meetingId");

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
                    // 카카오톡이 있을 경우?
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });
        updateKakaoLoginUi();
    }

    public String BitmapToString(Bitmap bitmap){
        // bitmap을 바이트배열 string으로 변경하는 메소드
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        return Base64.getEncoder().encodeToString(b);
    }

    public void confirmSignuped(User user) {
        if(nextIntent) {
            Intent intent = new Intent(LoginActivity.this, MessageInput.class);
            intent.putExtra("meetingId", meetingId);
            startActivity(intent);
            finish();
        }
        if(isTrue) {
            // 이전 로그인 이력이 있을 경우 -> Home으로 이동
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(LoginActivity.this, UserInfoInputActivity.class);
            startActivity(intent);
            finish();
        }

    }


    public void updateKakaoLoginUi() {
        // 카카오 UI 가져오는 메소드 (로그인 핵심 기능)
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    // 유저 정보가 정상 전달 되었을 경우
                    Log.i(TAG, "id " + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "userimage " + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    try {
                        // 카카오톡 프로필 사진 불러옴
                        mBitmap = new LoadImage().execute(user.getKakaoAccount().getProfile().getProfileImageUrl()).get();
                    } catch (ExecutionException | InterruptedException e) {
                        // 사진 불러오기 오류
                        e.printStackTrace();
                    }

                    setSharedPreferences(user); // 유저 정보 sharedPreferences에 저장



                    confirmSignUp(user);
                }
                if (throwable != null) {
                    // 로그인 시 오류 났을 때
                    // 키해시가 등록 안 되어 있으면 오류남
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }

    public void confirmSignUp(User user) {
        // 회원 가입 되어 있는지 여부 검색
        String url = getString(R.string.url) + "/users/overlap/" + user.getId();
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("result");
                    if (result.equals("true")) {
                        isTrue = true;
                        Log.i("confirm", "true");
                    } else {
                        isTrue = false;
                        Log.i("confirm", "false");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "서버 통신 시 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextintent;
                SharedPreferences autoLogin = getSharedPreferences("autologin", MODE_PRIVATE);
                boolean isAuto = autoLogin.getBoolean("autologin", false);
                if(nextIntent) {  // MessageInput에서 여기로 왔을 경우
                    SharedPreferences.Editor loginEditor = autoLogin.edit();
                    loginEditor.putBoolean("autologin", true);
                    loginEditor.apply();
                    Log.i("autologin", "this is loginActivity");
                    //Boolean isAuto = autoLogin.getBoolean("autologin", false);
                    Intent inputintent = new Intent(LoginActivity.this, MessageInput.class);
                    inputintent.putExtra("meetingId", meetingId);
                    startActivity(inputintent);
                    finish();
                }
                else {
                    if(isAuto) {  // 자동 로그인이 되어 있는지 확인 -> Home으로 이동
                        SharedPreferences.Editor loginEditor = autoLogin.edit();
                        loginEditor.putBoolean("autologin", true);
                        loginEditor.apply();
                        Log.i("autologin", "this is loginActivity");
                        //Boolean isAuto = autoLogin.getBoolean("autologin", false);
                        nextintent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(nextintent);
                        finish();
                    }
                    if (!isTrue) {  // 이미 회원가입 함 -> HomeActivity
                        SharedPreferences.Editor loginEditor = autoLogin.edit();
                        loginEditor.putBoolean("autologin", true);
                        loginEditor.apply();
                        Log.i("autologin", "this is loginActivity");
                        //Boolean isAuto = autoLogin.getBoolean("autologin", false);
                        nextintent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(nextintent);
                        finish();
                    } else {  // 회원 가입이 안 되어 있음 -> UserInfoInputActivity
                        nextintent = new Intent(LoginActivity.this, UserInfoInputActivity.class);
                        startActivity(nextintent);
                        finish();
                    }
                }

            }
        }, 0);

    }

    private void setSharedPreferences(User user){
        // 유저 이미지, 아이디, 닉네임 sharedpreferences에 저장
        userImageString = BitmapToString(mBitmap);
        editor = sharedPreferences.edit();
        editor.putLong("userId", user.getId());
        editor.putString("userNickName", user.getKakaoAccount().getProfile().getNickname());
        editor.putString("userImage", userImageString);
        Log.i("userimage", userImageString);
        editor.apply();
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        // 이미지 로딩하는 메소드
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 아직 로딩중일 때
            super.onPreExecute();
            /*progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("이미지 로딩중입니다...");
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null) {
                // bitmap이 정상 전달되었을 때 bitmap sharedPreferences에 저장
                userImageString = BitmapToString(bitmap);
                Log.i("userImageString", userImageString);
                editor.putString("userImage", userImageString);
                editor.apply();
            } else {
                // 이미지 전달 안 됐을 경우
                /*progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "이미지 로딩 중 오류가 발생했습니다", Toast.LENGTH_SHORT).show();*/
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            // 서버 url에서 bitmap 가져옴 (백그라운드 실행)
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
