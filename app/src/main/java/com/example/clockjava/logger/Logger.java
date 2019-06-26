package com.example.clockjava.logger;

import android.util.Log;

import com.example.clockjava.R;

public final class Logger {

    private static final String LOG_TAG_CLOCK = "Clock_Tag";

    public static void log(String logMessage) {
        Log.d(LOG_TAG_CLOCK, logMessage);
    }
}
