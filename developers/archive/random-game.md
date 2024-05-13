# 随机游戏

随机出一个游戏列表，可指定数量

经过默认的筛选，不会返回一些特别小众的、未经过修订的游戏


<br>

## Resource Path

`GET /open/archive/random-game `

`version: 1`

<br>

## Request Parameter


| 参数名 | 类型    |   默认值  | 必要  | 校验       | 说明      |
|-----|-------|-----|-----|----------|---------|
| num | int32 |     |   ✅  | 取值范围1~10 | 要随机出的数量 |

<br>

## Request Body
无

<br>


## Response Body

本接口必定能返回符合期望的数据条数

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
