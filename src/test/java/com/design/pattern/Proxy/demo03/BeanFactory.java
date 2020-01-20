package com.design.pattern.Proxy.demo03;

import com.design.pattern.Proxy.demo03.service.BaseService;
import com.design.pattern.Proxy.demo03.service.ZhiLiao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 步骤4：创建代理工厂（BeanFactory），对所有吃的行为有一个监听。
 */
public class BeanFactory {

    public static BaseService newInstance(Class classFile) {
        //1.创建一个真实的小蝉
        BaseService xiaochan = new ZhiLiao();
        //2.创建代理实现类对象  螳螂
        InvocationHandler tangLang = new AgentTangLang(xiaochan);

        //3.向JVM所要代理对象（监听对象） 监听蝉何时吃树叶，通知螳螂
        Class array[] =  classFile.getInterfaces();
        BaseService proxy1 = (BaseService) Proxy.newProxyInstance(classFile.getClassLoader(), array, tangLang);

        //-----------------步骤6：在BeanFactory中的newInstance方法添加代理实现类（黄雀）--------------------
        //4.创建代理实现类  黄雀
        InvocationHandler huangQue = new AgentHuangQue(proxy1);
        BaseService proxy2 =(BaseService) Proxy.newProxyInstance(classFile.getClassLoader(), array, huangQue);
        return proxy2;

    }
}