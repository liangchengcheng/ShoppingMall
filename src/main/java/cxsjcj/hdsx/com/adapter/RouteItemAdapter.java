package cxsjcj.hdsx.com.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cxsjcj.hdsx.com.bean.RouteInfo;
import cxsjcj.hdsx.com.myapplication.R;


public class RouteItemAdapter extends BaseAdapter {
	
	private Context context;
	private List<RouteInfo> infos = new ArrayList<RouteInfo>();
	public RouteItemAdapter(Context context,List<RouteInfo> infos){
		this.context = context;
		this.infos = infos;
	}
	@Override
	public int getCount() {
		return infos.size();
	}
	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = View.inflate(context, R.layout.view_route_item, null);
			holder = new ViewHolder();
			holder.icon =  (ImageView) convertView.findViewById(R.id. iv_route_icon );
			holder.icon_top_line =  convertView.findViewById(R.id.icon_top_line);
			holder.icon_bottom_line =  convertView.findViewById(R.id.icon_bottom_line );
			//holder.ll_bottom_line =  (LinearLayout) convertView.findViewById(R.id.ll_bottom_line );
			holder.info =  (TextView) convertView.findViewById(R.id.tv_route_info );
			holder.info.setAutoLinkMask(Linkify.ALL);
			holder.time =  (TextView) convertView.findViewById(R.id.tv_route_time );
		    convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (position == 0) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.logistics_track_arrive));
			convertView.setBackgroundColor(Color.parseColor("#F4F5F5"));
			holder.icon_top_line.setVisibility(View.INVISIBLE);
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
			holder.icon_bottom_line.setBackgroundColor(Color.RED);
		}else if (position == 3) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hourglass_full_black_18dp));
			convertView.setBackgroundColor(Color.parseColor("#F4F5F5"));
			holder.icon_bottom_line.setVisibility(View.INVISIBLE);
			holder.icon_top_line.setVisibility(View.VISIBLE);
		}
		else if (position == 2) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launch_black_18dp));
			convertView.setBackgroundColor(Color.parseColor("#F4F5F5"));
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
			holder.icon_top_line.setVisibility(View.VISIBLE);
		}
		else if (position == 1) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_open_in_browser_black_18dp));
			convertView.setBackgroundColor(Color.parseColor("#F4F5F5"));
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
			holder.icon_top_line.setVisibility(View.VISIBLE);
			holder.icon_top_line.setBackgroundColor(Color.RED);
		}
		else {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.logistics_track_point));
			convertView.setBackgroundColor(Color.parseColor("#F4F5F5"));
			holder.icon_top_line.setVisibility(View.VISIBLE);
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
		}
		
		holder.info.setText(infos.get(position).getInfo());
		holder.time.setText(infos.get(position).getTime());
		return convertView;
	}
	
	static class ViewHolder{
		private ImageView icon;
		private View icon_top_line;
		private View icon_bottom_line;
		private TextView info;
		private TextView time;
	}
}
