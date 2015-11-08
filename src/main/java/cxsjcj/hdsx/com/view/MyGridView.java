package cxsjcj.hdsx.com.view;

import android.widget.GridView;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月31日20:18:23
 * Description: 固定高度的gridview（用在将gridview固定在scrollview里面的时候造成gridviw不能进行滑动的bug）
 */
public class MyGridView extends GridView {
	public MyGridView(android.content.Context context,
					  android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}