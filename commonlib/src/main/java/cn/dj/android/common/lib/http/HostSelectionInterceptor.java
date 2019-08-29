package cn.dj.android.common.lib.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 动态host
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/20
 */
public class HostSelectionInterceptor implements Interceptor {

    private volatile String host;

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String host = this.host;
        if (host != null) {
            HttpUrl newUrl = request.url().newBuilder()
                    .host(host)
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }
}
