# JDBC
JDBC（Java DataBase Connectivity）是Java和数据库之间的一个桥梁，是一个规范而不是一个实现，能够执行SQL语句。它由一组用Java语言编写的类和接口组成。各种不同类型的数据库都有相应的实现。  

## 1. MYSQL JDBC使用

###  1.1 装载MYSQL的JDBC驱动并进行初始化  
1. 导入专用的jar包  
下载mysql-connector-java-5.0.8-bin.jar包，并在工程中导入，如果没有完成导包操作，后面会抛出ClassNotFoundException  
2. 初始化驱动  
通过初始化驱动类com.mysql.jdbc.Driver来实现驱动初始化  

		try {
			Class.forName("com.mysql.jdbc.Driver");		
		} catch (ClassNotFoundException e) { 				
			e.printStackTrace();
		}  

Class.forName是把这个类加载到JVM中，加载的时候，就会执行其中的静态初始化块，完成驱动的初始化的相关工作。   
### 1.2 建立JDBC和数据库之间的Connection连接  
连接数据库时需要提供以下信息：  
* 数据库所处于的ip:127.0.0.1 (这里是本机)
* 数据库的端口号： 3306 （mysql专用端口号）
* 数据库名称 exam（根据你自己数据库中的名称填写）
* 编码方式 UTF-8
* 账号 root
* 密码 admin

因为在进行数据库的增删改查的时候都需要与数据库建立连接，所以可以在项目中将建立连接写成一个工具方法，用的时候直接调用即可：  

	public static Connection getConnection(){
		Connection conn = null;
		 try {
			 	//初始化驱动类com.mysql.jdbc.Driver
	            Class.forName("com.mysql.jdbc.Driver");
	            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/exam?characterEncoding=UTF-8","root", "admin");
	            //该类就在 mysql-connector-java-5.0.8-bin.jar中,如果忘记了第一个步骤的导包，就会抛出ClassNotFoundException
	        } catch (ClassNotFoundException e) { 				
	            e.printStackTrace();
	        }catch (SQLException e) {							
	            e.printStackTrace();
	        }
		 return conn;
	}

### 1.3 创建Statement或者PreparedStatement接口，执行SQL语句
#### 1.3.1 使用Statement接口
在Statement中使用字符串拼接的方式,该方式存在句法复杂，容易犯错等缺点,在实际过程中使用的非常的少。

	Statement s = conn.createStatement();
	// 准备sql语句
	// 注意： 字符串要用单引号'
	String sql = "insert into t_courses values(null,"+"'数学')";
	//在statement中使用字符串拼接的方式，这种方式存在诸多问题
	s.execute(sql);
	System.out.println("执行插入语句成功");

#### 1.3.2 使用PreparedStatement接口
与 Statement一样，PreparedStatement也是用来执行sql语句的与创建Statement不同的是，需要根据sql语句创建PreparedStatement。除此之外，还能够通过设置参数，指定相应的值，而不是Statement那样使用字符串拼接。  

**增删改**

	String sql = "insert into t_course(course_name) values(?)"; 
	String sql = "delete from t_course where course_id = ?";
	String sql = "update t_course set course_name =? where course_id=?";
	Connection conn = null;				//和数据库取得连接
	PreparedStatement pstmt = null;		//创建statement
	try{
		conn = DbUtil.getConnection();
		pstmt = (PreparedStatement) conn.prepareStatement(sql);
		pstmt.setString(1, courseName); //给占位符赋值
		pstmt.setInt(2, courseId);
		pstmt.executeUpdate();			//执行
	}catch(SQLException e){
		e.printStackTrace();
	}
	finally{
		DbUtil.close(pstmt);
		DbUtil.close(conn);		//必须关闭
	}

使用PreparedStatement时，他的SQL语句不再采用字符串拼接的方式，而是采用占位符的方式。“？”在这里就起到占位符的作用。这种方式除了避免了statement拼接字符串的繁琐之外，还能够提高性能。每次SQL语句都是一样的，数据库就不会再次编译，这样能够显著提高性能。  
后面需要用到PreparedStatement接口创建的pstmt的set方法给占位符进行赋值。注意一点，**这里的参数索引是从1开始的**。   
增删改都使用pstmt.executeUpdate();语句进行SQL语句的提交,如果添加的数据量比较大的话，可以用批量添加  

**批量添加**    
	
	for(int i=1;i<100;i++){
	     pstmt.setInt(1,8000+i);
	     pstmt.setString(2,"赵_"+i);
	     pstmt.addBatch();
		//批量更新
	     if(i%10==0){
	     pstmt.executeBatch();
	   	}
	}

