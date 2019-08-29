package cn.dj.android.common.lib.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import androidx.annotation.Nullable;

/**
 *  基础activity类
 *
 * @author : gaodianjie
 * @version : 1.0
 * @date : 2017-03-27
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认带有EditText的界面不弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
    }
}
