package com.design.pattern.Proxy.demo03.service;

/**
 * 步骤1：创建一个接口对象（BaseService），声明真实对象（蝉），
 * 蝉的行为被监听，需要被代理对象1（螳螂）吃掉，
 * 螳螂的主要任务也是吃，行为被监听，被代理对象2（AgentHuangQue）吃掉。
 *
 */
public interface BaseService {
    //吃是主要业务，也是被监听行为
    public void eat();
}