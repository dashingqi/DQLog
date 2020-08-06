package com.dashingqi.dqlog

import android.util.Log

/**
 * @author : zhangqi
 * @time : 2020/8/6
 * desc :
 */
object DQLog {

    private var mDQLogInterceptor: DQLogInterceptor? = null

    fun v(msg: Any?) {
        v(null, msg)
    }

    fun v(tag: String?, msg: Any?) {
        printMessage(LogConstant.Level_V, tag, msg)
    }

    fun d(msg: Any?) {
        d(null, msg)
    }

    fun d(tag: String?, msg: Any?) {
        printMessage(LogConstant.Level_D, tag, msg)
    }

    fun i(msg: Any?) {
        i(null, msg)
    }

    fun i(tag: String?, msg: Any?) {
        printMessage(LogConstant.Level_I, tag, msg)
    }

    fun w(msg: Any?) {
        w(null, msg)
    }

    fun w(tag: String?, msg: Any?) {
        printMessage(LogConstant.Level_W, tag, msg)
    }

    fun e(msg: Any?) {
        e(null, msg)
    }

    fun e(tag: String?, msg: Any?) {
        printMessage(LogConstant.Level_E, tag, msg)
    }

    /**
     * 最终打印信息的部分
     */
    private fun printMessage(level: Int, tag: String?, msg: Any?) {
        if (!checkInterceptor(level)) {
            return
        }

        // 这里面msg可能是 Array 也可能是 list
        var parseTag = parseTag(tag)

        when (msg) {
            is Array<*> -> {

            }
            is List<*> -> {

            }
            is String -> {
                var msgStr = msg.toString()
                printStrMessage(level, parseTag, msgStr)
            }
        }
    }

    /**
     * 打印字符串信息
     */
    private fun printStrMessage(level: Int, tag: String, msg: String?) {
        print(level, tag, LogConstant.LOG_START)
        print(level, tag, msg)
        print(level, tag, LogConstant.LOG_END)
    }

    private fun print(level: Int, tag: String, msg: String?) {
        when (level) {
            LogConstant.Level_I -> {
                Log.i(tag, msg)
            }

            LogConstant.Level_V -> {
                Log.v(tag, msg)
            }
            LogConstant.Level_D -> {
                Log.d(tag, msg)
            }

            LogConstant.Level_W -> {
                Log.w(tag, msg)
            }

            LogConstant.Level_E -> {
                Log.e(tag, msg)
            }
        }
    }


    fun setDQLogInterceptor(dqLogInterceptor: DQLogInterceptor) {
        mDQLogInterceptor = dqLogInterceptor
    }

    /**
     * 用于检查是否开启日志打印
     * 获取部分日志打印
     * 部分日志打印根据level来控制
     */
    private fun checkInterceptor(level: Int): Boolean {
        return if (mDQLogInterceptor == null) false
        else {
            mDQLogInterceptor?.process(level)!!
        }
    }

    /**
     * 解析tag
     */
    private fun parseTag(tag: String?): String {
        return when {
            tag == null -> {
                LogConstant.TAG_IS_NULL
            }
            tag.isEmpty() -> {
                LogConstant.TAG_IS_EMPTY
            }
            else -> {
                tag
            }
        }
    }
}