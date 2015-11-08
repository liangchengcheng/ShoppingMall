package cxsjcj.hdsx.com.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月31日20:15:45
 * Description: 一些基本的简单的操作的类
 */
// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
public class Utils {

    //将单位为dp的转换为px
    static int dpToPx(float dp, Resources resources) {
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    //读取文本的基本类
    public static String readText(InputStream source) throws IOException {
        return readText(source, null);
    }

    public static String readText(InputStream source, String encoding) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(source, encoding == null ? "UTF-8" : encoding);
        char[] cs = new char[10240];
        int len;
        while ((len = isr.read(cs)) > 0) {
            sb.append(cs, 0, len);
        }
        isr.close();
        return sb.toString();
    }
}
