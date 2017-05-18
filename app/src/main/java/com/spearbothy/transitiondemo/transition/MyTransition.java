package com.spearbothy.transitiondemo.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by mahao on 17-5-17.
 */

public class MyTransition extends Transition {

    private static final String TOP = "top";

    private static final String HEIGHT = "height";

    private int mPositionDuration = 2000;

    private int mSizeDuration = 2000;

    private Interpolator mPositionInterpolator = new LinearInterpolator();
    private Interpolator mSizeInterpolator = new LinearInterpolator();

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);

        transitionValues.values.put(TOP, rect.top);
        transitionValues.values.put(HEIGHT, view.getHeight());

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(TOP, 0);
        transitionValues.values.put(HEIGHT, transitionValues.view.getHeight());

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View endView = endValues.view;

        final int startTop = (int) startValues.values.get(TOP);
        final int startHeight = (int) startValues.values.get(HEIGHT);
        final int endTop = (int) endValues.values.get(TOP);
        final int endHeight = (int) endValues.values.get(HEIGHT);

        ViewCompat.setTranslationY(endView, startTop);
        endView.getLayoutParams().height = startHeight;
        endView.requestLayout();
        // 初始化布局

        ValueAnimator positionAnimator = ValueAnimator.ofInt(startTop, endTop);
        if (mPositionDuration > 0) {
            positionAnimator.setDuration(mPositionDuration);
        }
        positionAnimator.setInterpolator(mPositionInterpolator);

        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationY(endView, current);
            }
        });

        ValueAnimator sizeAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        if (mSizeDuration > 0) {
            sizeAnimator.setDuration(mSizeDuration);
        }
        sizeAnimator.setInterpolator(mSizeInterpolator);

        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                endView.getLayoutParams().height = current;
                endView.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(sizeAnimator).after(positionAnimator);

        return set;
    }

    @Override
    public String[] getTransitionProperties() {
        return new String[]{
                TOP, HEIGHT
        };
    }

}
