# 查询机构下的游戏

指定机构

返回所有该机构下的游戏，没有则为空数组


<br>

## Resource Path

`GET /open/archive/game `

`version: 1`

<br>

## Request Parameter


| 参数名 | 类型    |   默认值  | 必要  | 校验  | 说明   |
|-----|-------|-----|-----|-----|------|
| orgId | int64 |     |   ✅  |     | 机构ID |

<br>

## Request Body
无

<br>

## Response Body
```json
[
  {
    "gid": 0,
    "developerId": 0,
    "name": "",
    "chineseName": "",
    "haveChinese": false,
    "mainImg": "",
    "releaseDate": "2024-05-13",
    "state": "FREEZE"
  }
]
```
