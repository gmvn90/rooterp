/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author macOS
 */

@Embeddable @Builder @AllArgsConstructor
public class SampleSentItem {
    
    private String sampleSentItemId = "";
    private WeightNote lotNumber;
    private ApprovalStatus status;
    private User updater;
    private Date updated;
    
    public SampleSentItem() {
    		this.sampleSentItemId = UUID.randomUUID().toString();
    }
    
    public String getSampleSentItemId() {
        return sampleSentItemId;
    }

    public void setSampleSentItemId(String sampleSentItemId) {
        this.sampleSentItemId = sampleSentItemId;
    }
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "ref_number")
    public WeightNote getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(WeightNote lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Enumerated(EnumType.STRING)
    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    
    
    
}
