package com.example.clockjava;

import android.util.Log;

public final class Logger {

    private static final String LOG_TAG_CLOCK = String.valueOf(R.string.log_tag);

    public static void log(String logMessage) {
        Log.d(LOG_TAG_CLOCK, logMessage);
    }
}
