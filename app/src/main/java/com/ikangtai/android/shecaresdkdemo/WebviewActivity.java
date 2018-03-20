package com.ikangtai.android.shecaresdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ikangtai.android.shecaresdk.ShecareSdk;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String curveUrl = ShecareSdk.getCurveUrl();
        Log.i("ble", curveUrl);
        webView.loadUrl(curveUrl);
    }
}
