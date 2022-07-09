# Design & Bulid

## 1. Project Environment

Windows 11 Professional Edition X64 -- version 22000.739

IntelliJ IDEA Ultimate 2021.3.3 X64

Apache Tomcat X64 --version 9.0.52

MySQL Server --version 5.7

JDK --version 11LTS



## 2. Project Structure

DnB-Webprogram

├─administrator-side-software

│ ├─.idea

│ ├─.mvn

│ ├─src

│ │ ├─main

│ │ │ ├─java

│ │ │ │ └─com

│ │ │ │   └─example

│ │ │ │     └─administratorsidesoftware

│ │ │ │       ├─common

│ │ │ │       │  ├─Constant.java （常量类）

│ │ │ │       │  ├─GoodsType.java （商品种类枚举类）

│ │ │ │       │  ├─Result.java （前后端通信统一接口类）

│ │ │ │       │  └─State.java （业务执行状态枚举类）

│ │ │ │       ├─config

│ │ │ │       │  ├─CorsConfig.java （前后端跨域配置类）

│ │ │ │       │  └─MybatisPlusConfig.java （mybatis-plus配置类）

│ │ │ │       ├─controller （控制层）

│ │ │ │       │  ├─EchartsController.java 

│ │ │ │       │  ├─GoodsController.java

│ │ │ │       │  ├─ManagerController.java

│ │ │ │       │  ├─PositionController.java

│ │ │ │       │  ├─WarehouseController.java

│ │ │ │       │  ├─WorkerController.java

│ │ │ │       │  └─DTO      （data transfer object）

│ │ │ │       │     ├─CaptchaDTO.java

│ │ │ │       │     ├─GoodsDTO.java

│ │ │ │       │     ├─ManagerDTO.java

│ │ │ │       │     ├─PositionDTO.java

│ │ │ │       │     ├─WarehouseDTO.java

│ │ │ │       │     └─WorkerDTO.java

│ │ │ │       ├─entity (实体类）

│ │ │ │       │  ├─Goods.java

│ │ │ │       │  ├─Manager.java

│ │ │ │       │  ├─Position.java

│ │ │ │       │  ├─Record.java

│ │ │ │       │  ├─Warehouse.java

│ │ │ │       │  └─Worker.java

│ │ │ │       ├─mapper （持久层）

│ │ │ │       │  ├─GoodsMapper.java

│ │ │ │       │  ├─ManagerMapper.java

│ │ │ │       │  ├─PositionMapper.java

│ │ │ │       │  ├─RecordMapper.java

│ │ │ │       │  ├─WarehouseMapper.java

│ │ │ │       │  └─WorkerMapper.java

│ │ │ │       └─ service （业务层）

│ │ │ │       │  ├─GoodsService.java

│ │ │ │       │  ├─ManagerService.java

│ │ │ │       │  ├─PositionService.java

│ │ │ │       │  ├─RecordService.java

│ │ │ │       │  ├─WarehouseService.java

│ │ │ │       │  └─WorkerService.java

│  │ │ │       └─ AdministratorSideSoftwareApplication.java   （启动类）

│ │ │ └─resources

│ │ │   ├─application.yml （配置文件）

│ │ │   └─mapper （持久层实现）

│ │ │       ├─GoodsMapper.xml

│ │ │       ├─ManagerMapper.xml

│ │ │       └─RecordMapper.xml

│ │ └─test

│ └─target

│  

└─dnb-vuejs

  ├─.idea 

├─node_modules 

├─public 

│  └─index.html

│ 

  └─src

​    ├─assets 

​    ├─router

​    │   └─index.js（

​    ├─utils

​    │   └─request.js （封装了axios，方便向后端发出网络请求）

​    ├─views

​    │    ├─Analysis.vue

​    │    ├─GoodsInf.vue 

​    │    ├─Login.vue   

​    │    ├─Manage.vue  

​    │    ├─WarehouseInf.vue 

​    │    ├─WarehouseMan.vue 

​    │    └─WorkerMan.vue

​    └─main.js （实例化Vue, Element-ui, axois等，并配置全局变量）