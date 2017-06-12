package com.spearbothy.transitiondemo.transition;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public void onClick(View v) {
        TransitionManager.beginDelayedTransition(mRootView, new ColorChangeTransition());
        mColorView.setBackgroundColor(Color.parseColor(mColor[mNum % mColor.length]));
        mNum++;
    }


    public static class ColorChangeTransition extends Transition {
        // key
        private static final String PROPNAME_BACKGROUND = "colorchangetransition:change_color:background";

        @Override
        public void captureStartValues(TransitionValues transitionValues) {
            // 开始场景的回调，计算初始状态
            if (transitionValues.view.getBackground() instanceof ColorDrawable) {
                // 计算颜色的值
                captureValues(transitionValues);
            }
        }

        // 获取色值，并保存
        private void captureValues(TransitionValues values) {
            View view = values.view;
            // 保存对应的状态
            values.values.put(PROPNAME_BACKGROUND, ((ColorDrawable) values.view.getBackground()).getColor());
        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
            // 结束场景的回调，计算结束状态
            if (transitionValues.view.getBackground() instanceof ColorDrawable) {
                captureValues(transitionValues);
            }
        }

        @Override
        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            // 在开始与结束场景都存在的时
            if (null == startValues || null == endValues) {
                return null;
            }
            // 动画的目标view
            final View view = endValues.view;
            // 初始状态和结束状态
            int startBackground = (Integer) startValues.values.get(PROPNAME_BACKGROUND);
            int endBackground = (Integer) endValues.values.get(PROPNAME_BACKGROUND);

            if (startBackground != endBackground) {
                // 创建动画
                ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(),
                        startBackground, endBackground);
                animator.setDuration(500);
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
            // 返回我们自定义的属性key
            return new String[]{
                    PROPNAME_BACKGROUND
            };
        }
    }
}
