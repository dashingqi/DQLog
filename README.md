## DQLog
通用的日志打印

## 使用步骤
#### 配置 
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
> implementation 'io.github.dashingqi:log:0.0.1'
#### 初始化
> 初始化阶段主要做两件事情
> 1. 设置日志打印的开关
> 2. 设置Json串打印时的转化方法
###### 设置打印开关
```kotlin
 DQLog.setDQLogInterceptor(object : DQLogInterceptor {
            override fun process(level: Int): Boolean {
                //通常可以根据你打包的环境来设置，也可以根据你当前Build的版本来判断
                //这个条件可以灵活配置，看你需要什么
                return BuildConfig.DEBUG
            }

        })
```
###### 设置Json串打印时的转化方法
- 由于当前项目中使用了ARouter，所以在项目中可以去实现SerializationService接口，实现了对应的方法
```kotlin

Route(path = "/impl/json", name = "对象序列化")
class JsonSerializationProvider : SerializationService {

    var gson = Gson()
    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return gson.fromJson(input, clazz)
    }

    override fun init(context: Context?) {
    }

    override fun object2Json(instance: Any?): String {
        return gson.toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return gson.fromJson(input, clazz)
    }
}

val serializationService = ARouter.getInstance().navigation(SerializationService::class.java)
        DQLog.setDQJsonParse(object :DQJsonParse{
            override fun <T> jsonToObject(json: String?, classType: Type?): T {
                return serializationService.parseObject(json,classType)
            }

            override fun objectToJson(any: Any?): String? {
               return serializationService.object2Json(any)
            }

        })

```
- 如果不想使用SerializationService 提供的，也可以直接使用Gson来实现
```kotlin
 var gson = Gson()
 DQLog.setDQJsonParse(object :DQJsonParse{
            override fun <T> jsonToObject(json: String?, classType: Type?): T {
                return gson.fromJson(json, classType)
            }

            override fun objectToJson(any: Any?): String? {
               return gson.toJson(any)
            }

        })
```

