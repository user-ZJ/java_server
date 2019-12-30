# swagger ui

## 简介

[Swagger](http://swagger.io/) 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。总体目标是使客户端和文件系统作为服务器以同样的速度来更新。文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。Swagger 让部署管理和使用功能强大的API从未如此简单。  

## 依赖

```xml
    <dependencies>
        <!-- Spring MVC support -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.1.4.RELEASE</version>
        </dependency>
        <!--JSTL-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
            <scope>runtime</scope>
        </dependency>
        <!-- swagger2 核心依赖 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.6.1</version>
        </dependency>
        <!-- swagger-ui 为项目提供api展示及测试的界面 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.6.1</version>
        </dependency>
        <!-- 集成 swagger 的时候，缺少这个 jar包是不OK的-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.2.3</version>
        </dependency>
 
    </dependencies>
```



## 常用注解

- @Api()用于类； 表示标识这个类是swagger的资源 
- @ApiOperation()用于方法； 表示一个http请求的操作 
- @ApiParam()用于方法，参数，字段说明； 表示对参数的添加元数据（说明或是否必填等） 
- @ApiModel()用于类 表示对类进行说明，用于参数用实体类接收 
- @ApiModelProperty()用于方法，字段 ；表示对model属性的说明或者数据操作更改 
- @ApiIgnore()用于类，方法，方法参数 ；表示这个方法或者类被忽略 
- @ApiImplicitParam() 用于方法 ；表示单独的请求参数 
- @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam



## 参考

https://www.cnblogs.com/zjfjava/p/9555045.html