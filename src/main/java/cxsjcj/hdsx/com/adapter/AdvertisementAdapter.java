package cxsjcj.hdsx.com.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.ImageLoaderUtil;


/**
 * 广告轮播adapter
 *@author dong
 *@date 2015年3月8日下午3:46:35
 *@contance dong854163@163.com
 */
public class AdvertisementAdapter extends PagerAdapter {

	private Context context;
	private List<View> views;
	JSONArray advertiseArray;
	
	public AdvertisementAdapter() {
		super();
	}

	public AdvertisementAdapter(Context context, List<View> views, JSONArray advertiseArray) {
		this.context = context;
		this.views = views;
		this.advertiseArray = advertiseArray;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		final int POSITION = position;
		View view = views.get(position);
		try {
			//图片的下载地址在json里面
			String head_img = advertiseArray.optJSONObject(position).optString("head_img");
			ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
			ImageLoaderUtil.getImage(context, ivAdvertise, head_img, R.drawable.loading1, R.drawable.ic_launcher, 0, 0);
			ivAdvertise.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "第"+POSITION+"个广告图片被点击", Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
