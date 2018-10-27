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
public class MarketFobDiffDTO {
    private Integer id;
    private String gradeMaster;
    private float diff;
    private Date updatedDate;
    private Float lastDiff;
    private Date lastDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeMaster() {
        return gradeMaster;
    }

    public void setGradeMaster(String gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = diff;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Float getLastDiff() {
        return lastDiff;
    }

    public void setLastDiff(Float lastDiff) {
        this.lastDiff = lastDiff;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
    
    
    
}
