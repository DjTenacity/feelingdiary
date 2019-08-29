package cn.dj.android.common.lib.ui.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.dj.android.common.lib.R;
import cn.dj.android.common.lib.util.PermissionUtil;


/**
 * 权限申请代理 Activity,替所有需要权限申请的地方进行权限申请工作
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-08-23
 */
@TargetApi(Build.VERSION_CODES.M)
public final class ShadowActivity extends AppCompatActivity {
    private String[] permissions;
    private static final int REQUEST_CODE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 如果有需求 可以添加 SnackBar 支持,暂时不支持
        //setContentView(new CoordinatorLayout(this));
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        permissions = intent.getStringArrayExtra("permissions");
        System.out.println("开始授权");
        if (PermissionUtil.getInstance().shouldShowPermissionRequestTip(this, permissions)) {
            showTip();
        } else {
            ActivityCompat.requestPermissions(ShadowActivity.this, permissions, REQUEST_CODE);
        }

    }


    private void showTip() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.PermissionReqTipDialog)
                .setTitle("需要权限")
                .setMessage("如果您需要使用此功能,请您提供权限,即在后面的对话框中点击确认按钮授权。")
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        PermissionUtil.getInstance().userCancel();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                        PermissionUtil.getInstance().userCancel();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ShadowActivity.this, permissions, REQUEST_CODE);
                    }
                })
                .setCancelable(false)
                .show();
        dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#212121"));
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00b0ff"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            this.finish();
            PermissionUtil.getInstance().grantedResult(permissions, grantResults);
        }
    }

}
