# 主题列表

获取月幕 Galgame 最新发布的资讯或专栏。

<br>

## Resource Path

`GET /open/topic/news` —— 获取最新资讯列表

`GET /open/topic/column` —— 获取最新专栏列表

`version: 1`

<br>

## Request Parameter

| 参数名 | 类型    | 默认值 | 必要 | 校验   | 说明               |
|-----|-------|-----|----|------|------------------|
| page | int32 | 1   | ❌  | 大于 0 | 页号，每页返回 10 条数据 |

<br>

## Request Body
无

<br>

## Response Body

两个接口的返回结构一致。`mainImg` 为主图完整地址，`topicUrl` 为文章页面完整地址。

```json
[
  {
    "topicId": 123,
    "author": 456,
    "mainImg": "https://cdn.ymgal.games/topic/main/example.webp",
    "topicUrl": "https://www.ymgal.games/co/article/123",
    "title": "文章标题",
    "introduction": "文章简介",
    "views": 100,
    "replyNum": 10,
    "likesNum": 20,
    "favoritesNum": 5,
    "publishTime": "2026-08-05 12:00:00",
    "createAt": "作者名称",
    "topicCategory": "资讯"
  }
]
```
