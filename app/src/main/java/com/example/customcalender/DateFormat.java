package com.example.customcalender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    public static String getCurrentDate(String format) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
       // ComLog.d("format ? "+format);
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    public static String getDate(String format, int day) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);      //년 더하기


        return sdf.format(cal.getTime());
    }

    public static String getDDate(String date, String format, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();

        try {
            Date newDate = sdf.parse(date);

            cal.setTime(newDate);
            cal.add(Calendar.DATE, day);      //년 더하기

        } catch (ParseException e) {
          //  ComLog.d("DateFormat error-01");
//            e.printStackTrace();
        }


        return sdf.format(cal.getTime());
    }

    public static String getMDate(String date, String format, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();

        try {
            Date newDate = sdf.parse(date);

            cal.setTime(newDate);
            cal.add(Calendar.MONTH, day);      //년 더하기

        } catch (ParseException e) {
           // ComLog.d("DateFormat error-02");
//            e.printStackTrace();
        }


        return sdf.format(cal.getTime());
    }

    public static String getYDate(String date, String format, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();

        try {
            Date newDate = sdf.parse(date);

            cal.setTime(newDate);
            cal.add(Calendar.YEAR, year);      //년 더하기

        } catch (ParseException e) {
           // ComLog.d("DateFormat error-03");
//            e.printStackTrace();
        }


        return sdf.format(cal.getTime());
    }

    public static String newFormat(String date, String oldFormat, String newFormat) {
        String newDate = "";
        SimpleDateFormat old_format = new SimpleDateFormat(oldFormat);
        SimpleDateFormat new_format = new SimpleDateFormat(newFormat);

        try {
            Date old_date = old_format.parse(date);

            newDate = new_format.format(old_date);
        } catch (ParseException e) {
        //    ComLog.d("DateFormat error-04");
//            e.printStackTrace();
        }

        return newDate;
    }

    public static boolean isValidationDate(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.parse(date);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
