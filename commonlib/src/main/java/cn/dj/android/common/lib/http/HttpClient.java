package cn.dj.android.common.lib.http;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.dj.android.common.lib.App;
import cn.dj.android.common.lib.util.Log;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/3/18
 */
public class HttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();

    private static final int DISK_CACHE_SIZE = (int) 50 * 1024 * 1024;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    protected static OkHttpClient okHttpClient = new OkHttpClient();

    private static final OauthInterceptor oauthInterceptor = new OauthInterceptor("");
    private static final GzipRequestInterceptor gzipRequestInterceptor = new GzipRequestInterceptor();
    private static final HostSelectionInterceptor hostSelectionInterceptor = new HostSelectionInterceptor();
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i(TAG, message);
                }
            });
    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(DateSerializer.getDefaultGson());
    private static final RxJava2CallAdapterFactory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();


    /**
     * 获取retrofit实例
     *
     * @param baseUrl the base url
     * @return the retrofit
     * @author : gaodianjie / 2017-03-06
     */
    public static Retrofit retrofit(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient())
                .build();
    }

    /**
     * 获取retrofit实例
     *
     * @param baseUrl      the base url
     * @param interceptors the interceptors
     * @return the retrofit
     * @author : gaodianjie / 2017-03-06
     */
    public static Retrofit retrofit(String baseUrl, Interceptor... interceptors) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient(interceptors))
                .build();
    }

    /**
     * 设置拦截器
     *
     * @param interceptors
     * @return
     */
    public static OkHttpClient okHttpClient(Interceptor... interceptors) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.interceptors().addAll(buildInterceptors(interceptors));
        builder.cache(builderCache());
        return builder.build();
    }

    /**
     * 构建拦截器数组
     *
     * @param interceptors
     * @return
     */
    public static List<Interceptor> buildInterceptors(Interceptor... interceptors) {
        List<Interceptor> list = new ArrayList<Interceptor>();
        list.add(oauthInterceptor);
        if (App.logFlag) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        list.add(loggingInterceptor);
        for(int i=0;i < interceptors.length;i++){
            list.add(interceptors[i]);
        }
        list.add(hostSelectionInterceptor);
        return list;
    }

    /**
     * 构建缓存
     *
     * @return
     */
    private static Cache builderCache() {
        File cacheDir = new File(App.context.getCacheDir(), "http_cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return cache;
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param json   发送Json数据
     * @return
     * @throws IOException
     */
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    /**
     * POST请求
     * @param url  请求地址
     * @param body 发送数据对象
     * @return
     * @throws IOException
     */
    public static Response post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response;
    }

    /**
     * 根据Json字符串创建RequestBody
     * @param json
     * @return
     */
    public static RequestBody createJsonBody(String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    /**
     * 根据文件创建RequestBody
     * @param file
     * @return
     */
    public static RequestBody createFileBody(File file) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        return body;
    }

    /**
     * 根据参数Map构建RequestBody
     * @param params
     * @return
     */
    public static RequestBody createFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            builder.add(key, params.get(key));
        }
        RequestBody body = builder.build();
        return body;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        HttpClient.okHttpClient = okHttpClient;
    }
}
