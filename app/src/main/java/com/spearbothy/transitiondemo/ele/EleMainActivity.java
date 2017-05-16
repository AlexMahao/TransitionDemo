package com.spearbothy.transitiondemo.ele;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spearbothy.transitiondemo.R;

/**
 * Created by mahao on 17-5-16.
 */

public class EleMainActivity extends AppCompatActivity {

    public static final String VIEW_INFO_EXTRA = "VIEW_INFO_EXTRA";

    private View mSearchBgVIew;

    public static EleMainActivity activity;
    private View mInputView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele);
        activity = this;
        mSearchBgVIew = findViewById(R.id.tv_search_bg);
        mInputView = findViewById(R.id.input);

        mSearchBgVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EleMainActivity.this, EleSearchActivity.class);
                intent.putExtra(VIEW_INFO_EXTRA, createBuildInfo());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private Bundle createBuildInfo() {
        int[] screenLocation = new int[2];
        mInputView.getLocationOnScreen(screenLocation);
        Bundle b = new Bundle();
        int left = screenLocation[0];
        b.putInt("left", left);
        return b;
    }


    public void hide() {
        mSearchBgVIew.setVisibility(View.INVISIBLE);
    }

    public void show() {
        mSearchBgVIew.setVisibility(View.VISIBLE);
    }

}
