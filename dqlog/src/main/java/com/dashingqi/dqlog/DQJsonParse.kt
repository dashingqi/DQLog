package com.dashingqi.dqlog

import java.lang.reflect.Type

/**
 *
 * @ProjectName: DQLog
 * @Package: com.dashingqi.dqlog
 * @ClassName: DQJsonParse
 * @Author: DashingQI
 * @CreateDate: 2020/8/6 11:19 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/6 11:19 PM
 * @UpdateRemark:
 * @Version: 1.0
 */
interface DQJsonParse {

    /**
     * 对象解析程JSON
     */
    fun objectToJson(any:Any?):String?

    /**
     * json转成Object
     */
    fun <T> jsonToObject(json:String?,classType:Type?):T
}