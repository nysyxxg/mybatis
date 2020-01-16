package com.design.pattern.decorator;

/**
 *
 * 装饰者模式与MyBatis二级缓存中装饰者模式的使用
 * 什么是装饰者模式
 *        装饰者模式的定义是 动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更加有弹性的替代方案。
 *         在设计的时候，往往要给一个对象的功能进行一些修饰，对功能进行拓展和增强，以满足我们的需求。
 * 使用装饰者模式的实例
 */
public  interface Competitor {
    public void sing();
}
