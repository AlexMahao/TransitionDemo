package com.spearbothy.transitiondemo.behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.MyHeaderBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spearbothy.transitiondemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahao on 17-5-19.
 */

public class BehaviorActivity extends AppCompatActivity {

    private RecyclerView mRecycler;

    private List<String> mData = new ArrayList<>();
    private MyAdapter mAdapter;
    private FrameLayout mHeaderLayout;
    private MyHeaderBehavior mHeaderBehavior;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        initData();

        mRecycler = (RecyclerView) findViewById(R.id.content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter();
        mRecycler.setAdapter(mAdapter);

        mHeaderLayout = (FrameLayout) findViewById(R.id.header);
        mHeaderBehavior = (MyHeaderBehavior) ((CoordinatorLayout.LayoutParams) mHeaderLayout.getLayoutParams()).getBehavior();


        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mHeaderLayout!=null && mHeaderBehavior.isClose()){
            mHeaderBehavior.openPager();
        }else{
            super.onBackPressed();
        }
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add(i + "");
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.item_behavior, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
