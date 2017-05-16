package com.spearbothy.transitiondemo.custom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spearbothy.transitiondemo.R;


/**
 * Created by mahao on 17-5-16.
 */

public class ShareElement2Activity extends AppCompatActivity {


    private TextView mShareTextView;
    private int originViewLeft;
    private int originViewTop;
    private int originViewWidth;
    private int originViewHeight;
    private int deltaX, deltaY;
    private float scaleX, scaleY;
    private TextView mDescTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_share_element_2);

        mShareTextView = (TextView) findViewById(R.id.share);

        mDescTextView = (TextView) findViewById(R.id.desc);

        // 取出传递过来的originView信息
        extractViewInfoFromBundle(getIntent());

        onUiReady();
    }

    private void onUiReady() {
        mShareTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // remove previous listener
                mShareTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                //准备场景
                prepareScene();
                //播放动画
                runEnterAnimation();
                return true;
            }
        });

    }

    private void extractViewInfoFromBundle(Intent intent) {
        Bundle extra = intent.getBundleExtra(ShareElementActivity.VIEW_INFO_EXTRA);
        originViewLeft = extra.getInt("left");
        originViewTop = extra.getInt("top");
        originViewWidth = extra.getInt("width");
        originViewHeight = extra.getInt("height");
    }

    private void prepareScene() {
        //缩放到起始view大小
        scaleX = (float) originViewWidth / mShareTextView.getWidth();
        scaleY = (float) originViewHeight / mShareTextView.getHeight();
        mShareTextView.setScaleX(scaleX);
        mShareTextView.setScaleY(scaleY);

        int[] screenLocation = new int[2];
        mShareTextView.getLocationOnScreen(screenLocation);
        //移动到起始view位置
        deltaX = originViewLeft - screenLocation[0];
        deltaY = originViewTop - screenLocation[1];
        mShareTextView.setTranslationX(deltaX); // x 位置
        mShareTextView.setTranslationY(deltaY); // y位置

    }


    private void runEnterAnimation() {
        mShareTextView.setVisibility(View.VISIBLE);


        //执行动画
        mShareTextView.animate()
                .setDuration(500)
                .setInterpolator(new LinearInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0)
                .start();


        mDescTextView.animate()
                .setDuration(500)
                .alpha(1)
                .start();

    }

    @Override
    public void onBackPressed() {
        runExitAnimation();
    }

    private void runExitAnimation() {
        mShareTextView.animate()
                .setDuration(500)
                .setInterpolator(new LinearInterpolator())
                .scaleX(scaleX)
                .scaleY(scaleY)
                .translationX(deltaX)
                .translationY(deltaY)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }).start();

        mDescTextView.animate()
                .setDuration(500)
                .alpha(0)
                .start();

    }

}
