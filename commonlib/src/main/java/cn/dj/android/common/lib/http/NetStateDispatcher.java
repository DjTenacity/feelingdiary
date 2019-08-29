package cn.dj.android.common.lib.http;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 分发网络变化消息
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/8/15
 */
public final class NetStateDispatcher {
    private static class LazyHolder {
        private static final NetStateDispatcher INSTANCE = new NetStateDispatcher();
    }

    private NetStateDispatcher() {
    }

    /**
     * Get instance net state dispatcher.
     *
     * @return the net state dispatcher
     * @author : gaodianjie / 2016-08-15
     */
    public static final NetStateDispatcher getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Net state listeners
     */
    static List<NetStateListener> netStateListeners = new ArrayList<>();

    /**
     * Add listener.
     *
     * @param listener the listener
     * @author : gaodianjie / 2016-08-15
     */
    public void addListener(NetStateListener listener) {
        if (listener == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!netStateListeners.contains(listener))
                netStateListeners.add(listener);
        }
    }

    /**
     * Delete listener.
     *
     * @param observer the observer
     * @author : gaodianjie / 2016-08-15
     */
    public synchronized void deleteListener(NetStateListener observer) {
        netStateListeners.remove(observer);
    }

    /**
     * Delete all listeners.
     *
     * @author : gaodianjie / 2016-08-15
     */
    public synchronized void deleteAllListeners() {
        netStateListeners.clear();
    }


    /**
     * Notify listeners.
     *
     * @param connected the connected
     * @author : gaodianjie / 2016-08-15
     */
    @SuppressWarnings("unchecked")
    public void notifyListeners(boolean connected) {
        NetStateListener[] arrays = null;
        synchronized (this) {
            if (netStateListeners.size() >0) {
                int size = netStateListeners.size();
                arrays = new NetStateListener[size];
                netStateListeners.toArray(arrays);
                for (NetStateListener listener : arrays) {
                    listener.onNetConnectionChanged(connected);
                }
            }
        }
    }

}
