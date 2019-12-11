package com.demo.entity;

public class Person {
    private String name;
    private String sex;
    public Person(){
        System.out.println("无参构造调用了...");
    }

    public Person(String name,String sex){
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
