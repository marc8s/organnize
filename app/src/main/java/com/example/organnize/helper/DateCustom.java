package com.example.organnize.helper;

import java.text.SimpleDateFormat;

public class DateCustom {
    public static String dateToday(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }
}
