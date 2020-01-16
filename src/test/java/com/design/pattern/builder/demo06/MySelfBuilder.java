package com.design.pattern.builder.demo06;

import com.design.pattern.builder.demo05.Reservation;

import java.util.Date;

/**
 * 构建者模式：
 * 需要实现的接口
 */
public interface MySelfBuilder {
    
    public MySelfBuilder futurize(Date date) ;
    
    public MySelfBuilder setCity(String city);
    
    public MySelfBuilder setDollarsPerHead(double dollarsPerHead);
    
    public MySelfBuilder setSite(boolean hasSite);
    
    public MySelfBuilder setHeadCount(int headCount);
    // 构建需要的实体对象
    MySelf build(String input) throws Exception;
}