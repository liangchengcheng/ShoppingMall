package cxsjcj.hdsx.com.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cxsjcj.hdsx.com.bean.Jinbi;

/**
 * @author 梁铖城
 * @brief 自定义
 * @date 2015年8月14日09:03:15
 */
public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClass;

    /**
     * GsonRequest的构造函数
     * @param method 请求的方式
     * @param url    请求的url地址
     * @param clazz  传入进来的类型[暂时删除]
     * @param listener 监听器
     * @param errorListener 错误的监听
     */
    public GsonRequest(int method, String url,Class<T> clazz,  Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }
    /**
     *构造函数
     * @param url    请求的url地址
     * @param clazz  传入进来的类型[暂时删除]
     * @param listener 监听器
     * @param errorListener 错误的监听
     */
    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }
    /**
     * 在这里我先暂时的写死成具体的数据类，到时候看看怎么修改这块
     * @param response volleyresponse
     * @return  json  by  gson to list<Objetc>
     */
    @Override
    protected Response <T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            //此处对数据是否为空做判断
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    /**
     * 处理错误
     * @param response 响应流
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
