package com.base.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 动画
 * Created by DNJ on 2019/8/15.
 */

public class AnimationUtil {

    public static void rotation(View view, float start, float end, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        animator.setDuration(duration);
        animator.start();
    }


    /**
     * 中心旋转，实现View顺时针旋转，逆时针恢复
     *
     * @param view     旋转的view
     * @param fl       旋转角度
     * @param duration 动画时间
     */
    public static void rotation(View view, float fl, long duration) {
        ObjectAnimator animator;
        if (view.getRotation() != 0) {
            animator = ObjectAnimator.ofFloat(view, "rotation", fl, 0);
        } else {
            animator = ObjectAnimator.ofFloat(view, "rotation", 0, fl);
        }
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 显示隐藏
     *
     * @param view  执行的View
     * @param alpha 透明度
     */
    public static void alphaAnimation(View view, float alpha) {
        ObjectAnimator animator;
        if (alpha > 0 && alpha <= 1) {
            view.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(view, "alpha", 0, alpha);
        } else if (alpha == 0) {
            animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        } else {
            view.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        }
        animator.setDuration(500);
        if (view.getAnimation() != null)
            view.clearAnimation();
        animator.start();
        //动画监听
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (alpha == 0) {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 显示隐藏
     *
     * @param view     执行的View
     * @param alpha    透明度
     * @param duration 动画时间
     */
    public static void alphaAnimation(View view, float alpha, int duration) {
        ObjectAnimator animator;
        if (alpha > 0 && alpha <= 1) {
            view.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(view, "alpha", 0, alpha);
        } else if (alpha == 0) {
            animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        } else {
            view.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        }
        animator.setDuration(duration);
        animator.start();
        //动画监听
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (alpha == 0) {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    public static void scaleAnimation(View view) {
        scaleAnimation(view, 0.95f);
    }

    /**
     * 布局缩放加透明
     * 点击响应交互动画
     *
     * @param view
     * @param alpha
     */
    public static void scaleAnimation(View view, float alpha) {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", alpha, 1.05f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", alpha, 1.05f, 1f);
        ObjectAnimator alphaV = ObjectAnimator.ofFloat(view, "alpha", alpha, 1.05f, 1f);

        animatorSet.setDuration(800);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY).with(alphaV);
        animatorSet.start();
    }

    public static void scaleShowX(View view) {
        //沿x轴放大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        //移动
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, "translationX", 2f, 0);
        //透明动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        AnimatorSet set = new AnimatorSet();
        //同时沿X,Y轴放大，且改变透明度，然后移动
        set.play(scaleXAnimator).with(animator).before(translationXAnimator);
        //都设置3s，也可以为每个单独设置
        set.setDuration(300);
        set.start();
    }

    public static void scaleHideX(View view) {
        //沿x轴放大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        //移动
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, "translationX", 2f, 0);
        //透明动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        AnimatorSet set = new AnimatorSet();
        //同时沿X,Y轴放大，且改变透明度，然后移动
        set.play(scaleXAnimator).with(animator).before(translationXAnimator);
        //都设置300ms，也可以为每个单独设置
        set.setDuration(300);
        set.start();
    }

    /**
     * 做布局数据重置时
     * 隐藏再显示，XY轴缩放0.98f
     */
    public static void alphaShow(View view) {
        view.clearAnimation();
        //透明动画:隐藏
        ObjectAnimator animatorHide = ObjectAnimator.ofFloat(view, "alpha", 1, 0f);
        animatorHide.setDuration(50);
        //动画监听
        animatorHide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                view.setVisibility(View.GONE);
            }
        });
        //透明动画
        ObjectAnimator animatorShow = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        animatorShow.setDuration(800);
        animatorShow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                view.setVisibility(View.VISIBLE);
            }
        });
        //沿x轴放大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.98f, 1f);
        scaleXAnimator.setDuration(1000);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.98f, 1f);
        scaleYAnimator.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorHide).before(animatorShow).with(scaleXAnimator).with(scaleYAnimator);
        //都设置300ms，也可以为每个单独设置
//        set.setDuration(300);
        set.start();
    }
}

