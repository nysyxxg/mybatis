package com.design.pattern.Proxy.demo01;

import com.design.pattern.Proxy.demo01.agent.ProxyFactory;

public class ProxyTestMain {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 第一种方式：自己new一个对象
        //0.创建被索要的类型的实例对象
//        MyPersonService person = new MyPersonService();
//        person.eating("鸡");

        // 第二种方式： 创建一个代理对象来执行
        BaseService xiaoming = ProxyFactory.newInstance(MyPersonService.class);
        xiaoming.eating("鸡");
        xiaoming.sleep();
        xiaoming.wcing();
    }
}