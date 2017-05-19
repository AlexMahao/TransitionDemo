package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.spearbothy.transitiondemo.App;
import com.spearbothy.transitiondemo.R;

/**
 * Created by mahao on 17-5-19.
 */

public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    public TitleBehavior() {
    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = -getTitleHeight();
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        int headerOffsetRange = getHeaderOffsetRange();
        int titleOffsetRange = getTitleHeight();

        if (dependency.getTranslationY() == headerOffsetRange) {
            child.setTranslationY(titleOffsetRange); //直接设置终值，避免出现误差
        } else if(dependency.getTranslationY()==0){
            child.setTranslationY(0);
        }else{
            child.setTranslationY(dependency.getTranslationY()/headerOffsetRange*titleOffsetRange);
        }
    }

    private int getHeaderOffsetRange() {
        // 头部可偏移的最大范围
        return App.sApp.getResources().getDimensionPixelOffset(R.dimen.header_offset);
    }

    //标题高度
    private int getTitleHeight() {
        return App.sApp.getResources().getDimensionPixelOffset(R.dimen.title);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.header;
    }

}
