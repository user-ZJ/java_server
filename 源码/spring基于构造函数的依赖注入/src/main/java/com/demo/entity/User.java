package com.demo.entity;


public class User {
    private String name;
    private String sex;

    public User(){
        System.out.println("无参构造调用了...");
    }

    public User(String name, String sex){
        System.out.println("构造函数User(String name, String sex)");
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
