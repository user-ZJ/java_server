# Spring,Spring MVC及Spring Boot区别

Spring：

Spring，一般指代的是Spring Framework，

Spring MVC：

主要用于开发WEB应用和网络接口，它是Spring的一个模块，通过Dispatcher Servlet, ModelAndView 和 View Resolver，让应用开发变得很容易  

Spring Boot：

实现自动配置，降低项目搭建的复杂度

三者比较：

Spring 就像一个大家族，有众多衍生产品例如 Boot，Security，JPA等等。但他们的基础都是Spring 的 IOC 和 AOP，IOC提供了依赖注入的容器，而AOP解决了面向切面的编程，然后在此两者的基础上实现了其他衍生产品的高级功能；Spring MVC是基于 Servlet 的一个 MVC 框架，主要解决 WEB 开发的问题，因为 Spring 的配置非常复杂，各种xml，properties处理起来比较繁琐。于是为了简化开发者的使用，Spring社区创造性地推出了Spring Boot，它遵循约定优于配置，极大降低了Spring使用门槛，但又不失Spring原本灵活强大的功能

**Spring MVC和Spring Boot都属于Spring，Spring MVC 是基于Spring的一个 MVC 框架，而Spring Boot 是基于Spring的一套快速开发整合包**

