package cn.dj.android.common.lib.util;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.dj.android.common.lib.App;
import cn.dj.android.common.lib.R;

/**
 * Glide工具类
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/17
 */
public class GlideUtils {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private GlideUtils() {
    }

    private static class GlideControlHolder {
        private static GlideUtils instance = new GlideUtils();
    }

    /**
     * 获取GlideUtils实例
     *
     * @return the glide utils
     * @author : gaodianjie / 2016-08-11
     */
    public static GlideUtils getInstance() {
        return GlideControlHolder.instance;
    }

    /**
     * 将资源ID转为Uri
     *
     * @param resourceId the resource id
     * @return the uri
     * @author : gaodianjie / 2016-08-11
     */
    public Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + App.context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    /**
     * 加载drawable图片
     *
     * @param resId     the res id
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadResImage(int resId, ImageView imageView) {
        Glide.with(App.context)
                .load(resourceIdToUri(resId))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }


    /**
     * 加载drawable图片 不使用缓存
     *
     * @param resId     the res id
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadResImageNoCache(int resId, ImageView imageView) {
        Glide.with(App.context)
                .load(resourceIdToUri(resId))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }


    /**
     * 加载本地图片
     *
     * @param path      the path
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadLocalImage(String path, ImageView imageView) {
        Glide.with(App.context)
                .load("file://" + path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }


    /**
     * 加载本地图片 不使用缓存
     *
     * @param path      the path
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadLocalImageNoCache(String path, ImageView imageView) {
        Glide.with(App.context)
                .load("file://" + path)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param url       the url
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadImage(String url, ImageView imageView) {
        Glide.with(App.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }


    /**
     * 加载网络图片 不缓存
     *
     * @param url       the url
     * @param imageView the image view
     * @author : gaodianjie / 2016-08-11
     */
    public void loadImageNoCache(String url, ImageView imageView) {
        Glide.with(App.context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }

    /**
     * 加载网络Transform图片
     *
     * @param url             the url
     * @param imageView       the image view
     * @param transformations the transformations
     * @author : gaodianjie / 2016-08-11
     */
    public void loadTransformImage(String url, ImageView imageView, Transformation transformations) {
        Glide.with(App.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(transformations)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }

    /**
     * 加载drawable Transform图片.
     *
     * @param resId           the res id
     * @param imageView       the image view
     * @param transformations the transformations
     * @author : gaodianjie / 2016-08-11
     */
    public void loadTransformResImage(int resId, ImageView imageView, Transformation transformations) {
        Glide.with(App.context)
                .load(resourceIdToUri(resId))
                .bitmapTransform(transformations)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }


    /**
     * 加载本地Transform图片
     *
     * @param path            the path
     * @param imageView       the image view
     * @param transformations the transformations
     * @author : gaodianjie / 2016-08-11
     */
    public void loadTransformLocalImage(String path, ImageView imageView, Transformation transformations) {
        Glide.with(App.context)
                .load("file://" + path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(transformations)
                .placeholder(R.drawable.ic_glide_placeholder)
                .error(R.drawable.ic_glide_error)
                .into(imageView);
    }
}
