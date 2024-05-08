# 搜索游戏-精确

精确搜索游戏，直接返回对应目标 **详情**

具体的来说，此接口要求传入一个尽可能符合游戏名（或游戏中文名）的关键字以供查询

对于单个档案而言，将同时判定原名与中文名，取高值

本接口命中到复数档案时，返回名称相似度最高的数据

> keyword 匹配根据 [Trigram](https://lhncbc.nlm.nih.gov/ii/tools/MTI/trigram.html) 算法实现

此接口并不能保证匹配精确性，只能说，它在满足以下条件时，是可靠的

* 入参 keyword 是游戏的全名，如：月に寄りそう乙女の作法、近月少女的礼仪
* 游戏的全名所对应的游戏档案只有 1 个

如果你的入参 keyword 是游戏别名、简称、外号等，大概率你是查不到的，除非它正好和某个游戏名的匹配度较高



<br>

## Resource Path

`GET /open/archive/search-game`

<br>

## Request Parameter

| 参数名 | 类型     |   默认值  | 必要  | 校验  | 说明                        |
|-----|--------|-----|-----|-----|---------------------------|
|  mode   | string |     |   ✅  |     | 固定值: `accurate`, 请直接传入此参数 |
|   keyword  | string |     |  ✅   |  length < 96   | 查询关键字                     |
|    similarity | int32  |     |   ✅  |  取值范围 50~99   | 百分比相似度，只会定位到相似度比此参数更高的数据  |

<br>

## Request Body
无

<br>

## Response Body
```json lines
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

* game 字段数据和 [业务模型](business-model.md) 中定义的游戏业务模型一致，内部嵌套了 character、staff 等关联关系
* cidMapping 是一个映射表`(cid -> data)`，返回此游戏涉及到的所有 character 基础信息
* pidMapping 是一个映射表`(pid -> data)`，返回此游戏涉及到的所有 person 基础信息

<br>
