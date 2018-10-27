/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

/**
 *
 * @author kiendn
 */
public class WeightNoteRef {
    private int id;
    private String refNumber;

    public WeightNoteRef(int id, String refNumber) {
        this.id = id;
        this.refNumber = refNumber;
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
    
}
