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
public interface RefNumberProviderService {
    public String getNumberWithoutIncreasing();
    public void resetNumber(RefNumberCurrentInfo info);
    public String getNewNumber();
    public String getPrefix();
}