**查**  

	String sql = "select * from t_course order by course_id";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//创建一个集合对象用来存放查询到的数据
	List<Course> courseList = new ArrayList<>();
	try {
		conn = DbUtil.getConnection();
		pstmt = (PreparedStatement) conn.prepareStatement(sql);
		rs = (ResultSet) pstmt.executeQuery();
		while (rs.next()){
			int courseId = rs.getInt("course_id");
			String courseName = rs.getString("course_name");
			//每个记录对应一个对象
			Course course = new Course();
			course.setCourseId(courseId);
			course.setCourseName(courseName);
			//将对象放到集合中
			courseList.add(course);
		}
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally{
		DbUtil.close(pstmt);
		DbUtil.close(conn);		//必须关闭
	}
	return courseList;

查询操作使用executeQuery()进行更新 


**PreparedStatement的优点**：  
1、其使用参数设置，可读性好，不易记错。在statement中使用字符串拼接，可读性和维护性比较差。  
2、其具有预编译机制，性能比statement更快。  
3、其能够有效防止SQL注入攻击   

execute和executeUpdate的区别
相同点：二者都能够执行增加、删除、修改等操作。  
不同点：  
1、execute可以执行查询语句，然后通过getResult把结果取出来。executeUpdate不能执行查询语句。  
2、execute返回Boolean类型，true表示执行的是查询语句，false表示执行的insert、delete、update等。executeUpdate的返回值是int，表示有多少条数据受到了影响。  

### 1.4 事务
在JDBC中处理事务，都是通过Connection完成的，同一事务中所有的操作，都在使用同一个Connection对象。  
Connection的三个方法与事务有关：  
**setAutoCommit（boolean）**:设置是否为自动提交事务，如果true（默认值为true）表示自动提交，也就是每条执行的SQL语句都是一个单独的事务，如果设置为false，那么相当于开启了事务了；con.setAutoCommit(false) 表示开启事务。  
**commit（）**：提交结束事务。  
**rollback（）**：回滚结束事务。     
JDBC处理事务的代码格式：  

	try{
	     con.setAutoCommit(false);//开启事务
	     ......
	     con.commit();//try的最后提交事务      
	} catch（） {
	    con.rollback();//回滚事务
	}

### 1.5 获取自增主键ID

	public static void main(String[] args) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT&useSSL=false";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "123456";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
 
            String sql = "insert into account(username,name,age) values(?,?,?)";
 
            PreparedStatement preparedStatement = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
 
            //start widh 1
            preparedStatement.setString(1,"6");
            preparedStatement.setString(2,"6");
            preparedStatement.setString(3,"6");
 
 
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
 
            System.out.println("id="+id);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
### 数据访问层 Data Access Layer (DAL)
DAL最大的好处是它通过一些方法调用简化了数据库访问操作，比如insert()和find()，而不是建立连接并执行sql语句。  
这一层处理所有与数据库相关的调用和查询。  
**Data Transfer Object**：这个层应该包含一个简单的类叫做数据传输对象(DTO)这个对象只是一个简单的表映射，表中的每一列都是这个类中的一个成员变量。  
该层使用简单的java对象创建、修改、删除或搜索实体，而不是处理SQL语句和其他与数据库相关的命令  
我们希望将该表映射到java代码，为此可以创建一个包含相同字段的简单类(bean)。   
为了使它更封装，我们应该声明所有字段变量为私有的，并创建acessor (setter和getter)，以及构造函数  
**示例**： （提供默认的空构造函数、完整的构造函数和不带id参数的完整构造函数。 ）

	public class User {
	    private Integer id;
	    private String name;
	    private String pass;
	    private Integer age;
	    public User() {
	    }
	    public User(String name, String pass, Integer age) {
	        this.name = name;
	        this.pass = pass;
	        this.age = age;
	    }
	    public User(Integer id, String name, String pass, Integer age) {
	        this.id = id;
	        this.name = name;
	        this.pass = pass;
	        this.age = age;
	    }
	    public Integer getAge() {
	        return age;
	    }
	    public void setAge(Integer age) {
	        this.age = age;
	    }
	    public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getPass() {
	        return pass;
	    }
	    public void setPass(String pass) {
	        this.pass = pass;
	    }
	}

**Connecting to Database**：通过创建一个连接到数据库的中央类来方便地连接到数据库
在这个类中，需要提供连接参数，如数据库JDBC URL、用户名和密码作为final变量(最好从属性或XML配置文件中获取)  
提供一个方法来返回一个连接对象，如果连接失败，则返回null，最好抛出一个运行时异常  
示例：  

	import com.mysql.jdbc.Driver;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	/**
	 * Connect to Database
	 * @author hany.said
	 */
	public class ConnectionFactory {
	    public static final String URL = "jdbc:mysql://localhost:3306/testdb";
	    public static final String USER = "testuser";
	    public static final String PASS = "testpass";
	    /**
	     * Get a connection to database
	     * @return Connection object
	     */
	    public static Connection getConnection()
	    {
	      try {
	          DriverManager.registerDriver(new Driver());
	          return DriverManager.getConnection(URL, USER, PASS);
	      } catch (SQLException ex) {
	          throw new RuntimeException("Error connecting to the database", ex);
	      }
	    }
	    /**
	     * Test Connection
	     */
	    public static void main(String[] args) {
	        Connection connection = connectionFactory.getConnection();
	    }
	}
		  
**Data Access Object（Dao）**:DAO可以执行CRUD操作，它可以创建、恢复、更新数据、从表中删除数据。  
我们的DAO接口应该是这样的:  

	public interface IUserDao {
	    User getUser();
	    Set<User> getAllUsers();
	    User getUserByUserNameAndPassword();
	    boolean insertUser();
	    boolean updateUser();
	    boolean deleteUser();
	}

另外需要创建一个Dao接口的实现类

	public class UserDaoImpl implements IUserDao{
		public User getUser(int id) {
		    Connection connection = connectionFactory.getConnection();
		        try {
		            Statement stmt = connection.createStatement();
		            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id=" + id);
		            if(rs.next())
		            {
		                User user = new User();
		                user.setId( rs.getInt("id") );
		                user.setName( rs.getString("name") );
		                user.setPass( rs.getString("pass") );
		                user.setAge( rs.getInt("age") );
		                return user;
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    return null;
		}
	}



jdbc 事务、获取自增、获取元数据、ORM、DAO、数据库连接池  

ORM=Object Relationship Database Mapping   
对象和关系数据库的映射   
DAO=DataAccess Object  
数据访问对象 


# 参考
https://dzone.com/articles/building-simple-data-access-layer-using-jdbc  
https://www.cnblogs.com/noteless/p/10319299.html  

