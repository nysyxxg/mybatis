package com.design.pattern.builder.demo05;

import com.design.pattern.builder.demo06.DefaultMyselfBuilder;

public class ReservationBuilderTest {
    public static void main(String[] args) throws Exception {
        //Date, November 5, Headcount, 250, City, Shanghai, DollarsPerHead, 60,HasSite, false
        String input = "Date, November 5, Headcount, 20, City, Shanghai, DollarsPerHead, 60,HasSite, false";
        DefaultReservationBuilder builder = new DefaultReservationBuilder();
        ReservationDirector director = new ReservationDirector(builder);
        Reservation reservation = director.construct(input);
        System.out.println(reservation);
    }

}
