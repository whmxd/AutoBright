package com.base.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.user.base.R;

/**
 * 加载动画
 * Created by DNJ on 2019/4/6.
 */

public class LoadingDialog {

    private Context context;
    private Dialog dialog;


    public LoadingDialog(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.DialogLoadingStyle);
        }
        dialog.setContentView(R.layout.base_dialog_loading);
        parameter();
    }

    //dialog参数设置
    private void parameter() {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.1f; //背景阴影
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        lp.width = (int) (dm.widthPixels * 0.2);
        lp.height = (int) (dm.heightPixels * 0.2);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        //dialog含有EditText时，软件盘的不显示
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void setCancelable(boolean cancelable){
        dialog.setCancelable(cancelable);
    }

    public void show() {
        if (dialog != null) {
            //管理同一对象，多次show，只保证显示一个
            if (dialog.isShowing()) {
                dialog.cancel();
            }
            dialog.show();
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        dialog.setOnCancelListener(listener);
    }

    public void cancel() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
