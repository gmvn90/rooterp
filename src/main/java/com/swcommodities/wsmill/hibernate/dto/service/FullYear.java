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
public class FullYear {
    private int year = 2017;
    
    public FullYear(int year) {
        Assert.isTrue(year > 2000 && year < 3000);
        this.year = year;
    }
    
    public FullYear(String fourLetterNumber) {
        int year = Integer.valueOf(fourLetterNumber);
        Assert.isTrue(year > 2000 && year < 3000);
        this.year = year;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getTwoLetterYear() {
        return year % 100;
    }
    
    @Override
    public String toString() {
        return String.format("%d", year);
    }
}
