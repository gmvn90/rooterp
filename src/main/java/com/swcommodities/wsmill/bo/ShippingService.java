/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dao.DeliveryInsDao;
import com.swcommodities.wsmill.hibernate.dao.ShippingDao;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.object.ShippingInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author duhc
 */
public class ShippingService {
    private ShippingDao shippingDao;
    private DeliveryInsDao deliveryInsDao;
    private WeightNoteService weightNoteService;

    public void setWeightNoteService(WeightNoteService weightNoteService) {
        this.weightNoteService = weightNoteService;
    }

    public void setDeliveryInsDao(DeliveryInsDao deliveryInsDao) {
        this.deliveryInsDao = deliveryInsDao;
    }

    public void setShippingDao(ShippingDao shippingDao) {
        this.shippingDao = shippingDao;
    }
    
    public ArrayList<ShippingInstruction> getShippingRefList(String searchString) {
        return shippingDao.getShippingRefList(searchString);
    }
    
    public ShippingInstruction getSiById(int id) {
        return shippingDao.getSiById(id);
    }
    
    public ShippingInstruction getSiByRef(String ref) {
        return shippingDao.getSiByRef(ref);
    }
    
    public String getNewContractRef() {
        return shippingDao.getNewContractRef();
    }

    public Integer getLastestId () { return shippingDao.getLatestId();}
    
    public int updateShip(ShippingInstruction ship){
        return shippingDao.updateShip(ship);
    }
    
    public long countRow() {
        return shippingDao.countRow();
    }
    
    public ArrayList<ShippingInstructionObj> searchShippingIns(String searchTerm, String order, int start, int amount, String colName, int grade, int sup, Byte status) {
        return shippingDao.searchShippingIns(searchTerm, order, start, amount, colName, grade, sup, status);
    }

    public ArrayList<HashMap> searchShippingIns_v2(String order, int start, int amount,
                                                   String colName, int gradeId, int clientId, Byte status) throws ParseException {
        return shippingDao.searchShippingIns_v2(order, start, amount, colName, gradeId, clientId, status);
    }
    public long getTotalAfterFilter() {
        return shippingDao.getTotalAfterFilter();
    }
    
    public Map countTotals(String searchTerm, int grade, int sup, Byte status) {
        return shippingDao.countTotals(searchTerm, grade, sup, status);
    }
    
    public ArrayList<ShippingInstruction> getAllSIRefList(){
        return shippingDao.getAllSIRefList();
    }
    
    public ArrayList<GradeMaster> getAllGrades(){
        return shippingDao.getAllGrades();
    }
    
    public ArrayList<CompanyMaster> getCompanyInSi() {
        return shippingDao.getCompanyInSi();
    }
    
    public GradeMaster getSIGrades(ShippingInstruction si) {
        if (si != null) {
            return si.getGradeMaster();
        }
        return null;
    }
    
    public boolean delete_si(ShippingInstruction si, String username, String reason) {
        if (si.getStatus() == Constants.COMPLETE) {
            return false;
        }
        else if (!deliveryInsDao.checkInstDeletable(si.getId(), Constants.EX)) {
            return false;
        } else {
            if (weightNoteService.getWeightNoteFromInst(si.getId(), Constants.EX_TYPE) != null) {
                for (WeightNote wn : weightNoteService.getWeightNoteFromInst(si.getId(), Constants.EX_TYPE)) {
                    weightNoteService.delete_wn(wn, username, reason);
                }
            }

            String[][] main_arr = {
                {"type", "delete"},
                {"user", username},
                {"element", ""},
                {"reason", reason}
            };

            if (si.getLog() == null) {
                si.setLog("");
            }
            String log = si.getLog() + "," + Common.convertToJson((Object) main_arr);
            si.setLog(log);
            si.setStatus(Constants.DELETED);
            shippingDao.updateShip(si);
            return true;
        }

    }

    public JSONObject delete_si_surround(ShippingInstruction si) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("title", "Cannot delete instruction " + si.getRefNumber() + " because of following reasons:");
        JSONArray a_json = new JSONArray();
        if (si.getStatus() == Constants.COMPLETE) {
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "Instruction " + si.getRefNumber() + " is completed");
            a_json.put(sub_json);
        }
        if (weightNoteService.getWeightNoteFromInst(si.getId(), Constants.EX_TYPE) != null) {
            for (WeightNote wn : weightNoteService.getWeightNoteFromInst(si.getId(), Constants.EX_TYPE)) {
                if (wn.getWrcId() != null) {
                    JSONObject sub_json = new JSONObject();
                    sub_json.put("a_row", "Weight note " + wn.getRefNumber() + " is in a Warehouse Receipt");
                    a_json.put(sub_json);
                }
                if (wn.getStatus() == Constants.COMPLETE) {
                    JSONObject sub_json = new JSONObject();
                    sub_json.put("a_row", "Weight note " + wn.getRefNumber() + " is completed");
                    a_json.put(sub_json);
                }
                if (wn.getWeightNoteReceipts() != null) {
                    for (WeightNoteReceipt wnr : wn.getWeightNoteReceipts()) {
                        if (wnr.getStatus() == Constants.ALLOCATED) {
                            JSONObject sub_json = new JSONObject();
                            sub_json.put("a_row", "Weight Note Receipt " + wnr.getRefNumber() + " is allocated");
                            a_json.put(sub_json);
                        }
                    }
                }
            }
        }
        json.put("msg", a_json);
        return json;
    }
    
    public JSONArray getContainers(int si_id) throws JSONException {
        return weightNoteService.getContainers(si_id);
    }
    
    public ArrayList<ShippingInstructionObj> getExportReportInfo(int client_id, String from, String to) {
        return shippingDao.getExportReportInfo(client_id, from, to);
    }
    
    public ShippingInstruction getFullSiById(int id){
        return shippingDao.getFullSiById(id);
    }
    
    public ArrayList<HashMap> getPendingSi(int clientid) {
        return shippingDao.getPendingSi(clientid);
    }
    
    public Map getShippingFullInfo(int id) {
        return shippingDao.getShippingFullInfo(id);
    }
    
    public String getNotifyInString(int id) {
        return shippingDao.getNotifyInString(id);
    }
    
    public Map getAccessoriesInMap(int id) {
        return shippingDao.getAccessoriesInMap(id);
    }
    
    public String getDocumentInString(int id) {
        return shippingDao.getDocumentInString(id);
    }
    
    public ArrayList<HashMap> getShippingReport(int si_id) {
        return shippingDao.getShippingReport(si_id);
    }
    
    public ArrayList<HashMap> getShippingReportDetailWnr(int si_id) {
        return shippingDao.getShippingReportDetailWnr(si_id);
    }
    
    public ArrayList<HashMap> getShippingReportDetailWn(int si_id) {
        return shippingDao.getShippingReportDetailWn(si_id);
    }
}
