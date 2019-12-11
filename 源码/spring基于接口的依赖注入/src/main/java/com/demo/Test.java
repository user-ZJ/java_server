package com.demo;

import com.demo.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        //读取配置文件，加载里面的配置等
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        //通过getBean得到对象，就是在application.xml中配置的<bean/>中的id
        User user = (User) applicationContext.getBean("user");
        //得到对象，便执行其方法
        user.getUserInfo().print();
    }
}
