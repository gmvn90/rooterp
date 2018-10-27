/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

import java.util.Date;

/**
 *
 * @author duhc
 */
public class ProcessingInstructionObj {
    private int pi_id;
    private String ref_number;
    private String origin;
    private String quality;
    private String grade_name;
    private String packing_name;
    private Float allocated;
    private Float in_process;
    private Float ex_process;
    private Float pending;
    private Date from_date;
    private Date to_date;
    private byte status;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
    
    /**
     * @return the pi_id
     */
    public int getPi_id() {
        return pi_id;
    }

    /**
     * @param pi_id the pi_id to set
     */
    public void setPi_id(int pi_id) {
        this.pi_id = pi_id;
    }

    /**
     * @return the ref_number
     */
    public String getRef_number() {
        return ref_number;
    }

    /**
     * @param ref_number the ref_number to set
     */
    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * @return the grade_name
     */
    public String getGrade_name() {
        return grade_name;
    }

    /**
     * @param grade_name the grade_name to set
     */
    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    /**
     * @return the packing_name
     */
    public String getPacking_name() {
        return packing_name;
    }

    /**
     * @param packing_name the packing_name to set
     */
    public void setPacking_name(String packing_name) {
        this.packing_name = packing_name;
    }

    /**
     * @return the allocated
     */
    public Float getAllocated() {
        return allocated;
    }

    /**
     * @param allocated the allocated to set
     */
    public void setAllocated(Float allocated) {
        this.allocated = allocated;
    }

    /**
     * @return the in_process
     */
    public Float getIn_process() {
        return in_process;
    }

    /**
     * @param in_process the in_process to set
     */
    public void setIn_process(Float in_process) {
        this.in_process = in_process;
    }

    /**
     * @return the ex_process
     */
    public Float getEx_process() {
        return ex_process;
    }

    /**
     * @param ex_process the ex_process to set
     */
    public void setEx_process(Float ex_process) {
        this.ex_process = ex_process;
    }

    /**
     * @return the pending
     */
    public Float getPending() {
        return pending;
    }

    /**
     * @return the from_date
     */
    public Date getFrom_date() {
        return from_date;
    }

    /**
     * @param from_date the from_date to set
     */
    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    /**
     * @return the to_date
     */
    public Date getTo_date() {
        return to_date;
    }

    /**
     * @param to_date the to_date to set
     */
    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    /**
     * @param pending the pending to set
     */
    public void setPending(Float pending) {
        this.pending = pending;
    }

}
