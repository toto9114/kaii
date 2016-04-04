package com.example.kaii;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by RND on 2016-03-30.
 */
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
