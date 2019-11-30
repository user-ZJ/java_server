package com.demo.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;

/**
 * 使用连接池技术管理数据库连接
 */
public class DBUtil {

    //数据库连接池对象
    private static DataSource dataSource;

    //通过配置文件来获取数据库参数
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties prop = new Properties();
            InputStream is = DBUtil.class.getClassLoader()
                    .getResourceAsStream("dbcpconfig.properties");
            prop.load(is);
            is.close();

            // 利用工厂模式创建数据库连接池
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        /*
         * 通过连接池获取一个空闲连接
         */
        return dataSource.getConnection();
    }


    /**
     * 关闭数据库连接
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 测试是否连接成功
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        for (int i = 0; i < 10; i++) {
            Connection connection = DBUtil.getConnection();
            System.out.println(connection);
            System.out.println(connection.getClass().getName());
            DBUtil.free(null, null, connection);
        }
    }
}
