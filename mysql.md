# mysql

## 1.mysql安装

### 1.1 Linux/UNIX 上安装 MySQL
1. 检测系统是否自带安装 MySQL  
rpm -qa | grep mysql   
2. 卸载已安装的mysql   
rpm -e mysql　　// 普通删除模式  
rpm -e --nodeps mysql　　// 强力删除模式，如果使用上面命令删除时，提示有依赖的其它文件，则用该命令可以对其进行强力删除  
3. 安装mysql  
wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm  
rpm -ivh mysql-community-release-el7-5.noarch.rpm  
yum update  
yum install mysql-server    
权限设置：
chown mysql:mysql -R /var/lib/mysql  
初始化 MySQL：  
mysqld --initialize  
启动 MySQL：  
systemctl start mysqld  
查看 MySQL 运行状态：  
systemctl status mysqld  
4. 验证 MySQL 安装  
使用 mysqladmin 工具来获取服务器状态：  
mysqladmin --version  
5. 设置root用户密码
使用以下命令来创建root用户的密码：  
mysqladmin -u root password "new_password";  
或  
mysql -u root -p
Enter password:  

### 1.2 Windows 上安装 MySQL  
1. mysql下载  
链接：https://pan.baidu.com/s/1yV0OudJIOHpptQ9CTybq_g 
提取码：m1h3
2. mysql安装
window下双击安装包按默认安装即可  
3. 配置mysql
mysql配置文件位置在C:\ProgramData\MySQL\MySQL Server 8.0\my.ini  
一般修改端口port=3306和数据库存放位置参数datadir=C:/ProgramData/MySQL/MySQL Server 8.0/Data     

**使用Navicat图形化管理数据库，且可以支持多种数据库**


## 2. 数据库管理
### 2.1 MySQL 连接
mysql -u root -p  
Enter password:  

### 2.2 创建数据库  
在登陆 MySQL 服务后，使用 create 命令创建数据库，语法如下:    
> mysql> create DATABASE RUNOOB;  

使用 mysqladmin 创建数据库  
> mysqladmin -u root -p create RUNOOB  

### 2.3 删除数据库
drop 命令删除数据库  
> drop database <数据库名>;  

使用 mysqladmin 删除数据库  
> mysqladmin -u root -p drop RUNOOB  
> Enter password:  

### 2.4 选择数据库
使用use命令选择数据库  
> mysql> use RUNOOB;  

