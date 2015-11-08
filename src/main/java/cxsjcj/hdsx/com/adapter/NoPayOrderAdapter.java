package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.itangqi.greendao.Note;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.activity.order.OrderProgressActiviy;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:     2015年9月20日22:20:33
 * Description:
 */
public class NoPayOrderAdapter extends RecyclerView.Adapter<NoPayOrderAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Note> mDataSet;

    private static int[] draws = new int[]{
            R.drawable.bb,   R.drawable.cc,  R.drawable.dd,   R.drawable.ee
    };

    public NoPayOrderAdapter(Context context, List<Note> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.order_item, parent, false);
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
        holder.tv_prices.setText(mDataSet.get(position).getJiage() + "元");
        holder.tv_state.setText("等待付款中");
        holder.btn_comment.setText("付款");
        holder.btn_delete.setText("取消订单");
        holder.item_view.setTag(mDataSet.get(position));
        holder.btn_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderProgressActiviy.class));
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext).iconRes(R.drawable.ic_launcher)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title("删除订单")
                        .content("你确定要删除你的订单么？删除后")
                        .positiveText("删除")
                        .negativeText("取消")
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();
            }
        });
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
        public TextView tv_state;
        public View item_view;
        public Button btn_comment;
        public Button btn_delete;
        public Button btn_logistics;

        public ViewHolder(View itemView) {
            super(itemView);
            item_view=itemView;
            iv_shopping_things = (ImageView) itemView.findViewById(R.id.iv_command);
            tv_prices = (TextView) itemView.findViewById(R.id.tv_princes);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            btn_comment= (Button) itemView.findViewById(R.id.btn_comment);
            btn_delete= (Button) itemView.findViewById(R.id.btn_delete);
            btn_logistics= (Button) itemView.findViewById(R.id.btn_look);
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
