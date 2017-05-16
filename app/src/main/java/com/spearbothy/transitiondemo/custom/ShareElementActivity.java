package com.spearbothy.transitiondemo.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spearbothy.transitiondemo.R;

/**
 * Created by mahao on 17-5-16.
 */

public class ShareElementActivity extends AppCompatActivity {

    public static final String VIEW_INFO_EXTRA = "VIEW_INFO_EXTRA";

    private TextView mShareTextVIew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_share_element_1);

        mShareTextVIew = ((TextView) findViewById(R.id.share));
    }


    public void next(View view) {
        Intent intent = new Intent(this, ShareElement2Activity.class);
        intent.putExtra(VIEW_INFO_EXTRA, createBuildInfo());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private Bundle createBuildInfo() {
        int[] screenLocation = new int[2];
        mShareTextVIew.getLocationOnScreen(screenLocation);
        Bundle b = new Bundle();
        int left = screenLocation[0];
        int top = screenLocation[1];
        int width = mShareTextVIew.getWidth();
        int height = mShareTextVIew.getHeight();
        b.putInt("left", left);
        b.putInt("top", top);
        b.putInt("width", width);
        b.putInt("height", height);
        return b;
    }
}
