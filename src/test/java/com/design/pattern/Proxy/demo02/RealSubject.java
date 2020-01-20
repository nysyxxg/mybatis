package com.design.pattern.Proxy.demo02;

/**
 *  然后为接口RealSubject新建一个实现类RealSubject
 * RealSubject
 * 真实主题类
 * @author
 * @create 2018-03-29 14:21
 **/
public class RealSubject implements Subject {
    public RealSubject(){
    }
    @Override
    public void doSomething(String str) {
        System.out.println("RealSubject do something----------->" + str);
    }
}