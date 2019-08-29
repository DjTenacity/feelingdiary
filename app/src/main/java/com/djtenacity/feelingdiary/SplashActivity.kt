package com.djtenacity.feelingdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdog18.util.setOnToggleListener
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 描   述:
 * 创 建 人: gaodianjie
 * 创建日期: 2019/8/29 15:52
 * @author  admin
 */
class SplashActivity : AppCompatActivity() {
    //    var toggle = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        d.setOnClickListener({
//            toggle = !toggle;
//            if (toggle) {
//                motion_layout_with_splash.setDebugMode(2)
//            } else {
//                motion_layout_with_splash.setDebugMode(1)
//            }
//        })

        motion_layout_with_splash.transitionToEnd();

        d.setOnToggleListener { toggle ->
            if (toggle) {
                motion_layout_with_splash.setDebugMode(2)
            } else {
                motion_layout_with_splash.setDebugMode(1)
            }
        }
    }
}