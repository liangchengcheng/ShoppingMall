package cxsjcj.hdsx.com.network.test;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import cxsjcj.hdsx.com.network.api.GsonGetRequest;

/**
 * Created by chengcheng on 2015/7/28.
 */
public class AllTest {
    //1
//    private void ImageQuest() {
//        final ImageRequest imageRequest = new ImageRequest("imageurl", new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                //imageview.setimage(response)
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //设置一个失败的图片就是了
//            }
//        });
//    }

    private void networkimage() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(new ImageLoadingARecyclerViewAdapter(sIMAGES));
    }

    private void gsonget()
    {
        final GsonGetRequest<DummyObject>getgson;//这里还没写完呢
    }
}
