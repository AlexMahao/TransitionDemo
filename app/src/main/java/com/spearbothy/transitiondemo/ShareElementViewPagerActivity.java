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

    private static int enter = 0;

    public static int exit = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_example);
        mRootView = (GridLayout) findViewById(R.id.root_view);

        for (int i = 0; i < mRootView.getChildCount(); i++) {
            View view = mRootView.getChildAt(i);
            view.setBackgroundColor(Color.parseColor(mColors[i]));
            ViewCompat.setTransitionName(view, "color_" + i);
            view.setTag(i);
            view.setOnClickListener(this);
        }


        setExitSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (exit != enter) {
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
        intent.putExtra("index", (Integer) v.getTag());
        exit = enter = (int) v.getTag();
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this, v, v.getTransitionName()).toBundle()
        );
    }
}
