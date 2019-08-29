package cn.dj.android.common.lib.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.dj.android.common.lib.ui.activity.ShadowActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限申请工具，使用基于Queue的请求队列实现，如果同时有几个地方进行权限申请
 * 那么 按照FIFO 规则进行权限申请，请尽量避免在异步代码中申请权限，否则
 * 可能影响代码的正确执行
 * <p>
 * 必要申请的运行时权限
 * CALENDAR组
 * android.permission.READ_CALENDAR
 * android.permission.WRITE_CALENDAR
 * CAMERA组
 * android.permission.CAMERA
 * CONTACTS组
 * android.permission.READ_CONTACTS
 * android.permission.WRITE_CONTACTS
 * android.permission.GET_ACCOUNTS
 * LOCATION组
 * android.permission.ACCESS_FINE_LOCATION
 * android.permission.ACCESS_COARSE_LOCATION
 * MICROPHONE组
 * android.permission.RECORD_AUDIO
 * PHONE组
 * android.permission.READ_PHONE_STATE
 * android.permission.CALL_PHONE
 * android.permission.READ_CALL_LOG
 * android.permission.WRITE_CALL_LOG
 * android.permission.ADD_VOICEMAIL
 * android.permission.USE_SIP
 * android.permission.PROCESS_OUTGOING_CALLS
 * SENSORS组
 * android.permission.BODY_SENSORS
 * SMS组
 * android.permission.SEND_SMS
 * android.permission.RECEIVE_SMS
 * android.permission.READ_SMS
 * android.permission.RECEIVE_WAP_PUSH
 * android.permission.RECEIVE_MMS
 * STORAGE组
 * android.permission.READ_EXTERNAL_STORAGE
 * android.permission.WRITE_EXTERNAL_STORAGE
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-08-22
 */
public final class PermissionUtil {

    private PermissionReq mCurrentPermissionReq;
    private AppCompatActivity mActivity;

    private PermissionUtil() {

    }

    /**
     * Get instance permission utils.
     *
     * @return the permission utils
     * @author : gaodianjie / 2016-08-22
     */
    public static PermissionUtil getInstance() {
        return LazyHolder.permissionUtil;
    }

