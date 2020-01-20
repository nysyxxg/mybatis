package com.design.pattern.factory;

import javax.sql.DataSource;

/**
 * 工厂方法模式
 */
public interface MyDataSourceFactory {// 首先：有一个对工厂的抽象接口类

    //生产数据源,直接得到javax.sql.DataSource
    DataSource getDataSource();

}