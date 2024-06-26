# 接入概述

本文档描述的是 月幕Galgame 站点（简称：月幕 或 ymgal）可供开发者接入的 HTTPS API。

部分接口可能需要访问权限，如用户登录、数据修改等，但大部分查询接口 —— 通常来说，这代表着游客在月幕可以阅览的所有数据，在我们的API中是没有权限判定的，您可以自由访问。

月幕的开放接口使用 OAuth2.0 协议认证，**包括公共接口**，这是为了保证后台设计的同一性而决定的。 调用时，您需要 `client_id` 与 `client_secret`
作为客户端凭证，通过允许的认证方式获取到一个 `access_token` 后，携带此token实行请求。

我们提供了公共的 `client_id` 与 `client_secret` 供您使用，它能够满足大部分人的学习、实验、整理、调研等需求。

月幕**不能**保证各API的可访问性，我们只能保证它在大部分地区，以及境外是可访问的。

月幕**不能**保证域名的变动可控，它可能会在某次DNS污染后被更换，发生这种情况时，我们会尽可能延续旧域名的可用性，这并不受到保障，不代表您可以直接使用旧域名、或拒绝更新。

文档中所陈列的接口均为稳定状态。

<br>

## 开发者须知
（默认接入方已阅读）
* 月幕的API服务仅适用于非商业用途，请不要将其用于商业项目。
* API访问有速率限制以便控制服务器资源，请避免使用并发、循环等调用方式，对于静态资源需做好缓存。
* 若您申请了专属的 `client_id` 与 `client_secret`，请不要将其发布到互联网中（如交流论坛、开源代码），当然，你发布了也行，只是在被坏东西滥用时，走的是您的流量池，这可能会导致您正常的业务受到影响。
* 若您的项目是一个可在互联网访问、查看的项目，您需要在项目的说明区域（如README、关于、页脚等）提到月幕Galgame。
* 在开发出现问题时，可以通过接口调用的返回码，来发现和解决问题，具体请查看[全局返回对象](api-response.md)。
* 您的服务需要收集用户任何数据的，必须事先获得用户的明确同意，且仅应当收集为运营及功能实现目的而必要的用户数据， 同时应当告知用户相关数据收集的目的、范围及使用方式等，保障用户知情权。
* 您收集用户的数据后，必须采取必要的保护措施，防止用户数据被盗、泄漏等。
* 请勿公开表达或暗示您与月幕之间存在合作关系，或声称月幕对您的认可。

<br>

## Public client identifier

* client_id: `ymgal`
* client_secret: `luna0327`

暂未开启站内申请渠道（懒得写，有需要的话再看），若您想申请专属的 `client_id`，请通过提出 issue 的方式，言明您的用途与项目主页

当然，访问速率限制总归是有的，无非是多与少、共享与否的区别

若您开发的是服务型、分发型应用，为了避免影响业务，请自行实现一个妥善的请求拒绝策略，您可以在[全局返回对象](api-response.md)中查看相关错误码

<br>

## 关于

月幕的开放API并未提供所有服务功能。

一般情况下，日常更新迭代的API可能只是因为站长写代码写嗨了才得以实现，不一定会是按照业务线或模块顺序提供，同时，一不小心鸽它大半年也是很正常的一件事，不得不品鉴。

在 issues 内已同意实现的不会鸽，若有相关需求，请在 [issues](https://github.com/ymgal/docs/issues) 提出。

总之，别急。

另外，本文档是个单文件HTML，由程序自动构建，其内容由多个 Markdown 文件组成。

若你发现文档错误、显示异常、渲染失败等情况，欢迎前往此文档的 Github 仓库提交 PR 或 issue。

和 IE 相关的不受理，老哥换个浏览器吧。

* [源文档](https://github.com/ymgal/docs/blob/main/developers/overview.md)
* [页面构建程序](https://github.com/ymgal/docs/blob/main/htmlgen/src/main/kotlin/Main.kt)

<br>