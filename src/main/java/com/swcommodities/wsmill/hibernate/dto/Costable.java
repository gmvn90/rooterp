/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import com.swcommodities.wsmill.domain.model.ShippingUnit;
import com.swcommodities.wsmill.domain.model.exceptions.UnitNotExistedException;

/**
 *
 * @author macOS
 */

public interface Costable {
    public String getOptionUnit();
    public String getOptionName();
    public double getCostValue();
    default public ShippingUnit getOptionUnitEnum() throws UnitNotExistedException {
        switch (getOptionUnit()) {
            case "1 Pallet":
                // 1 total
                return ShippingUnit.OnePallet;
            case "Per Bag - 60kg":
                return ShippingUnit.PerBag60Kg;
            case "Per Bag - Bulk":
                return ShippingUnit.PerBagBulk;
            case "Per Bag - Big":
                return ShippingUnit.PerBagBig;
            case "Per Bag":
                // 1 total
                String name = getOptionName();
                if(name.equals("bulk__bulk-bag")) {
                    return ShippingUnit.PerBagBulk;
                } else if(name.equals("big-bag__big-bag")) {
                    return ShippingUnit.PerBagBig;
                }
                return ShippingUnit.PerBag60Kg;
            case "26.25 Kwh/20'Cont.":
            case "20' Cont.":
            case "20 Cont.":
            case "16Kg/20' Cont.":
            case "6 sides/20' Cont.":
                // 4 total
                return ShippingUnit.TwentyContainer;
            case "Per B/L":
            case "Per/BL":
                // 2 total
                return ShippingUnit.PerBillOfLading;
            case "$/Mt./%":
            case "$/Mt.":
            case "$/Mt./Mth.":
                return ShippingUnit.PerTon;
            default:
                throw new UnitNotExistedException(String.format("%s does not exist", getOptionUnit()));
        }
    }
    
    public default double getCostPerMetricTon(double tonPerContainer, int numberOfContainer) throws UnitNotExistedException {
        return getOptionUnitEnum().getPerMetricTonCost(this.getCostValue(), tonPerContainer, numberOfContainer);
    }
}
