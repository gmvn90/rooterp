/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

/**
 *
 * @author macOS
 */
public enum ShippingUnit {
    OnePallet {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit * 10 / tonPerContainer;
        }
    }, PerBag60Kg {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit * 320 / tonPerContainer;
        }
    }, PerBagBulk {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit / tonPerContainer;
        }
    }, PerBagBig {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit;
        }
    }, TwentyContainer {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit / tonPerContainer;
        }
    }, PerBillOfLading {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit / numberOfContainer / tonPerContainer;
        }
    }, PerTon {
        @Override
        public double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer) {
            return costForThisUnit;
        }
    };
    
    public abstract double getPerMetricTonCost(double costForThisUnit, double tonPerContainer, double numberOfContainer);
}
