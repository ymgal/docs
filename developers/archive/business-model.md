# 档案业务模型

业务对象建模，在 Archive 模块中有 4 种平级的档案模型，他们互相之间通过某些形式约定了多对多的关系

* Game，游戏
* Organization，机构
* Character，角色
* Person，真实人物

<br>

其中, 游戏档案 (Game) **包含**以下实体关系， 一对多

```
characters - CharacterRelation[]
staff  -  Staff[]
releases  -  Release[]
```

并且通过以下字段和其他的模型产生关联， 多对多

```
developerId -  Organization
staff  -  Staff#pid  -  Person
characters - CharacterRelation#cid -  Character
characters - CharacterRelation#cvid - Person
```

游戏之外的几种模型都比较简单，查看对应小节的字段解释与 JSON 样例即可

<br>
<br>

## Archive
首先请先看一下这个抽象的档案模型，本模型定义了月幕中档案的公用基本参数

Game、Organization、Character、Person 均属于该模型的一种具体实现

虽然在后端的设计中它们之间并不是一个直接的继承或组合关系，但是在 API 对接时，你可以理解为上述四种档案模型均是继承自 Archive 的子类，这与请求、响应的现状是相符的


| 字段名           | 类型               | nullable | 说明     | 例子                  | 备注                                       |
|---------------|------------------|-------|--------|---------------------|------------------------------------------|
| name          | string           |       | 档案名    | 月に寄りそう乙女の作法         | 可能是英文、中文，反正原名是什么就是什么                     |
| chineseName   | string           | ✅     | 档案中文名  | 大藏游星                | 这个是汉化名。若本身原名即是中文，那么这里是可能没有的              |
| extensionName  | ExtensionName[]  |       | 拓展名    | []                  | 一般来说是外号、别称、还有各个国家的不同翻译                   |
| introduction     | string           |       | 简介、说明  |                     |                                          |
| state  | ArchiveStateEnum |       | 档案状态   | FREEZE              | 标识状态的，可以等同于string                        |
| weights      | int32            |       | 权重     | 0                   |                                          |
| mainImg  | string           | ✅     | 主图     |                     | 在作为接口返回值时是不会为null的，检查到null时会自动设置为默认的图片地址 |
| moreEntry | MoreEntry[]      |       | 额外的键值对 | []                  | 一般用来放置额外信息                               |
| publishVersion       | int32            |       | 发布版本   | 0                   |                                          |
| publishTime        | datetime         |   ✅    | 发布时间   | 2023-08-07 00:01:50 | === string                               |
| publisher          | int64            |   ✅    | 发布者   |                     |                                          |

ExtensionName、MoreEntry 均属于单纯为了承载信息的内容字段，并不与任何档案之间具备任何关联关系，在本章节最后面统一列出详细信息

<br>

### ArchiveStateEnum

ArchiveStateEnum 是一个枚举，标识了这个档案处于什么状态，不同的状态可能需要不同的访问权限才可以查看。

共三种类型：
* FREEZE
* PROTECTED_PUBLISHED
* PUBLIC_PUBLISHED

绝大部分接口中接入者都不需要关心这个东西

<br>
<br>



## Game

游戏档案，JSON示例请查看最后一节

| 字段名           | 类型                  |   nullable  | 说明        | 例子                           | 备注                           |
|---------------|---------------------|-----|-----------| ------------------------------ |------------------------------|
| gid           | int64               |     | 游戏档案ID    |             31147                   |         https://www.ymgal.games/ga31147                     |
| developerId   | int64               |     | 游戏品牌的机构ID |                                | 关联关系用, 等同于orgId              |
| haveChinese   | boolean             |     | 有中文版      | true                           |                              |
| typeDesc      | string              |     | 游戏类型描述    | バトルADV |                              |
| releaseDate   | Date                |  ✅   | 发售日期      | 2020-01-01                     | string：yyyy-MM-dd， 指的是正式发售日期 |
| restricted | boolean             |     | 限制级       | false                          | 带有露点、暴力等内容为true              |
| country | string              |   ✅  | 国家        |                           |                              |
| characters    | CharacterRelation[] |     | 出场人物      |                                | 主角配角们，关联的Charater            |
| releases      | Release[]           |     | 发行版本      |                                | 该游戏发行过的版本                    |
| website       | Website[]           |     | 相关网站      |                                | 官网、售卖网站等                     |
| staff         | Staff[]             |     | 制作员工      |                                | 声优、画师、脚本等人力资源。               |

