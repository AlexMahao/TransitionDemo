package com.spearbothy.transitiondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mahao on 17-5-15.
 */
public class ShareElementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_elements);
        Fragment sharedElementFragment1 = new ShareElementsFragment1();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_container, sharedElementFragment1)
                .commit();
    }
}
