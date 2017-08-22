package com.logischtech.mytalentapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Reportlink extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlink);
        mWebView = (WebView) findViewById(R.id.webview);
        final String link = getIntent().getStringExtra("reportlink");
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + link);

//
//mWebView.loadUrl(link)

    }
}