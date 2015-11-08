package cxsjcj.hdsx.com.bean;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by chengcheng on 2015/8/10.
 */
public class ZhuangbeiObjectDeserializer implements JsonDeserializer<Zhuangbei> {
    @Override
    public Zhuangbei deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final Zhuangbei dummyObject = new Zhuangbei();
        final JsonObject jsonObject = json.getAsJsonObject();

        dummyObject.setImage(jsonObject.get("image").getAsString());
        dummyObject.setJiage(jsonObject.get("jiage").getAsString());
        dummyObject.setMiaoshu(jsonObject.get("miaoshu").getAsString());
        dummyObject.setShangpinid(jsonObject.get("shangpinid").getAsString());
        dummyObject.setShijian(jsonObject.get("shijian").getAsString());
        dummyObject.setTitle(jsonObject.get("title").getAsString());
        dummyObject.setBuyid(jsonObject.get("buyid").getAsString());
        dummyObject.setZhuangtai(jsonObject.get("zhuangtai").getAsString());
        dummyObject.setLianxi(jsonObject.get("lianxi").getAsString());
        dummyObject.setQufu(jsonObject.get("qufu").getAsString());
        dummyObject.setJiaoyifangshi(jsonObject.get("jiaoyifangshi").getAsString());
        return dummyObject;
    }
}
