/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

/**
 *
 * @author duhc
 */
public class StockReportObj extends StockTotal {

    private int wn_id;
    private String wn_ref;
    private String inst_ref;
    private int num;
    private String packing_name;
    private String date;
    private String area;
    private String cup;
    private String client;
    private String grade;

    public StockReportObj() {
    }

    public StockReportObj(int wn_id, String wn_ref, String inst_ref, int num, String packing_name, String date, String area, String cup, String client) {
        super();
        this.wn_id = wn_id;
        this.wn_ref = wn_ref;
        this.inst_ref = inst_ref;
        this.num = num;
        this.packing_name = packing_name;
        this.date = date;
        this.area = area;
        this.cup = cup;
        this.client = client;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public int getWn_id() {
        return wn_id;
    }

    public void setWn_id(int wn_id) {
        this.wn_id = wn_id;
    }

    public String getWn_ref() {
        return wn_ref;
    }

    public void setWn_ref(String wn_ref) {
        this.wn_ref = wn_ref;
    }

    public String getInst_ref() {
        return inst_ref;
    }

    public void setInst_ref(String inst_ref) {
        this.inst_ref = inst_ref;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPacking_name() {
        return packing_name;
    }

    public void setPacking_name(String packing_name) {
        this.packing_name = packing_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
