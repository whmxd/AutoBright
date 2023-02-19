package com.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.base.util.ActivityManagerUtil;
import com.base.util.StatusBarUtil;
import com.user.base.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean beforeEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    //事件分发，键盘管理，隐藏和弹起
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getViewAtViewGroup((int) (ev.getRawX()), (int) (ev.getRawY()));
            boolean specialInput = getSpecialInput(v);
            if (isShouldHideInput(v, ev) && !specialInput) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && isSoftShowing()) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                //清除焦点，特别是EditText，提升交互效果
                if (getCurrentFocus() != null) {
                    getCurrentFocus().clearFocus();
                }
            }
            if ((v != null && v instanceof EditText) || specialInput) {
                beforeEdit = true;
            } else if (beforeEdit) {
                beforeEdit = false;
                if (v != null && (saveGetStringFromObj(v.getTag())).equals("click"))
                    return super.dispatchTouchEvent(ev);
                if (isSoftShowing()) {
//                    return false;
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) {
            //非默认值
            getResources();
        }
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//现在是竖屏
            showNavigationBar(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//现在是横屏
            showNavigationBar(false);
        }
    }


    @Override
    public Resources getResources() {//禁止手机调整字体大小、显示大小
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }


    /**
     * 横竖屏切换
     *
     * @param isShow true 竖屏
     */
    private void showNavigationBar(boolean isShow) {//切换系统状态栏显示状态
        Window window = getWindow();
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 隐藏虚拟栏
     *
     * @param activity
     */
    protected void hideBottomUIMenu(Activity activity) {//隐藏虚拟按键，并且全屏
        View decorView = activity.getWindow().getDecorView();
        if (decorView == null) return;
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {//滑动仍能出现
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }

    private boolean getSpecialInput(View v) {
        if (v != null) {
            Object tag = v.getTag();
            if (tag != null && (tag instanceof String) && ((String) tag).equals("input")) {
                return true;
            }
        }
        return false;
    }

    private String saveGetStringFromObj(Object tag) {
        if (tag == null)
            return "";
        if (tag instanceof String)
            return (String) tag;
        return "";
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            return false;
        }
        return true;
    }

    /**
     * 根据坐标获取相对应的子控件<br>
     * 在重写ViewGroup使用
     *
     * @param x 坐标
     * @param y 坐标
     * @return 目标View
     */
    protected View getViewAtViewGroup(int x, int y) {
        return findViewByXY(getRootView(this), x, y);
    }

    private View findViewByXY(View view, int x, int y) {
        View targetView = getTouchTarget(view, x, y);
        if (targetView == null)
            return null;
        else if (targetView instanceof ViewGroup) {
            ViewGroup v = (ViewGroup) targetView;
            for (int i = 0; i < v.getChildCount(); i++) {
                View tempView = findViewByXY(v.getChildAt(i), x, y);
                if (tempView != null) {
                    return tempView;
                }
            }
        }
        return targetView;
    }

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    private View getTouchTarget(View view, int x, int y) {
        View targetView = null;
        // 判断view是否可以聚焦
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getInstance().removeActivity(this);
    }


    /**
     * 默认显示软键盘
     */
    protected void showInputMethod() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 默认隐藏软键盘
     */
    protected void hideSoftInput() {
        // 设置默认键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 主动显示键盘
     * @param et 输入焦点
     */
    protected final void showInput(final EditText et) {
        et.postDelayed((Runnable)(new Runnable() {
            public final void run() {
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager) BaseActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput((View)et, 1);
            }
        }), 100);
    }

    /**
     * 主动隐藏键盘
     */
    protected final void hideInput() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        View v = this.getWindow().peekDecorView();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
