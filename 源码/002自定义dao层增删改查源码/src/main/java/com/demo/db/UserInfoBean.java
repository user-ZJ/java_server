package com.demo.db;

public class UserInfoBean {
    private int age;
    private String userName;
    UserInfoBean(){}
    UserInfoBean(int age,String userName){
        this.age=age;
        this.userName=userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString(){
        return "user_name:"+userName+" age:"+age;
    }
}
