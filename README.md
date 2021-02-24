# <img src="img/logo.png"  width="36px" height="36px"/>MyAdmin后台管理系统

## 介绍

该项目是一个前后端分离的项目，后端基于Spring Boot、MyBatis-Plus、Shiro+JWT并整合了Activiti工作流。前端基于Vue以及ElementUI，主要作为一个基础平台，包含后台管理系统中所需的所有功能，可直接在此基础上进行业务功能开发

- 演示地址：http://8.129.86.120/
  - 演示环境说明：演示地址目前只有**工作流示例**和**生成代码展示**可以进行增加及修改系列操作，其他功能只开放了查询操作
  - 账号：管理员（`admin`），其他用户（`user1、user2、user3... `具体可查看系统中用户模块）
  - 密码：所有用户的密码都为`123456`
- 相关文档：[独立完成系统开发 系列博客](https://blog.csdn.net/f4112cd/category_9712097.html)，如果觉得写得不错或对你有帮助可以给个一键三连哈~
- 源码

| GitHub                            | Gitee                            |
| --------------------------------- | -------------------------------- |
| https://github.com/cdfan/my-admin | https://gitee.com/cdfan/my-admin |

提示：项目暂未完全开源，如有需要可持续关注，谢谢

## 项目特性

- 项目中内置代码生成器可以快速生成前后端代码，并且由于使用了MyBatis-Plus所以开发中基本不需要编写SQL，极大提高开发效率
- 该系统集成了Activiti工作流，支持在线设计流程图以及关联业务并部署流程，并且系统中还提供了审批流和业务流的使用示例
- 开发时严格遵循阿里Java代码规范，注释非常完整并且通过Swagger2生成接口文档，可以很好的支持前后端分离的的开发模式
- 系统中包含各种监控，包括所有请求、登录、SQL及服务器性能的监控，便于实时查看及分析
- 包含完善的日志记录及管理，便于后续的分析及排查
- 同时支持Redis和EhCache缓存，可根据实际场景进行切换
- 通过Token进行认证及鉴权，支持分布式及单机环境，可以很容易实现服务器的水平扩展
- 支持接口级别的功能鉴权与数据鉴权，可自定义对权限进行分配
- 前端包含一系列封装好的常用组件以及常用的图表组件，开箱即用
- 系统采用响应式布局，除了支持PC端还支持移动端，并且还支持系统主题自由切换

## 系统功能

- 首页：对本周和上周的访问数据进行统计对比
- 系统管理
  - 用户管理：提供系统中用户的相关配置，可以给用户赋予角色及分配部门
  - 角色管理：对系统中的角色进行管理，可以给不同的角色配置不同的权限
  - 部门管理：提供系统中部门的相关配置
  - 菜单管理：维护系统的菜单及菜单中的按钮
  - 业务字典：用于维护系统中可枚举的数据
- 系统监控
  - 日志监控
    - 登录日志：用于记录每个用户每次登录时的信息及状态
    - 操作日志：用于记录操作中所涉及的接口的请求信息，如果接口发生异常还会记录异常信息
  - 服务监控：对服务器的信息状态进行实时监控
  - 数据源监控：Druid提供的监控，用于监控数据源及操作的SQL信息
- 系统工具
  - 代码生成：提供代码生成的配置以及生成代码等操作
  - 接口文档：通过Swagger生成的接口文档，里面包含每个接口的信息
  - 图标：包含系统中的所有图标，其中里面含有ElementUI内置图标以及自己添加的SVG图标
  - 图表：一些ECharts图表的使用示例
  - 地图：高德地图的使用示例，该示例提供位置定位以及位置搜索等功能
  - 常用组件：包含系统中封装的常用组件的使用示例
  - 错误页面：错误页面的示意图，包含404以及401的示意图
- 工作流
  - 模型管理：用于维护工作流模型，支持在线设计流程图、查看流程文件及部署设计的流程
  - 流程管理：对部署之后的流程进行维护，支持部署外部流程文件以及将部署后的流程转为系统中的流程模型还有将流程跟业务进行关联等操作
  - 待办任务：当前登录人员的代办任务信息，可在该页面中对任务进行办理，如果有代办任务消息通知中会有相关提示
  - 已办任务：当前登录人员已经办理的所有任务，可对已办理任务所属流程的流程信息进行查看
  - 运行中流程：系统中所有正在运行的流程实例，可对这些流程进行查看及挂起等相关操作
  - 已结束流程：包含系统中所有已完成的流程实例，可对历史流程进行查看
  - 流程示例
    - 请假审批流演示：对审批流进行演示的示例，依附于请假这个业务，具体流程业务可查看相关流程模型
    - 购物流程业务流：对业务流进行演示的示例，依附于购物这个业务，具体流程业务可查看相关流程模型
- 业务管理
  - 示例
    - 代码生成展示：展示了通过代码生成器生成代码后直接使用在系统中的样子
- 个人中心：用于查看及维护个人信息，只有本人才能操作


## 技术栈

下面是本项目涉及到的技术，还有更细的没有一一列举出来，具体可以参考项目中的Maven以及npm依赖。

- 前端
  - 基础的：HTML、JavaScript、CSS、ES6、SCSS
  - Vue系列及相关：Vue.js、Vuex、Vue Router 、Vue CLI，ElementUI、Axios
  - 插件：插件有很多这里简单列举几个：EChartss、js-xlsx、wangEditor、vue-amap等等
  - 其他：Node.js、webpack、Mock.js、ESLint、Babel
- 后端
  - 基础的：Java
  - 框架：Spring Boot、MyBatis-Plus、Shiro、Swagger2、Activiti
  - 数据库：MySQL
  - 缓存：EhCache、Redis
  - 其他：JWT、Druid、Logback等等




## 特别鸣谢

- 该项目前端主要基于[vue-element-admin](https://github.com/PanJiaChen/Vue-element-admin)前端模板进行开发，感谢 PanJiaChen大佬提供的前端模板
- 在项目开发过程中查资料的时候发现已经有大佬基于vue-element-admin开发了[eladmin](https://github.com/elunez/eladmin) ，写的还是很不错的，因为都是基于vue-element-admin开发的所以有些功能的前端对该项目有所借鉴，感谢elunez大佬的开源分享





## 支持浏览器

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" style=""/>](https://godban.github.io/browsers-support-badges/) | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox"/>](https://godban.github.io/browsers-support-badges/) | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="Edge" />](https://godban.github.io/browsers-support-badges/) | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari"/>](https://godban.github.io/browsers-support-badges/) |
| :--------------------------------------: | :--------------------------------------: | :--------------------------------------: | :--------------------------------------: |
|                  Chrome                  |                 Firefox                  |                   Edge                   |                  Safari                  |

**注意**：不兼容IE(Internet Explorer)系列



## 系统预览

### PC端

| ![1](img/1.png)   | ![2](img/2.png)   |
| ----------------- | ----------------- |
| ![3](img/3.png)   | ![4](img/4.png)   |
| ![5](img/5.png)   | ![6](img/6.png)   |
| ![7](img/7.png)   | ![8](img/8.png)   |
| ![9](img/9.png)   | ![10](img/10.png) |
| ![11](img/11.png) | ![12](img/12.png) |
| ![13](img/13.png) | ![14](img/14.png) |
| ![15](img/15.png) | ![16](img/16.png) |
| ![17](img/17.png) | ![18](img/18.png) |
| ![19](img/19.png) | ![20](img/20.png) |
| ![21](img/21.png) | ![22](img/22.png) |
| ![23](img/23.png) | ![24](img/24.png) |
| ![25](img/25.png) | ![26](img/26.png) |
| ![27](img/27.png) | ![28](img/28.png) |
| ![29](img/29.png) | ![30](img/30.png) |
| ![31](img/31.png) | ![32](img/32.png) |



### 移动端

| 竖版                  | 横版                  |
| ------------------- | ------------------- |
| ![1_1](img/1_1.png) | ![2_1](img/2_1.png) |
| ![1_2](img/1_2.png) | ![2_2](img/2_2.png) |



### 工作流演示

**注意**：gif动图稍微有点大，如果GitHub上看不了可以到[Gitee](https://gitee.com/cdfan/my-admin)上面查看或自己到演示地址中测试

**提示**：Gitee中大于1M的文件要求登录后才能查看哦

#### 请假审批流

![leaveProcess](img/leaveProcess.gif)



#### 并行请假审批流

![leaveProcessParallel](img/leaveProcessParallel.gif)



#### 购物流程业务流

![shopping](img/shopping.gif)



## 支持

如果觉得这个项目以及相关博客不错有帮助到了你，希望你可以去 [Github](https://github.com/cdfan/my-admin)或者 [Gitee](https://gitee.com/cdfan/my-admin) 帮我点个 ⭐ ，以及[博客](https://blog.csdn.net/f4112cd/category_9712097.html)给个一键三连，这将对我是极大的鼓励，谢谢~

[![Gitee star](https://gitee.com/cdfan/my-admin/badge/star.svg?theme=white)](https://gitee.com/cdfan/my-admin)&ensp; [![GitHub star](https://img.shields.io/github/stars/cdfan/my-admin?style=social)](https://github.com/cdfan/my-admin)

