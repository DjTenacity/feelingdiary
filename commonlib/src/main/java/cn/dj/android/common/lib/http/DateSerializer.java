package cn.dj.android.common.lib.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期序列化
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/6/3
 */
public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

    public static final String GSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(GSON_DATE_FORMAT,Locale.getDefault());
    public static final Gson gson = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).registerTypeAdapter(new TypeToken<Date>() {
    }.getType(), new DateSerializer()).create();

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (null != json.getAsString()) {
            try {
                return simpleDateFormat.parse(json.getAsString());
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (null != src) {
            return new JsonPrimitive(src.getTime());
        }
        return null;
    }

    public static Gson getDefaultGson() {
        return gson;
    }

}
