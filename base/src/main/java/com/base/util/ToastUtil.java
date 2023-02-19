package com.base.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Toast统一管理类
 */
public class ToastUtil {
    private static Toast toast;
    private static final boolean isShow = true;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int message) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, int message) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param duration 时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, duration);
            } else {
                toast.setText(message);
                toast.setDuration(duration);
            }
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param duration 时间
     */
    public static void show(Context context, int message, int duration) {
        if (isShow && context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, duration);
            } else {
                toast.setText(message);
                toast.setDuration(duration);
            }
            toast.show();
        }
    }
}