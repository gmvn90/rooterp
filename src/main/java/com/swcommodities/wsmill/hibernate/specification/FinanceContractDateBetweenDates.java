/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import java.util.Date;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;

/**
 *
 * @author macOS
 */
public class FinanceContractDateBetweenDates extends DateBetweenDates<FinanceContract> {
    
    public FinanceContractDateBetweenDates(Date startDate, Date endDate) {
        super(startDate, endDate);
    }
}
