# 查询人物详情

通过 pid 查询单个人物的详情


<br>

## Resource Path

`GET /open/archive`

`version: 1`

<br>

## Request Parameter


| 参数名 | 类型    |   默认值  | 必要  | 校验  | 说明   |
|-----|-------|-----|-----|-----|------|
| pid | int64 |     |   ✅  |     | 人物ID |

<br>

## Request Body
无

<br>

## Response Body
```json
{
  "person": {
    "publishVersion": 1,
    "publishTime": "2022-10-20 02:04:06",
    "publisher": 6228,
    "name": "高野直子",
    "chineseName": "高野直子",
    "extensionName": [
      {
        "name": "川村みどり",
        "type": "马甲",
        "desc": ""
      },
      {
        "name": "雅姫乃",
        "type": "马甲",
        "desc": ""
      }
    ],
    "introduction": "高野直子(1968年6月18日 - )，是日本的女性声优……",
    "state": "PUBLIC_PUBLISHED",
    "weights": 0,
    "mainImg": "https://store.ymgal.games/archive/main/d6/d6ba6218f7bd43a081978c6ff2c4ffc3.webp",
    "moreEntry": [],
    "pid": 10008,
    "country": "ja",
    "birthday": "1968-06-16",
    "gender": 2,
    "website": [
      {
        "title": "事务所个人页",
        "link": "https://sigma7.co.jp/actors/takano_naoko"
      },
      {
        "title": "个人博客",
        "link": "http://naokoblog.exblog.jp/"
      }
    ],
    "type": "PERSON",
    "freeze": false
  }
}
```

**说明：**
* person 字段数据和 [档案业务模型](business-model.md) 中定义的 Person 模型一致

<br>
