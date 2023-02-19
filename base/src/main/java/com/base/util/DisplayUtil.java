package com.base.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * 状态栏高度、屏幕大小、距离单位转换工具类
 * Created by DNJ on 2019/5/30.
 */

public class DisplayUtil {

    private static final DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

    // 获取宽度,包括手机状态栏和虚拟栏高度一起
    public static int getScreenWidth(Context context) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            windowMgr.getDefaultDisplay().getRealMetrics(dm);
            return dm.widthPixels;
        }else{
            return metrics.widthPixels;
        }

    }

    // 获取高度,包括手机状态栏和虚拟栏高度一起
    public static int getScreenHeight(Context context) {
        //4.2之后获取到的虚拟栏高度
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            windowMgr.getDefaultDisplay().getRealMetrics(dm);
            return dm.heightPixels;
        }else{
            return metrics.heightPixels;
        }
    }



    /**
     * dp转px
     *
     * @param dp dp单位值
     * @return px单位值
     */
    public static int dp2px(float dp) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px px单位值
     * @return dp单位值
     */
    public static int px2dp(float px) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param px px单位值
     * @return sp单位值
     */
    public static int px2sp(float px) {
        float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param sp sp单位值
     * @return px单位值
     */
    public static int sp2px(float sp) {
        float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    /**
     * 通过字符大小获取单个字符的Px
     */
    public static float getWidthFontSize(Context context, int fontSize) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(fontSize);
        textView.setText("单");
        return getCharacterWidth(textView);
    }

    /**
     * 获取文本字符串的像素大小
     */
    private static float getCharacterWidth(TextView text) {
        if (null == text || "".equals(text.getText().toString())) {
            return 0f;
        }
        Paint paint = new Paint();
        paint.setTextSize(text.getTextSize() * text.getTextScaleX());
        float text_width = paint.measureText(text.getText().toString());
        return text_width;
    }

    public static void getScreenRect(Context ctx_, Rect outrect_) {
        Display screenSize = ((WindowManager) ctx_.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        outrect_.set(0, 0, screenSize.getWidth(), screenSize.getHeight());
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Context activity) {
        int statusHeight = 0;
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object object = localClass.newInstance();
            int height = Integer.parseInt(localClass.getField("status_bar_height").get(object).toString());
            statusHeight = activity.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取状态栏高度
     * @return 返回状态栏高度的px单位
     */
    public static int statusBarHeight(Context context) {
        int result = 0;
        int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (id != 0) {
            result = context.getResources().getDimensionPixelSize(id);
        }
        return result;
    }
}
