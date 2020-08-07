package com.dashingqi.dqlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化DQLog
        DQLog.setDQLogInterceptor(object : DQLogInterceptor {
            override fun process(level: Int): Boolean {
                return !BuildConfig.DEBUG
            }

        })

        DQLog.d("test debug")
        DQLog.d("debug tag", "test tag debug")
        DQLog.i("test information")
        DQLog.w("test warning")
        DQLog.e("test error")
    }
}