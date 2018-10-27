/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.service;

/**
 *
 * @author macOS
 */

public interface RefNumberCurrentInfo {
    public int getTwoNumberCurrentYear();
    public int getTwoNumberMaxYearInDatabase();
    public int getMaxNumber();
    public int compareTo(RefNumberCurrentInfo other);
}
