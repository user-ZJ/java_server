# SpringBoot

JNDI（Java Naming and Directory Interface）



一开始，Spring用XML配置，而且是很多XML配置。Spring 2.5引入了基于注解的组件扫描，这消除了大量针对应用程序自身组件的显式XML配置。Spring 3.0引入了基于Java的配置，这是一种类型安全的可重构配置方式，
可以代替XML。



自动配置、SpringBoot起步依赖、Spring Boot CLI和Actuator

自动配置：针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置

起步依赖：告诉Spring Boot需要什么功能，它就能引入需要的库

选择在构建时和运行时要包含在应用程序里的库，往往要花费不少工夫，而Spring Boot的起
步依赖（starter dependency）将常用依赖聚合在一起，借此简化一切。它不仅简化了你的构建说
明，还让你不必苦思冥想特定库和版本

Spring Boot CLI命令行界面：这是Spring Boot的可选特性，借此你只需写代码就能完成完整的应用程序，
无需传统项目构建。

Spring Boot的Actuator让你能一窥应用程序运行时的内部工作细节，看看Spring应用程序上下文里都有哪些Bean，Spring MVC控制器是怎么与路径映射的，应用程序都能取到哪些配置属性，诸如此类

Spring Boot没有引入任何形式的代码生成，而是利用了Spring 4的条件化配置特性，以及Maven和Gradle提供的传递依赖解析，以此实现Spring应用程序上下文里的自动配置  

从根本上来说，Spring Boot的项目只是普通的Spring项目，只是它们正好用到了Spring Boot的起步依赖和自动配置而已。因此，那些你早已熟悉的从头创建Spring项目的技术或工具，都能用于Spring Boot项目。然

## 自动配置

Spring Boot的自动配置是一个运行时（更准确地说，是应用程序启动时）的过程，考虑了众多因素，才决定Spring配置应该用哪个，不该用哪个。举几个例子，下面这些情况都是Spring Boot的自动配置要考虑的

 Spring的JdbcTemplate是不是在Classpath里？如果是，并且有DataSource的Bean，则
自动配置一个JdbcTemplate的Bean。
 Thymeleaf是不是在Classpath里？如果是，则配置Thymeleaf的模板解析器、视图解析器以
及模板引擎。
 Spring Security是不是在Classpath里？如果是，则进行一个非常基本的Web安全设置。
每当应用程序启动的时候，Spring Boot的自动配置都要做将近200个这样的决定，涵盖安全、集成、持久化、Web开发等诸多方面。所有这些自动配置就是为了尽量不让你自己写配置。

@Entity注解表明它是一个JPA实体

id属性加了@Id和@GeneratedValue(strategy=GenerationType.AUTO)注解，说明这个字段是实体的唯一标识，并且这个字段的值是自动生成的

在向应用程序加入Spring Boot时，有个名为spring-boot-autoconfigure的JAR文件，其中包含了很多配置类。每个配置类都在应用程序的Classpath里，都有机会为应用程序的配置添砖加瓦。这些配置类里有用于Thymeleaf的配置，有用于Spring Data JPA的配置，有用于Spiring MVC的配置，还有很多其他东西的配置，你可以自己选择是否在Spring应用程序里使用它们。

所有这些配置如此与众不同，原因在于它们利用了Spring的条件化配置，这是Spring 4.0引入的新特性。条件化配置允许配置存在于应用程序中，但在满足某些特定条件之前都忽略这个配置。

在Spring里可以很方便地编写你自己的条件，你所要做的就是实现Condition接口，覆盖它的matches()方法。

