package com.spearbothy.transitiondemo.transition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;

import com.spearbothy.transitiondemo.R;


/**
 *  第一种,基于场景的动画
 *
 *      原理： 将rootView下的元素全部移除，然后加载end的场景
 *
 *    - 构造结束场景Scene
 *          -- rootView  需要变化的parent
 *          -- view  变化的视图
 *    - 指定变化的方式
 *
 *          --   ChangeBounds 位置变化 （根据id）
 *
 *
 *    - TransitionManager.go(Scene,transition); // 变化场景
 *
 *  第二种，基于场景变化监听的动画
 *
 *      对于第一种，我们需要创建场景，为了简单，提供第二种方式
 *
 *      TransitionManager.beginDelayedTransition(RootView, transition);
 *
 *      监听某一节点下的视图状态与对应的动画
 *
 *      当rootView下有相关view发生了与对应transition所监听的状态变化时，将执行动画
 *
 *      系统会保存一个当前视图树状态的场景，然后我们直接修改视图树，在下一次绘制时，系统会自动对比之前保存的视图树，然后执行一步动画。
 *
 */
public class SceneActivity extends AppCompatActivity {

    private FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (FrameLayout) findViewById(R.id.rootView);
    }

    public void begin(View view){
        Scene scene2 = Scene.getSceneForLayout(rootView, R.layout.scene2, this);
        TransitionManager.go(scene2, new ChangeBounds());
    }
}
