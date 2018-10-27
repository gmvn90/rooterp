/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author macOS
 */
public class DailyBasisService {
    
    
    public static List<TerminalMonthYear> getPreviousTerminalMonthIncludeCurrentDesc(TerminalMonthYear currentTerminalMonthYear, int number) {
        List<TerminalMonthYear> result = new ArrayList<>();
        result.add(currentTerminalMonthYear);
        TerminalMonthYear currentSwap = currentTerminalMonthYear;
        for(int i = 0; i < number - 1; i++) {
            TerminalMonthYear previousItem = currentSwap.getPreviousTerminalMonthYear();
            result.add(previousItem);
            currentSwap = previousItem;
        }
        return result;
    }
    
    public static List<TerminalMonthYear> getPreviousTerminalMonthIncludeCurrentAsc(TerminalMonthYear currentTerminalMonthYear, int number) {
        List<TerminalMonthYear> result = new ArrayList<>();
        result.add(currentTerminalMonthYear);
        TerminalMonthYear currentSwap = currentTerminalMonthYear;
        for(int i = 0; i < number - 1; i++) {
            TerminalMonthYear nextItem = currentSwap.getNextTerminalMonthYear();
            result.add(nextItem);
            currentSwap = nextItem;
        }
        return result;
    }
    
    public static List<TerminalMonthYear> getPreviousTerminalMonthIncludeCurrentDesc(TerminalMonthYear currentTerminalMonthYear) {
        return getPreviousTerminalMonthIncludeCurrentDesc(currentTerminalMonthYear, 6);
    }
    
    public static List<TerminalMonthYear> getNextTerminalMonthIncludeCurrentAsc(TerminalMonthYear currentTerminalMonthYear) {
        return getPreviousTerminalMonthIncludeCurrentAsc(currentTerminalMonthYear, 7);
    }
    
    private static int getCurrentMonth_OneBased() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }
    
    private static int getCurrentYear_FourLetter() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }
    
    public static TerminalMonthYear getCurrentTerminalMonthYear() {
        return getCurrentTerminalMonthYear(getCurrentYear_FourLetter(), getCurrentMonth_OneBased());
    }
    
    public static TerminalMonthYear getCurrentTerminalMonthYear(int year, int month) {
        if(month % 2 == 0) {
            month = month - 1;
        }
        return new TerminalMonthYear(year, month);
    }
    
}
