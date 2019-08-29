package cn.dj.android.common.lib.http;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 授权拦截
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/3/18
 */
public class OauthInterceptor implements Interceptor {

    private final String accessToken;

    public OauthInterceptor(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(accessToken)) {
            builder.header("Authorization", "token " + accessToken);
        }
        return chain.proceed(builder.build());
    }

}
