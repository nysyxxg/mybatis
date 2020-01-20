package com.design.pattern.Proxy.demo01.agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理对象类
 */
public class Agent implements InvocationHandler {
    private Object obj;// 被开发人员索要的真实对象

    //完成次要业务和主要业务绑定
    public Agent(Object param) {
        this.obj = param;
    }

    /*
     *  Object proxy:本次负责监听对象----onclick
     *  Method method: 被拦截的主要业务方法
     *  Object[] params:被拦截的主要业务方法接受的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        //1.读取被拦截的方法名称
        String methodName = method.getName();
        //2 针对特殊方法，进行单独处理
        if ("eating".equals(methodName)) {//饭前要洗手
            wash();
            method.invoke(obj, params);
        } else {//便后要洗手
            method.invoke(obj, params);
            wash();
        }
        return null;
    }

    //次要业务
    private void wash() {
        System.out.println("洗手");//解耦合；
    }
}