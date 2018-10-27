/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;

/**
 *
 * @author macOS
 */
public class DailyBasisToValue {
    public double mapDailyBasis(DailyBasis dailyBasis) {
        if(dailyBasis == null) {
            return 0;
        }
        return dailyBasis.getCurrentBasis();
    }
}
