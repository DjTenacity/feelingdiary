package cn.dj.android.common.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import cn.dj.android.common.lib.App;

/**
 * SP相关工具类
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-06-23
 */
public class SPUtils {

    /**
     * 获得SharedPreferences实例
     * @Date  gaodianjie / Aug 21, 2014
     * @return
     */
    public static SharedPreferences getSharePreferences() {
        return App.context.getSharedPreferences(AppUtils.getApplicationName(),Context.MODE_PRIVATE);
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void setString(String key, String value) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(key);
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public static String getString(String key) {
        return getString(key, "");
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static String getString(String key,String defaultValue) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null)
            return preferences.getString(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void setInt(String key, int value) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }
    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static int getInt(String key,int defaultValue) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null)
            return preferences.getInt(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void setLong(String key, long value) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null)
            return preferences.getLong(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloat(String key, float value) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static float getFloat(String key, float defaultValue) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null)
            return preferences.getFloat(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void setBoolean(String key, boolean value) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null)
            return preferences.getBoolean(key, defaultValue);
        else
            return defaultValue;
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public static Map<String, ?> getAll() {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null){
            return preferences.getAll();
        } else {
            return null;
        }
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public static void remove(String key) {
        SharedPreferences preferences = getSharePreferences();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(key);
            editor.commit();
        }
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean contains(String key) {
        SharedPreferences preferences = getSharePreferences();
        return preferences.contains(key);
    }

    /**
     * SP中清除所有数据
     * @Date  gaodianjie / Aug 21, 2014
     */
    public static void clearAll(){
        SharedPreferences preferences = getSharePreferences();
        preferences.edit().clear().commit();
    }
}