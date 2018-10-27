/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.swcommodities.wsmill.hibernate.dao.CompanyDao;
import com.swcommodities.wsmill.hibernate.dao.QualityReportDao;
import com.swcommodities.wsmill.hibernate.dao.WarehouseReceiptDao;
import com.swcommodities.wsmill.hibernate.dao.WeightNoteDao;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.json.obj.WeightNoteList;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
public class WarehouseReceiptService {
    
    private WarehouseReceiptDao warehouseReceiptDao;
    private CompanyDao companyDao;
    private QualityReportDao qualityReportDao;
    private WeightNoteDao weightNoteDao;
    private CommonService commonService;
    
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }
    
    public void setWarehouseReceiptDao(WarehouseReceiptDao warehouseReceiptDao) {
        this.warehouseReceiptDao = warehouseReceiptDao;
    }
    
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    
    public void setQualityReportDao(QualityReportDao qualityReportDao) {
        this.qualityReportDao = qualityReportDao;
    }
    
    public void setWeightNoteDao(WeightNoteDao weightNoteDao) {
        this.weightNoteDao = weightNoteDao;
    }
    
    public WarehouseReceipt findById(int id) {
        return warehouseReceiptDao.findById(id);
    }
    
    public ArrayList<WarehouseReceipt> getWarehouseReceipt(String type) {
        return warehouseReceiptDao.getWarehouseReceipt(type);
    }
    
    public WarehouseReceipt getWrFromJsonObj(com.swcommodities.wsmill.json.obj.WarehouseReceipt wrJson, HttpServletRequest request) throws Exception {
        WarehouseReceipt wr = new WarehouseReceipt();
        if (wrJson.getId() > -1) {
            wr.setId(wrJson.getId());
            wr.setQualityReport(qualityReportDao.getQualityReportById(wrJson.getQrId()));
            wr.setRefNumber(wrJson.getRefNumber());
            wr.setDate(Common.convertStringToDate(wrJson.getDate(), Common.date_format_a));
        }
        wr.setCompanyMasterByQualityControllerId(companyDao.getCompanyById(wrJson.getQualityControllerId()));
        wr.setCompanyMasterByWeightControllerId(companyDao.getCompanyById(wrJson.getWeightControllerId()));
        wr.setStatus(wrJson.getStatus());
        wr.setRemark(wrJson.getRemark());
        wr.setInstId(wrJson.getInstId());
        wr.setInstType(wrJson.getInstType());
        
        wr.setLastUpdated(new Date());
        wr.setUser((User) request.getSession().getAttribute("user"));
        return wr;
    }
    
    public ArrayList<WeightNote> getWnByWR(Integer wr_id) {
        if (wr_id != null && wr_id > 0) {
            return warehouseReceiptDao.getWnByWR(wr_id);
        }
        return new ArrayList<>();
    }
    
    public ArrayList<Integer> updateSelectedWN(ArrayList<Integer> wns, int wr_id) {
        //get wn list by wr_id
        if (!wns.isEmpty()) {
            if (wr_id > 0) {
                ArrayList<WeightNote> old_wn_list = warehouseReceiptDao.getWnByWR(wr_id);   /* get old weight note list  */
                ArrayList<Integer> un_exist = new ArrayList<>();                            /* get all weight note that are deleted from warehouse receipt  */
                for (WeightNote old : old_wn_list) {
                    boolean isExist = false;
                    for (Integer wn : wns) {
                        if (old.getId().equals(wn)) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        un_exist.add(old.getId());
                    }
                }
                for (Integer un : un_exist) {                                              /* update deleted WN */
                    WeightNote wn = weightNoteDao.getWnById(un);
                    wn.setWrcId(null);
                    weightNoteDao.updateWN(wn);
                }
                for (Integer wn : wns) {                                                   /* update new added WN */
                    WeightNote wn_new = weightNoteDao.getWnById(wn);
                    wn_new.setWrcId(wr_id);
                    weightNoteDao.updateWN(wn_new);
                }
            }
            return wns;
        }
        return null;
    }
    
    public String getNewWrRef(String type) {
        return warehouseReceiptDao.getNewWrRef(type);
    }
    
    public int update(WarehouseReceipt wr, String[] id_arr) {
        if (wr != null) {
            String log = ((wr.getLog() != null && !wr.getLog().equals("")) ? wr.getLog() + "," : "") + wrJsonObject(wr,id_arr);
            wr.setLog(log);
            return warehouseReceiptDao.update(wr);
        }
        return -1;
    }
    
    public ArrayList<WeightNote> getWnListFromArray(String[] id_arr) {
        ArrayList<WeightNote> list = new ArrayList<>();
        for (String id_arr1 : id_arr) {
            list.add(weightNoteDao.getWnById(Integer.parseInt(id_arr1)));
        }
        return list;
    }
    
    public String wnJsonObject(String[] id_arr, String type) {
        Gson gson = new Gson();
        WeightNoteList wnl = new WeightNoteList(getWnListFromArray(id_arr), type);
        return "[" + gson.toJson(wnl) + "]";
    }
    
    public String wrJsonObject(WarehouseReceipt wr, String[] id_arr) {
        Gson gson = new Gson();
        String grade = "";
        String supplier = "";
        Object[] grade_supplier;
        switch (wr.getInstType()) {
            case "IM":
                grade_supplier = commonService.getGradeCompanyFromInst(wr.getInstId(), "IM", "supplier_id");
                if (grade_supplier != null && grade_supplier.length > 0) {
                    grade = grade_supplier[0].toString();
                    supplier = grade_supplier[1].toString();
                }
                break;
            case "EX":
                grade_supplier = commonService.getGradeCompanyFromInst(wr.getInstId(), "EX", "client_id");
                if (grade_supplier != null && grade_supplier.length > 0) {
                    grade = grade_supplier[0].toString();
                    supplier = grade_supplier[1].toString();
                }
                break;
        }
        
        com.swcommodities.wsmill.json.obj.WarehouseReceipt wrJson = new com.swcommodities.wsmill.json.obj.WarehouseReceipt(wr, ((wr.getId() == null) ? getWnByWR(wr.getId()) : getWnListFromArray(id_arr)), grade, supplier);
        return gson.toJson(wrJson);
    }
    
    public WarehouseReceipt getWrByQrId(int id) {
        return warehouseReceiptDao.getWrByQrId(id);
    }
}
