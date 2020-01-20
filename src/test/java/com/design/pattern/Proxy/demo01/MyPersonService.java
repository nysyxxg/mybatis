package com.design.pattern.Proxy.demo01;

public class MyPersonService implements BaseService {

    @Override
    public void eating(String food) {
        System.out.println("狼吞虎咽吃 "+food);
    }

    @Override
    public void wcing() {
       System.out.println("减体重...");
    }

    @Override
    public void sleep() {
        System.out.println("........我要休息............");
    }
}