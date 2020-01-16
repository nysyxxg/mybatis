package com.design.pattern.decorator;

/**
 * 鼓掌类：我们需要继承与基础的那个唱歌的方法，同时使用组合，
 * 在内部保有一个super class。同时提供一个有参的构造方法。
 *
 */
public class CompetitorApplause implements Competitor {
    private Competitor competitor;

    public CompetitorApplause(Competitor competitor) {
        this.competitor = competitor;
    }

    public void sing() {
        applause();
        competitor.sing();
    }

    public void applause() {
        System.out.println("........鼓掌......啪啪啪！！啪啪啪！！！");
    }

}
