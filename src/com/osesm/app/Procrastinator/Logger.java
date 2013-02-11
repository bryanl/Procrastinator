package com.osesm.app.Procrastinator;

import android.util.Log;

public class Logger {

    private final static String TAG = "Procrastinator";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void e(String message, Throwable t) {
        Log.e(TAG, message, t);
    }

    public static void w(String message) {
        Log.w(TAG, message);
    }
}
