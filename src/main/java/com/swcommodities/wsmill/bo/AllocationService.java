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
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dao.AllocationDao;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.object.AllocationList;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class AllocationService {

    private AllocationDao allocationDao;

    private ProcessingService processingService;

    private ShippingService shippingService;

    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void setProcessingService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    public void setAllocationDao(AllocationDao allocationDao) {
        this.allocationDao = allocationDao;
    }

    public int updateAllocation(WnrAllocation allocation) {

        if (allocation != null) {
            if (allocation.getInstType().toString().equals("P")) {
                allocation.setProcessingInstruction(processingService.getPiById(allocation.getInstId()));
            } else {
                allocation.setShippingInstruction(shippingService.getSiById(allocation.getInstId()));
            }
            return allocationDao.updateAllocation(allocation);
        }
        return -1;
    }

    public void delete(WnrAllocation allocation) {
        if (allocation.getStatus().equals(Constants.PENDING)) {
            allocationDao.delete(allocation);
        }
    }

    public WnrAllocation findByWnrId(int id) {
        return allocationDao.findByWnrId(id);
    }

    public WnrAllocation findByWnrRef(String ref_number, int inst_id) {
        return allocationDao.findByWnrRef(ref_number, inst_id);
    }

    public long countRow(String type) {
        return allocationDao.countRow(type);
    }

    public ArrayList<AllocationList> searchGlobe(String searchTerm, String order, int start, int amount, String colName, int grade_id, String type, int status, int inst_id, String from_date, String to_date) throws ParseException {
        return allocationDao.searchGlobe(searchTerm, order, start, amount, colName, grade_id, type, status, inst_id, from_date, to_date);
    }

    public long getTotalAfterFilter(){
        return allocationDao.getTotalAfterFilter();
    }
    
    public Map countTotals(String searchTerm, int grade, String type, int status, int inst_id, String from_date, String to_date) throws ParseException {
        return allocationDao.countTotals(searchTerm, grade, type, status, inst_id, from_date, to_date);
    }
    
    public Map getInstInfo(int inst_id, String type){
        return allocationDao.getInstInfo(inst_id, type);
    }
    
    public String getDetailWNJson(int wn_id, ArrayList<WeightNoteReceipt> wnrs, int perm, String status, String function){
        Map<String,Object> map = new HashMap<>();
        map.put("wn_id",wn_id + "");
        map.put("perm", perm);
        map.put("function",function);
        map.put("status",status);
        ArrayList<Map<String,Object>> wnr_list = new ArrayList<>();
        for (WeightNoteReceipt wnr : wnrs) {
            Map<String,Object> wnr_map = new HashMap<>();
            wnr_map.put("wnr_id", wnr.getId().toString());
            wnr_map.put("ref_number", wnr.getRefNumber());
            wnr_map.put("gross", wnr.getGrossWeight().toString());
            wnr_map.put("tare", wnr.getTareWeight().toString());
            wnr_map.put("net", (wnr.getGrossWeight() - wnr.getTareWeight()) + "");
            wnr_map.put("out_status", allocationDao.checkAllocateStatus(wnr.getId()));
            
            if (status.equals("De-Allocate")){
               wnr_map.put("show_out", 1);
            }else{
                wnr_map.put("show_out", 0);
            }
            
            wnr_list.add(wnr_map);
        }
        JSONArray json_array = new JSONArray(wnr_list);
        map.put("wnrs",json_array);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
    
    public int isAllocated(String ref_number, int inst_id, String type){
        return allocationDao.isAllocated(ref_number, inst_id, type);
    }
}
