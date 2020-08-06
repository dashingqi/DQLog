package com.dashingqi.dqlog

/**
 * @author : zhangqi
 * @time : 2020/8/6
 * desc :
 */
object LogConstant {

    const val Level_V = 0

    const val Level_D = 1

    const val Level_I = 2

    const val Level_W = 3

    const val Level_E = 4

    const val TAG_IS_NULL = "tag is null"

    const val TAG_IS_EMPTY = "tag is empty"

    const val LOG_START =
        "====================================== start print ======================================"

    const val LOG_END =
        "====================================== end print ========================================="

    /**
     * 换行符
     */
     val LINE_ENTER = System.getProperty("line.separator")
}