package com.example.organnize.helper;

import java.text.SimpleDateFormat;

public class DateCustom {
    public static String dateToday(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String onlyMonthYear(String date){
        //separa os caracteres com base na '/'
        String returnDate[] = date.split("/");
        String month = returnDate[1];
        String year = returnDate[2];
        String monthYear = month + year;
        return monthYear;

    }
}
