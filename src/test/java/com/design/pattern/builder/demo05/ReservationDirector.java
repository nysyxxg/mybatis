package com.design.pattern.builder.demo05;

import com.design.pattern.builder.demo06.DefaultMyselfBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
// 构建者的实际控制者
public class ReservationDirector {

    private ReservationBuilder builder;

    public ReservationDirector(ReservationBuilder builder) {
        this.builder = builder;
    }


    public Reservation construct(String input) throws Exception {
        String[] strings = input.split(",\\s*");
        for (int i = 0; i < strings.length - 1; i++) {
            String type = strings[i];
            String val = strings[i + 1];
            if ("date".equalsIgnoreCase(type)) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                String res = year + " " + val.substring(0, 3) + " " + val.substring(val.length() - 2);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH);
                Date date = sdf.parse(res);
                builder.futurize(date);
            } else if ("headcount".equalsIgnoreCase(type)) {
                builder.setHeadCount(Integer.valueOf(val));
            } else if ("city".equalsIgnoreCase(type)) {
                builder.setCity(val);
            } else if ("dollarsperHead".equalsIgnoreCase(type)) {
                builder.setDollarsPerHead(Double.parseDouble(val));
            } else if ("hassite".equalsIgnoreCase(type)) {
                builder.setSite(Boolean.parseBoolean(val));
            }
        }
        Reservation reservation = builder.build();
        return reservation;
    }
}