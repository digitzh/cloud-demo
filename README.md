# cloud-demo

## 介绍

SpringCloud学习。有2个微服务，分别是订单（service-order）和商品（service-product）。

在Seata一节，则有3个微服务，分别是订单（service-order）、商品（service-product）和账户（service-account）。

- 使用 Spring Boot多层架构
- 使用 Nacos 作为注册和配置中心
- 使用 OpenFeign 从订单调用商品服务
- 使用 Sentinel 进行流量控制, 熔断降级与链路追踪
- 使用 Gateway 作为网关
- 使用 Seata 进行分布式事务管理

