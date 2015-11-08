package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/1/15  18:18.
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/1/15        ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class AnimAdapter extends RecyclerView.Adapter<AnimAdapter.NormalTextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<Product> mTitles = new ArrayList<>();

    public AnimAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addTitles(ArrayList<Product>  titles) {
      // Collections.addAll(mTitles, titles);
        mTitles.addAll(titles);
        notifyItemRangeInserted(0, titles.size());
    }

    public void addTitle(Product title) {
        mTitles.add(1, title);
        notifyItemInserted(1);
    }

    public void remove(int position) {
        mTitles.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.grid_item_text, parent, false), this);
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTextView.setText(mTitles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view)
        TextView mTextView;
        AnimAdapter mAdapter;

        NormalTextViewHolder(View view, AnimAdapter adapter) {
            super(view);
            mAdapter = adapter;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.cv_item)
        void onItemClick() {
            Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
            if (getPosition() == 2) {
                mAdapter.remove(getPosition());
            } else {

            }
        }
    }
}
