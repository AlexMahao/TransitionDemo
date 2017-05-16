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

    private View mSearchBgVIew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele);

        mSearchBgVIew = findViewById(R.id.tv_search_bg);

        mSearchBgVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EleMainActivity.this,EleSearchActivity.class);
                int location[] = new int[2];
                mSearchBgVIew.getLocationOnScreen(location);
                intent.putExtra("x",location[0]);
                intent.putExtra("y",location[1]);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
