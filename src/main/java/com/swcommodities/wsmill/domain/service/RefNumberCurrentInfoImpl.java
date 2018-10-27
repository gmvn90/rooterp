/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.service;

import java.util.Calendar;

/**
 *
 * @author trung
 */
public class RefNumberCurrentInfoImpl implements RefNumberCurrentInfo {
    
    private final int twoNumberMaxYearInDatabase;
    private final int maxNumber;
    
    public RefNumberCurrentInfoImpl(int twoNumberMaxYearInDatabase, int maxNumber) {
        this.twoNumberMaxYearInDatabase = twoNumberMaxYearInDatabase;
        this.maxNumber = maxNumber;
    }

    @Override
    public int getTwoNumberCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR) % 100;
    }

    @Override
    public int getTwoNumberMaxYearInDatabase() {
        return twoNumberMaxYearInDatabase;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
    
    private static int weight(RefNumberCurrentInfo info) {
        return info.getTwoNumberMaxYearInDatabase() * 1__000__000 + info.getMaxNumber();
    }

    @Override
    public int compareTo(RefNumberCurrentInfo other) {
        if(weight(this) > weight(other)) {
            return 1;
        }
        else if(weight(this) < weight(other)) {
            return -1;
        }
        return 0;
    }
    
}
