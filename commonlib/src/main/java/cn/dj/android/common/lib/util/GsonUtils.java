package cn.dj.android.common.lib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Gson工具类
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/17
 */
public class GsonUtils {

    /**
     * 解析json字符串
     * @param jsonStr
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T parseJSON(String jsonStr, Class<T> t) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        T bean = gson.fromJson(jsonStr, t);
        return bean;
    }

    /**
     * 解析json数组
     * @param response
     * @param type
     *           Type type = new TypeToken&ltArrayList&ltAnimeInfo>>() {
     *            }.getType();
     * @return
     */
    public static <T> T parseJSONArray(String response, Type type) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        T data = gson.fromJson(response, type);
        return data;
    }

    /**
     * 转为json字符
     * @param object
     * @return
     */
    public static String toJson(Object object){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
         return gson.toJson(object);
    }

}
