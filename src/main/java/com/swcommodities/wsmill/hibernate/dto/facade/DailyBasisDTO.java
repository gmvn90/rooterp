/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import java.util.Date;

/**
 *
 * @author macOS
 */
public class DailyBasisDTO {
    private Integer id;
    private String terminalMonth;
    private int tmCode;
    private float currentBasis;
    private Date updatedDate;
    private Float previousBasis;
    private Date previousDate;
    private String log;
    private float liffeHigh;
    private float liffeLow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerminalMonth() {
        return terminalMonth;
    }

    public void setTerminalMonth(String terminalMonth) {
        this.terminalMonth = terminalMonth;
    }

    public int getTmCode() {
        return tmCode;
    }

    public void setTmCode(int tmCode) {
        this.tmCode = tmCode;
    }

    public float getCurrentBasis() {
        return currentBasis;
    }

    public void setCurrentBasis(float currentBasis) {
        this.currentBasis = currentBasis;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Float getPreviousBasis() {
        return previousBasis;
    }

    public void setPreviousBasis(Float previousBasis) {
        this.previousBasis = previousBasis;
    }

    public Date getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(Date previousDate) {
        this.previousDate = previousDate;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public float getLiffeHigh() {
        return liffeHigh;
    }

    public void setLiffeHigh(float liffeHigh) {
        this.liffeHigh = liffeHigh;
    }

    public float getLiffeLow() {
        return liffeLow;
    }

    public void setLiffeLow(float liffeLow) {
        this.liffeLow = liffeLow;
    }
    
    
}
