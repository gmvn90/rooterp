/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.el;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author macOS
 */
public class Format {
    public static String getTons(Double number) {
        if(number == null) {
            return "";
        }
        return String.format("%.3f", number);
    }
    
    public static String getDateOnly(Date date) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat dt1 = new SimpleDateFormat(DateConstant.dateOnly);
        return dt1.format(date);
    }
    
    public static String getDateOnlyWithTwoNumberYear(Date date) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat dt1 = new SimpleDateFormat(DateConstant.dateOnlyWithTwoNumberYear);
        return dt1.format(date);
    }
    
    public static String getDateTime(Date date) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat dt1 = new SimpleDateFormat(DateConstant.dateTime);
        return dt1.format(date);
    }
    
    public static String getDateTimeAMPM(Date date) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat dt1 = new SimpleDateFormat(DateConstant.dateTimeAMPM);
        return dt1.format(date);
    }
    
    public static String getMoneyWithComma(Double number) {
        if(number == null) {
            return "";
        }
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String res = formatter.format(number);
        if(res.equals("-0.00")) {
            res = "0.00";
        }
        return res;
    }
    
    public static String getMoneyForInput(Double number) {
        if(number == null) {
            return "";
        }
        return String.format("%.2f", number);
    }
}