Website 属于单纯为了承载信息的内容字段，在本章节最后面统一列出详细信息

<br>

### CharacterRelation

| 字段名   | 类型     |  nullable   | 说明       | 例子 | 备注                     |
| -------- |--------|-----|----------| ---- |------------------------|
| cid    | int64  |     | 角色ID     |  | Character#cid          |
| cvId    | int64 |   ✅  | 配音员的人物ID |  | Person#pid， 一个角色可以没有配音 |
| characterPosition | number |     | 角色定位     | 1    | 1=主角  2=配角             |



### Release

| 字段名               | 类型     |   nullable  | 说明       | 例子                                       | 备注  |
|-------------------|--------|-----| ---------- | ------------------------------------------ |-----|
| id                | int64  |     | 发布名称   |                               | 主键  |
| release_name      | string |     | 发布名称   | ISLAND 体験版                              |     |
| related_link      | string |  ✅   | 相关链接   | http://never-island.com/game/download.html |     |
| platform          | string |     | 发行平台   | PS4 、PC、 PS Vita、 Nintendo Switch       |     |
| release_date      | Date   |   ✅  | 发售日期   | 2020-01-01                                 |  string：yyyy-MM-dd   |
| restriction_level | string |     | 限制级等级 | All ages、 15+                             |     |
| country           | string |     | 发售国家   | Chinese、English、Japanese                 |     |



### Staff

游戏制作人员

| 字段名      | 类型   |  nullable   | 说明          | 例子           | 备注                                |
|----------| ------ |-----|-------------| -------------- |-----------------------------------|
| sid      | int64 |     |             |                | 主键                                |
| pid      | int64 |   ✅  | 真实人物的pid    |                | 虽然有staff名字，但是不晓得是对应哪个人的话，这个字段就为空咯 |
| job_name | string |     | 职位名         | 原画设计、剧本 | 该staff的担当职责                       |
| desc     | string |  ✅    | 跟在员工名称后面的备注 |                |                                   |
| emp_name | string |     | 员工名称 |                | **参与名**，不一定是真名                    |


<br>
<br>

## Organization

组织机构，可能是发行商、开发商、工作室等等等等

| 字段名         | 类型        |   nullable  | 说明   | 例子       | 备注                                                        |
| -------------- |-----------|-----|------| ---------- |-----------------------------------------------------------|
| orgId       | int64     |     | 主键   |     10041       |                 https://www.ymgal.games/oa10041                                          |
| country | string    |   ✅  | 国家   |                           |                                                           |
| website        | Website[] |     | 相关网站 |            | 官方推特、官网等                                                  |
| birthday       | Date      |  ✅  | 建立日期 | 2020-01-01 | string：yyyy-MM-dd，有时可能会出现 Y >= 3000的情况，这一般是因为不知道具体的年份才设置的 |

<br>
<br>


## Character

角色档案

| 字段名            | 类型         |  nullable   | 说明        | 例子       | 备注                      |
|----------------|------------|-----|-----------| ---------- |-------------------------|
| cid            | int64      |     | 主键        |      19366      |           https://www.ymgal.games/ca19366              |
| gender         | GenderEnum |     | 性别        | 1          | number                  |
| birthday       | Date       |  ✅   | 生日        | 2201-01-01 | 同 Organization#birthday |

<br>

### GenderEnum

这个枚举也会在人那里用到，虽然真实人物没有扶她 = =

Java 
```java
public enum GenderEnum {

    UNKNOWN(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女"),
    // ふたなり
    FUTANARI(3, "扶她");

    private int code;
    private String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
```

TypeScript
```typescript
export enum Gender {
    UNKNOWN = 0, //"未知"
    MAN = 1, //男
    WOMAN = 2,//女
    // ふたなり
    FUTANARI = 3,//扶她
}
```

<br>
<br>



## Person

现实人类个体

需要注意的是 Person 档案的 name 并不一定就是那个人的真名。 自称、约定速成的名字 都可能存在

