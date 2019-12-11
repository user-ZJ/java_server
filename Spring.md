# Spring
Spring 是模块化的，允许你挑选和选择适用于你的模块，不必要把剩余部分也引入。下面的部分对在 Spring 框架中所有可用的模块给出了详细的介绍。    
![](images/spring_1.png)   
核心容器由spring-core，spring-beans，spring-context，spring-context-support和spring-expression（SpEL，Spring表达式语言，Spring Expression Language）等模块组成。  
spring-core模块提供了框架的基本组成部分，包括 IoC 和依赖注入功能  
spring-beans 模块提供 BeanFactory，工厂模式的微妙实现，它移除了编码式单例的需要，并且可以把配置和依赖从实际编码逻辑中解耦。    
context模块建立在由core和 beans 模块的基础上建立起来的，它以一种类似于JNDI注册的方式访问对象。Context模块继承自Bean模块，并且添加了国际化（比如，使用资源束）、事件传播、资源加载和透明地创建上下文（比如，通过Servelet容器）等功能。    
spring-expression模块提供了强大的表达式语言，用于在运行时查询和操作对象图。支持set和get属性值、属性赋值、方法调用、访问数组集合及索引的内容、逻辑算术运算、命名变量、通过名字从Spring IoC容器检索对象，还支持列表的投影、选择以及聚合等。  

它们的完整依赖关系如下图所示：  
![](images/spring_2.png)    

数据访问/集成：数据访问/集成层包括 JDBC，ORM，OXM，JMS 和事务处理模块  
Web：Web 层由 Web，Web-MVC，Web-Socket 和 Web-Portlet 组成  
其他：还有其他一些重要的模块，像 AOP，Aspects，Instrumentation，Messaging和测试模块  

## 环境配置
	<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>5.2.1.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.2.1.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>5.2.1.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>5.2.1.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
    <dependency>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
      <version>1.1.1</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/log4j/log4j -->
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>

## IoC 容器
Spring 容器是 Spring 框架的核心。容器将创建对象，把它们连接在一起，配置它们，并管理他们的整个生命周期从创建到销毁。   
Spring 容器使用依赖注入（DI）来管理组成一个应用程序的组件。  
IOC 容器具有依赖注入功能的容器，它可以创建对象，IOC 容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。通常new一个实例，控制权由程序员控制，而"控制反转"是指new实例工作不由程序员来做而是交给Spring容器来做。在Spring中BeanFactory是IOC容器的实际代表者。  
**Spring BeanFactory 容器**：它是最简单的容器，给 DI 提供了基本的支持，它用 org.springframework.beans.factory.BeanFactory 接口来定义。BeanFactory 仍然可以用于轻量级的应用程序，如移动设备或基于 applet 的应用程序，其中它的数据量和速度是显著。      
**Spring ApplicationContext 容器**：ApplicationContext 容器包括 BeanFactory 容器的所有功能，添加了更多的企业特定的功能，例如从一个属性文件中解析文本信息的能力，发布应用程序事件给感兴趣的事件监听器的能力。该容器是由 org.springframework.context.ApplicationContext 接口定义。    

### BeanFactory 容器
BeanFactory是一个最简单的容器，它主要的功能是为依赖注入 （DI） 提供支持，在 Spring 中，有大量对 BeanFactory 接口的实现。其中，最常被使用的是 XmlBeanFactory 类。这个容器从一个 XML 文件中读取配置元数据，由这些元数据来生成一个被配置化的系统或者应用。   
在资源宝贵的移动设备或者基于 applet 的应用当中， BeanFactory 会被优先选择。否则，一般使用的是 ApplicationContext，除非你有更好的理由选择 BeanFactory。   

	//利用框架提供的 XmlBeanFactory() API 去生成工厂 bean 
	//以及利用 ClassPathResource() API 去加载在路径 CLASSPATH 下可用的 bean 配置文件。
	XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("application.xml"));
	//利用第一步生成的 bean 工厂对象的 getBean() 方法得到所需要的 bean。 
	//这个方法通过配置文件中的 bean ID 来返回一个真正的对象，该对象最后可以用于实际的对象。一旦得到这个对象，你就可以利用这个对象来调用任何方法
	IService iService = (IService) factory.getBean("IService");
	iService.doSome();

