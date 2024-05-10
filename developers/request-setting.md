# HTTP 请求设置

域名：[https://www.ymgal.games](https://www.ymgal.games)

API 端点均基于该域名实行请求。

在请求月幕的开放 API 时，需要遵守相关的规定才能成功访问。

简单的来说，你只需要在 Request Header 中设置几个字段即可。

<br>

## Accept

我们默认所有的接口均使用 JSON 格式返回。

请在你的请求头中设置：

`Accept: application/json;charset=utf-8`

<br>

## Authorization

我们默认所有的接口调用均需要 access_token

请在你的请求头中设置：

`Authorization: Bearer access_token`

如:

`Authorization: Bearer 2fdace3c-651f-4484-85ac-9a449da43f05`

<br>

## version 
我们默认所有的接口调用均需要指定 version ，接口文档中会声明接口版本

请在你的请求头中设置 （版本为1的情况）：

`version: 1`