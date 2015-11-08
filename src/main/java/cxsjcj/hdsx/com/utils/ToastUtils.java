package cxsjcj.hdsx.com.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author: 梁铖城
 * @date: 2015年9月2日10:29:28
 * @description: Toast 的工具类
 * @blog: http:www.17yxb.cn
 */
public class ToastUtils {
    public static Toast mToast;
    public static  void showToast(Context context ,String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
