package com.base.util;

import android.view.View;


/**
 * 点击控制，两次点击按钮之间的点击间隔不能少于1000毫秒
 * Created by DNJ on 2019/5/30.
 */

public abstract class OnMulClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 300;
    private static long lastClickTime;

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            AnimationUtil.scaleAnimation(v, 0.95f);
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onMultiClick(v);
        }
    }

    public abstract void onMultiClick(View v);

}