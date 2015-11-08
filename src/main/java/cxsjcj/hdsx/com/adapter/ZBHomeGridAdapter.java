package cxsjcj.hdsx.com.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.bean.Zhuangbei;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * @author  梁铖城
 * @date 2015年8月22日18:20:41
 * @description 装备的列表的数据装载器
 */
public class ZBHomeGridAdapter extends RecyclerView.Adapter<ZBHomeGridAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<Zhuangbei> mDataSet;
//    private static int[] co = new int[]{
//            R.color.md_deep_orange_50, R.color.md_brown_100, R.color.accent_green, R.color.theme3
//    };
    private static int[] draws = new int[]{
            R.drawable.bb,   R.drawable.cc,  R.drawable.dd,   R.drawable.ee
    };

    public ZBHomeGridAdapter(Context context, List<Zhuangbei> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.zb_home_page_grid_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mDataSet.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.loading1)
                .crossFade()
                .into(holder.image);
        holder.text.setText(mDataSet.get(position).getJiage()+"元");
        holder.item_view.setTag(mDataSet.get(position));
        //  holder.ll_back.setBackgroundColor(co[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void add(ArrayList<Zhuangbei> text) {
        mDataSet.addAll(text);
        notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Zhuangbei) v.getTag());
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;
        public LinearLayout ll_back;
        public View item_view;

        public ViewHolder(View itemView) {
            super(itemView);
            item_view=itemView;
            image = (ImageView) itemView.findViewById(R.id.ivFeedCenter);
            text = (TextView) itemView.findViewById(R.id.text);
            ll_back = (LinearLayout) itemView.findViewById(R.id.ll_back);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Zhuangbei data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
