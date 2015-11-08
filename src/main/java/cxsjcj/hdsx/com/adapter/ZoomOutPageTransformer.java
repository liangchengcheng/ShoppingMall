package cxsjcj.hdsx.com.adapter;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:  2015年10月25日15:46:17
 * Description:  关于viewpager的动画的实现的基本的PageTransformer
 * Note:    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1030/1870.html(兼容低版本的介绍)
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @SuppressLint("NewApi")
    public void transformPage(View view, float position)
    {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        Log.e("TAG", view + " , " + position + "");
        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0)
            {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else
            {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
