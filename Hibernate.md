# Hibernate
Hibernate是一种ORM框架，全称为 Object_Relative DateBase-Mapping，在Java对象与关系数据库之间建立某种映射，以实现直接存取Java对象！   
![](images/hibernate_1.jpg)    

Hibernate 使用 XML 文件来处理映射 Java 类别到数据库表格中，并且不用编写任何代码。  
为在数据库中直接储存和检索 Java 对象提供简单的 APIs。  
抽象不熟悉的 SQL 类型，并为我们提供工作中所熟悉的 Java 对象。    
Hibernate 不需要应用程序服务器来操作。    
操控你数据库中对象复杂的关联。  
最小化与访问数据库的智能提取策略。  
提供简单的数据询问。   


## Hibernate 架构
Hibernate 应用程序架构视图:  
![](images/hibernate_2.jpg)     
详细的 Hibernate 应用程序体系结构视图以及一些重要的类。   
![](images/hibernate_3.jpg)       

**配置对象**  
配置对象是你在任何 Hibernate 应用程序中创造的第一个 Hibernate 对象，并且经常只在应用程序初始化期间创造。它代表了 Hibernate 所需一个配置或属性文件。配置对象提供了两种基础组件。  
* 数据库连接：由 Hibernate 支持的一个或多个配置文件处理。这些文件是 hibernate.properties 和 hibernate.cfg.xml。  
* 类映射设置：这个组件创造了 Java 类和数据库表格之间的联系。  

**SessionFactory 对象**  
配置对象被用于创造一个 SessionFactory 对象，使用提供的配置文件为应用程序依次配置 Hibernate，并允许实例化一个会话对象。SessionFactory 是一个线程安全对象并由应用程序所有的线程所使用。  
SessionFactory 是一个重量级对象所以通常它都是在应用程序启动时创造然后留存为以后使用。每个数据库需要一个 SessionFactory 对象使用一个单独的配置文件。所以如果你使用多种数据库那么你要创造多种 SessionFactory 对象。  
**Session 对象**    
一个会话被用于与数据库的物理连接。Session 对象是轻量级的，并被设计为每次实例化都需要与数据库的交互。持久对象通过 Session 对象保存和检索。  
Session 对象不应该长时间保持开启状态因为它们通常情况下并非线程安全，并且它们应该按照所需创造和销毁。  
**Transaction 对象**   
一个事务代表了与数据库工作的一个单元并且大部分 RDBMS 支持事务功能。在 Hibernate 中事务由底层事务管理器和事务（来自 JDBC 或者 JTA）处理。  
这是一个选择性对象，Hibernate 应用程序可能不选择使用这个接口，而是在自己应用程序代码中管理事务。  
**Query 对象**  
Query 对象使用 SQL 或者 Hibernate 查询语言（HQL）字符串在数据库中来检索数据并创造对象。一个查询的实例被用于连结查询参数，限制由查询返回的结果数量，并最终执行查询。  
**Criteria 对象**   
Criteria 对象被用于创造和执行面向规则查询的对象来检索对象。  

## 环境配置
在maven的pom.xml中配置  

	<dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>5.2.10.Final</version>
    </dependency>


## 配置对象
Hibernate 需要事先知道在哪里找到映射信息，这些映射信息定义了 Java 类怎样关联到数据库表。Hibernate 也需要一套相关数据库和其它相关参数的配置设置。所有这些信息通常是作为一个标准的 Java 属性文件提供的，名叫 hibernate.properties。又或者是作为 XML 文件提供的，名叫 hibernate.cfg.xml。  
一般使用hibernate.cfg.xml 这个 XML 格式文件来决定在我的例子里指定需要的 Hibernate 应用属性，保存在应用程序的类路径的根目录里。   

	<?xml version="1.0" encoding="utf-8"?>
	<!DOCTYPE hibernate-configuration SYSTEM 
	"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
	
	<hibernate-configuration>
	   <session-factory>
	   <!-- 这个属性使 Hibernate 应用为被选择的数据库生成适当的 SQL -->
	   <property name="hibernate.dialect">
	      org.hibernate.dialect.MySQLDialect
	   </property>
	   <!-- JDBC 驱动程序类 -->
	   <property name="hibernate.connection.driver_class">
	      com.mysql.jdbc.Driver
	   </property>
	
	   <!-- 数据库实例的 JDBC URL,连接test数据库 -->
	   <property name="hibernate.connection.url">
	      jdbc:mysql://localhost/test
	   </property>
	   <!-- 数据库用户名 -->
	   <property name="hibernate.connection.username">
	      root
	   </property>
	   <!-- 数据库密码 -->
	   <property name="hibernate.connection.password">
	      root123
	   </property>
       <!-- 可以将向数据库发送的SQL语句显示出来 -->
       <property name="hibernate.show_sql">true</property>
       <!-- 格式化SQL语句 -->
       <property name="hibernate.format_sql">true</property>
	   <!-- 限制在 Hibernate 应用数据库连接池中连接的数量 -->
       <property name="hibernate.connection.pool_size">10</property>
       <!-- 允许在 JDBC 连接中使用自动提交模式 -->
       <property name="hibernate.connection.autocommit">true</property>
	   <!-- 配置hibernate的映射文件所在的位置 -->
	   <!-- List of XML mapping files -->
	   <mapping resource="Employee.hbm.xml"/>
	
	</session-factory>
	</hibernate-configuration>   

