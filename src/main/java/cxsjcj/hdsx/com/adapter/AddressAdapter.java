package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * @author: 梁铖城
 * @date: 2015年8月29日18:48:5
 * @description: 地址的数据装载器
 * @blog: http:www.17yxb.cn
 */
public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<AddressBean> linkmans = new ArrayList<AddressBean>();
    private View.OnClickListener clickListener;
    public AddressAdapter(Context context, List<AddressBean> linkmans){
        this.context = context;
        this.linkmans = linkmans;
    }
    @Override
    public int getCount() {
        return linkmans.size();
    }
    @Override
    public Object getItem(int position) {
        return linkmans.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.activity_shopping_address_item, null);
            holder = new ViewHolder();
            holder.selected =  (ImageView) convertView.findViewById(R.id.iv_ad_selected );
            holder.name =  (TextView) convertView.findViewById(R.id.tv_ad_linkman );
            holder.phone =  (TextView) convertView.findViewById(R.id.tv_ad_phone );
            holder.address =  (TextView) convertView.findViewById(R.id.tv_address );
            holder.ad_layout =  (FrameLayout) convertView.findViewById(R.id.fl_ad_select );
            holder.ad_edit =  (FrameLayout) convertView.findViewById(R.id.fl_ad_edit );
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String Isdefault=linkmans.get(position).getIsdefault();
        if (Isdefault.equals("是")) {
            holder.selected.setVisibility(View.VISIBLE);
            context.getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit().putString("addressee1",linkmans.get(position).getAddressee())
                    .putString("aphone1", linkmans.get(position).getPhone()).
                    putString("aid", linkmans.get(position).getAID()).putString("address1", linkmans.get(position).getAddress())
                   .commit();

        }else {
            holder.selected.setVisibility(View.GONE);
        }
        holder.ad_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.name.setText(linkmans.get(position).getAddressee());
        holder.phone.setText(linkmans.get(position).getPhone());
        holder.address.setText(linkmans.get(position).getAddress());
        return convertView;
    }
    static class ViewHolder{
        private ImageView selected;
        private TextView name;
        private TextView phone;
        private TextView address;
        private FrameLayout ad_layout;
        private FrameLayout ad_edit;
    }
}
