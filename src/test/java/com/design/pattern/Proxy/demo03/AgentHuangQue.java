package com.design.pattern.Proxy.demo03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 步骤5：上面几个步骤基本上一级代理已经实现，下面主要思考螳螂的主要业务是什么？
 */
public class AgentHuangQue implements InvocationHandler {

    private Object proxy1;//监听小婵的监听对象

    public AgentHuangQue(Object proxy1) {
        this.proxy1 = proxy1;
    }

    //method就是螳螂的主要方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //主要业务 螳螂军吃蝉,让一级代理对象通知螳螂，蝉吃树叶
        method.invoke(proxy1, args);
        otherService();
        return null;
    }

    private void otherService() {
        System.out.println("黄雀吃螳螂");
    }

}