package com.design.pattern.decorator;

/**
 * 这个类作为基础类
 * 微笑类
 */
public class CompetitorSimle implements Competitor {

    public void sing() {
        simle();
    }

    public void simle() {
        System.out.println("........哈哈哈哈啊哈！！开心的大笑！！！");
    }
}