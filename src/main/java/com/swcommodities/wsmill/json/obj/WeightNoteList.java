/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.json.obj;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class WeightNoteList {

    private ArrayList<WN_Object> wn_list;

    public WeightNoteList(ArrayList<WeightNote> wns, String type) {
        this.wn_list = new ArrayList<>();
        for (WeightNote wn : wns) {
            wn_list.add(new WN_Object(wn, type));
        }
    }

    public ArrayList<WN_Object> getWn_list() {
        return wn_list;
    }

    public void setWn_list(ArrayList<WN_Object> wn_list) {
        this.wn_list = wn_list;
    }

    public class WN_Object {

        String btn_id;
        String wn_id;
        String wn_ref;
        String wn_status;
        String wn_style;
        String qr_ref;
        String qr_status;
        String qr_style;
        String wn_grade;
        String wn_bags;
        String wn_net;
        String btnClass;
        String value;

        public WN_Object(WeightNote wn, String type) {
            this.btn_id = "avai_" + wn.getId();
            this.wn_id = wn.getId().toString();
            this.wn_ref = wn.getRefNumber();
            this.wn_status = (wn.getStatus().equals(Constants.COMPLETE) ? "Complete" : "Pending");
            this.wn_style = (wn.getStatus().equals(Constants.COMPLETE) ? "color:black" : "color:red");
            this.qr_ref = wn.getQualityReport().getRefNumber();
            this.qr_status = (wn.getQualityReport().getStatus().equals(Constants.COMPLETE) ? "Complete" : "Pending");
            this.qr_style = (wn.getQualityReport().getStatus().equals(Constants.COMPLETE) ? "color:black" : "color:red");
            this.wn_grade = wn.getGradeMaster().getName();
            ArrayList<WeightNoteReceipt> wnrs = new ArrayList<>(wn.getWeightNoteReceipts());
            int bag = 0;
            float net = 0;
            for (WeightNoteReceipt wnr : wnrs) {
                if (!wnr.getStatus().equals(Constants.DELETED)) {
                    bag += wnr.getNoOfBags();
                    net += wnr.getGrossWeight() - wnr.getTareWeight();
                }
            }
            this.wn_bags = bag + "";
            this.wn_net = net + "";
            this.btnClass = (type.equals("selected") ? "btnSelected" : "");
            this.value = (type.equals("selected") ? "Delete" : "");
        }

        public String getBtn_id() {
            return btn_id;
        }

        public void setBtn_id(String btn_id) {
            this.btn_id = btn_id;
        }

        public String getWn_id() {
            return wn_id;
        }

        public void setWn_id(String wn_id) {
            this.wn_id = wn_id;
        }

        public String getWn_ref() {
            return wn_ref;
        }

        public void setWn_ref(String wn_ref) {
            this.wn_ref = wn_ref;
        }

        public String getWn_status() {
            return wn_status;
        }

        public void setWn_status(String wn_status) {
            this.wn_status = wn_status;
        }

        public String getWn_style() {
            return wn_style;
        }

        public void setWn_style(String wn_style) {
            this.wn_style = wn_style;
        }

        public String getQr_ref() {
            return qr_ref;
        }

        public void setQr_ref(String qr_ref) {
            this.qr_ref = qr_ref;
        }

        public String getQr_status() {
            return qr_status;
        }

        public void setQr_status(String qr_status) {
            this.qr_status = qr_status;
        }

        public String getQr_style() {
            return qr_style;
        }

        public void setQr_style(String qr_style) {
            this.qr_style = qr_style;
        }

        public String getWn_grade() {
            return wn_grade;
        }

        public void setWn_grade(String wn_grade) {
            this.wn_grade = wn_grade;
        }

        public String getWn_bags() {
            return wn_bags;
        }

        public void setWn_bags(String wn_bags) {
            this.wn_bags = wn_bags;
        }

        public String getWn_net() {
            return wn_net;
        }

        public void setWn_net(String wn_net) {
            this.wn_net = wn_net;
        }

        public String getBtnClass() {
            return btnClass;
        }

        public void setBtnClass(String btnClass) {
            this.btnClass = btnClass;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