### ApplicationContext 容器
Application Context 是 BeanFactory 的子接口，也被成为 Spring 上下文。  
Application Context 是 spring 中较高级的容器。和 BeanFactory 类似，它可以加载配置文件中定义的 bean，将所有的 bean 集中在一起，当有请求的时候分配 bean。 另外，它增加了企业所需要的功能，比如，从属性文件中解析文本信息和将事件传递给所指定的监听器。这个容器在 org.springframework.context.ApplicationContext interface 接口中定义。  
ApplicationContext 包含 BeanFactory 所有的功能，一般情况下，相对于 BeanFactory，ApplicationContext 会更加优秀。当然，BeanFactory 仍可以在轻量级应用中使用，比如移动设备或者基于 applet 的应用程序。  
最常被使用的 ApplicationContext 接口实现：  
* FileSystemXmlApplicationContext：该容器从 XML 文件中加载已被定义的 bean。在这里，你需要提供给构造器 XML 文件的完整路径。  
* ClassPathXmlApplicationContext：该容器从 XML 文件中加载已被定义的 bean。在这里，你不需要提供 XML 文件的完整路径，只需正确配置 CLASSPATH 环境变量即可，因为，容器会从 CLASSPATH 中搜索 bean 配置文件。  
* WebXmlApplicationContext：该容器会在一个 web 应用程序的范围内加载在 XML 文件中已被定义的 bean。   

		//读取配置文件，加载里面的配置等
		ApplicationContext context = new FileSystemXmlApplicationContext
            ("C:/Users/zack/workspace/HelloSpring/src/application.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        //通过getBean得到对象，其中IService就是我们刚刚在application.xml中配置的<bean/>中的id
        IService iService = (IService) applicationContext.getBean("IService");
        //得到对象，便执行其方法
        iService.doSome();

## Bean
bean 是一个被实例化，组装，并通过 Spring IoC 容器所管理的对象，是由用容器提供的配置元数据创建的  

**Bean 与 Spring 容器的关系**：  
![](images/spring_3.jpg)    

Spring IoC 容器完全由实际编写的配置元数据的格式解耦。有下面三种方法把配置元数据提供给 Spring 容器：  
* 基于 XML 的配置文件  
* 基于注解的配置  
* 基于 Java 的配置  

## 控制反转(IoC) 
IOC是Inversion of Control的缩写。  
1996年，Michael Mattson在一篇有关探讨面向对象框架的文章中，首先提出了IOC 这个概念。  
IOC理论提出的观点大体是这样的：借助于“第三方”实现具有依赖关系的对象之间的解耦。  

**依赖**： 如果在 Class A 中，有 Class B 的实例，则称 Class A 对 Class B 有一个依赖。

在采用面向对象方法设计的软件系统中，它的底层实现都是由N个对象组成的，所有的对象通过彼此的合作，最终实现系统的业务逻辑。  

软件系统在没有引入IOC容器之前，对象A依赖于对象B，那么对象A在初始化或者运行到某一点的时候，自己必须主动去创建对象B或者使用已经创建的对象B。无论是创建还是使用对象B，控制权都在自己手上。  
软件系统在引入IOC容器之后，这种情形就完全改变了，由于IOC容器的加入，对象A与对象B之间失去了直接联系，所以，当对象A运行到需要对象B的时候，IOC容器会主动创建一个对象B注入到对象A需要的地方。  
通过前后的对比，我们不难看出来：对象A获得依赖对象B的过程,由主动行为变为了被动行为，控制权颠倒过来了，这就是“控制反转”这个名称的由来。    

**IOC的别名：依赖注入(DI) **  
2004年，Martin Fowler探讨了同一个问题，既然IOC是控制反转，那么到底是“哪些方面的控制被反转了呢？”，经过详细地分析和论证后，他得出了答案：“获得依赖对象的过程被反转了”。控制被反转之后，获得依赖对象的过程由自身管理变为了由IOC容器主动注入。于是，他给“控制反转”取了一个更合适的名字叫做“依赖注入（Dependency Injection）”。  
他的这个答案，实际上给出了实现IOC的方法：注入。所谓依赖注入，就是由IOC容器在运行期间，动态地将某种依赖关系注入到对象之中。    
所以，依赖注入(DI)和控制反转(IOC)是从不同的角度的描述的同一件事情，就是指通过引入IOC容器，利用依赖关系注入的方式，实现对象之间的解耦。   

对象A依赖于对象B,当对象 A需要用到对象B的时候，IOC容器就会立即创建一个对象B送给对象A。IOC容器就是一个对象制造工厂，你需要什么，它会给你送去，你直接使用就行了，而再也不用去关心你所用的东西是如何制成的，也不用关心最后是怎么被销毁的，这一切全部由IOC容器包办。   
在传统的实现中，由程序内部代码来控制组件之间的关系。我们经常使用new关键字来实现两个组件之间关系的组合，这种实现方式会造成组件之间耦合。IOC很好地解决了该问题，它将实现组件间关系从程序内部提到外部容器，也就是说由容器在运行期将组件间的某种依赖关系动态注入组件中。  

**IOC为我们带来了什么好处**  
 我们还是从USB的例子说起，使用USB外部设备比使用内置硬盘，到底带来什么好处？
 第一、USB设备作为电脑主机的外部设备，在插入主机之前，与电脑主机没有任何的关系，只有被我们连接在一起之后，两者才发生联系，具有相关性。所以，无论两者中的任何一方出现什么的问题，都不会影响另一方的运行。这种特性体现在软件工程中，就是**可维护性比较好，非常便于进行单元测试，便于调试程序和诊断故障。**代码中的每一个Class都可以单独测试，彼此之间互不影响，只要保证自身的功能无误即可，这就是组件之间低耦合或者无耦合带来的好处。    
 第二、USB设备和电脑主机的之间无关性，还带来了另外一个好处，生产USB设备的厂商和生产电脑主机的厂商完全可以是互不相干的人，各干各事，他们之间唯一需要遵守的就是USB接口标准。这种特性体现在软件开发过程中，好处可是太大了。每个开发团队的成员都只需要关心实现自身的业务逻辑，完全不用去关心其它的人工作进展，因为你的任务跟别人没有任何关系，你的任务可以单独测试，你的任务也不用依赖于别人的组件，再也不用扯不清责任了。所以，在一个大中型项目中，团队成员分工明确、责任明晰，很容易将一个大的任务划分为细小的任务，开发效率和产品质量必将得到大幅度的提高。  
 第三、同一个USB外部设备可以插接到任何支持USB的设备，可以插接到电脑主机，也可以插接到DV机，USB外部设备可以被反复利用。在软件工程中，这种特性就是**可复用性好**，我们可以把具有普遍性的常用组件独立出来，反复利用到项目中的其它部分，或者是其它项目，当然这也是面向对象的基本特征。显然，IOC不仅更好地贯彻了这个原则，提高了模块的可复用性。符合接口标准的实现，都可以插接到支持此标准的模块中。  
 第四、同USB外部设备一样，模块具有热插拔特性。IOC生成对象的方式转为外置方式，也就是把对象生成放在配置文件里进行定义，这样，当我们更换一个实现子类将会变得很简单，只要修改配置文件就可以了，完全**具有热插拨的特性**。  

**IOC容器的技术剖析**  
IOC中最基本的技术就是“反射(Reflection)”编程，目前.Net C#、Java和PHP5等语言均支持，其中PHP5的技术书籍中，有时候也被翻译成“映射”。有关反射的概念和用法，大家应该都很清楚，通俗来讲就是根据给出的类名（字符串方式）来动态地生成对象。这种编程方式可以让对象在生成时才决定到底是哪一种对象。反射的应用是很广泛的，很多的成熟的框架，比如象Java中的hibernate、Spring框架，.Net中 NHibernate、Spring.NET框架都是把“反射”做为最基本的技术手段。  
反射技术其实很早就出现了，但一直被忽略，没有被进一步的利用。当时的反射编程方式相对于正常的对象生成方式要慢至少得10倍。现在的反射技术经过改良优化，已经非常成熟，反射方式生成对象和通常对象生成方式，速度已经相差不大了，大约为1-2倍的差距。  
我们可以把IOC容器的工作模式看做是工厂模式的升华，可以把IOC容器看作是一个工厂，这个工厂里要生产的对象都在配置文件中给出定义，然后利用编程语言的的反射编程，根据配置文件中给出的类名生成相应的对象。从实现来看，IOC是把以前在工厂方法里写死的对象生成代码，改变为由配置文件来定义，也就是把工厂和对象生成这两者独立分隔开来，目的就是提高灵活性和可维护性。

## 依赖注入（Dependency Injection）
Spring框架的核心功能之一就是通过依赖注入的方式来管理Bean之间的依赖关系    
所谓依赖注入，就是由IOC容器在运行期间，动态地将某种依赖关系注入到对象之中。
对象A依赖于对象B,当对象 A需要用到对象B的时候，IOC容器就会立即创建一个对象B送给对象A。IOC容器就是一个对象制造工厂，你需要什么，它会给你送去，你直接使用就行了，而再也不用去关心你所用的东西是如何制成的，也不用关心最后是怎么被销毁的，这一切全部由IOC容器包办。  

### DI示例
依赖 如果在 Class A 中，有 Class B 的实例，则称 Class A 对 Class B 有一个依赖。例如下面类 Human 中用到一个 Father 对象，我们就说类 Human 对类 Father 有一个依赖。  

	public class Human {
	    ...
	    Father father;
	    ...
	    public Human() {
	        father = new Father();
	    }
	}

仔细看这段代码我们会发现存在一些问题：
(1). 如果现在要改变 father 生成方式，如需要用new Father(String name)初始化 father，需要修改 Human 代码；
(2). 如果想测试不同 Father 对象对 Human 的影响很困难，因为 father 的初始化被写死在了 Human 的构造函数中；
(3). 如果new Father()过程非常缓慢，单测时我们希望用已经初始化好的 father 对象 Mock 掉这个过程也很困难。  

依赖注入 上面将依赖在构造函数中直接初始化是一种 Hard init 方式，弊端在于两个类不够独立，不方便测试。我们还有另外一种 Init 方式，如下：  

	public class Human {
	    ...
	    Father father;
	    ...
	    public Human(Father father) {
	        this.father = father;
	    }
	}
上面代码中，我们将 father 对象作为构造函数的一个参数传入。在调用 Human 的构造方法之前外部就已经初始化好了 Father 对象。像这种非自己主动初始化依赖，而通过外部来传入依赖的方式，我们就称为依赖注入。
现在我们发现上面 1 中存在的两个问题都很好解决了，简单的说依赖注入主要有两个好处：
(1). 解耦，将依赖之间解耦。
(2). 因为已经解耦，所以方便做单元测试，尤其是 Mock 测试。  

DI 主要有两种变体：  
1. Constructor-based dependency injection：当容器调用带有多个参数的构造函数类时，实现基于构造函数的 DI，每个代表在其他类中的一个依赖关系。  
2. Setter-based dependency injection：基于 setter 方法的 DI 是通过在调用无参数的构造函数或无参数的静态工厂方法实例化 bean 之后容器调用 beans 的 setter 方法来实现的。    


依赖注入有三种实现方式：
1. 构造函数注入
2. set方法参数注入  
3. 接口注入

基于@Autowired的自动装配，默认是根据类型注入，可以用于构造器、接口、方法注入  
@Resource注解可以标注在字段或属性的setter方法上，但它默认按名称装配。名称可以通过@Resource的name属性指定，当注解标注在字段上，即默认取字段的名称作为bean名称寻找依赖对象，当注解标注在属性的setter方法上，即默认取属性名作为bean名称寻找依赖对象。

### 基于构造函数的依赖注入
通过将@Autowired注解放在构造器上来完成构造器注入，默认构造器参数通过类型自动装配，如下所示：  

	 public class Test1 {  
	    private MessageInterface message;  
	    @Autowired //构造器注入  
	    private Test1(MessageInterface message) {  
	        this.message = message;    
	    } 
	    //省略getter和setter  
	 }

### 基于接口的依赖注入
通过将@Autowired注解放在构造器上来完成接口注入。  

	public class Test2 {  
	    @Autowired //接口注入  
	    private MessageInterface messageInterface;  
	    //省略getter和setter  
	}

### 基于设值函数的依赖注入
通过将@Autowired注解放在方法上来完成方法参数注入。  

	public class Test3 {  
	    private MessageInterface message;  
	 
	    @Autowired //setter方法注入  
	    public void setMessage(MessageInterface message) {  
	        this.message = message;  
	    }  
	    public String getMessage() {  
	        return message;  
	    }  
	}
	
	public class Test4 {  
	    private MessageInterface message;  //
	    private List<String> list;  
	    @Autowired(required = true) //任意一个或多个参数方法注入  
	    private void initMessage(MessageInterface message, ArrayList<String> list) {  
	        this.message = message;  
	        this.list = list;  
	    }  
	    //省略getter和setter  
	} 


## 基于注解的配置  
从 Spring 2.5 开始就可以使用注解来配置依赖注入。而不是采用 XML 来描述一个 bean 关联关系，你可以使用相关类，方法或字段声明的注解，将 bean 配置移动到组件类本身。      
如果你想在 Spring 应用程序中使用的任何注解，在配置文件中加入：  

	<context:annotation-config/>

### @Required 注解
@Required 注释应用于 bean 属性的 setter 方法，它表明受影响的 bean 属性在配置时必须放在 XML 配置文件中，否则容器就会抛出一个 BeanInitializationException 异常。  

### @Autowired 注释





编程式事务需要自己写beginTransaction()、commit()、rollback()等事务管理方法，声明式事务是通过注解或配置由spring自动处理，编程式事务粒度更细 


# 参考
IoC/DI: 
https://www.cnblogs.com/jhli/p/6019895.html  
https://www.cnblogs.com/sunzhao/p/8334008.html

