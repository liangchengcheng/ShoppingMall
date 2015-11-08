package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月2日09:37:58
 * Description:  关于内容更新的适配器
 */
public class ContentAdapter extends BaseAdapter  {

    private List<Product> mDatas;
    private LayoutInflater mLayoutInflater;

    public ContentAdapter(Context context, List<Product> mDatas){
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.listview_tow_item,null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_contents);
            viewHolder.iv_content_image = (ImageView) view.findViewById(R.id.iv_content_image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mDatas.get(i).getTitle());
        viewHolder.iv_content_image.setBackgroundResource(mDatas.get(i).getImg());
        return view;
    }

    class ViewHolder{
        TextView textView;
        ImageView iv_content_image;
    }
}
