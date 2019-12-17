package com.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDao {
    public void addUserInfo(UserInfoBean userInfoBean){
        Connection conn = DBUtil.getConnection();
        String sql = "INSERT INTO user_info(user_name,age) values(?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行
            ptmt.setString(1,userInfoBean.getUserName());
            ptmt.setInt(2,userInfoBean.getAge());
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //重复打开数据库时需要关闭数据库
        }
    }
    public void updateUserInfo(UserInfoBean userInfoBean){
        Connection conn = DBUtil.getConnection();
        String sql = "UPDATE user_info set age=? where user_name=?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行
            ptmt.setInt(1,userInfoBean.getAge());
            ptmt.setString(2,userInfoBean.getUserName());
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //重复打开数据库时需要关闭数据库
        }
    }
    public void delUserInfo(UserInfoBean userInfoBean){
        Connection conn = DBUtil.getConnection();
        String sql = "delete from user_info where user_name=? and age=?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userInfoBean.getUserName());
            ptmt.setInt(2,userInfoBean.getAge());
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //重复打开数据库时需要关闭数据库
        }
    }
    public List<UserInfoBean> queryUserInfo(){
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM user_info";
        List<UserInfoBean> userInfoBeanList = new ArrayList<UserInfoBean>();
        try {
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            while(rs.next()){
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserName(rs.getString("user_name"));
                userInfoBean.setAge(rs.getInt("age"));
                userInfoBeanList.add(userInfoBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //重复打开数据库时需要关闭数据库
        }
        return userInfoBeanList;
    }
}
