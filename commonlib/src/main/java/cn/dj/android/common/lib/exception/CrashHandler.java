package cn.dj.android.common.lib.exception;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;


import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.TreeSet;

import cn.dj.android.common.lib.util.AppUtils;
import cn.dj.android.common.lib.util.FileUtils;
import cn.dj.android.common.lib.util.Log;
import cn.dj.android.common.lib.util.NetworkUtils;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/17
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();

    /** 程序的Context对象 */
    public Context mContext;
    /** 异常信息实体 **/
    public static CrashInfo crashInfo;
    /** CrashHandler实例 */
    public static CrashHandler INSTANCE;
    /** 系统默认的UncaughtException处理类 */
    public UncaughtExceptionHandler mDefaultHandler;

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(startMain);
            System.exit(1);
        }


    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                Toast.makeText(mContext, "程序出错啦:" + msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // 保存错误报告文件
        ex.printStackTrace();
//        saveBugToFile(collectCrashInfo(), ex);
        // 发送错误报告到服务器
//		sendCrashReportsToServer();
        return true;
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     * @author : gaodianjie / May 13, 2014
     */
    private void sendCrashReportsToServer() {
        String[] crFiles = FileUtils.getBugFiles();
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(FileUtils.createFileDir(FileUtils.CONTENT_TYPE_BUG), fileName);
                postReport(cr);
//				cr.delete();// 删除已发送的报告
            }
        }
    }


    /**
     * 提交到服务器
     * @author : gaodianjie / Aug 17, 2014
     * @param file
     */
    private void postReport(File file) {
        // 使用HTTP Post 发送错误报告到服务器
    }

    /**
     * 在程序启动时候, 调用该函数来发送以前没有发送的报告
     * @author : gaodianjie / May 13, 2014
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer();
    }


    /**
     * 写入文件
     * @author : gaodianjie / Aug 8, 2014
     * @param crashInfo
     * @param ex
     */
    private void saveBugToFile(String crashInfo, Throwable ex) {
        FileUtils.writeBugToFile(crashInfo, ex);
    }


    /**
     * 搜集信息
     * @author : gaodianjie / Aug 8, 2014
     */
    private String collectCrashInfo() {
        if (crashInfo == null) {
            crashInfo = new CrashInfo(AppUtils.getVersionName(), AppUtils.getVersionCode(), AppUtils.getImei()
                    , AppUtils.getMac(), android.os.Build.MODEL, android.os.Build.VERSION.RELEASE,
                    NetworkUtils.isConnected(), NetworkUtils.getNetworkType().name());
        }

        return crashInfo.toString();
    }


}
