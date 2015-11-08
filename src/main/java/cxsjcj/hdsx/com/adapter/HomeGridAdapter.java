package cxsjcj.hdsx.com.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Created by Wasabeef on 2015/01/03.
 */
public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private static int[] draws = new int[]{
            R.drawable.bb,   R.drawable.cc,  R.drawable.dd,   R.drawable.ee
    };
    private ArrayList<Product> mDataSet;

    public HomeGridAdapter(Context context) {
        mContext = context;
        mDataSet=new ArrayList<>();
    }


    public void addTitles(List<Product> dataSet) {
        mDataSet.addAll(dataSet);
        notifyItemRangeInserted(0, dataSet.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.home_page_grid_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mDataSet.get(position).getImg()).into(holder.image);
        holder.text.setText(mDataSet.get(position).getTitle());
        holder.item_view.setTag(mDataSet.get(position).getTitle());
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

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,String.valueOf(v.getTag()));
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

    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
