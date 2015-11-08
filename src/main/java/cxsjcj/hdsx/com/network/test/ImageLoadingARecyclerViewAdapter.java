package cxsjcj.hdsx.com.network.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * Created by chengcheng on 2015/7/28.
 */
public class ImageLoadingARecyclerViewAdapter extends RecyclerView.Adapter {
    private final List<String>mInages;
    public ImageLoadingARecyclerViewAdapter(List<String> images)
    {
        mInages=images;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_network_image_view,parent,false);
        return  new ViewHolderImage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //直接把viewholder提取在外面了
        final  ViewHolderImage viewHolderImage= (ViewHolderImage) holder;
    }

    @Override
    public int getItemCount() {
        return mInages.size();
    }
}
