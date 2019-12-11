package com.demo.impl;

import com.demo.inter.IUserInfo;

public class UserInfoImpl implements IUserInfo {
    @Override
    public void print() {
        System.out.println("test interface inject!!");
    }
}
