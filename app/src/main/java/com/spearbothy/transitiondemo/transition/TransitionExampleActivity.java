package com.spearbothy.transitiondemo.transition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.GridLayout;

import com.spearbothy.transitiondemo.R;

/**
 * Fade() 透明度
 * <p>
 * captureStartValues() 计算开始的状态
 * <p>
 * captureEndValues()  : 当视图发生变化时，计算技术的状态
 * <p>
 * createAnimator()  ： 根据开始和结束状态的值创建合适的动画集合
 * <p>
 * <p>
 *
 *      Window.FEATURE_ACTIVITY_TRANSITIONS
 *
 *       ActivityOptions.makeSceneTransitionAnimation(activity, pairs).toBundle();
 *
 *       finishAfterTransition()
 *
 * Created by mahao on 17-5-17.
 */
public class TransitionExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout mRootView;
    private View mRedBoxView;
    private View mBlueBoxView;
    private View mBlackBoxView;
    private View mYellowBoxView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_example);
        mRootView = (GridLayout) findViewById(R.id.root_view);

        mRootView.setOnClickListener(this);

        mRedBoxView = findViewById(R.id.red_box);
        mBlueBoxView = findViewById(R.id.blue_box);
        mBlackBoxView = findViewById(R.id.black_box);
        mYellowBoxView = findViewById(R.id.yellow_box);
    }

    @Override
    public void onClick(View v) {
        // 启动延时动画
        TransitionManager.beginDelayedTransition(mRootView, new Explode());
        // 切换控件的状态
        toggleVisibility(mRedBoxView, mBlueBoxView, mBlackBoxView, mYellowBoxView);
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
