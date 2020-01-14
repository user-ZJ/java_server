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

## SpringMVC+SwaggerUI

源码路径：源码\012springmvc+swiggerui

### 1. 导入依赖包

### 2. 配置SwaggerConfig

```java
package com.demo.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc     //开启网络服务，要不会找不到swaggerui
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())  //显示所有类
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只显示添加@Api注解的类
                .build();
    }

    /**
     * @return 生成文档说明信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo API文档") //粗标题
                .description("接口文档") //描述
                .termsOfServiceUrl("http://xxx.xxx.com")
                .version("1.0.0")  //api version
                .license("LICENSE")  //链接名称
                .licenseUrl("http://xxx.xxx.com")   //链接地址
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
```

### 3. 配置spring-mvc.xml（二选一）

xml配置和继承WebMvcConfigurationSupport类，重写addResourceHandlers等效，配置一个即可

```xml
<bean class="springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration" id="swagger2Config"/>
<mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
<mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
```





## 参考

https://www.cnblogs.com/zjfjava/p/9555045.html