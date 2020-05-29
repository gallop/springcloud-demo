## Spring Cloud Demo项目

>本项目采用eureka作为服务发现组件，采用jwt做微服务模块的授权，采用springcloud gateway作为服务网关，在网关进行限流、全局过滤、鉴权等操作，并解析jwt token中授权的用户信息，放在header中传递到后端供后端微服务模块使用。
组织结构简要说明：

```
springcloud-demo
├── consumer-service -- 后端微服务项目
├── eureka -- 服务注册与发现中心
├── gallop-common -- 工具类及通用代码
└── gateway-hystrix -- 服务网关

```