package com.spearbothy.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.spearbothy.transitiondemo.R;

/**
 * Created by mahao on 17-5-18.
 */

public class ColorTransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private View mColorView;

    private String mColor[] = {"#FF00FF", "#FFFF00", "#FF0000"};

    private int mNum = 0;
    private ViewGroup mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_transition_color);

        mColorView = findViewById(R.id.color_view);
        mColorView.setOnClickListener(this);

        mRootView = (ViewGroup) findViewById(R.id.root_view);

        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }


    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        TransitionManager.beginDelayedTransition(mRootView, new ColorChangeTransition());
    }

    @Override
    public void onClick(View v) {
        mColorView.setBackgroundColor(Color.parseColor(mColor[mNum % mColor.length]));
        mNum++;
    }


    public static class ColorChangeTransition extends Transition {
        private static final String PROPNAME_BACKGROUND = "colorchangetransition:change_color:background";


        @Override
        public void captureStartValues(TransitionValues transitionValues) {
            if (transitionValues.view.getBackground() instanceof ColorDrawable) {
                captureValues(transitionValues);
            }
        }

        private void captureValues(TransitionValues values) {
            View view = values.view;
            values.values.put(PROPNAME_BACKGROUND, ((ColorDrawable) values.view.getBackground()).getColor());

        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
            if (transitionValues.view.getBackground() instanceof ColorDrawable) {
                captureValues(transitionValues);
            }
        }


        @Override
        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            if (null == startValues || null == endValues) {
                return null;
            }
            final View view = endValues.view;
            int startBackground = (Integer) startValues.values.get(PROPNAME_BACKGROUND);
            int endBackground = (Integer) endValues.values.get(PROPNAME_BACKGROUND);

            if (startBackground != endBackground) {
                ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(),
                        startBackground, endBackground);
                animator.setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object value = animation.getAnimatedValue();
                        if (null != value) {
                            view.setBackgroundColor((Integer) value);
                        }
                    }
                });
                return animator;
            }
            return null;
        }

        @Override
        public String[] getTransitionProperties() {
            return new String[]{
                    PROPNAME_BACKGROUND
            };
        }
    }


}
