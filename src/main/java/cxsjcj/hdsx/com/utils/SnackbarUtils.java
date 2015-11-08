package cxsjcj.hdsx.com.utils;

import android.app.Activity;
import android.graphics.Color;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月16日09:54:02
 * Description: 封装的一个背景色可以根据当期的主题的颜色变换Snackbar
 */

// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
public class SnackbarUtils {

    public static void show(Activity activity, int message) {
        int color = Color.BLACK;
        if (activity instanceof MyBaseActivity) {
            color = ((MyBaseActivity) activity).getColorPrimary();
        }
        color = color & 0xddffffff;
        SnackbarManager.show(Snackbar.with(activity.getApplicationContext())
                .color(color)
                .duration((Snackbar.SnackbarDuration.LENGTH_SHORT.getDuration() / 2))
                .text(message), activity);
    }
}
