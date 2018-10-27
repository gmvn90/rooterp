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

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author macOS
 */
@Entity
@Table(name = "daily_basis")
public class DailyBasis implements java.io.Serializable {

    private Integer id;
    private User user;
    private String terminalMonth;
    private int tmCode;
    private float currentBasis;
    private Date updatedDate;
    private Float previousBasis;
    private Date previousDate;
    private String log;
    private float liffeHigh;
    private float liffeLow;

    public DailyBasis() {
    }

    public DailyBasis(int id) {
        this.id = id;
    }

    public DailyBasis(String id) {
        this(Integer.valueOf(id));

    }

    public DailyBasis(String terminalMonth, float liffeHigh, float liffeLow, int tmCode, float currentBasis, Date updatedDate) {
        this.terminalMonth = terminalMonth;
        this.liffeHigh = liffeHigh;
        this.liffeLow = liffeLow;
        this.tmCode = tmCode;
        this.currentBasis = currentBasis;
        this.updatedDate = updatedDate;
    }

    public DailyBasis(String terminalMonth, float liffeHigh, float liffeLow, int tmCode, float currentBasis) {
        this(terminalMonth, liffeHigh, liffeLow, tmCode, currentBasis, new Date());
    }

    public static DailyBasis fromJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        return fromJson(obj);
    }

    public static DailyBasis fromJson(JSONObject obj) throws JSONException {
        DailyBasis dailyBasis = new DailyBasis(obj.getString("terminalMonth"), (float) obj.getDouble("liffeHigh"),
            (float) obj.getDouble("liffeLow"), obj.getInt("tmCode"), (float) obj.getDouble("currentBasis"));
        return dailyBasis;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "terminal_month", nullable = false, length = 11, unique = true)
    public String getTerminalMonth() {
        return this.terminalMonth;
    }

    public void setTerminalMonth(String terminalMonth) {
        this.terminalMonth = terminalMonth;
    }

    @Column(name = "tm_code", nullable = false, unique = true)
    public int getTmCode() {
        return this.tmCode;
    }

    public void setTmCode(int tmCode) {
        this.tmCode = tmCode;
    }

    @Column(name = "current_basis", nullable = false, precision = 9)
    public float getCurrentBasis() {
        return this.currentBasis;
    }

    public void setCurrentBasis(float currentBasis) {
        this.currentBasis = currentBasis;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false, length = 19)
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "previous_basis", precision = 9)
    public Float getPreviousBasis() {
        return this.previousBasis;
    }

    public void setPreviousBasis(Float previousBasis) {
        this.previousBasis = previousBasis;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "previous_date", length = 19)
    public Date getPreviousDate() {
        return this.previousDate;
    }

    public void setPreviousDate(Date previousDate) {
        this.previousDate = previousDate;
    }

    @Column(name = "log")
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Column(name = "liffe_high", nullable = false, precision = 9)
    public float getLiffeHigh() {
        return liffeHigh;
    }

    public void setLiffeHigh(float liffeHigh) {
        this.liffeHigh = liffeHigh;
    }

    @Column(name = "liffe_low", nullable = false, precision = 9)
    public float getLiffeLow() {
        return liffeLow;
    }

    public void setLiffeLow(float liffeLow) {
        this.liffeLow = liffeLow;
    }

}
