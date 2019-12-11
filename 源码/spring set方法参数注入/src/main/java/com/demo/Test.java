package com.demo;

import com.demo.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        //读取配置文件，加载里面的配置等
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        //通过getBean得到对象，就是在application.xml中配置的<bean/>中的id
        Person person = (Person) applicationContext.getBean("person");
        //得到对象，便执行其方法
        person.setName("test");
        person.setSex("man");
        System.out.println(person.getName()+"是"+person.getSex()+"人！！！");
    }
}
