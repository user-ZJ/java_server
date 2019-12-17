package com.demo;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 8104961490512068314L;
    private int id;
    private String name;
    private String dept;
    private String phone;
    private String website;
    private String user_website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getUser_website() {
        return user_website;
    }

    public void setUser_website(String user_website) {
        this.user_website = user_website;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", user_website='" + user_website + '\'' +
                '}';
    }
}
