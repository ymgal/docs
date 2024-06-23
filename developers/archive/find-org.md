# 查询机构详情

通过 orgId 查询单个机构的详情


<br>

## Resource Path

`GET /open/archive`

`version: 1`

<br>

## Request Parameter


| 参数名   | 类型    |   默认值  | 必要  | 校验  | 说明   |
|-------|-------|-----|-----|-----|------|
| orgId | int64 |     |   ✅  |     | 机构ID |

<br>

## Request Body
无

<br>

## Response Body
```json
{
    "org": {
      "publishVersion": 4,
      "publishTime": "2024-05-11 23:00:42",
      "publisher": 1,
      "name": "枕",
      "chineseName": "枕社",
      "extensionName": [
        {
          "name": "まくら",
          "type": "",
          "desc": ""
        },
        {
          "name": "Makura",
          "type": "",
          "desc": ""
        }
      ],
      "introduction": "枕，是一家日本视觉小说制作工作室……",
      "state": "PUBLIC_PUBLISHED",
      "weights": 0,
      "mainImg": "https://store.ymgal.games/archive/main/00/00946e721af7447fa8e5028f7950ca6b.webp",
      "moreEntry": [],
      "orgId": 10270,
      "country": "ja",
      "website": [
        {
          "title": "wikidata",
          "link": "https://en.wikidata.org/wiki/Q6006086"
        },
        {
          "title": "homepage",
          "link": "http://www.makura-soft.com/"
        }
      ],
      "type": "ORGANIZATION",
      "freeze": false
    }
  }
```

**说明：**
* org 字段数据和 [档案业务模型](business-model.md) 中定义的 Organization 模型一致

<br>
