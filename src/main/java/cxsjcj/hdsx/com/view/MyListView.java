package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月2日15:02:07
 * Description:
 */
public class MyListView extends ListView {

    private final String TAG = "MyListView";

    public MyListView(Context context){
        super(context);
    }
    
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

    }
}
