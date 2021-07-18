package com.example.momo;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "82f5cb2e90721109d9a01b2ebcbe8367");
    }
}
