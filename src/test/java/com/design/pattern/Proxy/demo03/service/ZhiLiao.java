package com.design.pattern.Proxy.demo03.service;

import com.design.pattern.Proxy.demo03.service.BaseService;

/**
 * 步骤2：创建真实对象（蝉）
 */
public class ZhiLiao implements BaseService {
    @Override
    public void eat() {
        System.out.println("蝉吃树叶");
    }
}