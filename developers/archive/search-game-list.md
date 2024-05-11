# 搜索游戏列表

同月幕Galgame主站搜索功能。

传入查询 keyword 时，此接口会根据以下要素命中数据：

* 中文名与原名的完全匹配
* 与中文名或原名相似度 > 0.5
* keyword 分词后，根据倒排索引命中的文本向量，按权重大小直出

以上优先度依次递减。

<br>

同时此接口支持别名、外号、圈内称呼等查询。

比如：罚抄

可以命中：Rewrite（改写）

当然，能查 != 查的对，查询不等式秒了。


<br>

## Resource Path

`GET /open/archive/search-game`

`version: 1`

<br>

## Request Parameter

| 参数名 | 类型     |   默认值  | 必要  | 校验          | 说明                    |
|-----|--------|-----|-----|-------------|-----------------------|
|  mode   | string |     |   ✅  |             | 固定值: `list`, 请直接传入此参数 |
|   keyword  | string |     |  ✅   | length < 96 | 查询关键字                 |
|    pageNum | int32  |     |   ✅  | 大于 0        | 页号                    |
|    pageSize | int32  |     |   ✅  | 取值范围 1~20   | 页大小                   |

<br>

## Request Body
无

<br>

## Response Body

此接口返回一个 Page，下方示例仅为 Page 中列表的单条数据

字段说明请与档案API章节的档案模型对照，这里并没有需要额外注意的参数。


```json
{
  "orgId": 0,
  "orgName": "",
  "releaseDate": "2024-05-10",
  "haveChinese": false,
  "id": 0,
  "name": "",
  "chineseName": "",
  "state": "FREEZE",
  "weights": 0,
  "mainImg": "",
  "publishVersion": 0,
  "publishTime": "2024-05-10 16:15:16",
  "publisher": 0,
  "score": ""
}
```