package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author: 梁铖城
 * @date: 2015年9月9日08:17:51
 * @description: 一个禁止左右滑动的viewpager
 * @blog: http:www.17yxb.cn
 */
public class CustomViewPager extends ViewPager {

    private boolean isCanScroll = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y){
        super.scrollTo(x, y);

    }

    @Override
    public void setCurrentItem(int item) {

        super.setCurrentItem(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        if (isCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

}
