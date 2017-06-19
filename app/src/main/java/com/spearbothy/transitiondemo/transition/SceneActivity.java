package com.spearbothy.transitiondemo.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.spearbothy.transitiondemo.R;


/**
 * 第一种,基于场景的动画
 * <p>
 * 原理： 将rootView下的元素全部移除，然后加载end的场景
 * <p>
 * - 构造结束场景Scene
 * -- rootView  需要变化的parent
 * -- view  变化的视图
 * - 指定变化的方式
 * <p>
 * --   ChangeBounds 位置变化 （根据id）
 * <p>
 * <p>
 * - TransitionManager.go(Scene,transition); // 变化场景
 * <p>
 * 第二种，基于场景变化监听的动画
 * <p>
 * 对于第一种，我们需要创建场景，为了简单，提供第二种方式
 * <p>
 * TransitionManager.beginDelayedTransition(RootView, transition);
 * <p>
 * 监听某一节点下的视图状态与对应的动画
 * <p>
 * 当rootView下有相关view发生了与对应transition所监听的状态变化时，将执行动画
 * <p>
 * 系统会保存一个当前视图树状态的场景，然后我们直接修改视图树，在下一次绘制时，系统会自动对比之前保存的视图树，然后执行一步动画。
 * <p>
 * <p>
 * 控件匹配的规则
 * <p>
 * - Transition  中的匹配规则
 * private static final int[] DEFAULT_MATCH_ORDER = {
 * MATCH_NAME,   // xml中 transitionName
 * MATCH_INSTANCE,  //  具有相同的引用对象
 * MATCH_ID,  // id
 * MATCH_ITEM_ID, // ListView 的item id
 * };
 */
public class SceneActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        rootView = (FrameLayout) findViewById(R.id.rootView);
        // 重新加载布局
        findViewById(R.id.image1).setOnClickListener(this);
        findViewById(R.id.image2).setOnClickListener(this);
        findViewById(R.id.image3).setOnClickListener(this);

//        Transition mFadeTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition);
    }

    public void begin(View view) {
        // 加载场景
        Scene scene2 = Scene.getSceneForLayout(rootView, R.layout.scene2, this);
        // Transition
        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.addTarget(R.id.image2);
        // 启动动画
        TransitionManager.go(scene2, changeBounds);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }
}
