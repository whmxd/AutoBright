package com.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

/**
 * 页面统一管理类
 * Created by DNJ on 2019/4/5
 */

public class ActivityManagerUtil {
    private static final Stack<Activity> sActivityStack = new Stack<>();
    private static ActivityManagerUtil sActivityManager;

    public Stack<Activity> getActivityStack() {
        return sActivityStack;
    }

    /**
     * 单一实例
     */
    public static ActivityManagerUtil getInstance() {
        if (sActivityManager == null) {
            synchronized (ActivityManagerUtil.class) {
                sActivityManager = new ActivityManagerUtil();
            }
        }
        return sActivityManager;
    }



    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        sActivityStack.add(activity);
    }

    /**
     * 删除堆栈中的Activity
     */
    public void removeActivity(Activity activity) {
        if (sActivityStack.isEmpty()) {
            return;
        }
        sActivityStack.remove(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = sActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = sActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }

    }


    /**
     * 结束非指定类名的Activity
     */
    public void finishOtherActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }

    }

    //获取指定类名的Activity
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }

    public void finishAllOtherActivity(Activity activity) {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i) && sActivityStack.get(i) != activity) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }

    public void recreateAllOtherActivity(Activity activity) {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i) && sActivityStack.get(i) != activity) {
                sActivityStack.get(i).recreate();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 在多任务列表页面隐藏App窗口
     */
    public static void hideAppWindow(Context context, boolean isHide){
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //控制App的窗口是否在多任务列表显示
            activityManager.getAppTasks().get(0).setExcludeFromRecents(isHide);
        }catch (Exception e){

        }
    }

    /**
     * 返回桌面，防止杀掉进程
     */
    public static void showDesktop(Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }
}

