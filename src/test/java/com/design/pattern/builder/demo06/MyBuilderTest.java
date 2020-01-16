package com.design.pattern.builder.demo06;

import com.design.pattern.builder.demo05.DefaultReservationBuilder;
import com.design.pattern.builder.demo05.Reservation;
import com.design.pattern.builder.demo05.ReservationBuilder;
import com.design.pattern.builder.demo05.ReservationDirector;

public class MyBuilderTest {
    public static void main(String[] args) throws Exception {
        //Date, November 5, Headcount, 250, City, Shanghai, DollarsPerHead, 60,HasSite, false
        String input = "Date, November 5, Headcount, 20, City, Shanghai, DollarsPerHead, 60,HasSite, false";
        MySelfBuilder builder = new DefaultMyselfBuilder();
        MySelf mySelf = builder.build(input);
        System.out.println(mySelf);
    }

}
