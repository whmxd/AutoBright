package com.base.util;

import android.util.Log;


/**
 * 输出日志
 */
public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();
    private static final boolean DEBUG = true;

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(int msg) {
        if (DEBUG) {
            Log.i(TAG, msg + "");
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, int msg) {
        if (DEBUG) {
            Log.i(tag, msg + "");
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }


}