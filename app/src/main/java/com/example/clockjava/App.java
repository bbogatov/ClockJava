package com.example.clockjava;

import android.app.Application;
import android.content.Context;

/**
 * This class used for getting context in classes where it absent
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
