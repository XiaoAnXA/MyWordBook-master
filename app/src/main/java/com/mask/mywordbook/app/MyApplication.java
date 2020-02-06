package com.mask.mywordbook.app;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context contexts;

    @Override
    public void onCreate() {
        super.onCreate();
        contexts = getApplicationContext();
    }

    public static Context getContext() {
        return contexts;
    }
}