自动配置会做出以下配置决策，它们和之前的例子息息相关。
 因为Classpath 里有H2 ， 所以会创建一个嵌入式的H2 数据库Bean ， 它的类型是javax.sql.DataSource，JPA实现（Hibernate）需要它来访问数据库。
 因为Classpath里有Hibernate（Spring Data JPA传递引入的）的实体管理器，所以自动配置会配置与Hibernate 相关的Bean ， 包括Spring 的LocalContainerEntityManager-FactoryBean和JpaVendorAdapter。
 因为Classpath里有Spring Data JPA，所以它会自动配置为根据仓库的接口创建仓库实现。
 因为Classpath里有Thymeleaf，所以Thymeleaf会配置为Spring MVC的视图，包括一个Thymeleaf的模板解析器、模板引擎及视图解析器。视图解析器会解析相对于Classpath根目录的/templates目录里的模板。
 因为Classpath 里有Spring MVC （ 归功于Web 起步依赖）， 所以会配置Spring 的DispatcherServlet并启用Spring MVC。
 因为这是一个Spring MVC Web应用程序，所以会注册一个资源处理器，把相对于Classpath根目录的/static目录里的静态内容提供出来。（这个资源处理器还能处理/public、/resources和/META-INF/resources的静态内容。）
 因为Classpath里有Tomcat（通过Web起步依赖传递引用），所以会启动一个嵌入式的Tomcat容器，监听8080端口。

由此可见，Spring Boot自动配置承担起了配置Spring的重任，因此你能专注于编写自己的应用程序。

### 自定义配置

虽然自动配置很方便，但在开发Spring应用程序时其中的一些用法也有点武断。要是你在配置Spring时希望或者需要有所不同，该怎么办？

两种影响自动配置的方式——使用显式配置进行覆盖和使用属性进行精细化配置。

**显示配置进行覆盖**

Spring Boot的自动配置和@ConditionalOnMissingBean让你能显式地覆盖那些可以自动配置的Bean

**通过属性文件外置配置**

```bash
a. 命令行参数里指定
java -jar readinglist-0.0.1-SNAPSHOT.jar --spring.main.show-banner=false
b. 创建一个名为application.properties的文件
spring.main.show-banner=false
c. 创建名为application.yml的YAML文
spring:
	main:
		show-banner: false
d. 将属性设置为环境变量
export spring_main_show_banner=false
```

Spring Boot应用程序有多种设置途径。Spring Boot能从多种属性源获得属性，包括如下几处。

(1) 命令行参数  
(2) java:comp/env里的JNDI属性  
(3) JVM系统属性  
(4) 操作系统环境变量  
(5) 随机生成的带random.*前缀的属性（在设置其他属性时，可以引用它们，比如${random.long}）  
(6) 应用程序以外的application.properties或者appliaction.yml文件  
(7) 打包在应用程序内的application.properties或者appliaction.yml文件  
(8) 通过@PropertySource标注的属性源  
(9) 默认属性  
这个列表按照优先级排序，也就是说，任何在高优先级属性源里设置的属性都会覆盖低优先级的相同属性。例如，命令行参数会覆盖其他属性源里的属性。  

application.properties和application.yml文件能放在以下四个位置。  
(1) 外置，在相对于应用程序运行目录的/config子目录里。  
(2) 外置，在应用程序运行的目录里。  
(3) 内置，在config包内。  
(4) 内置，在Classpath根目录。  
同样，这个列表按照优先级排序。也就是说，/config子目录里的application.properties会覆盖应用程序Classpath里的application.properties中的相同属性。  
此外，如果你在同一优先级位置同时有application.properties和application.yml，那么application.yml里的属性会覆盖application.properties里的属性。  

server.port  监听端口，默认为8080



## 起步依赖

如果没用Spring Boot的话，你会向项目里添加哪些依赖呢？要用Spring MVC的话，你需要哪个Spring依赖？你还记得Thymeleaf的Group和Artifact ID吗？你应该用哪个版本的Spring Data JPA呢？它们放在一起兼容吗？

如果没有Spring Boot起步依赖，你就有不少功课要做。而你想要做的只不过是开发一个Spring Web应用程序，使用Thymeleaf视图，通过JPA进行数据持久化。但在开始编写第一行代码之前，你得搞明白，要支持你的计划，需要往构建说明里加入哪些东西。

