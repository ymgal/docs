# 查询游戏详情

通过 gid 查询单个游戏的详情

和**搜索游戏-精确**这个接口的返回值一模一样

除非你是瞎几把传的 gid， 否则不会出现 NOT FOUND 的错误

<br>


[https://www.ymgal.games/GA31147](https://www.ymgal.games/GA31147)

GA31147 === Game archive with id 31147

<br>

## Resource Path

`GET /open/archive`

`version: 1`

<br>

## Request Parameter


| 参数名 | 类型    |   默认值  | 必要  | 校验  | 说明   |
|-----|-------|-----|-----|-----|------|
| gid | int64 |     |   ✅  |     | 游戏ID |

<br>

## Request Body
无

<br>

## Response Body
```json
{
    "game": {},
    "cidMapping": {
        "19366": {
            "cid": 19366,
            "name": "桜小路 ルナ", // 此档案名称，原名
            "mainImg": "",  // 一个完整的图片路径
            "state": "PUBLIC_PUBLISHED",  // 此档案状态
            "freeze": false   // 是否属于一个被冻结的档案
        }
    },
    "pidMapping": {
        "10018": {
            "pid": 10018,
            "name": "橋本 みゆき",
            "mainImg": "",
            "state": "PUBLIC_PUBLISHED",
            "freeze": false
        }
    }
}
```

**说明：**

* game 字段数据和 [档案业务模型](business-model.md) 中定义的 Game 模型一致，内部嵌套了 character、staff 等关联关系
* cidMapping 是一个映射表`(cid -> data)`，返回此游戏涉及到的所有 character 基础信息
* pidMapping 是一个映射表`(pid -> data)`，返回此游戏涉及到的所有 person 基础信息

<br>
