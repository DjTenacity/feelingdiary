package cn.dj.android.common.lib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;


import java.util.Timer;
import java.util.TimerTask;

import cn.dj.android.common.lib.http.NetStateDispatcher;
import cn.dj.android.common.lib.util.Log;


/**
 * 网络广播
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2014-08-17
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    public static final String TAG = ConnectivityReceiver.class.getSimpleName();
    static private boolean connected = false;
    static private int netType = -1;
    private final ConnectivityManager connectivityManager;
    private static volatile boolean isRegister = false;

    /**
     * @param context
     */
    public ConnectivityReceiver(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        bind(context);
    }

    /**
     * bind
     *
     * @param context
     * @Date mw.gao / Aug 14, 2014
     */
    public void bind(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (!isRegister) {
            isRegister = true;
            context.registerReceiver(this, filter);
        }
        checkConnection();
    }


    /**
     * unbind
     *
     * @param context
     * @Date mw.gao / Aug 14, 2014
     */
    public void unbind(Context context) {
        context.unregisterReceiver(this);
    }

    private void checkConnection() {
        final NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            connected = (info.getState() == State.CONNECTED);
            netType = info.getType();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "ConnectivityReceiver: onReceive() is called with " + intent);
        String action = intent.getAction();
        if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.v(TAG, "onReceive() called with " + intent);
            return;
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            netType = info.getType();
            State state = info.getState();
            Log.d(TAG, "Network Type  = " + info.getTypeName() + "; Network State = " + state);
            if (info.isConnected()) {
                connected = true;
                NetStateDispatcher.getInstance().notifyListeners(true);
                Log.i(TAG, "Network connected");
            }
        } else {
            connected = false;
            NetStateDispatcher.getInstance().notifyListeners(false);
            Log.e(TAG, "Network unavailable");

        }


    }

    public static void waitForWifi(Context ctx, int timeout) {

        if (ctx == null)
            return;

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        WifiManager wm = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        int wifiState = wm.getWifiState();

        if (wifiState == WifiManager.WIFI_STATE_ENABLED || wifiState == WifiManager.WIFI_STATE_ENABLING) {

            final Thread thisThread = Thread.currentThread();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    thisThread.interrupt();
                }
            }, timeout);

            while (wifiState == WifiManager.WIFI_STATE_ENABLING || networkInfo.getState() == State.CONNECTING) {

                Log.d("ConnectivityReceiver", "Waiting for WiFi link"
                );

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }

                networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                wifiState = wm.getWifiState();

            }

            while (netType != ConnectivityManager.TYPE_WIFI
                    || connected == false) {

                Log.d("ConnectivityReceiver", "Waiting for WiFi connection");

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return;
                }

            }

        }

    }


}
