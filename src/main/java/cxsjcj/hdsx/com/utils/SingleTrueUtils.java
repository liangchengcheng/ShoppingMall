package cxsjcj.hdsx.com.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 梁铖城
 * @date: 2015年9月2日10:25:44
 * @description: 时间和时间戳的操作
 * @blog: http:www.17yxb.cn
 */
public class SingleTrueUtils {
    /*将一个时间格式化成用户可见的时间*/
    public static String singltime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);

    }

    /*将字符串的时间戳截取到10位*/
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /*将字符串的时间戳截取到10位*/
    public static String singlechou(long time) {
        String timechou = time + "";
        timechou = timechou.substring(0, timechou.length() - 3);
        return timechou;
    }

    //获取当前的时间
    public static long getTime() {
        return System.currentTimeMillis();
    }
}
