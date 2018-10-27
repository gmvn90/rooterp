/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dao.QualityReportDao;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.object.QualityReportObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class QualityReportService {
    private QualityReportDao qualityReportDao;

    public void setQualityReportDao(QualityReportDao qualityReportDao) {
        this.qualityReportDao = qualityReportDao;
    }

    public int updateQuality(QualityReport qr){
        return qualityReportDao.updateQuality(qr);
    }
    
    public String getNewQRRef(String type){
        return qualityReportDao.getNewQRRef(type);
    }
    
    public QualityReport getQualityReportByWeightnote(WeightNote wn){
        return qualityReportDao.getQualityReportByWeightnote(wn);
    }

    public CourierMaster getCourierById(int id) {
        return qualityReportDao.getCourierById(id);
    }
    
    public ArrayList<QualityReport> getQrRefList(String type) {
        return qualityReportDao.getQrRefList(type);
    }
    
    public QualityReport getQualityReportById(int id) {
        return qualityReportDao.getQualityReportById(id);
    }
    
    public ArrayList<QualityReportObj> searchQualityReport(String searchTerm, String order, int start, int amount, String colName, int grade, int sup, int buyer, String type, Byte status, String from, String to) {
        return qualityReportDao.searchQualityReport(searchTerm, order, start, amount, colName, grade, sup, buyer, type, status, from, to);
    }
    
    public long getTotalAfterFilter() {
        return qualityReportDao.getTotalAfterFilter();
    }
    
    public ArrayList<Map> getAllGrades(String type) {
        return qualityReportDao.getAllGrades(type);
    }
    
    public ArrayList<Map> getCompany(String type) {
        return qualityReportDao.getCompany(type);
    }
    
    public void delete_qr(QualityReport qr, String username) {
        String[] arr1 = {"type", "user", "element"};
        String[] arr2 = {"delete", username, ""};
        if (qr.getLog() == null) {
            qr.setLog("");
        }
        String log = qr.getLog() + "," + Common.generateJsonString(arr1, arr2);
        qr.setLog(log);
        qr.setStatus(Constants.DELETED);
        qualityReportDao.updateQuality(qr);
    }
    
    public JSONObject searchQualityReport_1(String type, int qr_id) throws JSONException {
        return qualityReportDao.searchQualityReport_1(type, qr_id);
    }

    public SampleSent getSampleSentById(int id) {
        return qualityReportDao.getSampleSentById(id);
    }

    public String getNewSampleSentRef() {
        return qualityReportDao.getNewSampleSentRef();
    }

    public String getNewSampleSentTypeRef() {
        return qualityReportDao.getNewSampleSentTypeRef();
    }

    public SampleSent newSampleSent(SampleSent obj) {
        return qualityReportDao.newSampleSent(obj);
    }

    public SampleSent updateSampleSent(SampleSent obj) {
        return qualityReportDao.updateSampleSent(obj);
    }

    public ArrayList<HashMap> getSampleSentList(String order, int start, int amount,
                                                String colName, int clientId, Byte sentStatus, Byte approvalStatus) throws ParseException {
        return qualityReportDao.getSampleSentList(order, start, amount, colName, clientId, sentStatus, approvalStatus);
    }

    public JSONObject getSampleSentFullDetailById(int id) throws JSONException {
        return qualityReportDao.getSampleSentFullDetailById(id);
    }
}
