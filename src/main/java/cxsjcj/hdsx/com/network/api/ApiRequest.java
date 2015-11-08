package cxsjcj.hdsx.com.network.api;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.bean.Jinbi;
import cxsjcj.hdsx.com.bean.JinbiObjectDeserializer;
import cxsjcj.hdsx.com.bean.Zhuangbei;
import cxsjcj.hdsx.com.bean.ZhuangbeiObjectDeserializer;

/**
 * 网路请求的API
 *
 * @author http:www.17yxb.cn
 */
public class ApiRequest {
    /**
     * Returns a dummy object
     *
     * @param listener   一个正确答案的响应流
     * @param errorListener 一个错误答案的响应流
     * @return @return {ArrayList<Jinbi>}
     */
    public static GsonGetRequest<Jinbi> getDummyObject
    (
            Response.Listener<Jinbi> listener,
            Response.ErrorListener errorListener
    ) {
        final String url = "http://www.17yxb.cn/Zhuangbeihandler.ashx?pd=1";

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Jinbi.class, new JinbiObjectDeserializer())
                .create();
        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<Jinbi>() {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }
    /**
     * 返回一个装备的集合
     * @param listener      is the listener for the correct answer
     * @param errorListener is the listener for the error response
     * @return {@link ArrayList<Jinbi>}
     */
    public static GsonGetRequest<ArrayList<Zhuangbei>> getDummyObjectArray
    (
            Response.Listener<ArrayList<Zhuangbei>> listener,
            Response.ErrorListener errorListener,
            int position
    ) {
        final String url = "http://www.17yxb.cn/Zhuangbeihandler.ashx?pd="+position;

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Zhuangbei.class, new ZhuangbeiObjectDeserializer())
                .create();
        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<ArrayList<Zhuangbei>>() {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }
    /**
     * 这个是一个案例展示了如何使用volley的post去发送一段请求：test
     * @param listener        成功的监听器
     * @param errorListener   失败响应的监听器
     * @return {@link ArrayList<Jinbi>}
     */
    public static GsonPostRequest getDummyObjectArrayWithPost
    (
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        final String url = "http://www.17yxb.cn/Zhuangbeihandler.ashx?pd=1";
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Jinbi.class, new JinbiObjectDeserializer())
                .create();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Ficus");
        jsonObject.addProperty("surname", "Kirkpatrick");

        final JsonArray squareGuys = new JsonArray();
        final JsonObject dev1 = new JsonObject();
        final JsonObject dev2 = new JsonObject();
        dev1.addProperty("name", "Jake Wharton");
        dev2.addProperty("name", "Jesse Wilson");
        squareGuys.add(dev1);
        squareGuys.add(dev2);
        jsonObject.add("squareGuys", squareGuys);
        final GsonPostRequest gsonPostRequest = new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<AddressBean>() {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
        gsonPostRequest.setShouldCache(false);
        return gsonPostRequest;
    }
}
