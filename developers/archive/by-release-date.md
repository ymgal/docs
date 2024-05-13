# 查询日期区间内发行的游戏

传入一个日期区间，查询出发行时间在该区间内的游戏

一般可用作查询 本月新作、上月新作 等


<br>

## Resource Path

`GET /open/archive/game `

`version: 1`

<br>

## Request Parameter

| 参数名 | 类型   |   默认值  | 必要  | 校验         | 说明            |
|-----|------|-----|-----|------------|---------------|
| releaseStartDate | Date |     |   ✅  | yyyy-MM-dd | = string，开始时间 |
| releaseEndDate | Date |     |   ✅  | yyyy-MM-dd | = string，结束时间 |

1. 开始时间不能在结束时间之后
2. 区间内的天数不能超过 50
3. 开始时间必须在 1980 年之后
4. 开始时间不能超过当前五年，假设今年是2020年，则 releaseStartDate 不能是 2026-01-01

<br>

## Request Body

无

<br>

## Response Body

正确返回的话内容不会为 null，如指定区间内不存在发行游戏则返回空数组

```json
[
  {
    "restricted": false,
    "orgName": "",
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
