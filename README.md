技术选型

1. 服务注册与发现和服务配置中心选型

nacos VS Spring Cloud Eureka
Nacos = Spring Cloud Eureka + Spring Cloud Config
Nacos 可以与 Spring, Spring Boot, Spring Cloud 集成，并能代替 Spring Cloud Eureka, Spring Cloud Config。
通过 Nacos Server 和 spring-cloud-starter-alibaba-nacos-config 实现配置的动态变更。
通过 Nacos Server 和 spring-cloud-starter-alibaba-nacos-discovery 实现服务的注册与发现。

通过以上对比，选择nacos作为服务注册与发现服务和服务配置中心

2. 网关选型

spring cloud zuul VS spring cloud gateway
spring cloud 基于zuul1.x。
Zuul1.x构建于 Servlet 2.5，兼容 3.x，使用的是阻塞式的 API，不支持长连接，比如 websockets。
Spring Cloud Gateway构建于 Spring 5+，基于 Spring Boot 2.x 响应式的、非阻塞式的 API。同时，它支持 websockets，和 Spring 框架紧密集成。

性能比较：
组件					RPS(request per second)
Spring Cloud Gateway	Requests/sec:  32213.38
Zuul					Requests/sec:  20800.13

通过以上对比，性能上spring cloud gateway优于spring cloud zuul，而且使用起来比 Zuul 更简单，配置更方便。
但是目前spring cloud zuul成熟度高于spring cloud gateway。
因此此次选择zuul，日后spring cloud gateway更加成熟了可以切换到spring cloud gateway。

3. 负载均衡器选型
nacos和spring cloud zuul均集成了ribbon，而且没有其他的负载均衡组件，因此选用ribbon

4. 声明式调用
spring cloud figen, spring cloud提供的声明式调用组件。

5. 限流和熔断
Hystrix和Sentinel都提供了限流和熔断的功能。
Hystrix 的关注点在于以 隔离 和 熔断 为主的容错机制，超时或被熔断的调用将会快速失败，并可以提供 fallback 机制。
Sentinel 的侧重点在于：多样化的流量控制、熔断降级、系统负载保护、实时监控和控制台。
Sentinel更加轻量级，高性能；可以针对不同的调用关系，以不同的运行指标（如QPS、并发调用数、系统负载等）为基准，对资源调用进行流量控制；还提供了系统负载保护和控制面板。
通过以上对比，Sentinel功能更加细化和丰富，因此限流和熔断选择Sentinel


6. 网关鉴权
Spring Security AOuth2和JWT的方式，避免每次请求都需要远程调度用户认证服务。采用Spring Security OAuth2和JWT的方式，用户认证服务只验证一次，返回JWT。
返回的JWT包含用户的所有信息，包含权限信息，token一次生成，在不同的服务中通用，适合在分布式环境下的权限验证。



