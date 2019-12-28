# Spring MVC
Spring Web MVC是在Servlet API上构建的原始Web框架，从一开始就包含在Spring框架中。其正式名称“Spring Web MVC”来自其源模块(Spring -webmvc)的名称，但它更常见的名称是“Spring MVC”。

Spring Web MVC是一种基于Java的实现了Web MVC设计模式的请求驱动类型的轻量级Web框架，使用了MVC架构模式的思想，将web层进行职责解耦。

- 基于请求驱动指的就是使用请求-响应模型
- 框架的目的就是帮助我们简化开发，Spring Web MVC也是要简化我们日常Web开发 

MVC 设计模式代表 Model-View-Controller（模型-视图-控制器） 模式。这种模式用于应用程序的分层开发。  

- **Model（模型）** - 模型代表一个存取数据的对象或 JAVA POJO。它也可以带有逻辑，在数据变化时更新控制器。  
- **View（视图）** - 视图代表**模型**包含的数据的可视化。  
- **Controller（控制器）** - 控制器作用于模型和视图上。它控制数据流向模型对象，并在数据变化时更新视图。它使视图与模型分离开。  

## Spring MVC框架优点

Spring MVC 是 Spring 产品组合的一部分，它享有 Spring IoC容器紧密结合Spring松耦合等特点，因此它有Spring的所有优点。   

1、清晰的角色划分：

　　　　　　　　　 前端控制器（DispatcherServlet）、

　　　　　　　　　 请求到处理器映射（HandlerMapping）、

　　　　　　　　　 处理器适配器（HandlerAdapter）、

　　　　　　　　　 视图解析器（ViewResolver）、

　　　　　　　　　 处理器或页面控制器（Controller）、

　　　　 　　　　　验证器（ Validator）、

　　　　　　　　　 表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。

2、由于命令对象就是一个POJO，无需继承框架特定API，可以使用命令对象直接作为业务对象；

3、和Spring 其他框架无缝集成，是其它Web框架所不具备的；

4、可适配，通过HandlerAdapter可以支持任意的类作为处理器；

5、可定制性，HandlerMapping、ViewResolver等能够非常简单的定制；

6、功能强大的数据验证、格式化、绑定机制；

7、利用Spring提供的Mock对象能够非常简单的进行Web层单元测试；

8、本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。

9、RESTful风格的支持、简单的文件上传、约定优于配置的契约式编程支持、基于注解的零配置支持等等。

## SpringMVC架构图

![](images/springmvc_2.png)  



## SpringMVC框架请求处理流程

![](images/springmvc_1.png)  

第一步：发起请求到前端控制器(DispatcherServlet)   

第二步：前端控制器请求HandlerMapping查找 Handler （可以根据xml配置、注解进行查找）  

第三步：处理器映射器HandlerMapping向前端控制器返回Handler，HandlerMapping会把请求映射为HandlerExecutionChain对象(包含一个Handler处理器（页面控制器）对象，多个HandlerInterceptor拦截器对象），通过这种策略模式，很容易添加新的映射策略  

第四步：前端控制器调用处理器适配器去执行Handler   

第五步：处理器适配器HandlerAdapter将会根据适配的结果去执行Handler    

第六步：Handler执行完成给适配器返回ModelAndView  

第七步：处理器适配器向前端控制器返回ModelAndView （ModelAndView是springmvc框架的一个底层对象，包括 Model和view）  

第八步：前端控制器请求视图解析器去进行视图解析 （根据逻辑视图名解析成真正的视图(jsp)），通过这种策略很容易更换其他视图技术，只需要更改视图解析器即可  

第九步：视图解析器向前端控制器返回View   

第十步：前端控制器进行视图渲染 （视图渲染将模型数据(在ModelAndView对象中)填充到request域）  

第十一步：前端控制器向用户响应结果  



## DispatcherServlet

与许多其他web框架一样，Spring MVC也是围绕前端控制器模式设计的，其中一个中央Servlet DispatcherServlet提供了一个用于请求处理的共享算法，而实际工作是由可配置的委托组件执行的。该模型灵活，支持多种工作流程。  

DispatcherServlet 的任务就是拦截请求发送给 Spring MVC Controller。    

与其他Servlet一样，DispatcherServlet需要使用Java配置或web.xml根据Servlet规范声明和映射。然后，DispatcherServlet使用Spring配置来发现请求映射、视图解析、异常处理等所需的委托组件。  

## HandlerMapping

应用程序中可能会有多个控制器，DispatcherServlet 会查询一个或多个处理器映射来确定请求的下一站在哪里，处理器映射会根据请求所携带的 URL 信息来进行决策  

## Controller（控制器）

一旦选择了合适的控制器， DispatcherServlet 会将请求发送给选中的控制器，到了控制器，请求会卸下其负载（用户提交的请求）等待控制器处理完这些信息   

## Model

当控制器在完成逻辑处理后，通常会产生一些信息，这些信息就是需要返回给用户并在浏览器上显示的信息，它们被称为**模型（Model）**     

## View Resolver（视图解析器）

控制器就不会和特定的视图相耦合，传递给 DispatcherServlet 的视图名并不直接表示某个特定的 JSP。（实际上，它甚至不能确定视图就是 JSP）相反，**它传递的仅仅是一个逻辑名称，这个名称将会用来查找产生结果的真正视图。**

DispatcherServlet 将会使用视图解析器（view resolver）来将逻辑视图名匹配为一个特定的视图实现，它可能是也可能不是 JSP

## View

仅仅返回原始的信息是不够的——这些信息需要以用户友好的方式进行格式化，一般会是 HTML，所以，信息需要发送给一个**视图（view）**，通常会是 JSP。    



## @EnableWebMvc

将@EnableWebMvc添加给@Configuration类来导入SpringMvc的配置





## 参考

https://www.yiibai.com/spring_mvc/  

http://websystique.com/springmvc/spring-4-mvc-and-hibernate4-integration-example-using-annotations/  



