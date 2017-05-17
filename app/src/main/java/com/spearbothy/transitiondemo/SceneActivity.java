package com.spearbothy.transitiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;

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
