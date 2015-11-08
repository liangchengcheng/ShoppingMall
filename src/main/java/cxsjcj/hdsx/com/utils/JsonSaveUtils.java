package cxsjcj.hdsx.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: 梁铖城
 * @date: 2015年9月2日09:09:53
 * @description: 一个json的缓存的类  需要注意的是当前的这个只能给一个缓存一个数据，若果需要改变则自己修改
 * @blog: http:www.17yxb.cn
 */
public class JsonSaveUtils {

    /**
     * 将json从网上获取下来的时候顺便保存到sp里面
     *
     * @param context  上下文对象
     * @param data     需要缓存的json数据
     * @param savetime 需要保存的时间
     */
    public static void saveJsonData(Context context, String data, int savetime) {
        //获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences("json", Context.MODE_PRIVATE);
        //保存json的数据
        sp.edit().putString("jsondata", data).commit();
        //获取当前的时间
        String localtime = SingleTrueUtils.singlechou(System.currentTimeMillis());
        //保存当前的时间
        sp.edit().putString("currenttime", localtime).commit();
        //保存需要保存的时间
        sp.edit().putString("savetime", savetime + "").commit();
    }

    /**
     * 判断是否有sp的缓存的数据，过期数据归类为无数据
     *
     * @param context 获取sp的上下文对象
     * @return 是否存在
     */
    public static boolean isHaveData(Context context) {
        //获取sp对象
        SharedPreferences sp = context.getSharedPreferences("json", Context.MODE_PRIVATE);
        //获取保存的json的数据
        String havadata = sp.getString("jsondata", null);
        //判断数据是否为空或者" "
        if (havadata == null || "".equals(havadata)) {
            return false;
        } else {
            //获取保存的时候的那个时间点
            String updatetime = sp.getString("currenttime", null);
            //获取保存的天数
            String savetime = sp.getString("savetime", null);
            //判断上述的2个条件是否为空
            if (updatetime != null && savetime != null) {
                //获取当前的时间
                String localtime = SingleTrueUtils.singlechou(System.currentTimeMillis());
                int savedate = Integer.parseInt(savetime);
                //潘丹当前的时间和保存数据那会的时间是否超过过期
                if ((Long.parseLong(localtime) - Long.parseLong(updatetime)) / (60 * 60 * 24) > savedate) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 获取保存的json数据，不存在的话返回空格
     * @param context 获取上下文对象
     * @return 返回保存的json数据
     */
    public static String getJsonData(Context context) {
        if (isHaveData(context)) {
            SharedPreferences sp = context.getSharedPreferences("json", Context.MODE_PRIVATE);
            //获取保存的json的数据
            String havadata = sp.getString("jsondata", null);
            return havadata;
        } else {
            return " ";
        }
    }
}
