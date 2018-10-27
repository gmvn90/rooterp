/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author macOS
 */
@Entity
@Table(name = "market_fob_diff")
public class MarketFobDiff implements java.io.Serializable, Comparable<MarketFobDiff> {

    private Integer id;
    private GradeMaster gradeMaster;
    private User user;
    private float diff;
    private Date updatedDate;
    private Float lastDiff;
    private Date lastDate;
    private String log;

    public MarketFobDiff() {
    }

    public MarketFobDiff(GradeMaster gradeMaster, float diff, Date updatedDate) {
        this.gradeMaster = gradeMaster;
        this.diff = diff;
        this.updatedDate = updatedDate;
    }

    public MarketFobDiff(GradeMaster gradeMaster, User user, float diff, Date updatedDate, Float lastDiff, Date lastDate, String log) {
        this.gradeMaster = gradeMaster;
        this.user = user;
        this.diff = diff;
        this.updatedDate = updatedDate;
        this.lastDiff = lastDiff;
        this.lastDate = lastDate;
        this.log = log;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true) // otherwise first ref as POJO, others as id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false, unique = true)
    public GradeMaster getGradeMaster() {
        return this.gradeMaster;
    }

    public void setGradeMaster(GradeMaster gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "diff", nullable = false, precision = 9)
    public float getDiff() {
        return this.diff;
    }

    public void setDiff(float diff) {
        this.diff = diff;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false, length = 19)
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "last_diff", precision = 9)
    public Float getLastDiff() {
        return this.lastDiff;
    }

    public void setLastDiff(Float lastDiff) {
        this.lastDiff = lastDiff;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_date", length = 19)
    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Column(name = "log")
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public int compareTo(MarketFobDiff o) {
        if (o.getGradeMaster() == null || o.getGradeMaster().getName() == null) {
            return 1;
        }
        if (getGradeMaster() == null || getGradeMaster().getName() == null) {
            return -1;
        }
        return getGradeMaster().getName().compareTo(o.getGradeMaster().getName());
    }

}
