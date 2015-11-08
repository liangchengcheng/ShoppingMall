package cxsjcj.hdsx.com.network.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * GsonGetRequest继承自 volley的request
 *
 * @param <T>
 */
public class GsonGetRequest<T> extends Request<T> {
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    /**
     * 一个get的请求返回一个解析了的json
     *
     * @param url           URL of the request to make
     * @param type          is the type of the object to be returned
     * @param listener      is the listener for the right answer
     * @param errorListener is the listener for the wrong answer
     */
    public GsonGetRequest
    (String url, Type type, Gson gson,
     Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    /**
     * 响应流来处理错误
     * @param response
     */
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * 解析网络的请求
     * @param response 一个返回的响应流
     * @return 将得到的json解析成一个集合返回
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsons = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject josnobject = new JSONObject(jsons);
            JSONArray array = josnobject.getJSONArray("ds");
            String json = array.toString();
            Log.e("lcc", json);
            return (Response<T>) Response.success
                    (
                            gson.fromJson(json, type),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
