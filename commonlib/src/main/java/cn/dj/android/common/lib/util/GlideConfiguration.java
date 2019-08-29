package cn.dj.android.common.lib.util;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 *
 * Glide使用bitmap的编码问RGB565，有时的时候由于过度压缩导致了图片变绿。改变Glide的bitmap编码解决变绿问题。
 *
 * 在使用项目的AndroidManifest.xml中加入如下配置
 <meta-data
 android:name="cn.dj.android.common.lib.util.GlideConfiguration"
 android:value="GlideModule"/>
 *
 * @author : gaodianjie / mwgao@gzulmu.com
 * @version : 1.0
 * @date : 2016-10-14
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
