package com.example.kaii;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class SplashActivity extends AppCompatActivity {

    String key;
    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        key = PropertyManager.getInstance().getKey();

        if(!TextUtils.isEmpty(key)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE,key);
            startActivity(intent);
            finish();
        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 1500);

        }
    }
}