## Session
Session 用于获取与数据库的物理连接。  
Session 对象是轻量级的，并且设计为在每次需要与数据库进行交互时被实例化。持久态对象被保存，并通过 Session 对象检索找回。  
该 Session 对象不应该长时间保持开放状态，因为它们通常不能保证线程安全，而应该根据需求被创建和销毁。Session 的主要功能是为映射实体类的实例提供创建，读取和删除操作。这些实例可能在给定时间点时存在于以下三种状态之一：  
* 瞬时状态: 一种新的持久性实例，被 Hibernate 认为是瞬时的，它不与 Session 相关联，在数据库中没有与之关联的记录且无标识符值。  
* 持久状态：可以将一个瞬时状态实例通过与一个 Session 关联的方式将其转化为持久状态实例。持久状态实例在数据库中没有与之关联的记录，有标识符值，并与一个 Session 关联。  
* 脱管状态：一旦关闭 Hibernate Session，持久状态实例将会成为脱管状态实例。  

若 Session 实例的持久态类别是序列化的，则该 Session 实例是序列化的。一个典型的事务应该使用以下语法,如果 Session 引发异常，则事务必须被回滚，该 session 必须被丢弃。    

	Session session = factory.openSession();
	Transaction tx = null;
	try {
	   tx = session.beginTransaction();
	   // do some work
	   ...
	   tx.commit();
	}
	catch (Exception e) {
	   if (tx!=null) tx.rollback();
	   e.printStackTrace(); 
	}finally {
	   session.close();
	}
	

Session 方法及说明:  
 
| 方法 | 说明 |
|  --   |  --  |
| Transaction beginTransaction() | 开始工作单位，并返回关联事务对象。 | 
| void cancelQuery() | 取消当前的查询执行 |
| void clear() | 完全清除该会话 |
| Connection close() | 通过释放和清理 JDBC 连接以结束该会话。 |
| Criteria createCriteria(Class persistentClass)  | 为给定的实体类或实体类的超类创建一个新的 Criteria 实例。 |
| Criteria createCriteria(String entityName) | 为给定的实体名称创建一个新的 Criteria 实例。 |
| Serializable getIdentifier(Object object)  | 返回与给定实体相关联的会话的标识符值。 |
| Query createFilter(Object collection, String queryString)  | 为给定的集合和过滤字符创建查询的新实例。 |
| Query createQuery(String queryString)  | 为给定的 HQL 查询字符创建查询的新实例。 |
| SQLQuery createSQLQuery(String queryString)  | 为给定的 SQL 查询字符串创建 SQLQuery 的新实例。 |
| void delete(Object object) | 从数据存储中删除持久化实例 |
| void delete(String entityName, Object object) | 从数据存储中删除持久化实例 |
| Session get(String entityName, Serializable id) | 返回给定命名的且带有给定标识符或 null 的持久化实例（若无该种持久化实例）。 |
| SessionFactory getSessionFactory() | 获取创建该会话的 session 工厂。 |
| void refresh(Object object) | 从基本数据库中重新读取给定实例的状态 |
| Transaction getTransaction()  | 获取与该 session 关联的事务实例 |
| boolean isConnected() | 检查当前 session 是否连接。 |
| boolean isDirty()  | 该 session 中是否包含必须与数据库同步的变化？ |
| boolean isOpen()  | 检查该 session 是否仍处于开启状态。 |
| Serializable save(Object object) | 先分配一个生成的标识，以保持给定的瞬时状态实例。 |
| void saveOrUpdate(Object object) | 保存（对象）或更新（对象）给定的实例 |
| void update(Object object)  | 更新带有标识符且是给定的处于脱管状态的实例的持久化实例 |
| void update(String entityName, Object object) | 更新带有标识符且是给定的处于脱管状态的实例的持久化实例 |

