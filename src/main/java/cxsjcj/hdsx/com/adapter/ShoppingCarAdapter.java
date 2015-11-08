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
import com.itangqi.greendao.Note;
import java.util.ArrayList;
import java.util.List;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:     2015年9月20日22:20:33
 * Description:
 */
public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Note> mDataSet;

    private static int[] draws = new int[]{
            R.drawable.bb,   R.drawable.cc,  R.drawable.dd,   R.drawable.ee
    };

    public ShoppingCarAdapter(Context context, List<Note> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.shopping_car_item, parent, false);
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
                .into(holder.iv_shopping_things);
        holder.tv_prices.setText(mDataSet.get(position).getJiage()+"元");
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

    public void add(ArrayList<Note> text) {
        mDataSet.addAll(text);
        notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Note) v.getTag());
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_shopping_things;
        public TextView tv_prices;
        public TextView tv_name;
        public View item_view;

        public ViewHolder(View itemView) {
            super(itemView);
            item_view=itemView;
            iv_shopping_things = (ImageView) itemView.findViewById(R.id.iv_shopping_things);
            tv_prices = (TextView) itemView.findViewById(R.id.tv_prices);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Note data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
