package cxsjcj.hdsx.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月16日10:25:59
 * Description: 数据存储的基本的类
 */

// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
public class PreferenceUtils{

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor shareEditor;

    private static PreferenceUtils preferenceUtils = null;

    public static final String NOTE_TYPE_KEY = "NOTE_TYPE_KEY";

    private PreferenceUtils(Context context){
        sharedPreferences = context.getSharedPreferences("lcc", Context.MODE_PRIVATE);
        shareEditor = sharedPreferences.edit();
    }

    public static PreferenceUtils getInstance(Context context){
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(context.getApplicationContext());
                }
            }
        }
        return preferenceUtils;
    }

    public String getStringParam(String key){
        return getStringParam(key, "");
    }

    public String getStringParam(String key, String defaultString){
        return sharedPreferences.getString(key, defaultString);
    }

    public void saveParam(String key, String value)
    {
        shareEditor.putString(key,value).commit();
    }

    public boolean getBooleanParam(String key){
        return getBooleanParam(key, false);
    }

    public boolean getBooleanParam(String key, boolean defaultBool){
        return sharedPreferences.getBoolean(key, defaultBool);
    }

    public void saveParam(String key, boolean value){
        shareEditor.putBoolean(key, value).commit();
    }

    public int getIntParam(String key){
        return getIntParam(key, 0);
    }

    public int getIntParam(String key, int defaultInt){
        return sharedPreferences.getInt(key, defaultInt);
    }

    public void saveParam(String key, int value){
        shareEditor.putInt(key, value).commit();
    }

    public long getLongParam(String key){
        return getLongParam(key, 0);
    }

    public long getLongParam(String key, long defaultInt){
        return sharedPreferences.getLong(key, defaultInt);
    }

    public void saveParam(String key, long value){
        shareEditor.putLong(key, value).commit();
    }
}
