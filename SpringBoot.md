# SpringBoot

Spring Boot不是一门新技术，从本质上来说，Spring Boot就是Spring,它做了那些没有它你也会去做的Spring Bean配置。它使用“习惯优于配置”（项目中存在大量的配置，此外还内置了一个习惯性的配置，让你无需手动进行配置）的理念让你的项目快速运行起来。使用Spring Boot很容易创建一个独立运行（运行jar,内嵌Servlet容器）、准生产级别的基于Spring框架的项目，使用Spring Boot你可以不用或者只需要很少的Spring配置。   

### SpringBoot特点

- 自动配置：针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置
- 起步依赖：告诉Spring Boot需要什么功能，它就能引入需要的库。
- 命令行界面：这是Spring Boot的可选特性，借此你只需写代码就能完成完整的应用程序，无需传统项目构建。
- Actuator：让你能够深入运行中的Spring Boot应用程序，一探究竟。  





注解 @SpringBootApplication 表示这是一个SpringBoot应用，运行其主方法就会启动tomcat,默认端口是8080

@RestController 是spring4里的新注解，是@ResponseBody和@Controller的缩写

**com.how2java.springboot.SpringbootApplication** 类的主方法就把 tomcat 嵌入进去了，不需要手动启动 tomcat 

## jar包部署方式

SpringBoot默认打包为jar包，使用mvn install命令把spring工程打包为jar包，

在服务器上使用java -jar target/springboot-0.0.1-SNAPSHOT.jar启动

springboot自带tomcat，启动之后就可以通过ip和端口号访问 



## war包部署方式

1. **修改pom.xml文件将默认的jar方式改为war**

   ```xml
   <groupId>com.example</groupId>
   <artifactId>demo</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <name>demo</name>
   <packaging>war</packaging>
   <description>Demo project for Spring Boot</description>
   ```

2. **继承org.springframework.boot.web.servlet.support.SpringBootServletInitializer，实现configure方法**    
   为什么继承该类，SpringBootServletInitializer源码注释：  
   Note that a WebApplicationInitializer is only needed if you are building a war file and deploying it.   
   If you prefer to run an embedded web server then you won't need this at all
   启动类代码：    

   ```java
   @RestController
   @SpringBootApplication
   public class DemoApplication {
       @RequestMapping("/")
       public String index(){
           return "Hello Spring Boot";
       }
   
       public static void main(String[] args) {
           SpringApplication.run(DemoApplication.class, args);
       }
   
   }
   ```

   方式一，启动类继承SpringBootServletInitializer实现configure：  

   ```java
   @RestController
   @SpringBootApplication
   public class DemoApplication extends SpringBootServletInitializer {
       @RequestMapping("/")
       public String index(){
           return "Hello Spring Boot";
       }

       public static void main(String[] args) {
           SpringApplication.run(DemoApplication.class, args);
       }

       @Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
           return builder.sources(DemoApplication.class);
       }

   }
   ```

   方式二，新增加一个类继承SpringBootServletInitializer实现configure：  

   ```java
   public class ServletInitializer extends SpringBootServletInitializer {

       @Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
           //此处的DemoApplication.class为带有@SpringBootApplication注解的启动类
           return builder.sources(DemoApplication.class);
       }

   }
   ```

3. **war包配置**

   使用外部Tomcat部署访问的时候，application.properties(或者application.yml)中配置的  
   server.port=  
   server.servlet.context-path=  
   将失效，请使用tomcat的端口，tomcat，webapps下项目名进行访问。    
   为了防止应用上下文所导致的项目访问资源加载不到的问题，  
   建议pom.xml文件中<build></build>标签下添加<finalName></finalName>标签：  

   ```xml
   <build>
       <!-- 应与application.properties(或application.yml)中context-path保持一致 -->
       <finalName>war包名称</finalName>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>
       </plugins>
   </build>
   ```

4. **使用mvn命令行打包**

   mvn clean package  


## 异常处理

新增加一个类GlobalExceptionHandler，用于捕捉Exception异常以及其子类。

```java
package com.how2java.springboot.exception;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
 
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("errorPage");
        return mav;
    }
 
}
```

在helloController里面抛出异常

```java
@Controller
public class HelloController {
  
    @RequestMapping("/hello")
    public String hello(Model m) throws Exception {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        if(true){
            throw new Exception("some exception");
        }
        return "hello";
    }    
}
```

## 端口和上下文路径

通过修改application.properties，修改访问的端口号和上下文路径

```properties
spring.mvc.view.prefix=/WEB-INF/jsp/   #jsp扫描路径
spring.mvc.view.suffix=.jsp				#扫描.jsp文件
server.port=8888						#端口号
server.context-path=/test				#应用的上下文路径，也可以称为项目路径，是构成url地址的一部分
```

## 多配置切换

测试和生成环境配置通常不一样，此时就可以通过多配置文件实现多配置支持与灵活切换

3个配置文件：
核心配置文件：application.properties
开发环境用的配置文件：application-dev.properties
生产环境用的配置文件：application-pro.properties

application.properties:

```properties
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
spring.profiles.active=pro
```

application-dev.properties:

```properties
server.port=8080
server.context-path=/test
```

application-pro.properties:

```properties
server.port=80
server.context-path=/
```

不仅可以通过修改application.properties文件进行切换，还可以在部署环境下，指定不同的参数来确保生产环境总是使用的希望的那套配置  

java -jar target/springboot-0.0.1-SNAPSHOT.jar --spring.profiles.active=pro

java -jar target/springboot-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 参考

http://tengj.top/2017/02/26/springboot1/  

https://how2j.cn/k/springboot/springboot-war/1655.html