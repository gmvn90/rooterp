/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.DeliveryInsDao;
import com.swcommodities.wsmill.hibernate.dao.ProcessingDao;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingType;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.object.ProcessingInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class ProcessingService {

    private ProcessingDao processingDao;
    private DeliveryInsDao deliveryInsDao;
    private WeightNoteService weightNoteService;

    public void setDeliveryInsDao(DeliveryInsDao deliveryInsDao) {
        this.deliveryInsDao = deliveryInsDao;
    }

    public void setWeightNoteService(WeightNoteService weightNoteService) {
        this.weightNoteService = weightNoteService;
    }

    public void setProcessingDao(ProcessingDao processingDao) {
        this.processingDao = processingDao;
    }

    public ArrayList<ProcessingType> getAllProcessTypes() {
        return processingDao.getAllProcessTypes();
    }

    public ProcessingType getTypeById(int id) {
        return processingDao.getTypeById(id);
    }

    public ProcessingInstruction getPiByRef(String ref) {
        return processingDao.getPiByRef(ref);
    }

    public String getNewContractRef() {
        return processingDao.getNewContractRef();
    }

    public int getLastestId() { return processingDao.getLastestId(); }

    public int updateProcess(ProcessingInstruction process) {
        if (process != null) {
            return processingDao.updateProcess(process);
        } else {
            return -1;
        }
    }

    public ArrayList<ProcessingInstruction> getProcessingRefList(String searchString) {
        return processingDao.getProcessingRefList(searchString);
    }

    public ArrayList<ProcessingInstruction> getAllPIRefList() {
        return processingDao.getAllPIRefList();
    }

    public ProcessingInstruction getPiById(int id) {
        if (id > -1) {
            return processingDao.getPiById(id);
        }
        return null;
    }

    public GradeMaster getPIGrades(int id) {
        return processingDao.getPIGrades(id);
    }

    public GradeMaster getPIGrades(ProcessingInstruction pi) {
        if (pi != null) {
            return pi.getGradeMaster();
        }
        return null;
    }

    public ArrayList<GradeMaster> getAllGrades() {
        return processingDao.getAllGrades();
    }

    public long countRow() {
        return processingDao.countRow();
    }

    public ArrayList<ProcessingInstructionObj> searchProcessingIns(String searchTerm, String order, int start, int amount, String colName, int grade, int client, Byte status) {
        return processingDao.searchProcessingIns(searchTerm, order, start, amount, colName, grade, client, status);
    }

    public long getTotalAfterFilter() {
        return processingDao.getTotalAfterFilter();
    }

    public Map countTotals(String searchTerm, int grade, int client, Byte status) {
        return processingDao.countTotals(searchTerm, grade, client, status);
    }

    public ArrayList<CompanyMaster> getCompanyInPi() {
        return processingDao.getCompanyInPi();
    }

    public boolean delete_po(ProcessingInstruction po, String username, String reason) {
        if (po.getStatus() == Constants.COMPLETE) {
            return false;
        }
        else if (!deliveryInsDao.checkInstDeletable(po.getId(), Constants.IP) || !deliveryInsDao.checkInstDeletable(po.getId(), Constants.XP)) {
            return false;
        } else {
            if (weightNoteService.getWeightNoteFromInst(po.getId(), 2) != null) {
                for (WeightNote wn : weightNoteService.getWeightNoteFromInst(po.getId(), 2)) {
                    weightNoteService.delete_wn(wn, username, reason);
                }
            }

            String[][] main_arr = {
                {"type", "delete"},
                {"user", username},
                {"element", ""},
                {"reason", reason}
            };

            if (po.getLog() == null) {
                po.setLog("");
            }
            String log = po.getLog() + "," + Common.convertToJson((Object) main_arr);
            po.setLog(log);
            po.setStatus(Constants.DELETED);
            processingDao.updateProcess(po);
            return true;
        }

    }

    public JSONObject delete_po_surround(ProcessingInstruction po) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("title", "Cannot delete instruction " + po.getRefNumber() + " because of following reasons:");
        JSONArray a_json = new JSONArray();
        if (po.getStatus() == Constants.COMPLETE) {
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "Instruction " + po.getRefNumber() + " is completed");
            a_json.put(sub_json);
        }
        if (weightNoteService.getWeightNoteFromInst(po.getId(), 2) != null) {
            for (WeightNote wn : weightNoteService.getWeightNoteFromInst(po.getId(), 2)) {
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
    
    public Map countAllocated(int id) {
        return processingDao.countAllocated(id);
    }
    
    public Map getPendingProcessingMap(){
        Map m = new HashMap();
        ArrayList<Map> m_arr = processingDao.getPendingProcessingMap();
        if (m_arr != null && !m_arr.isEmpty()){
            JSONArray j_arr = new JSONArray(m_arr);
            m.put("pi_list", j_arr);
        }
        return m;
    }
    
    public Map countTotalAllocatedPiReport(int instid) {
        return processingDao.countTotalAllocatedPiReport(instid);
    }
    
    public ArrayList<HashMap> getPendingPi(int clientid) {
        return processingDao.getPendingPi(clientid);
    }
    
    public ArrayList<HashMap<String, Object>> searchProccessingInstruction(int client,
			String fromDate, String toDate){
    	return processingDao.searchProccessingInstruction(client, fromDate, toDate);
    }
}
