/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.service;
import java.text.DateFormatSymbols;

import org.springframework.util.Assert;
/**
 *
 * @author macOS
 */
public class TerminalMonth {
    private int month = 1;
    
    public TerminalMonth(int oneBasedMonth) {
        Assert.isTrue(isValid(oneBasedMonth));
        this.month = oneBasedMonth;
    }
    
    public TerminalMonth(String twoLetterMonth) {
        Assert.notNull(twoLetterMonth);
        Assert.isTrue(twoLetterMonth.length() == 2 && (twoLetterMonth.charAt(0) == '0' || twoLetterMonth.charAt(0) == '1'));
        this.month = Integer.valueOf(twoLetterMonth);
    }

    public int getMonth() {
        return month;
    }
    
    public String getThreeLetterMonth() {
       return new DateFormatSymbols().getMonths()[month-1].substring(0, 3);
    }
    
    @Override
    public String toString() {
        if(month < 11) {
            return String.format("0%d", month);
        }
        return String.format("%d", month);
    }
    
    public static boolean isValid(int month) {
        return month % 2 == 1 && month <= 11 && month >= 1;
    }
    
}
