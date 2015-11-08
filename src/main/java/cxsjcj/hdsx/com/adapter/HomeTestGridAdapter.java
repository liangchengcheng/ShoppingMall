package cxsjcj.hdsx.com.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cxsjcj.hdsx.com.myapplication.R;


public class HomeTestGridAdapter extends RecyclerView.Adapter<HomeTestGridAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<String> mDataSet;
    private static int[] draws = new int[]{
            R.drawable.bb,   R.drawable.cc,  R.drawable.dd,   R.drawable.ee
    };

    public HomeTestGridAdapter(Context context, List<String> dataSet) {
        mContext = context;
        mDataSet = dataSet;
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
        Picasso.with(mContext).load(draws[position]).into(holder.image);
        holder.text.setText(mDataSet.get(position));
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

    public void add(String text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
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
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
