# Maven

[下载地址](http://maven.apache.org/download.cgi)

Maven 提倡使用一个共同的标准目录结构，Maven 使用约定优于配置的原则
> ${basedir}	存放pom.xml和所有的子目录
${basedir}/src/main/java	项目的java源代码
${basedir}/src/main/resources	项目的资源，比如说property文件，springmvc.xml
${basedir}/src/test/java	项目的测试类，比如说Junit代码
${basedir}/src/test/resources	测试用的资源
${basedir}/src/main/webapp/WEB-INF	web应用文件目录，web项目的信息，比如存放web.xml、本地图片、jsp视图页面
${basedir}/target	打包输出目录
${basedir}/target/classes	编译输出目录
${basedir}/target/test-classes	测试编译输出目录
Test.java	Maven只会自动运行符合该命名规则的测试类
~/.m2/repository	Maven默认的本地仓库目录位置


POM( Project Object Model，项目对象模型 ) 是 Maven 工程的基本工作单元，是一个XML文件，包含了项目的基本信息，用于描述项目如何构建，声明项目依赖，等等。
POM 中可以指定以下配置：
>项目依赖
插件
执行目标
项目构建 profile
项目版本
项目开发者列表
相关邮件列表信息

	<project xmlns = "http://maven.apache.org/POM/4.0.0"
	    xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
	    xsi:schemaLocation = "http://maven.apache.org/POM/4.0.0
	    http://maven.apache.org/xsd/maven-4.0.0.xsd">
	 
	    <!-- 模型版本 -->
	    <modelVersion>4.0.0</modelVersion>
	    <!-- 公司或者组织的唯一标志，并且配置时生成的路径也是由此生成， 如com.companyname.project-group，maven会将该项目打成的jar包放本地路径：/com/companyname/project-group -->
	    <groupId>com.companyname.project-group</groupId>
	 
	    <!-- 项目的唯一ID，一个groupId下面可能多个项目，就是靠artifactId来区分的 -->
	    <artifactId>project</artifactId>
	 
	    <!-- 版本号 -->
	    <version>1.0</version>
	</project>

Maven 构建配置文件大体上有三种类型:
>项目级（Per Project）	定义在项目的POM文件pom.xml中
用户级 （Per User）	定义在Maven的设置xml文件中 (%USER_HOME%/.m2/settings.xml)
全局（Global）	定义在 Maven 全局的设置 xml 文件中 (%M2_HOME%/conf/settings.xml)


## maven仓库

### 本地仓库
Maven 的本地仓库，在安装 Maven 后并不会创建，它是在第一次执行 maven 命令的时候才被创建。  
运行 Maven 的时候，Maven 所需要的任何构件都是直接从本地仓库获取的。如果本地仓库没有，它会首先尝试从远程仓库下载构件至本地仓库，然后再使用本地仓库的构件。  
默认情况下，不管Linux还是 Windows，每个用户在自己的用户目录下都有一个路径名为 .m2/respository/ 的仓库目录。  
Maven 本地仓库默认被创建在 %USER_HOME% 目录下。要修改默认位置，在 %M2_HOME%\conf 目录中的 Maven 的 settings.xml 文件中定义另一个路径。  
&lt;localRepository&gt;C:/MyLocalRepository&lt;/localRepository&gt;  

### 中央仓库
Maven 中央仓库是由 Maven 社区提供的仓库，其中包含了大量常用的库。  
中央仓库包含了绝大多数流行的开源Java构件，以及源码、作者信息、SCM、信息、许可证信息等。一般来说，简单的Java项目依赖的构件都可以在这里下载到。  
要浏览中央仓库的内容，maven 社区提供了一个 URL：http://search.maven.org/#browse 。使用这个仓库，开发人员可以搜索所有可以获取的代码库。  

### 远程仓库
如果 Maven 在中央仓库中也找不到依赖的文件，它会停止构建过程并输出错误信息到控制台。为避免这种情况，Maven 提供了远程仓库的概念，它是开发人员自己定制仓库，包含了所需要的代码库或者其他工程中用到的 jar 文件。  
举例说明，使用下面的 pom.xml，Maven 将从远程仓库中下载该 pom.xml 中声明的所依赖的（在中央仓库中获取不到的）文件  

	<project xmlns="http://maven.apache.org/POM/4.0.0"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
	   http://maven.apache.org/xsd/maven-4.0.0.xsd">
	   <modelVersion>4.0.0</modelVersion>
	   <groupId>com.companyname.projectgroup</groupId>
	   <artifactId>project</artifactId>
	   <version>1.0</version>
	   <dependencies>
	      <dependency>
	         <groupId>com.companyname.common-lib</groupId>
	         <artifactId>common-lib</artifactId>
	         <version>1.0.0</version>
	      </dependency>
	   <dependencies>
	   <repositories>
	      <repository>
	         <id>companyname.lib1</id>
	         <url>http://download.companyname.org/maven2/lib1</url>
	      </repository>
	      <repository>
	         <id>companyname.lib2</id>
	         <url>http://download.companyname.org/maven2/lib2</url>
	      </repository>
	   </repositories>
	</project>
	
### Maven 依赖搜索顺序
当我们执行 Maven 构建命令时，Maven 开始按照以下顺序查找依赖的库:  
1. 在本地仓库中搜索
2. 在中央仓库中搜索
3. 如果远程仓库没有被设置，Maven 将简单的停滞处理并抛出错误（无法找到依赖的文件）
4. 在一个或多个远程仓库中搜索依赖的文件，如果找到则下载到本地仓库以备将来引用，否则 Maven 将停止处理并抛出错误（无法找到依赖的文件）

### Maven 阿里云(Aliyun)仓库
1.修改 maven 根目录下的 conf 文件夹中的 setting.xml 文件，在 mirrors 节点上，添加内容如下：  


	<mirrors>
	    <mirror>
	      <id>alimaven</id>
	      <name>aliyun maven</name>
	      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
	      <mirrorOf>central</mirrorOf>        
	    </mirror>
	</mirrors>	  

2.pom.xml文件里添加：


	<repositories>  
	        <repository>  
	            <id>alimaven</id>  
	            <name>aliyun maven</name>  
	            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
	            <releases>  
	                <enabled>true</enabled>  
	            </releases>  
	            <snapshots>  
	                <enabled>false</enabled>  
	            </snapshots>  
	        </repository>  
	</repositories>