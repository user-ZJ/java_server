package com.demo.db;

import java.util.List;

public class TestMain {
    public static void main(String [] args){
        UserInfoBean userInfoBean = new UserInfoBean(18,"ttt1");
        UserInfoDao userInfoDao = new UserInfoDao();
        userInfoDao.addUserInfo(userInfoBean);
        userInfoDao.updateUserInfo(userInfoBean);
        List<UserInfoBean> userInfoBeanList = userInfoDao.queryUserInfo();
        for(UserInfoBean userInfo:userInfoBeanList){
            System.out.println(userInfo.toString());
        }
        userInfoDao.delUserInfo(userInfoBean);
        userInfoBeanList = userInfoDao.queryUserInfo();
        for(UserInfoBean userInfo:userInfoBeanList){
            System.out.println(userInfo.toString());
        }
    }
}
