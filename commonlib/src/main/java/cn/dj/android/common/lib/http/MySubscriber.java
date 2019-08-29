package cn.dj.android.common.lib.http;


import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicReference;

import cn.dj.android.common.lib.App;
import cn.dj.android.common.lib.entity.ResponseData;
import cn.dj.android.common.lib.util.AppUtils;
import cn.dj.android.common.lib.util.Log;
import cn.dj.android.common.lib.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import retrofit2.HttpException;


/**
 * 封装Subscriber,异常处理
 *
 * @param <T> the type parameter
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-06-27
 */
public class MySubscriber<T extends ResponseData> extends AtomicReference<Subscription> implements Subscriber<T>, Subscription, Disposable {

    private static final String TAG = "MySubscriber";

    private static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    private static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态";
    private static final String CONNECT_EXCEPTION_CLIENT = "客户端请求异常，请联系管理员！";
    private static final String CONNECT_EXCEPTION_401 = "401 未授权 — 未授权客户机访问数据";
    private static final String CONNECT_EXCEPTION_403 = "403 禁止访问";
    private static final String CONNECT_EXCEPTION_404 = "404 服务器找不到给定的资源;文档不存在";

    private static final String CONNECT_EXCEPTION_500 = "500 内部服务器错误";
    private static final String CONNECT_EXCEPTION_501 = "501 未实现";
    private static final String CONNECT_EXCEPTION_502 = "502 网关错误";
    private static final String CONNECT_EXCEPTION_504 = "504 网关超时";
    private static final String CONNECT_EXCEPTION_505 = "505 HTTP 版本不受支持";

    private static final String CONNECT_EXCEPTION_SERVER = "服务端请求异常，请联系管理员！";
    private static final String UNKNOWN_HOST_EXCEPTION = "未知的Host异常，请检查您的网络状态";

    private static final long serialVersionUID = -7251123623727029452L;
    final Consumer<? super T> onNext;
    final Consumer<? super Throwable> onError;
    final Action onComplete;
    final Consumer<? super Subscription> onSubscribe;

    public MySubscriber(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        super();
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = Functions.EMPTY_ACTION;
        this.onSubscribe = FlowableInternalHelper.RequestMax.INSTANCE;
    }

    public MySubscriber(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
                        Action onComplete,
                        Consumer<? super Subscription> onSubscribe) {
        super();
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
        this.onSubscribe = onSubscribe;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this, subscription)) {
            try {
                onSubscribe.accept(this);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                subscription.cancel();
                onError(ex);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                if (t.isSuccess()) {
                    onNext.accept(t);
                } else {
                    if (t.getCode() == 101 || t.getCode() == 102 || t.getCode() == 103) {
                        Intent intent = new Intent();
                        intent.putExtra("from", "server");
                        intent.setAction(AppUtils.getAppPackageName(App.context) + ".login");
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        App.context.startActivity(intent);
                    } else {
                        onNext.accept(t);
                        Log.e(TAG, "ResponseData（响应错误）-- 错误码：" + t.getCode() + "-- >" + t.getMsg());
                        ToastUtils.normal(t.getMsg()).show();
                    }
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                get().cancel();
                onError(e);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onError(Throwable throwable) {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                onError.accept(throwable);
            } catch (Throwable e) {
                e.printStackTrace();
                Exceptions.throwIfFatal(e);
//              RxJavaPlugins.onError(new CompositeException(throwable, e));
            }
        } else {
            Log.e(TAG, "SubscriptionHelper.CANCELLED");
        }

        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            int code = exception.code();
            if (code >= 200 && code < 300) {
                Log.e(TAG, "Code:" + code + " Request success!");
            } else if (code == 401) {
                Log.e(TAG, CONNECT_EXCEPTION_401 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_401).show();
            } else if (code == 403) {
                Log.e(TAG, CONNECT_EXCEPTION_403 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_403).show();
            } else if (code == 404) {
                Log.e(TAG, CONNECT_EXCEPTION_404 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_404).show();
            } else if (code >= 400 && code < 500) {
                Log.e(TAG, "Code:" + code + " Client Error" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(code + " " + CONNECT_EXCEPTION_CLIENT).show();
            } else if (code == 500) {
                Log.e(TAG, CONNECT_EXCEPTION_500 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_500).show();
            } else if (code == 501) {
                Log.e(TAG, CONNECT_EXCEPTION_501 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_501).show();
            } else if (code == 502) {
                Log.e(TAG, CONNECT_EXCEPTION_502 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_502).show();
            } else if (code == 504) {
                Log.e(TAG, CONNECT_EXCEPTION_504 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_504).show();
            } else if (code == 505) {
                Log.e(TAG, CONNECT_EXCEPTION_505 + "\r\n" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(CONNECT_EXCEPTION_505).show();
            } else if (code >= 500 && code < 600) {
                Log.e(TAG, "Code:" + code + " Server Error" + exception.message() + "\r\n" + exception.toString());
                ToastUtils.normal(code + " " + CONNECT_EXCEPTION_SERVER).show();
            }
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e(TAG, "onError: SocketTimeoutException----" + SOCKET_TIMEOUT_EXCEPTION);
            ToastUtils.normal(SOCKET_TIMEOUT_EXCEPTION).show();
        } else if (throwable instanceof ConnectException) {
            Log.e(TAG, "onError: ConnectException-----" + CONNECT_EXCEPTION);
            ToastUtils.normal(CONNECT_EXCEPTION).show();
        } else if (throwable instanceof UnknownHostException) {
            Log.e(TAG, "onError: UnknownHostException-----" + UNKNOWN_HOST_EXCEPTION);
            ToastUtils.normal(UNKNOWN_HOST_EXCEPTION).show();
        } else if (throwable instanceof IOException) {
            Log.e(TAG, "onError: IOException-----" + throwable.toString());
            ToastUtils.normal(throwable.getMessage()).show();
        } else {
            Log.e(TAG, "onError:----" + throwable.getMessage() + "\r\n" + throwable.toString());
        }
        throwable.printStackTrace();
        onComplete();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onComplete() {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                onComplete.run();
            } catch (Throwable e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
                ToastUtils.normal(e.getMessage()).show();
            }
        }
    }

    @Override
    public void dispose() {
        cancel();
    }

    @Override
    public boolean isDisposed() {
        return get() == SubscriptionHelper.CANCELLED;
    }

    @Override
    public void request(long n) {
        get().request(n);
    }

    @Override
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

}