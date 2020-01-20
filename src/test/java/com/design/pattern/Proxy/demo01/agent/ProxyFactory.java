package com.design.pattern.Proxy.demo01.agent;

import com.design.pattern.Proxy.demo01.agent.Agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    //扮演一个角色（吃和拉的监听对象）
    public static <T> T newInstance(Class clz) throws InstantiationException, IllegalAccessException {
        Object realObj =   clz.newInstance();  // 使用反射创建一个对象
        //1.拥有一个代理实现类对象
        InvocationHandler agent = new Agent(realObj);//真实对象，被囚禁到了Agent中
        //2.申请/注册一个对特定行为进行监控对象（代理对象）
        /*
         *  loader:指向被监控的类文件在内存中真实地址
         *  interfaces（classArray）: 被监控的类所实现的接口，这个接口中声明的方法，就是需要被监控行为也是主要业务行为名称
         */
        Class classArray[] = clz.getInterfaces(); // 获取这个类的所有接口，
        //创建一个代理监听对象
          T ojb = (T) Proxy.newProxyInstance(clz.getClassLoader(), classArray, agent);
        return ojb;   //返回的是一个假货（披着羊皮的狼） 监听对象
    }
}