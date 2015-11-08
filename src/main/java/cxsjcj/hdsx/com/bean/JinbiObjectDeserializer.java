package cxsjcj.hdsx.com.bean;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by chengcheng on 2015/8/9.
 */
public class JinbiObjectDeserializer implements JsonDeserializer<Jinbi> {
    @Override
    public Jinbi deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final Jinbi dummyObject = new Jinbi();
        final JsonObject jsonObject = json.getAsJsonObject();
        dummyObject.setShuliang(jsonObject.get("shuliang").getAsString());
        dummyObject.setShoujia(jsonObject.get("shoujia").getAsString());
        dummyObject.setJiaoyifangshi(jsonObject.get("jiaoifangshi").getAsString());
        dummyObject.setQufu(jsonObject.get("qufu").getAsString());
        return dummyObject;
    }
}
