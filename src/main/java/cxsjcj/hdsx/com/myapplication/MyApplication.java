package cxsjcj.hdsx.com.myapplication;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by chengcheng on 2015/6/26.
 */
public class MyApplication extends Application {
    public static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        // 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
        requestQueue = Volley.newRequestQueue(this);
        // 初始化fresco库
        Fresco.initialize(this);
    }
}
