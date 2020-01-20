package com.design.pattern.Proxy.demo03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 步骤3：创建代理对象1（螳螂） 也是被行为监听对象。
 */
public class AgentTangLang implements InvocationHandler {

    private Object obj;//小蝉  真实对象
    //完成主要业务和次要业务绑定
    public AgentTangLang(Object param) {
        this.obj = param;
    }
    //invoke方法就是螳螂类的主要业务方法，也是需要被监听方法
    @Override
    public Object invoke(Object listener, Method method, Object[] args) throws Throwable {
        //蝉吃树叶
        method.invoke(obj, args);
        otherService();
        return null;
    }

    private void otherService(){
        System.out.println("螳螂吃蝉");
    }

}