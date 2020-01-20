package com.design.pattern.Proxy.demo02;


import javax.imageio.IIOException;

/**
 * Client
 * client测试代码
 *
 * @author
 * @create 2018-03-29 14:26
 **/
public class Client {
    public static void main(String[] args)  throws  InstantiationException, IllegalAccessException {
        // 保存生成的代理类的字节码文件
       // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // jdk动态代理测试
//        Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();

        Class clz = RealSubject.class;
        Object  obj = clz.newInstance();

        Subject subject = new JDKDynamicProxy(obj).getProxy();
        subject.doSomething("----------HAHA----------");
    }
}