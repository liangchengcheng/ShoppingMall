package cxsjcj.hdsx.com.network.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;

import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.myapplication.NetApplication;

/**
 *创建一个用volley加载图片的viewholder
 */
public class ViewHolderImage  extends RecyclerView.ViewHolder{
    private final NetworkImageView mNetworkImaggeView;

    public ViewHolderImage(View itemView) {
        super(itemView);

        mNetworkImaggeView= (NetworkImageView) itemView.findViewById(R.id.networkImageView);
//        mNetworkImaggeView.setDefaultImageResId();
//        mNetworkImaggeView.setErrorImageResId();;
    }
    public void setData(String imageUrl)
    {
        mNetworkImaggeView.setImageUrl(imageUrl, NetApplication.getInstance().getVolleyImageLoader());
    }
}
