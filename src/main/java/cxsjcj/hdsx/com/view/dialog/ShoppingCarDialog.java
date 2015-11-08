package cxsjcj.hdsx.com.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年9月17日10:38:43
 * Description: 一个弹窗弹出选择数量顺便显示价格
 */
public class ShoppingCarDialog extends Dialog{
    public ShoppingCarDialog(Context context) {
        super(context,R.style.progress_dialog);
    }

    public ShoppingCarDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_cart_num);
    }
}
