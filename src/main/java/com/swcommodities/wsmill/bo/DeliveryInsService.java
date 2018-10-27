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
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.view.DeliveryView;
import com.swcommodities.wsmill.object.DeliveryInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author duhc
 */
@Transactional(propagation = Propagation.REQUIRED)
public class DeliveryInsService {

    private DeliveryInsDao deliveryInsDao;
    private WeightNoteService weightNoteService;

    public void setWeightNoteService(WeightNoteService weightNoteService) {
        this.weightNoteService = weightNoteService;
    }

    public void setDeliveryInsDao(DeliveryInsDao deliveryInsDao) {
        this.deliveryInsDao = deliveryInsDao;
    }

    public DeliveryInstruction getDiByRef(String ref) {
        return deliveryInsDao.getDiByRef(ref);
    }

    public String getNewContractRef() {
        return deliveryInsDao.getNewContractRef();
    }

    public int updateDeliveryIns(DeliveryInstruction deliveryIns) {
        if (deliveryIns != null) {
            return deliveryInsDao.updateDeliveryIns(deliveryIns);
        } else {
            return -1;
        }
    }

    public Byte getDiStatus(int id) {
        DeliveryInstruction di = deliveryInsDao.getDiById(id);
        if (di != null) {
            return di.getStatus();
        }
        return 0;
    }

    public ArrayList<DeliveryInstruction> getDeliveryInsRefList(String searchString) {
        return deliveryInsDao.getDeliveryInsRefList(searchString);
    }

    public DeliveryInstruction getDiById(int id) {
        return deliveryInsDao.getDiById(id);
    }
    
    public DeliveryView getLazyDiById(int id) {
        return deliveryInsDao.getLazyDiById(id);
    }

    public ArrayList<DeliveryInstruction> getAllDIRefList() {
        return deliveryInsDao.getAllDIRefList();
    }

    public String getRefById(int id) {
        return deliveryInsDao.getRefById(id);
    }

    public long countRow() {
        return deliveryInsDao.countRow();
    }

    public ArrayList<DeliveryInstructionObj> searchDeliveryIns(String searchTerm, String order, int start, int amount, String colName, int grade, int sup, int buy, Byte status) {
        return deliveryInsDao.searchDeliveryIns(searchTerm, order, start, amount, colName, grade, sup, buy, status);
    }

    public long getTotalAfterFilter() {
        return deliveryInsDao.getTotalAfterFilter();
    }

    public Map countTotals(String searchTerm, int grade, int sup, int buy, Byte status) {
        return deliveryInsDao.countTotals(searchTerm, grade, sup, buy, status);
    }

    public ArrayList<CompanyMaster> getCompanyInDi(String companyType) {
        return deliveryInsDao.getCompanyInDi(companyType);
    }

    public ArrayList<GradeMaster> getAllGrades() {
        return deliveryInsDao.getAllGrades();
    }

    public boolean delete_di(DeliveryInstruction di, String username, String reason) {
        if (di.getStatus() == Constants.COMPLETE) {
            return false;
        }
        else if (!deliveryInsDao.checkInstDeletable(di.getId(), Constants.IM)) {
            return false;
        } else {
            if (weightNoteService.getWeightNoteFromInst(di.getId(), Constants.IM_TYPE) != null) {
                for (WeightNote wn : weightNoteService.getWeightNoteFromInst(di.getId(), Constants.IM_TYPE)) {
                    weightNoteService.delete_wn(wn, username, reason);
                }
            }

            String[][] main_arr = {
                {"type", "delete"},
                {"user", username},
                {"element", ""},
                {"reason", reason}
            };

            if (di.getLog() == null) {
                di.setLog("");
            }
            String log = di.getLog() + "," + Common.convertToJson((Object) main_arr);
            di.setLog(log);
            di.setStatus(Constants.DELETED);
            deliveryInsDao.updateDeliveryIns(di);
            return true;
        }

    }

    public JSONObject delete_di_surround(DeliveryInstruction di) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("title", "Cannot delete instruction " + di.getRefNumber() + " because of following reasons:");
        JSONArray a_json = new JSONArray();
        if (di.getStatus() == Constants.COMPLETE) {
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "Instruction " + di.getRefNumber() + " is completed");
            a_json.put(sub_json);
        }
        if (weightNoteService.getWeightNoteFromInst(di.getId(), Constants.IM_TYPE) != null) {
            for (WeightNote wn : weightNoteService.getWeightNoteFromInst(di.getId(), Constants.IM_TYPE)) {
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
    
    public Map countDelivered(int id, Byte status) {
        return deliveryInsDao.countDelivered(id, status);
    }
    
    public ArrayList<DeliveryInstructionObj> getImportReportInfo(int client_id, String from, String to) {
        return deliveryInsDao.getImportReportInfo(client_id, from, to);
    }
    
    public ArrayList<HashMap> getPendingDi(int clientid) {
        return deliveryInsDao.getPendingDi(clientid);
    }
}
