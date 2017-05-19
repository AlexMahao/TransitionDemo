package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.spearbothy.transitiondemo.App;
import com.spearbothy.transitiondemo.R;

import java.util.List;


/**
 * Created by mahao on 17-5-19.
 */

public class ContentBehavior extends HeaderScrollingViewBehavior {

    public ContentBehavior() {
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    private boolean isDependOn(View dependency) {
        return  dependency != null && dependency.getId() == R.id.header;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    @Override
    protected int getScrollRange(View v) {
        // 可滚动的偏移
        if (isDependOn(v)) {
            return Math.max(0, v.getMeasuredHeight()-getFinalHeight());
        } else {
            return super.getScrollRange(v);
        }
    }

    private int getFinalHeight() {
        return App.sApp.getResources().getDimensionPixelOffset(R.dimen.tab) + App.sApp.getResources().getDimensionPixelOffset(R.dimen.title);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view))
                return view;
        }
        return null;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY((int) (-dependency.getTranslationY() / (getHeaderOffsetRange() * 1.0f) * getScrollRange(dependency)));
    }

    private int getHeaderOffsetRange() {
        // 头部可偏移的最大范围
        return App.sApp.getResources().getDimensionPixelOffset(R.dimen.header_offset);
    }

}
