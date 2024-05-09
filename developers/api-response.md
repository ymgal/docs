# 全局返回对象

月幕的 API Response 有一个固定的格式，具体返回的数据会作为 `data` 字段嵌套在其中，这适用于没有显示声明特例的所有情况。

后续的接口文档中，所有阐述的返回值均遵循此规则，不会额外说明在外侧包装的全局返回对象。



<br>

## API Response

在接口调用出现异常、参数检查不通过等无法正确响应时，HTTP Status 也仍然会保持 200， 错误内容在 `code` `msg` 等字段中体现。 

- 例外情况：认证、授权失败时，对应的 HTTP Status 同步变更为 401、403。



如果接口调用失败，`success` 字段固定为 `false`


示例&说明
```jsons
{
  "success": true, // 本次调用是否成功
  "code": 0, // 详情见 —— 返回码
  "msg": "", // 接口反馈的信息，可能会为 null
  "data": {}// 接口实际返回的数据，可能会为 null
}
```


一个仅代表调用成功的返回值可能是这样的，没有 data，也没有 msg

```json
{
  "success": true,
  "code": 0
}
```
Java 模型
```java
@Data
public class R<T> {
    
    private boolean success = false;

    private int code = RCode.SUCCESS.getCode();

    private String msg;

    private T data;

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

        this.success = RCode.SUCCESS.valueIs(code);
    }
}
```

Kotlin 模型
```kotlin
data class R<T>(
    var code: Int = RCode.SUCCESS.code,
    var data: T? = null,
    var message: String? = null
) {

    var success: Boolean = true

    init {
        success = this.code == RCode.SUCCESS.code
    }
}
```
TS 模型
```typescript
declare interface R<T = any> {
    success: boolean;
    code: RCode;
    msg?: string;
    data?: T;
}
```

<br>


## Page

如果您请求的是一个分页接口，那么默认返回值的 data 格式如下所示

请求的列表会作为数组保存在 result 字段中

```jsons
{
  "result": [], // 响应的列表
  "total": 0, // 可供查询的总数
  "hasNext": false, // 是否有下一页
  "pageNum": 1, // 当前页
  "pageSize": 10 // 页大小
}
```

我们访问一个分页接口，得到的最终 HTTP Response Body 完整形态会长成这样
```json
{
  "success": true,
  "code": 0,
  "data": {
    "result": [
      {
        "name": "月に寄りそう乙女の作法",
        "chineseName": "近月少女的礼仪"
      },
      {
        "name": "月に寄りそう乙女の作法２",
        "chineseName": "近月少女的礼仪2"
      }
    ],
    "total": 2,
    "hasNext": false,
    "pageNum": 1,
    "pageSize": 10
  }
}
```

<br>


## 全局返回码
返回码是全局返回对象中的 `code` 字段，标识了这个响应的状态

当 `code === 0` 时，返回对象中的 `success` 字段恒定为 `true`

| code | name             | description                       |
|------|------------------|-----------------------------------|
| 0    | SUCCESS          | 调用成功，如果不是此响应码的话，数据肯定是拿不到的，需要自行检查  |
| 7    | OTHER            | 其他                                |
| 30   | TIME_OUT         | 超时                                |
| 50   | SYSTEM_ERROR     | 系统内部错误                            |
| 51   | ILLEGAL_VERSION  | 非法版本                              |
| 401  | UNAUTHORIZED     | 未经认证，这个code会改变 HTTP Status >> 401 |
| 403  | FORBIDDEN        | 未经授权，这个code会改变 HTTP Status >> 403 |
| 404  | NOT_FOUNT        | 找不到                               |
| 429  | TOO_MANY_REQUEST | 请求过多                              |
| 614  | ILLEGAL_PARAM    | 非法参数，校验失败时会遇到                     |

Java 模型
```java
public enum RCode {
    SUCCESS(0),//成功
    OTHER(7),//其他
    TIME_OUT(30),//超时
    SYSTEM_ERROR(50),//系统内部错误
    ILLEGAL_VERSION(51),//非法版本
    UNAUTHORIZED(401),//未经认证
    FORBIDDEN(403), //  未经授权
    NOT_FOUNT(404), //  找不到
    TOO_MANY_REQUEST(429), //请求过多
    ILLEGAL_PARAM(614);//非法参数

    private int code;

    RCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
```

Kotlin 模型
```kotlin
enum class RCode(val code: Int, val defaultMessage: String) {
    SUCCESS(0, ""),
    OTHER(7, "未知错误"),
    TIME_OUT(30, "超时"),
    SYSTEM_ERROR(50, "系统内部错误"),
    ILLEGAL_VERSION(51, "非法版本"),
    INITIALIZATION_FAILED(52, "初始化失败"),
    UNAUTHORIZED(401, "未经认证"),
    FORBIDDEN(403, "未经授权"),
    NOT_FOUND(404, "找不到"),
    TOO_MANY_REQUEST(429, "请求过多"),
    ILLEGAL_PARAM(614, "非法参数");
}
```

TS模型
```typescript
declare const enum RCode {
    SUCCESS = 0, // 成功
    OTHER = 7, // 其他
    TIME_OUT = 30, // 超时
    SYSTEM_ERROR = 50, // 系统内部错误
    ILLEGAL_VERSION = 51, // 非法版本
    UNAUTHORIZED = 401, // 未经认证
    FORBIDDEN = 403, //  未经授权
    NOT_FOUNT = 404, //  找不到
    TOO_MANY_REQUEST = 429, //  请求过多
    ILLEGAL_PARAM = 614// 非法参数
}
```

<br>

## 注意事项
* 出于对某些开发语言、环境的礼貌，以及敬畏，月幕的所有接口中，若出现数字精度大于 2^53 的情况（比如有些模型是通过 SnowFlake 算法生成的 ID），接口中返回的所有 **数字长整型**（int64） 都会转变为 **字符串**， 这可能会使您感到疑惑，实际是正常的，一般情况下您不需要为此付出额外的心智负担， 各语言主流的 json 处理库不会因此出现异常。

<br>