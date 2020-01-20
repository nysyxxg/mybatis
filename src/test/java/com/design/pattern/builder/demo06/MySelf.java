package com.design.pattern.builder.demo06;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString   //lombok的注解，方便代码查看以及编写
//预约信息  Reservation.java
public class MySelf {
    //Date, November 5, Headcount, 250, City, Shanghai, DollarsPerHead, 60,HasSite, false
    private Date date;
    private int headCount;
    private String city;
    private double dollarsPerHead;
    private boolean hasSite;

}