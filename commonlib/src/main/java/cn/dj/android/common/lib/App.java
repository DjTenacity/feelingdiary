package cn.dj.android.common.lib;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import cn.dj.android.common.lib.exception.CrashHandler;
import cn.dj.android.common.lib.receiver.ConnectivityReceiver;
import cn.dj.android.common.lib.util.ConstUtils;
import cn.dj.android.common.lib.util.DisplayUtils;
import cn.dj.android.common.lib.util.ToastUtils;

/**
 * 应用实体
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/4/23.
 */
public class App {

    public static Context context;
    public static Activity activity;
    public static ConnectivityReceiver connectivityReceiver;

    /**
     * 日志开关
     **/
    public static boolean logFlag = false;
    /**
     * 是否保存日志
     **/
    public static boolean isSave = true;
    /**
     * 日志级别，默认日志为VERBOSE
     **/
    public static int DEFAULT_LEVEL = android.util.Log.VERBOSE;


    /**
     * APP 初始化相关操作
     *
     * @param cxt the cxt
     * @author : gaodianjie / 2017-03-03
     */
    public static void init(Context cxt) {
        init(cxt, true);
    }

    /**
     * APP 初始化相关操作
     *
     * @param cxt
     * @param debug      是否调试
     */
    public static void init(final Context cxt, boolean debug) {
        context = cxt;
        if (debug) {
            logFlag = true;
            isSave = false;
            DEFAULT_LEVEL = android.util.Log.VERBOSE;
        } else {
            logFlag = false;
            isSave = true;
            DEFAULT_LEVEL = android.util.Log.WARN;
        }
        CrashHandler.getInstance().init(cxt);
        connectivityReceiver = new ConnectivityReceiver(context);
        DisplayUtils.init(context);

    }

    public static Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstUtils.MSG_ERROR:
                    ToastUtils.error((String) msg.obj).show();
                    break;
            }
        }
    };

    public static void sendErrorMsg(String errorMsg) {
        sendMsg(ConstUtils.MSG_ERROR, errorMsg);
    }

    public static void sendMsg(int what, String msg) {
        Message message = mHandler.obtainMessage();
        message.what = what;
        message.obj = msg;
        mHandler.sendMessage(message);
    }

    public static void destroy() {
        connectivityReceiver.unbind(context);
    }
}
