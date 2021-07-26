package com.example.momo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddressApiActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_address_set2);

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebChromeClient(new NewWebChromeClient(this));
        mWebView.loadUrl(getString(R.string.url) + "/pre-testing/html");
    }

    private class NewWebChromeClient extends WebChromeClient {

        private Activity mActivity = null;
        public NewWebChromeClient(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView newWebView = new WebView(AddressApiActivity.this);
            WebSettings webSettings = newWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            final Dialog dialog = new Dialog(AddressApiActivity.this);
            dialog.setContentView(newWebView);

            ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
            dialog.show();
            newWebView.setWebChromeClient(new WebChromeClient() {
                @Override public void onCloseWindow(WebView window) {
                    Intent intent = new Intent(AddressApiActivity.this, BasicAddressSetActivity1.class);
                    startActivity(intent);
                }
            });

            ((WebView.WebViewTransport)resultMsg.obj).setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;
        }
    }
}
