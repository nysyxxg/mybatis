package com.design.pattern.builder.demo06;

import com.design.pattern.builder.demo05.Reservation;
import com.design.pattern.builder.demo05.ReservationBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DefaultMyselfBuilder implements MySelfBuilder {
    private Date date;
    private int headCount;
    private String city;
    private double dollarsPerHead;
    private boolean hasSite;

    public DefaultMyselfBuilder(){

    }

    public MySelfBuilder futurize(Date date) {
        this.date = date;
        return this;
    }

    public MySelfBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public MySelfBuilder setDollarsPerHead(double dollarsPerHead) {
        this.dollarsPerHead = dollarsPerHead;
        return this;
    }

    public MySelfBuilder setSite(boolean hasSite) {
        this.hasSite = hasSite;
        return this;
    }

    public MySelfBuilder setHeadCount(int headCount) {
        this.headCount = headCount;
        return this;
    }

    public  MySelf build(String input) throws Exception {
        construct(input);
        MySelf myself = new  MySelf();
        myself.setCity(this.city);
        myself.setDate(this.date);
        myself.setDollarsPerHead(this.dollarsPerHead);
        myself.setHasSite(this.hasSite);
        myself.setHeadCount(this.headCount);
        return myself;
    }

    public void construct(String input) throws Exception {
        String[] strings = input.split(",\\s*");
        for (int i = 0; i < strings.length - 1; i++) {
            String type = strings[i];
            String val = strings[i + 1];
            if ("date".equalsIgnoreCase(type)) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                String res = year + " " + val.substring(0, 3) + " " + val.substring(val.length() - 2);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH);
                Date date = sdf.parse(res);
                this.futurize(date);
            } else if ("headcount".equalsIgnoreCase(type)) {
                this.setHeadCount(Integer.valueOf(val));
            } else if ("city".equalsIgnoreCase(type)) {
                this.setCity(val);
            } else if ("dollarsperHead".equalsIgnoreCase(type)) {
                this.setDollarsPerHead(Double.parseDouble(val));
            } else if ("hassite".equalsIgnoreCase(type)) {
                this.setSite(Boolean.parseBoolean(val));
            }
        }
    }
}