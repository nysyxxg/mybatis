package com.design.pattern.builder.demo05;

import java.util.Date;

/**
 * 构建者需要实现的接口
 */
public interface ReservationBuilder {
    
    public ReservationBuilder futurize(Date date) ;
    
    public ReservationBuilder setCity(String city);
    
    public ReservationBuilder setDollarsPerHead(double dollarsPerHead);
    
    public ReservationBuilder setSite(boolean hasSite);
    
    public ReservationBuilder setHeadCount(int headCount);
    // 构建需要的实体对象
    Reservation build();
}