package com.dashingqi.dqlog

/**
 * @author : zhangqi
 * @time : 2020/8/6
 * desc :
 */
interface DQLogInterceptor {

    fun process(level: Int): Boolean
}