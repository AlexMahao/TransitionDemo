package com.spearbothy.transitiondemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mahao on 17-5-22.
 */

public class ShareElementViewPager2Activity extends AppCompatActivity {

    private ViewPager mViewPager;

    private List<View> mImageViews = new ArrayList<>();


    private String[] mColors = {"#ff0000", "#0000ff", "#000000", "#ffff00"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_elements_viewpager);
        // 查找控件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        // 初始化显示的View集合
        initViews();
        // 初始化ViewPager
        MyAdapter adapter = new MyAdapter();
        mViewPager.setAdapter(adapter);
        int index = getIntent().getIntExtra("index", 0);
        mViewPager.setCurrentItem(index, false);
        // 延时启动共享动画
        postponeEnterTransition();
        // 当View加载完毕时，启动动画
        scheduleStartPostponedTransition(mImageViews.get(index));
        // 设置监听，更新共享元素数据
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ShareElementViewPagerActivity.exit = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void finishAfterTransition() {
        // 更新共享元素数据。设置对应回调
        setSharedElementCallback(mImageViews.get(mViewPager.getCurrentItem()));
        super.finishAfterTransition();
    }

    private void setSharedElementCallback(final View view) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            //  @Override
            public void onMapSharedElements(List names, Map sharedElements) {
                names.clear();
                sharedElements.clear();
                names.add(view.getTransitionName());
                sharedElements.put(view.getTransitionName(), view);
            }
        });
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //启动动画
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    public void initViews() {
        for (int i = 0; i < mColors.length; i++) {
            View view = new ImageView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            view.setBackgroundColor(Color.parseColor(mColors[i]));
            ViewCompat.setTransitionName(view, "color_" + i);
            mImageViews.add(view);
        }
    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }
    }
}
