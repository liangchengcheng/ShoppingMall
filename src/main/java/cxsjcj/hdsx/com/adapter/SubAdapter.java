package cxsjcj.hdsx.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cxsjcj.hdsx.com.myapplication.R;

@SuppressLint("InflateParams") public class SubAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	String[] citiy;
	public int foodpoition;

	public SubAdapter(Context context, String[] citiy) {
		this.context = context;
		this.citiy = citiy;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return citiy.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return citiy[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.items, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(citiy[position]);
		viewHolder.textView.setTextColor(Color.BLACK);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
