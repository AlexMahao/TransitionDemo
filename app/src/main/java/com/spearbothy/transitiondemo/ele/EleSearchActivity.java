package com.spearbothy.transitiondemo.ele;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spearbothy.transitiondemo.R;
import com.spearbothy.transitiondemo.custom.ShareElementActivity;

/**
 * Created by mahao on 17-5-16.
 */

public class EleSearchActivity extends AppCompatActivity {

    private RelativeLayout mSearchBGTxt;
    private TextView mSearchTxt;
    private FrameLayout mContentFrame;
    private ImageView mArrowImg;
    private boolean finishing;
    private View mFrameView;
    private EditText mEditText;

    private int originViewLeft;
    private int originViewTop;
    private int originViewWidth;
    private int originViewHeight;
    private int deltaX, deltaY;
    private float scaleX, scaleY;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele_search);
        mSearchBGTxt = (RelativeLayout) findViewById(R.id.tv_search_bg);
        mContentFrame = (FrameLayout) findViewById(R.id.frame_content_bg);
        mSearchTxt = (TextView) findViewById(R.id.tv_search);
        mArrowImg = (ImageView) findViewById(R.id.iv_arrow);
        mFrameView = findViewById(R.id.frame_bg);
        mEditText = (EditText) findViewById(R.id.input);



        mSearchBGTxt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                prepareScene();
                mSearchBGTxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                performEnterAnimation();
            }
        });

    }


    /**
     * 准备动画
     */
    private void prepareScene() {

        Bundle extra = getIntent().getBundleExtra(ShareElementActivity.VIEW_INFO_EXTRA);
        originViewLeft = extra.getInt("left");

        int[] screenLocation = new int[2];
        mEditText.getLocationOnScreen(screenLocation);
        //移动到起始view位置
        deltaX = originViewLeft - screenLocation[0];
        mEditText.setTranslationX(deltaX); // x 位置

        EleMainActivity.activity.hide();

    }

    private void performEnterAnimation() {
        // 150dp -> 70dp
        final ViewGroup.LayoutParams params = mFrameView.getLayoutParams();
        final ValueAnimator translateVa = ValueAnimator.ofInt(params.height, (int) dp2px(70));
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.height = (int) valueAnimator.getAnimatedValue();
                mFrameView.setLayoutParams(params);
                //EleMainActivity.activity.hide();
            }
        });

        ObjectAnimator contentAnim = ObjectAnimator.ofArgb(mFrameView,"backgroundColor",Color.parseColor("#000096FF"), Color.parseColor("#FF0096FF"));


        final FrameLayout.LayoutParams paramsEdit = (FrameLayout.LayoutParams) mSearchBGTxt.getLayoutParams();
        //
        ValueAnimator scaleVa = ValueAnimator.ofFloat(0, 1);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int left = (int) ((Float) valueAnimator.getAnimatedValue() * dp2px(35));
                int right = (int) ((Float) valueAnimator.getAnimatedValue() * dp2px(45));
                paramsEdit.setMargins(left, 0, right, 0);
            }
        });

        ValueAnimator alphaVa = ValueAnimator.ofFloat(0, 1f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
                mSearchTxt.setAlpha((Float) valueAnimator.getAnimatedValue());
                mArrowImg.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);
        contentAnim.setDuration(500);


        //执行动画
        mEditText.animate()
                .setDuration(500)
                .setInterpolator(new LinearInterpolator())
                .translationX(0)
                .start();

        alphaVa.start();
        translateVa.start();
        scaleVa.start();
        contentAnim.start();
    }

    @Override
    public void onBackPressed() {
        if (!finishing) {
            finishing = true;
            performExitAnimation();
        }
    }

    private float dp2px(int px) {
        return getResources().getDisplayMetrics().density * px;
    }

    private void performExitAnimation() {
        final ViewGroup.LayoutParams params = mFrameView.getLayoutParams();
        final ValueAnimator translateVa = ValueAnimator.ofInt(params.height, (int) dp2px(150));
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.height = (int) valueAnimator.getAnimatedValue();
                mFrameView.setLayoutParams(params);
            }
        });
        translateVa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                EleMainActivity.activity.show();
                finish();
                overridePendingTransition(0, 0);
            }
        });
        ObjectAnimator contentAnim = ObjectAnimator.ofArgb(mFrameView,"backgroundColor",Color.parseColor("#FF0096FF"), Color.parseColor("#000096FF"));



        final FrameLayout.LayoutParams paramsEdit = (FrameLayout.LayoutParams) mSearchBGTxt.getLayoutParams();
        //
        ValueAnimator scaleVa = ValueAnimator.ofFloat(1, 0);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int left = (int) ((Float) valueAnimator.getAnimatedValue() * dp2px(35));
                int right = (int) ((Float) valueAnimator.getAnimatedValue() * dp2px(45));
                paramsEdit.setMargins(left, 0, right, 0);
            }
        });

        ValueAnimator alphaVa = ValueAnimator.ofFloat(1, 0);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
                mSearchTxt.setAlpha((Float) valueAnimator.getAnimatedValue());
                mArrowImg.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        //执行动画
        mEditText.animate()
                .setDuration(500)
                .setInterpolator(new LinearInterpolator())
                .translationX(deltaX)
                .start();

        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);
        contentAnim.setDuration(500);

        alphaVa.start();
        translateVa.start();
        scaleVa.start();
        contentAnim.start();
    }

}