## 3.对数据表操作
### 3.1 创建数据表  
创建MySQL数据表的SQL通用语法：  
> CREATE TABLE table_name (column_name column_type);  
> 例子：  
> CREATE TABLE IF NOT EXISTS `runoob_tbl`(
   `runoob_id` INT UNSIGNED AUTO_INCREMENT,
   `runoob_title` VARCHAR(100) NOT NULL,
   `runoob_author` VARCHAR(40) NOT NULL,
   `submission_date` DATE,
   PRIMARY KEY ( `runoob_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

> AUTO_INCREMENT定义列为自增的属性，一般用于主键，数值会自动加1。  
> PRIMARY KEY关键字用于定义列为主键。 您可以使用多列来定义主键，列间以逗号分隔。  
> ENGINE 设置存储引擎，CHARSET 设置编码。  

### 3.2 删除数据表
删除MySQL数据表的通用语法：  
> DROP TABLE table_name ;
> 例子：  
> mysql> DROP TABLE runoob_tbl  

### 3.3 插入数据  
MySQL数据表插入数据通用的 INSERT INTO SQL语法：  
> INSERT INTO table_name ( field1, field2,...fieldN )
                       VALUES
                       ( value1, value2,...valueN );

### 3.4 查询数据
MySQL数据库中查询数据通用的 SELECT 语法：  
> SELECT column_name,column_name
FROM table_name
[WHERE Clause]
[LIMIT N][ OFFSET M]
> 查询语句中你可以使用一个或者多个表，表之间使用逗号(,)分割，并使用WHERE语句来设定查询条件  
> SELECT 命令可以读取一条或者多条记录。  
> 你可以使用星号（*）来代替其他字段，SELECT语句会返回表的所有字段数据  
> 你可以使用 WHERE 语句来包含任何条件。  
> 你可以使用 LIMIT 属性来设定返回的记录数。  
> 你可以通过OFFSET指定SELECT语句开始查询的数据偏移量。默认情况下偏移量为0。  
> 你可以使用 AND 或者 OR 指定一个或多个条件。  

group by:对select查询出来的结果集按照某个字段或者表达式进行分组，获得一组组的集合，然后从每组中取出一个指定字段或者表达式的值。  
having：用于对where和group by查询出来的分组经行过滤，查出满足条件的分组结果。它是一个过滤声明，是在查询返回结果集以后对查询结果进行的过滤操作。  
order by  

### 3.5 更新表
UPDATE 命令修改 MySQL 数据表数据的通用 SQL 语法：  
> UPDATE table_name SET field1=new-value1, field2=new-value2
[WHERE Clause]  
> 你可以同时更新一个或多个字段。  
> 你可以在 WHERE 子句中指定任何条件。  
> 你可以在一个单独表中同时更新数据。  

### 3.6 删除数据
DELETE 语句从 MySQL 数据表中删除数据的通用语法：  
> DELETE FROM table_name [WHERE Clause]  
> 如果没有指定 WHERE 子句，MySQL 表中的所有记录将被删除。  
> 你可以在 WHERE 子句中指定任何条件  
> 您可以在单个表中一次性删除记录。  

### 3.7 LIKE 子句模糊匹配
WHERE 子句中可以使用等号 = 来设定获取数据的条件，但是有时候我们需要获取 runoob_author 字段含有 "COM" 字符的所有记录，这时我们就需要在 WHERE 子句中使用 SQL LIKE 子句。  
SQL LIKE 子句中使用百分号 %字符来表示任意字符，类似于UNIX或正则表达式中的星号 *。如果没有使用百分号 %, LIKE 子句与等号 = 的效果是一样的。  
> mysql> SELECT * from runoob_tbl  WHERE runoob_author LIKE '%COM';

### 3.8 UNION 操作符,连接两个以上的 SELECT 语句
MySQL UNION 操作符用于连接两个以上的 SELECT 语句的结果组合到一个结果集合中。多个 SELECT 语句会删除重复的数据。   
> SELECT expression1, expression2, ... expression_n
FROM tables
[WHERE conditions]
UNION [ALL | DISTINCT]
SELECT expression1, expression2, ... expression_n
FROM tables
[WHERE conditions];
> DISTINCT: 可选，删除结果集中重复的数据。默认情况下 UNION 操作符已经删除了重复数据，所以 DISTINCT 修饰符对结果没啥影响。  
> ALL: 可选，返回所有结果集，包含重复数据。  

> SELECT country FROM Websites
UNION ALL
SELECT country FROM apps
ORDER BY country;

### 3.9 ORDER BY 排序
> SELECT field1, field2,...fieldN FROM table_name1, table_name2...
ORDER BY field1 [ASC [DESC][默认 ASC]], [field2...] [ASC [DESC][默认 ASC]]  
> 你可以使用任何字段来作为排序的条件，从而返回排序后的查询结果。  
> 你可以设定多个字段来排序。  
> 你可以使用 ASC 或 DESC 关键字来设置查询结果是按升序或降序排列。 默认情况下，它是按升序排列。  
> 你可以添加 WHERE...LIKE 子句来设置条件。  

> 升序：
> mysql> SELECT * from runoob_tbl ORDER BY submission_date ASC;  
> 降序：
> SELECT * from runoob_tbl ORDER BY submission_date DESC;  

### 3.10 GROUP BY 对结果集进行分组
GROUP BY 语句根据一个或多个列对结果集进行分组。  
在分组的列上我们可以使用 COUNT, SUM, AVG,等函数。   
> SELECT column_name, function(column_name)
FROM table_name
WHERE column_name operator value
GROUP BY column_name;  

### 3.11 JOIN 在两个或多个表中查询数据
可以在 SELECT, UPDATE 和 DELETE 语句中使用 Mysql 的 JOIN 来联合多表查询   
JOIN 按照功能大致分为如下三类:  
INNER JOIN（内连接,或等值连接）：获取两个表中字段匹配关系的记录。  
LEFT JOIN（左连接）：获取左表所有记录，即使右表没有对应匹配的记录。  
RIGHT JOIN（右连接）： 与 LEFT JOIN 相反，用于获取右表所有记录，即使左表没有对应匹配的记录。   
> mysql> SELECT a.runoob_id, a.runoob_author, b.runoob_count FROM runoob_tbl a INNER JOIN tcount_tbl b ON a.runoob_author = b.runoob_author;  
> 以上 SQL 语句等价于：  
> SELECT a.runoob_id, a.runoob_author, b.runoob_count FROM runoob_tbl a, tcount_tbl b WHERE a.runoob_author = b.runoob_author;

> mysql> SELECT a.runoob_id, a.runoob_author, b.runoob_count FROM runoob_tbl a LEFT JOIN tcount_tbl b ON a.runoob_author = b.runoob_author;
> SELECT a.runoob_id, a.runoob_author, b.runoob_count FROM runoob_tbl a RIGHT JOIN tcount_tbl b ON a.runoob_author = b.runoob_author;  

### 3.12 NULL 值处理
MySQL 使用 SQL SELECT 命令及 WHERE 子句来读取数据表中的数据,但是当提供的查询条件字段为 NULL 时，该命令可能就无法正常工作。  
为了处理这种情况，MySQL提供了三大运算符:  
* IS NULL: 当列的值是 NULL,此运算符返回 true。
* IS NOT NULL: 当列的值不为 NULL, 运算符返回 true。
* <=>: 比较操作符（不同于=运算符），当比较的的两个值为 NULL 时返回 false 。

在 MySQL 中，NULL 值与任何其它值的比较（即使是 NULL）永远返回 false，即 NULL = NULL 返回false 。  
MySQL 中处理 NULL 使用 IS NULL 和 IS NOT NULL 运算符。  
> mysql> SELECT * from runoob_test_tbl WHERE runoob_count IS NOT NULL;

### 3.13 REGEXP 正则表达式
> 查找name字段中以元音字符开头或以'ok'字符串结尾的所有数据：
> mysql> SELECT name FROM person_tbl WHERE name REGEXP '^[aeiou]|ok$';  

|模式|描述|
|----|----|
| ^ | 匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。 |
| $ | 匹配输入字符串的结束位置。如果设置了RegExp 对象的 Multiline 属性，$ 也匹配 '\n' 或 '\r' 之前的位置。 |
| . | 	匹配除 "\n" 之外的任何单个字符。要匹配包括 '\n' 在内的任何字符，请使用象 '[.\n]' 的模式。 |
| [...] | 字符集合。匹配所包含的任意一个字符。例如， '[abc]' 可以匹配 "plain" 中的 'a'。 |
| [^...] | 负值字符集合。匹配未包含的任意字符。例如， '[^abc]' 可以匹配 "plain" 中的'p'。 |
| p1&#124;p2&#124;p3 | 匹配 p1 或 p2 或 p3。例如，'z&#124;food' 能匹配 "z" 或 "food"。'(z&#124;f)ood' 则匹配 "zood" 或 "food"。 |
| * | 匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。 |
| + | 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。 |
| {n} | n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。 |
| {n,m} | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。 |  

### 3.14 ALTER 修改数据表名或者修改数据表字段  
1. 删除，添加或修改表字段  
> 删除表的 i 字段,如果数据表中只剩余一个字段则无法使用DROP来删除字段。:  
> mysql> ALTER TABLE testalter_tbl  DROP i;
> 使用 ADD 子句来向数据表中添加列,并定义数据类型:  
> mysql> ALTER TABLE testalter_tbl ADD i INT;  
> mysql> ALTER TABLE testalter_tbl ADD i INT FIRST;
> mysql> ALTER TABLE testalter_tbl ADD i INT AFTER c;
> FIRST 和 AFTER 关键字将添加字段设置为第一列或指定列

2. 修改字段类型及名称
> 使用MODIFY修改字段名
> mysql> ALTER TABLE testalter_tbl MODIFY c CHAR(10);
> 使用 CHANGE修改字段名和类型，在 CHANGE 关键字之后，紧跟着的是你要修改的字段名，然后指定新字段名及类型  
> mysql> ALTER TABLE testalter_tbl CHANGE i j BIGINT;  

3. 指定默认值和 NOT NULL
> mysql> ALTER TABLE testalter_tbl MODIFY j BIGINT NOT NULL DEFAULT 100;
> 如果你不设置默认值，MySQL会自动设置该字段默认为 NULL。  

4. 修改字段默认值
> 修改默认值
> mysql> ALTER TABLE testalter_tbl ALTER i SET DEFAULT 1000;  
> 删除字段的默认值  
> ALTER TABLE testalter_tbl ALTER i DROP DEFAULT;  

5. 修改表名
> mysql> ALTER TABLE testalter_tbl RENAME TO alter_tbl;


### 3.15 AUTO_INCREMENT自增序列使用
MySQL 序列是一组整数：1, 2, 3, ...，由于一张数据表只能有一个字段自增主键， 如果你想实现其他字段也实现自动增加，就可以使用MySQL序列来实现。  
1. 使用AUTO_INCREMENT 来定义列  
> mysql> CREATE TABLE insect
    -> (
    -> id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    -> PRIMARY KEY (id),
    -> name VARCHAR(30) NOT NULL, # type of insect
    -> date DATE NOT NULL, # date collected
    -> origin VARCHAR(30) NOT NULL # where collected
);
2. 获取AUTO_INCREMENT值
> 在MySQL的客户端中你可以使用 SQL中的LAST_INSERT_ID( ) 函数来获取最后的插入表中的自增列的值。  
3. 重置序列  
> 如果你删除了数据表中的多条记录，并希望对剩下数据的AUTO_INCREMENT列进行重新排列，那么你可以通过删除自增的列，然后重新添加来实现。 不过该操作要非常小心，如果在删除的同时又有新记录添加，有可能会出现数据混乱。操作如下所示：  
> mysql> ALTER TABLE insect DROP id;
mysql> ALTER TABLE insect
    -> ADD id INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
    -> ADD PRIMARY KEY (id);  
4. 设置序列的开始值  
> 一般情况下序列的开始值为1，但如果你需要指定一个开始值100，那我们可以通过以下语句来实现：  
> mysql> CREATE TABLE insect
    -> (
    -> id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    -> PRIMARY KEY (id),
    -> name VARCHAR(30) NOT NULL, 
    -> date DATE NOT NULL,
    -> origin VARCHAR(30) NOT NULL
)engine=innodb auto_increment=100 charset=utf8;  
> 或者  
> mysql> ALTER TABLE t AUTO_INCREMENT = 100;  

## 4. 事务
事务主要用于处理操作量大，复杂度高的数据。比如说，在人员管理系统中，你删除一个人员，你即需要删除人员的基本资料，也要删除和该人员相关的信息，如信箱，文章等等，这样，这些数据库操作语句就构成一个事务！  
* 在 MySQL 中**只有使用了 Innodb 数据库引擎**的数据库或表才支持事务。
* 事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行。
* 事务用来管理 insert,update,delete 语句  

一般来说，事务是必须满足4个条件（ACID）：原子性（Atomicity，或称不可分割性）、一致性（Consistency）、隔离性（Isolation，又称独立性）、持久性（Durability）。  
* 原子性：一个事务（transaction）中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。
* 一致性：在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。
* 隔离性：数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。  
* 持久性：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。  

> 在 MySQL 命令行的默认设置下，事务都是自动提交的，即执行 SQL 语句后就会马上执行 COMMIT 操作。因此要显式地开启一个事务务须使用命令 BEGIN 或 START TRANSACTION，或者执行命令 SET AUTOCOMMIT=0，用来禁止使用当前会话的自动提交。  

**事务控制语句**：  
BEGIN 或 START TRANSACTION 显式地开启一个事务；  
COMMIT 也可以使用 COMMIT WORK，不过二者是等价的。COMMIT 会提交事务，并使已对数据库进行的所有修改成为永久性的；  
ROLLBACK 也可以使用 ROLLBACK WORK，不过二者是等价的。回滚会结束用户的事务，并撤销正在进行的所有未提交的修改；  
SAVEPOINT identifier，SAVEPOINT 允许在事务中创建一个保存点，一个事务中可以有多个 SAVEPOINT；  
RELEASE SAVEPOINT identifier 删除一个事务的保存点，当没有指定的保存点时，执行该语句会抛出一个异常；  
ROLLBACK TO identifier 把事务回滚到标记点；  
SET TRANSACTION 用来设置事务的隔离级别。InnoDB 存储引擎提供事务的隔离级别有READ UNCOMMITTED、READ COMMITTED、REPEATABLE READ 和 SERIALIZABLE。  

## 5. 索引
索引分**单列索引**和**组合索引**,单列索引，即一个索引只包含单个列,组合索引，即一个索引包含多个列.  
实际上，索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录。  
虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件。  
建立索引会占用磁盘空间的索引文件  

1. 普通索引  
> 创建索引  
> CREATE INDEX indexName ON mytable(username(length));   
> 如果是CHAR，VARCHAR类型，length可以小于字段实际长度；如果是BLOB和TEXT类型，必须指定 length。  
> 修改表结构(添加索引)  
> ALTER table tableName ADD INDEX indexName(columnName)  
> 创建表的时候直接指定  
> CREATE TABLE mytable(   
ID INT NOT NULL,    
username VARCHAR(16) NOT NULL,   
INDEX [indexName] (username(length))   
);   
> 删除索引  
> DROP INDEX [indexName] ON mytable;   

2. 唯一索引  
唯一索引索引列的值必须唯一，但允许有空值。如果是组合索引，则列值的组合必须唯一。  
> 创建索引  
> CREATE UNIQUE INDEX indexName ON mytable(username(length))   
> 修改表结构  
> ALTER table mytable ADD UNIQUE [indexName] (username(length))  
> 创建表的时候直接指定  
> CREATE TABLE mytable(   
ID INT NOT NULL,    
username VARCHAR(16) NOT NULL,   
UNIQUE [indexName] (username(length))   
);   

3. 使用ALTER 命令添加和删除索引
* ALTER TABLE tbl_name ADD PRIMARY KEY (column_list): 该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL。
* ALTER TABLE tbl_name ADD UNIQUE index_name (column_list): 这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）。
* ALTER TABLE tbl_name ADD INDEX index_name (column_list): 添加普通索引，索引值可出现多次。
* ALTER TABLE tbl_name ADD FULLTEXT index_name (column_list):该语句指定了索引为 FULLTEXT ，用于全文索引。
> mysql> ALTER TABLE testalter_tbl ADD INDEX (c);
> mysql> ALTER TABLE testalter_tbl DROP INDEX c;  

## 6. 临时表
临时表在我们需要保存一些临时数据时是非常有用的。临时表只在当前连接可见，当关闭连接时，Mysql会自动删除表并释放所有空间。  
> mysql> CREATE TEMPORARY TABLE SalesSummary (
    -> product_name VARCHAR(50) NOT NULL
    -> , total_sales DECIMAL(12,2) NOT NULL DEFAULT 0.00
    -> , avg_unit_price DECIMAL(7,2) NOT NULL DEFAULT 0.00
    -> , total_units_sold INT UNSIGNED NOT NULL DEFAULT 0
);  

默认情况下，当你断开与数据库的连接后，临时表就会自动被销毁。当然你也可以在当前MySQL会话使用 DROP TABLE 命令来手动删除临时表。  
> DROP TABLE SalesSummary;    

## 7. 处理重复数据
1. 防止表中出现重复数据  
在 MySQL 数据表中设置指定的字段为 **PRIMARY KEY（主键）** 或者 **UNIQUE（唯一） 索引**来保证数据的唯一性；设置双主键模式来设置数据的唯一性，如果设置了双主键，那么那个键的默认值不能为 NULL，需设置为 NOT NULL  

2. 统计重复数据  
> mysql> SELECT COUNT(*) as repetitions, last_name, first_name
    -> FROM person_tbl
    -> GROUP BY last_name, first_name
    -> HAVING repetitions > 1;    

3. 过滤重复数据  
> 如果你需要读取不重复的数据可以在 SELECT 语句中使用 DISTINCT 关键字来过滤重复数据。  
> mysql> SELECT DISTINCT last_name, first_name
    -> FROM person_tbl;  
也可以使用 GROUP BY 来读取数据表中不重复的数据  
> mysql> SELECT last_name, first_name
    -> FROM person_tbl
    -> GROUP BY (last_name, first_name);  

4. 删除重复数据
> 删除数据表中的重复数据:
> mysql> CREATE TABLE tmp SELECT last_name, first_name, sex FROM person_tbl  GROUP BY (last_name, first_name, sex);
mysql> DROP TABLE person_tbl;
mysql> ALTER TABLE tmp RENAME TO person_tbl;  
> 也可以在数据表中添加 INDEX（索引） 和 PRIMAY KEY（主键）这种简单的方法来删除表中的重复记录  
> mysql> ALTER IGNORE TABLE person_tbl
    -> ADD PRIMARY KEY (last_name, first_name);

## 8. 导出数据
1. 导出表作为原始数据
mysqldump 是 mysql 用于转存储数据库的实用程序。它主要产生一个 SQL 脚本，其中包含从头重新创建数据库所必需的命令 CREATE TABLE INSERT 等。  
使用 mysqldump 导出数据需要使用 --tab 选项来指定导出文件指定的目录，该目标必须是可写的  
> $ mysqldump -u root -p --no-create-info --tab=/tmp RUNOOB runoob_tbl
2. 导出 SQL 格式的数据  
> $ mysqldump -u root -p RUNOOB runoob_tbl > dump.sql  
> 如果需要导出整个数据库的数据，可以使用以下命令：  
> mysqldump -u root -p RUNOOB > database_dump.sql
> 如果需要备份所有数据库，可以使用以下命令：  
> mysqldump -u root -p --all-databases > database_dump.sql

## 9. 导入数据
1. 将备份的数据库导入到MySQL服务器中，可以使用以下命令，使用以下命令你需要确认数据库已经创建：
> $ mysql -u root -p database_name < dump.sql  
2. 使用以下命令将导出的数据直接导入到远程的服务器上，但请确保两台服务器是相通的，是可以相互访问的
> $ mysqldump -u root -p database_name | mysql -h other-host.com database_name 
3. source 命令导入
source 命令导入数据库需要先登录到数库终端：
> mysql> create database abc;      # 创建数据库
mysql> use abc;                  # 使用已创建的数据库 
mysql> set names utf8;           # 设置编码
mysql> source /home/abc/abc.sql  # 导入备份数据库  
4. 使用 LOAD DATA 导入数据  
MySQL 中提供了LOAD DATA INFILE语句来插入数据  
> mysql> LOAD DATA LOCAL INFILE 'dump.txt' INTO TABLE mytbl;  
5. 使用 mysqlimport 导入数据  
mysqlimport 客户端提供了 LOAD DATA INFILEQL 语句的一个命令行接口。mysqlimport 的大多数选项直接对应 LOAD DATA INFILE 子句。  
从文件 dump.txt 中将数据导入到 mytbl 数据表中, 可以使用以下命令：  
> $ mysqlimport -u root -p --local mytbl dump.txt

## 10. 函数
https://www.runoob.com/mysql/mysql-functions.html
 







