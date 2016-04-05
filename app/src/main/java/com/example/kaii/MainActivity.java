package com.example.kaii;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView mWebView;

    public static final String EXTRA_MESSAGE = "site";
    private static final String URL_FORMAT = "http://kaii.plani.co.kr/system/native/gate/code/";

    public static final int MESSAGE_BACK_KEY_TIMEOUT = 0;
    public static final int BACK_KEY_TIME = 2000;

    boolean isBackPressed = false;
    Handler mHandler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BACK_KEY_TIMEOUT:
                    isBackPressed = false;
                    return true;
            }
            return false;
        }
    });


    ProgressDialog dialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context myApp = this;

        mWebView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        String key = intent.getStringExtra(EXTRA_MESSAGE);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        //javascript useable
        mWebView.getSettings().setJavaScriptEnabled(true);

        //jwplayer mp4 dom
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setWebChromeClient
                (
                        new WebChromeClient() {
                            //alter process
                            @Override
                            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                                new AlertDialog.Builder(myApp)
                                        .setTitle("Alert")
                                        .setMessage(message)
                                        .setPositiveButton(android.R.string.ok,
                                                new AlertDialog.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        result.confirm();
                                                    }
                                                })
                                        .setCancelable(false)
                                        .create()
                                        .show();

                                return true;
                            }

                            ;

                            //confirm process
                            @Override
                            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("Confirm")
                                        .setMessage(message)
                                        .setPositiveButton("네",
                                                new AlertDialog.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        result.confirm();
                                                    }
                                                })
                                        .setNegativeButton("아니오",
                                                new AlertDialog.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        result.cancel();
                                                    }
                                                })
                                        .setCancelable(false)
                                        .create()
                                        .show();

                                return true;
                            }
                        }
                );

        mWebView.setWebViewClient(new WebViewClientClass());

        //mWebView.loadUrl("http://kaii.plani.co.kr");
        Log.i("MainActivity",""+mWebView.getProgress());
        mWebView.loadUrl(URL_FORMAT + key);
    }



    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dialog.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            Log.i("MainActivity",mWebView.getUrl());
        } else {
            if (!isBackPressed) {
                Toast.makeText(this, R.string.back_pressed_message, Toast.LENGTH_SHORT).show();
                isBackPressed = true;
                mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_KEY_TIMEOUT, BACK_KEY_TIME);
            } else {
                mHandler.removeMessages(MESSAGE_BACK_KEY_TIMEOUT);
                finish();
            }
        }
    }
}