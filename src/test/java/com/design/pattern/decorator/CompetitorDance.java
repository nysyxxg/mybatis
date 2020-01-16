package com.design.pattern.decorator;

/**
 * 同样的我们定义跳舞和伴乐
 */
public class CompetitorDance implements Competitor {

    private Competitor competitor;

    public CompetitorDance(Competitor competitor) {
        this.competitor = competitor;
    }

    public void sing() {
        dance();
        competitor.sing();
    }

    public void dance() {
        System.out.println("........跳舞.....dancing");
    }
}