## 持久化类 Bean
Hibernate 的完整概念是提取 Java 类属性中的值，并且将它们保存到数据库表单中。映射文件能够帮助 Hibernate 确定如何从该类中提取值，并将它们映射在表格和相关域中。  
在 Hibernate 中，其对象或实例将会被存储在数据库表单中的 Java 类被称为持久化类。  
持久化类的主要规则:  
* 所有将被持久化的 Java 类都需要一个默认的构造函数
* 为了使对象能够在 Hibernate 和数据库中容易识别，所有类都需要包含一个 ID。此属性映射到数据库表的主键列。  
* 所有将被持久化的属性都应该声明为 private，并具有由 JavaBean 风格定义的 getXXX 和 setXXX 方法。  
* 所有的类是不可扩展或按 EJB 要求实现的一些特殊的类和接口。  

一个简单的 POJO 的例子:

	public class Employee {
	   private int id;
	   private String firstName; 
	   private String lastName;   
	   private int salary;  
	
	   public Employee() {}
	   public Employee(String fname, String lname, int salary) {
	      this.firstName = fname;
	      this.lastName = lname;
	      this.salary = salary;
	   }
	   public int getId() {
	      return id;
	   }
	   public void setId( int id ) {
	      this.id = id;
	   }
	   public String getFirstName() {
	      return firstName;
	   }
	   public void setFirstName( String first_name ) {
	      this.firstName = first_name;
	   }
	   public String getLastName() {
	      return lastName;
	   }
	   public void setLastName( String last_name ) {
	      this.lastName = last_name;
	   }
	   public int getSalary() {
	      return salary;
	   }
	   public void setSalary( int salary ) {
	      this.salary = salary;
	   }
	}
	
## 映射文件
一个对象/关系型映射一般定义在 XML 文件中。映射文件指示 Hibernate 如何将已经定义的类或类组与数据库中的表对应起来。以格式 &lt;classname&gt;.hbm.xml保存映射文件  

	<?xml version="1.0" encoding="utf-8"?>
	<!DOCTYPE hibernate-mapping PUBLIC 
	 "-//Hibernate/Hibernate Mapping DTD//EN"
	 "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd"> 
	
	<hibernate-mapping>
	   <class name="Employee" table="EMPLOYEE">
	      <meta attribute="class-description">
	         This class contains the employee detail. 
	      </meta>
	      <id name="id" type="int" column="id">
	         <generator class="native"/>
	      </id>
	      <property name="firstName" column="first_name" type="string"/>
	      <property name="lastName" column="last_name" type="string"/>
	      <property name="salary" column="salary" type="int"/>
	   </class>
	</hibernate-mapping>

* 映射文件是一个以hibernate-mapping为根元素的 XML 文件，里面包含所有class标签。
* class 标签是用来定义从一个 Java 类到数据库表的特定映射。Java 的类名使用 name 属性来表示，数据库表明用 table 属性来表示。
* meta 标签是一个可选元素，可以被用来修饰类。
* id 标签将类中独一无二的 ID 属性与数据库表中的主键关联起来。id 元素中的 name 属性引用类的性质，column 属性引用数据库表的列。type 属性保存 Hibernate 映射的类型，这个类型会将从 Java 转换成 SQL 数据类型。  
* 在 id 元素中的 generator 标签用来自动生成主键值。设置 generator 标签中的 class 属性可以设置 native 使 Hibernate 可以使用 identity, sequence 或 hilo 算法根据底层数据库的情况来创建主键。
* property 标签用来将 Java 类的属性与数据库表的列匹配。标签中 name 属性引用的是类的性质，column 属性引用的是数据库表的列。type 属性保存 Hibernate 映射的类型，这个类型会将从 Java 转换成 SQL 数据类型。  




Java Persistence API（JPA）