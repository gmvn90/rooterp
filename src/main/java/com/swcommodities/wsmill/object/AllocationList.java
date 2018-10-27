/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

/**
 *
 * @author shind_000
 */
public class AllocationList {
    private int id;
    private String refNumber;
    private String grade;
    private String packing;
    private float total;
    private float allocated;
    private float delivered;
    private String from_date;
    private String to_date;
    
    public AllocationList(){
        
    }

    public AllocationList(int id, String refNumber, String grade, String packing, float total, float allocated, float delivered, String from_date, String to_date) {
        this.id = id;
        this.refNumber = refNumber;
        this.grade = grade;
        this.packing = packing;
        this.total = total;
        this.allocated = allocated;
        this.delivered = delivered;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getAllocated() {
        return allocated;
    }

    public void setAllocated(float allocated) {
        this.allocated = allocated;
    }

    public float getDelivered() {
        return delivered;
    }

    public void setDelivered(float delivered) {
        this.delivered = delivered;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }
    
}
