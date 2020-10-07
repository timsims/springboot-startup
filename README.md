# Spring Boot Project Startup Template

## 项目说明

本项目用于快速构建基本框架, 会根据日常工作不断完善

已有功能 |  备注
----|---
用户注册| 路由 /register
用户登陆| 路由 /login
自动请求校验| 看 requests 目录
安全权限| spring security 自带
JWT 解码和校验| 已实现全局中间件校验 jwt 合法性
AES 加密解密| 兼容 Laravel 的数据结构
全局返回结构| 中国特色的 data, message, status 三件套， 可以调整为一律 200 返回

计划中的功能 | 备注
---|---
数据库迁移| flywaydb
补充单元测试| 没有单元测试能忍？
websocket,tcp 支持|
断路器| 


## 文件夹说明

### startup 代码根目录说明

目录 | 作用 | 备注
---|---|---
configurations| 配置目录，包括切不限所有 Bean ，服务依赖等启动配置
exceptions | 自定义异常以及异常捕捉
http| 仅和 http 相关的代码, 下面表格有详细说明
models | 数据库模型层 @Enitity
repository | JPA repository
services | 业务层 | 接口写在 services 下，实现写在 impl 目录中
components| 组件目录，主要是功能独立，基本不依赖其他层的代码| 加密， JWT, 第三方服务调用等

### http 目录说明
目录 | 作用 | 备注
---|---|---
controllers| 控制器
filters| 中间件
requests| 用户请求，主要用于数据校验
response| 数据响应

### resources 目录配置说明
本项目使用 yml 进行配置

application.yml 是主要配置文件，但其中的配置值均为占位符，具体的值通过 application-xxx.yml 进行配置

## 项目依赖的第三方模块

模块名称 | 作用 | 备注
---|---|---
spring-boot-starter-security| 安全和权限控制| configurations目录下的 `SecurityConfiguration` 是具体的配置
spring-boot-starter-validation| 数据校验| 用于 `request` 目录的请求校验
pring-boot-configuration-processor| configuration 自动化配置
io.jsonwebtoken| jwt 库|
org.projectlombok| 减少手写getter, setter| IntelliJ 需要安装同名插件才不会报错
org.modelmapper| 数据、模型映射 | 方便把用户请求 VO 转换为模型 DO ，把实体模型 DO 转换为 VO, 以及 json 转换为对应的类
commons-codec| aes 加密需要用到库
mysql-connector-java| mysql 连接
assertj-core | 单元测试
com.github.javafaker| 单元测试生成假数据 | 配合 lombok 的 builder 使用
    

