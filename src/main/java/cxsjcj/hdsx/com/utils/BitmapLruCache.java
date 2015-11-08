package cxsjcj.hdsx.com.utils;


import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年9月16日13:18:52
 * Description: 定义一个二级缓存
 */
// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
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
