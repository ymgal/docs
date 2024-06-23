# 查询角色详情

通过 cid 查询单个游戏出场角色的详情


<br>

## Resource Path

`GET /open/archive`

`version: 1`

<br>

## Request Parameter


| 参数名 | 类型    |   默认值  | 必要  | 校验  | 说明   |
|-----|-------|-----|-----|-----|------|
| cid | int64 |     |   ✅  |     | 角色ID |

<br>

## Request Body
无

<br>

## Response Body
```json
{
  "character": {
    "publishVersion": 4,
    "publishTime": "2021-10-05 22:38:56",
    "publisher": 223,
    "name": "小倉 朝日",
    "chineseName": "小仓朝日",
    "extensionName": [
      {
        "name": "Ookura Yuusei",
        "type": "",
        "desc": ""
      },
      {
        "name": "大蔵 遊星",
        "type": "",
        "desc": ""
      },
      {
        "name": "大藏游星",
        "type": "",
        "desc": ""
      }
    ],
    "introduction": "伪装起性别与身份，樱小路露娜的女仆……",
    "state": "PUBLIC_PUBLISHED",
    "weights": 0,
    "mainImg": "https://store.ymgal.games/archive/main/c9/c92c1ceafcc54ec9a182655faedae453.jpg",
    "moreEntry": [],
    "cid": 19365,
    "gender": 1,
    "birthday": "3000-01-06",
    "type": "CHARACTER",
    "freeze": false
  }
}
```

**说明：**
* character 字段数据和 [档案业务模型](business-model.md) 中定义的 Character 模型一致

<br>
