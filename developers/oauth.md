# 获取 Access Token

access_token 是月幕开放API的全局唯一接口调用凭据，调用各接口时都需使用access_token， 开发者需要进行妥善保存。

access_token 的有效期为 1 个小时，通过相同的认证参数在有效期内重复获取时，将获得重复的 access_token。 access_token 有效期未受到完全的保障，所以请 **不要使用定时器** 实现更新逻辑，而是在 **拒绝策略** 中实现。

<br>

## 基本概念

月幕的开放API基于 OAuth2.0 认证协议，您或许会需要以下文档对此协议进行深入了解：

* [https://www.oauth.com/](https://www.oauth.com/)

* [https://datatracker.ietf.org/doc/html/rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)

<br>

在这里需要指出：就算你完全没接触过 OAuth2.0，也并不影响你对接月幕的开放API，你只要把这个文档看完，照着做就行了。

> 在月幕的认证、授权流程中，接口返回值不遵循[全局返回对象](api-response.md)的返回值实现
> 
> 在月幕的认证、授权流程中，不需要遵循[HTTP 请求设置](request-setting.md)的规范

<br>

## 通过 Token 端点获取 Access Token

端点: `GET /oauth/token`

重复请求: 返回相同的 access_token

过期时间: 1h

<br>

### Client Credentials

* 此节说明的示例基于 OAuth2.0 Client Credentials Grant，适用于所有公共接口


* Client Credentials 模式不支持 Refresh Token

| Request param | Type   | Desc    | Value                       |
|---------------| ------ |---------|-----------------------------|
| client_id     | string | 标识一个客户端 | 你申请的 client_id，或公共的 client_id |
| client_secret | string | 客户端的密钥  | client_id 所对应的密钥            |
| grant_type | string | 授权类型    | client_credentials，固定值      |
| scope         | string | 可访问范围   | 您试图申请的可访问范围                 |

<br>


Example
```shell
curl 'https://www.ymgal.games/oauth/token?grant_type=client_credentials&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&scope=public'
```

Success

```json
{
  "access_token": "2fdace3c-651f-4484-85ac-9a449da43f05",
  "token_type": "bearer",
  "expires_in": 3599,
  "scope": "public"
}
```
Failed
```json
{
  "error": "invalid_client",
  "error_description": "Bad client credentials"
}
```
<br>

### 其他

暂无需求，所以暂未提供。

如果后续提供了 Authorization Code Grant 的实现，也只会是在需要用户权限的接口被发布时。

不同认证模式获取的 access_token 在相同的 scope 之下访问 API 没有任何区别。


<br>

## Scopes

| Scope  | Desc                                    |
|--------|-----------------------------------------|
| public | 公共接口，这里指的是不需要经过任何权限检查也能访问的接口，比如查询游戏详情。 
|


