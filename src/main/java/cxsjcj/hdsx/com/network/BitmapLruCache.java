package cxsjcj.hdsx.com.network;


import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
	public BitmapLruCache(int maxSize) {
		super(maxSize);
	}
	

	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}
	
//	@Override
//	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
//			Bitmap newValue) {
//		Logger.d("BitmapLruCache","bitmap recycle");
//		if(evicted) 
//		oldValue.recycle();
//		super.entryRemoved(evicted, key, oldValue, newValue);
//	}
	
	

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
		Log.d("BitmapLruCache", "bitmap size1>>"+bitmap.getRowBytes() * bitmap.getHeight()+"size2>>"+bitmap.getByteCount());
	}
}
