package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月31日20:19:13
 * Description: 重写了cardview可以设置它的颜色的padding 的值。
 */
// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
public class CardViewPlus extends CardView {

    private int mColor;

    public CardViewPlus(Context context) {
        this(context, null);
    }

    public CardViewPlus(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //对cardview的背景色进行设置
    @Override
    public void setCardBackgroundColor(int color) {
        super.setCardBackgroundColor(color);
        mColor = color;
    }

    //设置它的padding的值
    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {
        super.setContentPadding(0, 0, 0, 0);
    }

    public int getCardBackgroundColor() {
        return mColor;
    }
}
