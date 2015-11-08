package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date: 2015年11月2日14:13:29
 * Description:  实现首页画廊效果
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Product> mDatas;

    public GalleryAdapter(Context context, List<Product> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_horizontal_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view
                .findViewById(R.id.id_index_gallery_item_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.mImg.setImageResource(mDatas.get(i).getImg());
        viewHolder.mTxt.setText(mDatas.get(i).getTitle());
    }

}
