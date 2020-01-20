package com.design.pattern.Proxy.demo01;

//被监控的行为
public interface BaseService {
    //吃饭方法
    public void eating(String food);
    //上厕所方法
    public void wcing();

    // 休息
    public void sleep();
}