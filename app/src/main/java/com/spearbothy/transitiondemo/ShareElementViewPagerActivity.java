package com.spearbothy.transitiondemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by mahao on 17-5-22.
 */

public class ShareElementViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout mRootView;

    private String[] mColors = {"#ff0000", "#0000ff", "#000000", "#ffff00"};

    private static int enter = 0; // 第二个页面进入时的共享元素

    public static int exit = 0; // 第二个页面退出的共享元素索引

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_example);
        mRootView = (GridLayout) findViewById(R.id.root_view);

        for (int i = 0; i < mRootView.getChildCount(); i++) {
            View view = mRootView.getChildAt(i);
            view.setBackgroundColor(Color.parseColor(mColors[i]));
            // 设置transitionName属性
            ViewCompat.setTransitionName(view, "color_" + i);
            // transition = "color_1|2|3|4"
            view.setTag(i);
            view.setOnClickListener(this);
        }
        // 监听回调
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                // 该回调在列表页进入和退出时都会回调，所以需要加上判断条件
                if (exit != enter) {
                    // 更新共享元素
                    names.clear();
                    sharedElements.clear();
                    View view = mRootView.getChildAt(exit);
                    names.add(view.getTransitionName());
                    sharedElements.put(view.getTransitionName(), view);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ShareElementViewPager2Activity.class);
        // 指定启动`viewPager`的索引
        intent.putExtra("index", (Integer) v.getTag());
        // 更新对应索引表
        exit = enter = (int) v.getTag();
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this, v, v.getTransitionName()).toBundle()
        );
    }
}
