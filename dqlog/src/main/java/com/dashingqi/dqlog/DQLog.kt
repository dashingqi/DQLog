package com.dashingqi.dqlog

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author : zhangqi
 * @time : 2020/8/6
 * desc :
 */
object DQLog {

    private var mDQLogInterceptor: DQLogInterceptor? = null

    private var mDQJsonParse: DQJsonParse? = null


    /**
     * ===========================================打印基本数据===========================================
     */
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
        print(level, tag, msg)
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


    /**
     * ===========================================打印JSON数据===========================================
     */

    fun vJson(tag: String, data: Any?) {
        printJson(LogConstant.Level_V, tag, data)
    }

    fun dJson(tag: String, data: Any?) {
        printJson(LogConstant.Level_D, tag, data)
    }

    fun iJson(tag: String, data: Any?) {
        printJson(LogConstant.Level_I, tag, data)
    }

    fun wJson(tag: String, data: Any?) {
        printJson(LogConstant.Level_W, tag, data)
    }

    fun eJson(tag: String, data: Any?) {
        printJson(LogConstant.Level_E, tag, data)
    }

    private fun printJson(level: Int, tag: String, data: Any?) {

        //校验日志打印的等级
        if (!checkInterceptor(level)) return
        // 拿到信息数据
        var messageData = if (data is String) {
            data
        } else {
            mDQJsonParse?.objectToJson(data) ?: "jsonParse is null"
        }

        //解下来解析这个json字符串
        try {

            if (messageData.startsWith("{")) {
                //对象
                val jsonObject = JSONObject(messageData)
                messageData = jsonObject.toString(4)
            } else if (messageData.startsWith("[")) {
                //数组
                val jsonArray = JSONArray(messageData)
                messageData = jsonArray.toString(4)
            }

            printJsonList(level,tag,messageData.split(LogConstant.LINE_ENTER!!).toTypedArray())

        } catch (exception: JSONException) {
            exception.printStackTrace()
        }

    }

    /**
     * 打印json串数组
     * 以换行符为界限打印每一行的数据
     */
    private fun printJsonList(level: Int,tag: String,data:Array<*>) {

        printMessage(level,tag,LogConstant.LOG_START)
        for (lineData in data){
            printMessage(level,tag,lineData)
        }

        printMessage(level,tag,LogConstant.LOG_END)


    }


    /**
     * 用于配置日志打印的等级
     */
    fun setDQLogInterceptor(dqLogInterceptor: DQLogInterceptor) {
        mDQLogInterceptor = dqLogInterceptor
    }

    /**
     * 用于检查是否开启日志打印
     * 获取部分日志打印
     * 部分日志打印根据level来控制
     */
    private fun checkInterceptor(level: Int): Boolean {

        return mDQLogInterceptor?.process(level) ?: false
    }

    /**
     * 用于设置解析JSON的 模仿ARouter的 ServiceParse
     */
    fun setDQJsonParse(dqJsonParse: DQJsonParse) {
        mDQJsonParse = dqJsonParse
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