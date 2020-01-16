package com.design.pattern.decorator;

/**
 * 伴乐类
 */
public class CompetitorMusic implements Competitor {
    private Competitor competitor;

    public CompetitorMusic(Competitor competitor) {
        this.competitor = competitor;
    }

    public void sing() {
        music();
        competitor.sing();
    }

    public void music() {
        System.out.println("........伴奏音乐.....music!!!!");
    }

}