    /**
     * 申请权限
     *
     * @param context     the context
     * @param permissions 请求权限列表
     *                    此列表只需要传权限所在组中的任何一个
     *                    android 权限申请中，只要权限组中任意一个被授权，那么该
     *                    组中所有权限都会被授权。
     *                    比如需要同时申请 存储权限和相机权限
     *                    只需要传 new String[]{Manifest.permission.CAMERA,Manifest.permission.CAMERA,READ_EXTERNAL_STORAGE}
     *                    即可。当然，如果你想全部传，也无所谓了
     * @param listener    权限申请 监听
     * @author : gaodianjie / 2016-08-22
     */
    public void reqPermissions(@NonNull Context context, @NonNull String[] permissions, @NonNull OnReqPermissionListener listener) {
        if (null == listener) {
            throw new IllegalArgumentException("onReqPermissionListener 不能为空");
        }
        //android M 以下 默认拥有权限
        if (!isVersionGtM()) {
            listener.onSuccess();
            return;
        }

        //如果用户已经禁止权限（而且打勾），则直接反馈
//        String[] alreadyDeniedPermissions = getAlreadyDeniedPermissions(context, permissions);
//        if (null != alreadyDeniedPermissions) {
//            listener.onAlreadyDenied(alreadyDeniedPermissions);
//            return;
//        }
        //取得没有授权的权限列表
        Set<String> notGrantedP = new HashSet<>();
        for (String permission : permissions) {
            if (!isGranted(context, permission)) {
                notGrantedP.add(permission);
            }
        }

        if (notGrantedP.size() < 1) {
            //全部已经授权 直接返回
            listener.onSuccess();
        } else {
            //构造权限请求
            mCurrentPermissionReq = new PermissionReq(permissions, context, listener);
            Intent intent = new Intent(mCurrentPermissionReq.getContext(), ShadowActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("permissions", mCurrentPermissionReq.getPermissions());
            mCurrentPermissionReq.getContext().startActivity(intent);
        }
    }

    /**
     * 用户取消了权限申请
     *
     * @author : gaodianjie / 2016-08-22
     */
    public void userCancel() {
        if (null != mCurrentPermissionReq) {
            mCurrentPermissionReq.getListener().onUserCancel();
            //下一个
        }
    }

    /**
     * 授权结果
     *
     * @param reqPermissions 申请的权限列表
     * @param reqResult      授权结果
     * @author : gaodianjie / 2016-08-22
     */
    public void grantedResult(String[] reqPermissions, int reqResult[]) {
        //授权列表
        Set<String> grantedP = new HashSet<>();
        //拒绝列表
        Set<String> notGrantedP = new HashSet<>();
        for (int i = 0; i < reqResult.length; i++) {
            if (reqResult[i] == PackageManager.PERMISSION_GRANTED) {
                grantedP.add(reqPermissions[i]);
            } else {
                notGrantedP.add(reqPermissions[i]);
            }
        }
        if (notGrantedP.isEmpty()) {
            //拒绝为空 则表示权限申请成功
            if (null != mCurrentPermissionReq) {
                mCurrentPermissionReq.getListener().onSuccess();
            }
        } else {
            //权限申请失败，存在被拒绝项
            if (null != mCurrentPermissionReq) {
                mCurrentPermissionReq.getListener().onFailed(grantedP.toArray(new String[grantedP.size()]), notGrantedP.toArray(new String[notGrantedP.size()]));
            }
        }
    }

    /**
     * 判断是否已经被授权
     *
     * @param context
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGranted_(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断是否已经被授权
     *
     * @param context    the context
     * @param permission the permission
     * @return boolean
     * @author : gaodianjie / 2016-08-22
     */
    private boolean isGranted(Context context, String permission) {
        return !isVersionGtM() || isGranted_(context, permission);
    }

    /**
     * 判断当前操作系统版本是否是 6.0以上
     *
     * @return the boolean
     * @author : gaodianjie / 2016-08-22
     */
    private boolean isVersionGtM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断权限列表中那些已经被用户禁止掉，而且选择了不在提醒
     *
     * @param permissions
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private String[] getAlreadyDeniedPermissions(Context context, String[] permissions) {
        if (mActivity == null) {
            return null;
        }
        Set<String> temp = new HashSet<>();
        for (String permission : permissions) {
            if ((!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) && (!isGranted(context, permission))) {
                temp.add(permission);
            }
        }
        if (temp.isEmpty()) {
            return null;
        }
        return temp.toArray(new String[temp.size()]);
    }

    /**
     * @param activity    the activity
     * @param permissions the permissions
     * @return the boolean
     * @author : gaodianjie / 2016-08-22
     */
    @TargetApi(Build.VERSION_CODES.M)
    public synchronized boolean shouldShowPermissionRequestTip(AppCompatActivity activity, String[] permissions) {
        if (!(activity instanceof ShadowActivity)) {
            throw new IllegalArgumentException("只能在 ShadowActivity 中调用");
        }
        if (null == mActivity) {
            mActivity = activity;
        }
        boolean is = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                is = true;
                break;
            }
        }
        return is;
    }


    /**
     * 权限申请回掉函数
     *
     * @author : gaodianjie / heywakeup@gzulmu.com
     * @version : 1.0
     * @date : 2016-08-22
     */
    public static interface OnReqPermissionListener {
        /**
         * 用户取消掉授权动作
         *
         * @author : gaodianjie / 2016-08-22
         */
        void onUserCancel();

        /**
         * 所有权限授权成功
         *
         * @author : gaodianjie / 2016-08-22
         */
        void onSuccess();

        /**
         * 授权失败，即申请的权限中有一项或者多项被拒绝
         *
         * @param notGrantedPermission 被拒绝的权限
         * @param grantedPermission    用户授权的权限
         * @author : gaodianjie / 2016-08-22
         */
        void onFailed(String[] notGrantedPermission, String[] grantedPermission);

        /**
         * 已经被禁止
         *
         * @param alreadyDeniedPermissions 已经被禁止的 权限列表
         * @author : gaodianjie / 2016-08-22
         */
        void onAlreadyDenied(String[] alreadyDeniedPermissions);
    }

    private static final class LazyHolder {
        public static PermissionUtil permissionUtil = new PermissionUtil();
    }

    /**
     * 权限请求
     */
    private static final class PermissionReq {
        private final String[] permissions;
        private final Context context;
        private final OnReqPermissionListener listener;

        /**
         * Permission req.
         *
         * @param permissions the permissions
         * @param context     the context
         * @param listener    the listener
         */
        public PermissionReq(String[] permissions, Context context, OnReqPermissionListener listener) {
            this.permissions = permissions;
            this.context = context;
            this.listener = listener;
        }

        /**
         * 请求权限列表
         *
         * @return the string [ ]
         * @author : gaodianjie / 2016-08-22
         */
        public String[] getPermissions() {
            return permissions;
        }

        /**
         * 请求回调
         *
         * @return the on req permission listener
         * @author : gaodianjie / 2016-08-22
         */
        public OnReqPermissionListener getListener() {
            return listener;
        }

        /**
         * 请求所在context
         *
         * @return the context
         * @author : gaodianjie / 2016-08-22
         */
        public Context getContext() {
            return context;
        }
    }

}