| 字段名      | 类型        |  nullable   | 说明   | 例子        | 备注                   |
|----------|-----------|-----|------|-----------|----------------------|
| pid      | int64     |     | 主键   | 10150     |        https://www.ymgal.games/pa10150              |
| country  | string    |  ✅   | 国籍   |           |                      |
| birthday | Date      |  ✅   | 生日   | 2001-01-01 | 同 Character#birthday |
| gender   | GenderEnum    |     | 性别   | 1         | 同 Character#gender        |
| website  | Website[] |     | 相关网站 | []        |                      |



<br>
<br>

## 内容字段建模


### ExtensionName

拓展名称字段

| 字段名 | 类型   | nullable | 说明     | 例子                           | 备注               |
| ------ | ------ |----------| -------- | ------------------------------ | ------------------ |
| name   | string |          | 名称     | アイランド、ISLAND             |                    |
| type   | string |     ✅     | 类型描述 | 中文译名、英文译名、别名、简称 | 描述就是中文描述   |
| desc   | string |      ✅    | 备注     |                                | 跟在名称后面的备注 |


### MoreEntry

额外键值对， 补充说明用

有的编辑老哥会额外加些东西，比如：

* 18X补丁发布时间：xxx
* 特殊码：xxx
* 发售价格：xxx
* 等等……

| 字段名   | 类型   | nullable |
|-------| ------ |----------|
| key   | string |          |
| value | string |          |

### Website

和档案相关的网站

| 字段名 | 类型   | nullable | 说明    | 例子                  | 备注 |
| ------ | ------ |----------| ------- | --------------------- | ---- |
| title  | string |          | 标题    | wiki、官方网站        |      |
| link   | string |          | 网站URL | https://www.baidu.com |      |


<br>
<br>

---

## JSON Example (Game)

```json
{
      "publishVersion": 0,
      "publishTime": "2021-08-07 00:00:00",
      "name": "月に寄りそう乙女の作法",
      "chineseName": "近月少女的礼仪",
      "extensionName": [
        {
          "name": "つり乙",
          "type": "",
          "desc": ""
        },
        {
          "name": "Tsuriotsu",
          "type": "",
          "desc": ""
        }
      ],
      "introduction": "主人公大藏游星，作为代表了日本财界的“华丽一族”的大藏家的末端......",
      "state": "PUBLIC_PUBLISHED",
      "weights": 0,
      "mainImg": "http://store.dev-ymgal.com/archive/main/3d/3d99dc3f2888479f98eadb3501c50713.webp",
      "moreEntry": [],
      "gid": 31147,
      "developerId": 10041,
      "haveChinese": true,
      "typeDesc": "",
      "releaseDate": "2012-10-26",
      "restricted": true,
      "country": "",
      "website": [
        {
          "title": "官中网站",
          "link": "https://hikarifield.co.jp/tsukiniyorisou/"
        }
      ],
      "characters": [
        {
          "cid": 19365,
          "cvId": 11333,
          "characterPosition": 1
        },
        {
          "cid": 19366,
          "cvId": 10954,
          "characterPosition": 2
        }
      ],
      "releases": [
        {
          "id": 110064,
          "releaseName": "近月少女的礼仪",
          "relatedLink": "https://hikarifield.co.jp/tsukiniyorisou/",
          "platform": "Windows",
          "releaseLanguage": "Chinese",
          "restrictionLevel": "全年龄"
        },
        {
          "id": 110065,
          "releaseName": "A moon a sun and three macaroons",
          "relatedLink": "https://drive.google.com/file/d/1vTyy7AY0MG6RWJmi-qLsgPERjmm44WiG/view?usp=sharing",
          "platform": "Windows",
          "releaseDate": "2021-07-05",
          "releaseLanguage": "English",
          "restrictionLevel": "未分级"
        }
      ],
      "staff": [
        {
          "sid": 126376,
          "pid": 11083,
          "empName": "鈴平 ひろ",
          "empDesc": "",
          "jobName": "原画"
        },
        {
          "sid": 126377,
          "pid": 11236,
          "empName": "西又 葵",
          "empDesc": "",
          "jobName": "原画"
        }
      ],
      "type": "GAME",
      "freeze": false
    }
```
