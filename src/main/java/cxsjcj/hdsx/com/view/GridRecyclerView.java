package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年9月16日08:57:11
 * Description: 一个格子的ry的带载入小动画的封装
 */
public class GridRecyclerView extends RecyclerView {
//注意：下面的注释是这个类的使用方法
//    <com.antonioleiva.materializeyourapp.widgets.GridRecyclerView
//    android:id="@+id/recycler"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:layoutAnimation="@anim/grid_layout_animation"
//    app:layout_behavior="@string/appbar_scrolling_view_behavior"/>
//@Override public void onEnterAnimationComplete() {
//    super.onEnterAnimationComplete();
//    setRecyclerAdapter(recyclerView);
//    recyclerView.scheduleLayoutAnimation();
//}
    public GridRecyclerView(Context context) {
        super(context);
    }

    public GridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new ClassCastException("You should only use a GridLayoutManager with GridRecyclerView.");
        }
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {
        if (getAdapter() != null && getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutAnimationController.AnimationParameters animationParams = (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            int columns = ((GridLayoutManager) getLayoutManager()).getSpanCount();

            animationParams.count = count;
            animationParams.index = index;
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}
