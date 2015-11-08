package cxsjcj.hdsx.com.network;

import com.android.volley.Response;

import cxsjcj.hdsx.com.myapplication.NetApplication;


/**
 * @author 梁铖城
 * @brief 进行请求
 * @date 2015年8月14日09:34:06
 */
public class GsonDecode<T> {


    public void getGsonData(String url, Class<T> cls, Response.Listener<T> listener,
                            Response.ErrorListener errorListener) {

        GsonRequest<T> gsonRequest = new GsonRequest<T>(
                url,
                cls, listener, errorListener);
        NetApplication.requestQueue.add(gsonRequest);
    }
}
