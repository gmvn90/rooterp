/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import com.swcommodities.wsmill.hibernate.dto.Claim;
import java.util.Date;

/**
 *
 * @author macOS
 */
public class ClaimDateBetweenDates extends DateBetweenDates<Claim> {
    public ClaimDateBetweenDates(Date startDate, Date endDate) {
        super(startDate, endDate);
    }
}
