/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.WeightNoteReceiptDao;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.hibernate.dto.view.WnrAllocationView;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
@Transactional(propagation=Propagation.REQUIRED)
public class WeightNoteReceiptService {

    private WeightNoteReceiptDao weightNoteReceiptDao;
    private WeightNoteService weightNoteService;
    private DeliveryInstructionRepository deliveryInstructionRepository;

    public void setWeightNoteReceiptDao(WeightNoteReceiptDao weightNoteReceiptDao) {
        this.weightNoteReceiptDao = weightNoteReceiptDao;
    }

    public void setWeightNoteService(WeightNoteService weightNoteService) {
        this.weightNoteService = weightNoteService;
    }
    
    public void setDeliveryInstructionRepository(DeliveryInstructionRepository deliveryInstructionRepository) {
		this.deliveryInstructionRepository = deliveryInstructionRepository;
	}

	public ArrayList<WeightNoteReceipt> getWNRByWN(int wn_id) {
        return weightNoteReceiptDao.getWNRByWN(wn_id);
    }

    public ArrayList<WeightNoteReceipt> getAvailableWNR(int wn_id) {
        return weightNoteReceiptDao.getAvailableWNR(wn_id);
    }

    public ArrayList<WeightNoteReceipt> getAllocatedWNR(int wn_id, int inst_id) {
        return weightNoteReceiptDao.getAllocatedWNR(wn_id, inst_id);
    }

    public WeightNoteReceipt getWNRById(int id) {
        return weightNoteReceiptDao.getWNRById(id);
    }

    public int updateWNR(WeightNoteReceipt wnr) {
        int res = weightNoteReceiptDao.updateWNR(wnr);
        return res;
    }

    public String getNewWnrRef(String wn_ref) {
        return weightNoteReceiptDao.getNewWnrRef(wn_ref);
    }

    public int countNumberOfWnr(int wn_id) {
        return weightNoteReceiptDao.countNumberOfWnr(wn_id);
    }

    public Float getTotalByAreaId(int areaID, int clientid, int pledgeid, int gradeid) {
        return weightNoteReceiptDao.getTotalByArea(areaID, clientid, pledgeid, gradeid);
    }

    public ArrayList<Map> getWeightNoteReceiptsByArea(int area, int companyId) {
        return weightNoteReceiptDao.getWeightNoteReceiptsByArea(area, companyId);
    }

    public Number countNumberOf(int wn_id, String field_name) {
        return weightNoteReceiptDao.countNumberOf(wn_id, field_name);
    }

    public Float countProcessingAllocated(int inst_id, String type) {
        return weightNoteReceiptDao.countProcessingAllocated(inst_id, type);
    }

    public WeightNoteReceipt getWNRByRef(String ref_number) {
        return weightNoteReceiptDao.getWNRByRef(ref_number);
    }

    public Map delete_wnr(WeightNote wn, WeightNoteReceipt wnr, String username, String reason) {
    	
        Map m = new HashMap();
        if (wnr.getStatus() != Constants.ALLOCATED && wn.getStatus() != Constants.COMPLETE) {
            String[][] main_arr = {
                {"type", "delete"},
                {"user", username},
                {"element", ""},
                {"reason", reason}
            };
            String log = Common.convertToJson((Object) main_arr);
            wnr.setStatus(Constants.DELETED);
            wnr.setLog((wnr.getLog() != null) ? wnr.getLog() + "," + log : log);

            //WeightNote wn = weightNoteService.getWnById(wn_id);
            String[][] element_arr2 = {
                {"wnr_id", wnr.getId() + ""},
                {"wnr_ref", wnr.getRefNumber()},
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)}
            };
            String[][] main_arr2 = {
                {"type", "wnr_delete"},
                {"user", username},
                {"element", Common.convertToJson((Object) element_arr2)}
            };
            String log2 = Common.convertToJson((Object) main_arr2);
            wn.setLog((wn.getLog() != null) ? wn.getLog() + "," + log2 : log2);

            ArrayList<WnrAllocation> wnrAllocation = weightNoteReceiptDao.getAllocationByOutWnr(wnr.getId());
            if (wnrAllocation != null) {
                for (WnrAllocation wnrA : wnrAllocation) {
                    wnrA.setStatus((byte) 0);
                    wnrA.setDateOut(null);
                    wnrA.setWeightOut(null);
                    wnrA.setWeightNoteReceiptByOutWnrId(null);
                    wnrA.setUserByWeightOutUser(null);
                    weightNoteReceiptDao.saveWnrAllocation(wnrA);
                }
            }

            int b = weightNoteService.updateWN(wn);
            int a = weightNoteReceiptDao.updateWNR(wnr);

            m.put("status", "success");
            
            return m;
        } else {
            m.put("status", "allocated");
            m.put("ref_number", wnr.getRefNumber());

            return m;
        }
    }

    public String getWNRByMovId(int id) {
        return weightNoteReceiptDao.getWNRByMovId(id).toString();
    }

    public boolean updateWnrInfo(int client, int pledge, int area, String wns,String username, boolean isUpdateById) {
        if (!wns.equals("")) {
            return weightNoteReceiptDao.updateWnrInfo(client, pledge, area, wns, username, isUpdateById);
        }
        return false;
    }
    
    public int saveWnaView(WnrAllocationView wnav) {
        return weightNoteReceiptDao.saveWnaView(wnav);
    }
    
    public boolean checkWNRWeightedOut(int wnr_id) {
        return weightNoteReceiptDao.checkWnrIsWeightedOut(wnr_id);
    }
}