如果我们只在构建文件里指定这些功能，让构建过程自己搞明白我们要什么东西，岂不是更简单？这正是Spring Boot起步依赖的功能。

向项目中添加依赖是件富有挑战的事。你需要什么库？它的Group和Artifact是什么？你需要哪个版本？哪个版本不会和项目中的其他依赖发生冲突？

Spring Boot通过起步依赖为项目的依赖管理提供帮助。起步依赖其实就是特殊的Maven依赖和Gradle依赖，利用了传递依赖解析，把常用库聚合在一起，组成了几个为特定功能而定制的依赖。

org.springframework.boot:spring-boot-starter-web

比起减少依赖数量，起步依赖还引入了一些微妙的变化。向项目中添加了Web起步依赖，实际上指定了应用程序所需的一类功能。因为应用是个Web应用程序，所以加入了Web起步依赖。与之类似，如果应用程序要用到JPA持久化，那么就可以加入jpa起步依赖。如果需要安全功能，那就加入security起步依赖。简而言之，你不再需要考虑支持某种功能要用什么库了，引入相关起步依赖就行。

此外，Spring Boot的起步依赖还把你从“需要这些库的哪些版本”这个问题里解放了出来。起步依赖引入的库的版本都是经过测试的，因此你可以完全放心，它们之间不会出现不兼容的情况。

起步依赖本质上是一个Maven项目对象模型（Project Object Model，POM），定义了对其他库的传递依赖，这些东西加在一起即支持某项功能。很多起步依赖的命名都暗示了它们提供的某种或某类功能

我们并不需要指定版本号，起步依赖本身的版本是由正在使用的Spring Boot的版本来决定
的，而起步依赖则会决定它们引入的传递依赖的版本。

但如果你真想知道自己在用什么，在构建工具里总能找到你要的答案。在Gradle里，
dependencies任务会显示一个依赖树，其中包含了项目所用的每一个库以及它们的版本：
$ gradle dependencies
在Maven里使用dependency插件的tree目标也能获得相似的依赖树。
$ mvn dependency:tree
大部分情况下，你都无需关心每个Spring Boot起步依赖分别声明了些什么东西。

**覆盖起步依赖引入的传递依赖**

即使经过了Spring Boot团队的测试，起步依赖里所选的库仍有问题该怎么办？

说到底，起步依赖和你项目里的其他依赖没什么区别。也就是说，你可以通过构建工具中的功能，选择性地覆盖它们引入的传递依赖的版本号，排除传递依赖，当然还可以为那些Spring Boot起步依赖没有涵盖的库指定依赖。

在Maven里，可以用<exclusions>元素来排除传递依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
		<exclusion>
		<groupId>com.fasterxml.jackson.core</groupId>
		</exclusion>
	</exclusions>
