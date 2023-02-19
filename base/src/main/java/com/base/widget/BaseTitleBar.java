package com.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.base.util.DisplayUtil;
import com.base.util.OnMulClickListener;
import com.user.base.R;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.getMode;


/**
 * 头部
 * Created by DNJ on 2018/5/8.
 * UpDate by DNJ on 2020/5/29
 */

public class BaseTitleBar extends RelativeLayout implements View.OnClickListener {
    private final ImageView baseLeftImg;
    private TextView baseLeftText;
    private final TextView baseTitleText;
    private final TextView baseRightText;
    private final RelativeLayout layout;
    private OnMulClickListener clickListener;
    private boolean isOnClick = false;

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_title_bar, this);
        layout = findViewById(R.id.base_head_layout);
        baseLeftImg = findViewById(R.id.base_title_left_img);
        baseLeftText = findViewById(R.id.base_left_txt);
        baseTitleText = findViewById(R.id.base_title_text);
        baseRightText = findViewById(R.id.base_title_right_text);
        //自定义带返回键
        baseLeftImg.setOnClickListener(this);
        baseLeftText.setOnClickListener(this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
        String title = array.getString(R.styleable.BaseTitleBar_bar_title);
        int colorId = array.getColor(R.styleable.BaseTitleBar_bar_title_color, getResources().getColor(R.color.color_33));
        int titleSize = (int) array.getDimension(R.styleable.BaseTitleBar_bar_title_size, DisplayUtil.dp2px(15));
        int imgId = array.getResourceId(R.styleable.BaseTitleBar_bar_left_img, R.mipmap.base_icon_back_black); //common_icon_back_new
        String leftTxt = array.getString(R.styleable.BaseTitleBar_bar_left_txt);
        String rightTxt = array.getString(R.styleable.BaseTitleBar_bar_right_txt);
        int colorRightId = array.getResourceId(R.styleable.BaseTitleBar_bar_right_color, R.color.color_33);
        Drawable background = array.getDrawable(R.styleable.BaseTitleBar_bar_background);
        if (title != null) {
            baseTitleText.setText(title);
        } else {
            baseTitleText.setText(R.string.str_bar_title);
        }
        //如果设置了左边的文字，则显示文字，隐藏左侧的图标
        if (leftTxt != null && !leftTxt.isEmpty()) {
            baseLeftText.setText(leftTxt);
            baseLeftImg.setVisibility(GONE);
            baseLeftText.setVisibility(VISIBLE);
        } else {
            baseLeftText.setVisibility(GONE);
            baseLeftImg.setVisibility(VISIBLE);
        }
        baseTitleText.setTextColor(colorId);
        baseTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        baseLeftImg.setImageResource(imgId);
//        Glide.with(context).load(imgId)
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(context,10)))
//                .into(baseLeftImg);
        if (rightTxt != null && !rightTxt.isEmpty()) {
            baseRightText.setText(rightTxt);
            baseRightText.setVisibility(VISIBLE);
        } else {
            baseRightText.setVisibility(View.GONE);
        }
        baseRightText.setTextColor(getResources().getColor(colorRightId));
        layout.setBackground(background);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 当父view的模式为AT_MOST时，wrap_content模式
        // 当父view的模式为EXACITY时，match_parent或者固定值
        // 当父view的模式为UNSPECIFIED时，父容器不对view有任何限制，要多大给多大（多见于ListView、GridView）
        if (getMode(heightMeasureSpec) == EXACTLY) {//为match或者固定值，我们就重设title参数
            setViewLayoutParams(layout, widthMeasureSpec, heightMeasureSpec);
        }
        layout.setGravity(CENTER_VERTICAL);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.base_left_txt || id == R.id.base_title_left_img) {
            if (!isOnClick) { //有时候需要重写返回intent，不能直接finish,因此写一个回调
                ((Activity) getContext()).finish();
            } else {
                if (clickListener != null) {
                    clickListener.onMultiClick(v);
                }
            }
        }
    }

    /**
     * 重设 view 的宽高
     */
    public void setViewLayoutParams(View view, int nWidth, int nHeight) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp.height != nHeight || lp.width != nWidth) {
            lp.width = nWidth;
            lp.height = nHeight;
            view.setLayoutParams(lp);
        }
    }

    public void setLeftOnClickListener(OnMulClickListener clickListener) {
        isOnClick = true;
        this.clickListener = clickListener;
    }

    public void setRightOnClickListener(OnClickListener listener) {
        baseRightText.setOnClickListener(listener);
    }

    public void setBaseBackgroundColor(int resource) {
        layout.setBackgroundColor(getResources().getColor(resource));
    }

    public void setBackGroundDrawable(@DrawableRes int drawable) {
        layout.setBackground(getResources().getDrawable(drawable));
    }

    public void setBackGone() {
        baseLeftImg.setVisibility(View.GONE);
    }

    public void setBackINVISIBLE() {
        baseLeftImg.setVisibility(View.INVISIBLE);
        baseLeftImg.setEnabled(false);
    }

    //title
    public void setTitleText(@StringRes int resource) {
        baseTitleText.setText(resource);
    }

    public void setTitleText(String s) {
        if (s == null)
            s = "";
        if (s.equals(baseTitleText.getText().toString())) {
            return;
        }
        baseTitleText.setText(s);
    }

    public <T> void setTitleText(T s) {
        if (s instanceof String) {
            String str = (String) s;
            baseTitleText.setText(str);
        }
        if (s instanceof Integer) {
            Integer resource = (Integer) s;
            baseTitleText.setText(resource);
        }
    }

    //leftImg
    public void setLeftImgVisible(int visible) {
        baseLeftImg.setVisibility(visible);
    }

    //rightBtn
    public void setRightBtnText(int resource) {
        baseRightText.setVisibility(VISIBLE);
        baseRightText.setText(resource);
    }

    //rightBtn
    public String getRightBtnText() {
        return baseRightText.getText().toString();
    }


    public void setRightBtnText(String s) {
        baseRightText.setVisibility(VISIBLE);
        baseRightText.setText(s);
    }

    public void setRightBtnListener(final OnMulClickListener multiClickListener) {
        baseRightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                multiClickListener.onClick(v);
            }
        });
    }

    public void setRightGone() {
        baseRightText.setVisibility(View.GONE);
    }

    public TextView getTitleView() {
        if (baseTitleText != null)
            return baseTitleText;
        return null;
    }

}