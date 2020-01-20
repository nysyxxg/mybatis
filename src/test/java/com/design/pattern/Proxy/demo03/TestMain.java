package com.design.pattern.Proxy.demo03;

import com.design.pattern.Proxy.demo03.service.BaseService;
import com.design.pattern.Proxy.demo03.service.ZhiLiao;

/**
 * MyBatis|多级代理编程实现
 * https://www.jianshu.com/p/26da4d1b1550
 *  需求：
 *  实现一个多级代理，螳螂捕蝉，黄雀在后。
 *  蝉吃树叶，螳螂吃蝉，黄雀吃螳螂。
 * 步骤7：添加测试类
 */
public class TestMain {

    public static void main(String[] args) {    
        BaseService xiaochan = BeanFactory.newInstance(ZhiLiao.class);
        xiaochan.eat();
    }
}