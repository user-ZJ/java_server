package com.demo.hibernate.bean;

import javax.persistence.*;

@Entity
@Table(name="user_info")
public class UserBean {
    @Id
    @Column(name="user_id")
    private int id;
    @Column(name="user_name")
    private String username;
    @Column(name="user_phone")
    private String phone;
    @Column(name="user_website")
    private String website;
    @Column(name="user_dept")
    private String dept;

    public UserBean(){};
    public UserBean(int id){
        this.id = id;
    }
    public UserBean(int id,String username,String phone,String website,String dept){
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.website = website;
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString(){
        String str = "id:"+id+" username:"+username+" phone:"+phone+" website:"+website+" dept:"+dept;
        return str;
    }
}
