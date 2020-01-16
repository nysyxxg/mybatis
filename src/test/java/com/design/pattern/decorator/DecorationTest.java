package com.design.pattern.decorator;

import org.junit.Test;

/**
 *
 * 看上去没有什么太大的不同？事实上，装饰者模式灵活的使用了继承和多态，来使我们的系统很好的解耦。
 * 我们内部保有一个父类的对象以后，子类的构造方法需要提供一个父类。
 * 而我们构造子类的时候可以提供一个其他的子类给这个子类，
 * 这个子类在调用本身保有的那个父类的sing（）时，其实是调用了入参的子类的sing（）；
 * 因此我们可以根据不同的业务，不同的条件，让系统去组成不一样的定制化的歌手，这样就尽量少的对系统进行改动，也很大的减少了代码的冗余。
 * 测试方法如下
 *
 */
public class DecorationTest {
    @Test
    public void testA() {
        CompetitorSimle competitor = new CompetitorSimle();
        CompetitorMusic competitorMusic = new CompetitorMusic(competitor);
        CompetitorDance competitorDance = new CompetitorDance(competitorMusic);
        CompetitorApplause competitorApplause = new CompetitorApplause(competitorDance);
        competitorApplause.sing();
    }

    /**
     * 模拟Mybatis的缓存使用的装饰器模式
     */
    @Test
    public void testB() {
        Competitor competitor = new CompetitorSimle();
        competitor = new CompetitorMusic(competitor);
        competitor = new CompetitorDance(competitor);
        competitor = new CompetitorApplause(competitor);
        competitor.sing();
    }

    /**
     * 装饰器和 构建作者模式经常配合使用
     */
    @Test
    public void testWithBusiness() {
        Competitor competitor = new CompetitorSimle();
        int a = 10;
        //做业务判断
        if (a == 10) {
            competitor = new CompetitorMusic(competitor);
        }
        if (a > 35 && a <=50) {
            competitor = new CompetitorApplause(competitor);
        }
        if (a > 50) {
            competitor = new CompetitorDance(competitor);
        }
        competitor.sing();
    }
}