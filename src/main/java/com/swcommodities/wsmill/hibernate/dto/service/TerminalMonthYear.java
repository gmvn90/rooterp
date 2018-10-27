/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.service;

import org.springframework.util.Assert;

/**
 *
 * @author macOS
 */
public class TerminalMonthYear {
    private TerminalMonth terminalMonth;
    private FullYear fullYear;
    
    public TerminalMonthYear(int year, int month) {
        fullYear = new FullYear(year);
        terminalMonth = new TerminalMonth(month);
    }
    
    public TerminalMonthYear(int sixLetterNumber) {
        fullYear = new FullYear(sixLetterNumber / 100);
        terminalMonth = new TerminalMonth(sixLetterNumber % 100);
    }
    
    public TerminalMonthYear(String sixLetterString) {
        Assert.isTrue(sixLetterString.length() == 6);
        fullYear = new FullYear(sixLetterString.substring(0, 4));
        terminalMonth = new TerminalMonth(sixLetterString.substring(4));
    }
    
    public TerminalMonthYear getNextTerminalMonthYear() {
        int newMonth = terminalMonth.getMonth() == 11 ? 1 : terminalMonth.getMonth() + 2;
        int newYear = terminalMonth.getMonth() == 11 ? fullYear.getYear() + 1 : fullYear.getYear();
        return new TerminalMonthYear(newYear, newMonth);
    }
    
    public TerminalMonthYear getPreviousTerminalMonthYear() {
        int newMonth = terminalMonth.getMonth() == 1 ? 11 : terminalMonth.getMonth() - 2;
        int newYear = terminalMonth.getMonth() == 1 ? fullYear.getYear() - 1 : fullYear.getYear();
        return new TerminalMonthYear(newYear, newMonth);
    }
    
    @Override
    public String toString() {
        return String.format("%s%s", fullYear.toString(), terminalMonth.toString());
    }
    
    public int getValue() {
        return fullYear.getYear() * 100 + terminalMonth.getMonth();
    }
    
    public String getThreeLetterMonthYearValue() {
        return String.format("%s-%d", terminalMonth.getThreeLetterMonth(), fullYear.getTwoLetterYear());
    }
    
}
