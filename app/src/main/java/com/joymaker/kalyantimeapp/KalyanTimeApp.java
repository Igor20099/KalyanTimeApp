package com.joymaker.kalyantimeapp;

import android.app.Application;
import android.content.Context;

public class KalyanTimeApp extends Application {
     private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        KalyanTimeApp.context = getApplicationContext();

    }
    public static Context getAppContext() {
        return KalyanTimeApp.context;
    }
}
