package com.design.pattern.builder.demo05;

import java.util.Date;

public class DefaultReservationBuilder implements ReservationBuilder {
    private Date date;
    private int headCount;
    private String city;
    private double dollarsPerHead;
    private boolean hasSite;

    public ReservationBuilder futurize(Date date) {
        this.date = date;
        return this;
    }

    public ReservationBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public ReservationBuilder setDollarsPerHead(double dollarsPerHead) {
        this.dollarsPerHead = dollarsPerHead;
        return this;
    }

    public ReservationBuilder setSite(boolean hasSite) {
        this.hasSite = hasSite;
        return this;
    }

    public ReservationBuilder setHeadCount(int headCount) {
        this.headCount = headCount;
        return this;
    }

    public Reservation build() {
        Reservation reservation = new Reservation();
        reservation.setCity(this.city);
        reservation.setDate(this.date);
        reservation.setDollarsPerHead(this.dollarsPerHead);
        reservation.setHasSite(this.hasSite);
        reservation.setHeadCount(this.headCount);
        return reservation;
    }
}