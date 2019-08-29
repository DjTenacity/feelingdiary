package cn.dj.android.common.lib.http;

/**
 * 实际网络监听者
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-08-15
 */
public interface NetStateListener {
    /**
     * On net connection changed.
     *
     * @param connected the connected
     * @author : gaodianjie / 2016-08-15
     */
    void onNetConnectionChanged(boolean connected);
}
