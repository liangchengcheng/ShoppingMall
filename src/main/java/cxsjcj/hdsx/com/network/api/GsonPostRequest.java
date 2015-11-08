package cxsjcj.hdsx.com.network.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * GsonPostRequest 一个post的请求
 * @author http:www.17yxb.cn
 */
public class GsonPostRequest<T> extends JsonRequest<T>
{
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    /**
     * 一个post的请求够赞函数
     *
     * @param url 制造出一个请求的地址
     * @param type 规定了一个返回的类型
     * @param listener 一个正确响应的监听器
     * @param errorListener  一个错误响应的监听器
     */
    public GsonPostRequest
    (String url, String body, Type type, Gson gson,
     Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, url, body, listener, errorListener);

        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.e("nima",json);
            return (Response<T>) Response.success
                    (
                            gson.fromJson(json, type),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
    }
}
