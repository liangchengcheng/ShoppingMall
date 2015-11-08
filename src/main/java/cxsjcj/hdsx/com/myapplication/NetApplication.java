package cxsjcj.hdsx.com.myapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.itangqi.greendao.DaoMaster;
import com.itangqi.greendao.DaoSession;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.okhttp.OkHttpClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cxsjcj.hdsx.com.contacts.AppContacts;
import cxsjcj.hdsx.com.network.other.LruBitmapCache;
import cxsjcj.hdsx.com.network.other.OkHttpStack;

/**
 * Created by chengcheng on 2015/7/26
 * 注意此处2个.RequestQueue应该合并为一个
 */
public class NetApplication extends Application {
    // Singleton application mInstance
    private static NetApplication mInstance;
    // Volley request queue
    private RequestQueue mRequestQueue;
    // Volley image cache
    private LruBitmapCache mLruBitmapCache;
    // Volley image loader
    private ImageLoader mImageLoader;
    // Volley RequestQueue
    public static RequestQueue requestQueue;
    //sqlite相关
    public DaoSession daoSession;
    public SQLiteDatabase db;
    public DaoMaster.DevOpenHelper helper;
    public DaoMaster daoMaster;
    private static String DB_PATH = Environment.getExternalStorageDirectory().getPath();
    private static String DB_NAME = "meituan_cities.db";
    private static String ASSETS_NAME = "meituan_cities.db";

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        // 初始化fresco库
        Fresco.initialize(this);
        mInstance = this;
        setuoDatabase();



        ImageLoaderConfiguration.Builder configBuilder = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory();
        configBuilder.tasksProcessingOrder(QueueProcessingType.LIFO);
        //.writeDebugLogs(); // Remove for release app

        ImageLoaderConfiguration config = configBuilder.build();
        // Initialize ImageLoader with configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }

    /**
     * 返回一个单例的NetApplication
     *
     * @return 返回一个单例的NetApplication
     */
    public static NetApplication getInstance() {
        return mInstance;
    }

    /**
     * 返回一个volley的请求为了创建一个网络的请求
     *
     * @return {@link com.android.volley.RequestQueue}
     */
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    private static void addRequest(Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(Request<?> request, String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    /**
     * 退出所有的消息队列
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(String tag) {
        if (getInstance().getVolleyRequestQueue() != null) {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }

    public ImageLoader getVolleyImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader
                    (
                            getVolleyRequestQueue(),
                            NetApplication.getInstance().getVolleyImageCache()
                    );
        }
        return mImageLoader;
    }

    private void copyDataBase() throws IOException {
        //用流的方式去打开一个数据库
        InputStream myInput = getAssets().open(ASSETS_NAME);
        //数据库的具体的地方
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //创建一个图片的缓存
    private LruBitmapCache getVolleyImageCache() {
        if (mLruBitmapCache == null) {
            mLruBitmapCache = new LruBitmapCache(mInstance);
        }
        return mLruBitmapCache;
    }

    /*初始化数据这个数据库了库相关的东西*/
    private void setuoDatabase() {
        //通过datamaster的内部的类devophelper，你将可以得到一个便利的sqliteopenheler对象
        //可能你已经注意到了，你并不需要去编写这个数据库了，因为greendao一已经帮你做好了
        //注意的话 默认的daomaster devophelper会在数据库升级的时候就删除所有的数据了
        //所以在省事的项目里面一定要做一层封装
        helper = new DaoMaster.DevOpenHelper(this, AppContacts.DB_NAME, null);
        db = helper.getWritableDatabase();
        //需要注意的是数据库连接属于daomaster 所以session值的是相同的数据
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}

