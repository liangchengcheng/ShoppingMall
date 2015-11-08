package cxsjcj.hdsx.com.network;


import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class RequestManager {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private RequestManager() {
		// no instances
	}
	

	public static void init(Context context) {
		if(mRequestQueue == null){
			mRequestQueue = getRequestQueue(context);
		}
		
		if(mImageLoader == null){
			int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
			// Use 1/8th of the available memory for this memory cache.
			int cacheSize = 1024 * 1024 * memClass / 8;
			mImageLoader = new ImageLoader(mRequestQueue,new BitmapLruCache(cacheSize));
		}
	}
	

	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) 
			mRequestQueue = Volley.newRequestQueue(context);
		return mRequestQueue;
	}
	
	public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        request.setRetryPolicy(new DefaultRetryPolicy(8000, 0, 1f));
        mRequestQueue.add(request);
    }
	
	public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
}
