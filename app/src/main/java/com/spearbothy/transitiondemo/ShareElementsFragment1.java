package com.spearbothy.transitiondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mahao on 17-5-15.
 */

public class ShareElementsFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_elements1, container, false);
        final Fragment sharedElementFragment2 = new ShareElementsFragment2();
        final View sharedView = view.findViewById(R.id.view_top);
        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Slide slideTransition = new Slide(Gravity.LEFT);
                slideTransition.setDuration(1000L);
                sharedElementFragment2.setEnterTransition(slideTransition);
                // 声明共享元素过渡效果
                ChangeBounds changeBoundsTransition = new ChangeBounds();
                changeBoundsTransition.setDuration(1000L);
                // 设置过渡效果
                sharedElementFragment2.setSharedElementEnterTransition(changeBoundsTransition);
                // 切换fragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_container, sharedElementFragment2)
                        .addToBackStack(null)
                        // 添加共享元素
                        .addSharedElement(sharedView, "share")
                        .commit();
            }
        });
        return view;
    }
}