</dependency>
```

另一方面，也许项目需要Jackson，但你需要用另一个版本的Jackson来进行构建，而不是Web起步依赖里的那个。假设Web起步依赖引用了Jackson 2.3.4，但你需要使用2.4.3①。在Maven里，你可以直接在pom.xml中表达诉求，就像这样

```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.4.3</version>
</dependency>
```

Maven总是会用最近的依赖，也就是说，你在项目的构建说明文件里增加的这个依赖，会覆盖传递依赖引入的另一个依赖。

但假如你要的不是新版本的Jackson，而是一个较早的版本呢？

把老版本的依赖加入构建，并把Web起步依赖传递依赖的那个版本排除掉

## 命令行界面（Spring Boot CLI）

Spring Boot CLI让只写代码即可实现应用程序成为可能。Spring Boot CLI利用了起步依赖和自动配置，让你专注于代码本身。

CLI能检测到你使用了哪些类，它知道要向Classpath中添加哪些起步依赖才能让它运转起来。一旦那些依赖出现在Classpath中，一系列自动配置就会接踵而来，确保启用DispatcherServlet和Spring MVC，这样控制器就能响应HTTP请求了

Spring Boot CLI是Spring Boot的非必要组成部分。虽然它为Spring带来了惊人的力量，大大简化了开发，但也引入了一套不太常规的开发模型。要是这种开发模型与你的口味相去甚远，那也没关系，抛开CLI，你还是可以利用Spring Boot提供的其他东西。

## Actuator

Actuator提供在运行时检视应用程序内部情况的能力，包括如下细节：

* Spring应用程序上下文里配置的Bean
* Spring Boot的自动配置做的决策
* 应用程序取到的环境变量、系统属性、配置属性和命令行参数
* 应用程序里线程的当前状态
* 应用程序最近处理过的HTTP请求的追踪情况
* 各种和内存用量、垃圾回收、Web请求以及数据源用量相关的指标

Actuator通过Web端点和shell界面向外界提供信息。如果要借助shell界面，你可以打开SSH（Secure Shell），登入运行中的应用程序，发送指令查看它的情况

要启用Actuator的端点，只需在项目中引入Actuator的起步依赖即可

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Actuator的端点
HTTP方法 路 径 描 述
GET /autoconfig 提供了一份自动配置报告，记录哪些自动配置条件通过了，哪些没通过
GET /configprops 描述配置属性（包含默认值）如何注入Bean
GET /beans 描述应用程序上下文里全部的Bean，以及它们的关系
GET /dump 获取线程活动的快照   
GET /env 获取全部环境属性  
GET /env/{name} 根据名称获取特定的环境属性值
GET /health 报告应用程序的健康指标，这些值由HealthIndicator的实现类提供
GET /info 获取应用程序的定制信息，这些信息由info打头的属性提供
GET /mappings 描述全部的URI路径，以及它们和控制器（包含Actuator端点）的映射关系
GET /metrics 报告各种应用程序度量信息，比如内存用量和HTTP请求计数
GET /metrics/{name} 报告指定名称的应用程序度量值
POST /shutdown 关闭应用程序，要求endpoints.shutdown.enabled设置为true
GET /trace 提供基本的HTTP请求跟踪信息（时间戳、HTTP头等）

端点可以分为三大类：配置端点、度量端点和其他端点

**查看配置明细**







**Spring Initializr 初始化Spring Boot 项目**

Spring Boot CLI消除了不少设置工作，但如果你更倾向于传统Java项目结构，那你应该看看Spring Initializr。

Spring Initializr从本质上来说就是一个Web应用程序，它能为你生成Spring Boot项目结构。虽然不能生成应用程序代码，但它能为你提供一个基本的项目结构，以及一个用于构建代码的Maven或Gradle构建说明文件。你只需要写应用程序的代码就好了。

Spring Initializr有几种用法。
 通过Web界面使用。
 通过Spring Tool Suite使用。
 通过IntelliJ IDEA使用。
 使用Spring Boot CLI使用。



@SpringBootApplication开启了Spring的组件扫描和Spring Boot的自动配置功能。实际
上，@SpringBootApplication将三个有用的注解组合在了一起。
 Spring的@Configuration：标明该类使用Spring基于Java的配置。虽然本书不会写太多
配置，但我们会更倾向于使用基于Java而不是XML的配置。
 Spring的@ComponentScan：启用组件扫描，这样你写的Web控制器类和其他组件才能被
自动发现并注册为Spring应用程序上下文里的Bean。本章稍后会写一个简单的Spring MVC
控制器，使用@Controller进行注解，这样组件扫描才能找到它。
 Spring Boot 的@EnableAutoConfiguration ： 这个不起眼的小注解也可以称为
@Abracadabra①，就是这一行配置开启了Spring Boot自动配置的魔力，让你不用再写成
篇的配置了。



server.port=8000

加上这一行，嵌入式Tomcat的监听端口就变成了8000，而不是默认的8080

@SpringBootApplication注解。这会隐性开启组件扫描



属性是否生效取决于对应的组件是否声明为Spring应用程序上下文里的Bean（基本是自动配置的），为一个不生效的组件设置属性是没有用的。