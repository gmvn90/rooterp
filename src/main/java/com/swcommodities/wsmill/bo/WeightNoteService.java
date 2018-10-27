/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.WeightNoteDao;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.object.Allocation;
import com.swcommodities.wsmill.object.PiReportAllocatedWn;
import com.swcommodities.wsmill.object.PiReportAllocatedWnr;
import com.swcommodities.wsmill.object.StockReportInprocess;
import com.swcommodities.wsmill.object.StockReportObj;
import com.swcommodities.wsmill.object.WeighingObj;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class WeightNoteService {

    private WeightNoteDao weightNoteDao;
    private DeliveryInsService deliveryService;
    private ShippingService shippingService;
    private ProcessingService processingService;
    private QualityReportService qualityReportService;
    private WeightNoteReceiptService weightNoteReceiptService;
    private PackingService packingService;
    private WarehouseCellService warehouseCellService;
    private DeliveryInstructionRepository deliveryInstructionRepository;

    public void setWarehouseCellService(WarehouseCellService warehouseCellService) {
        this.warehouseCellService = warehouseCellService;
    }

    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void setPackingService(PackingService packingService) {
        this.packingService = packingService;
    }

    public void setWeightNoteReceiptService(WeightNoteReceiptService weightNoteReceiptService) {
        this.weightNoteReceiptService = weightNoteReceiptService;
    }

    public void setQualityReportService(QualityReportService qualityReportService) {
        this.qualityReportService = qualityReportService;
    }

    public void setDeliveryService(DeliveryInsService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setProcessingService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    public void setWeightNoteDao(WeightNoteDao weightNoteDao) {
        this.weightNoteDao = weightNoteDao;
    }

    public void setDeliveryInstructionRepository(DeliveryInstructionRepository deliveryInstructionRepository) {
		this.deliveryInstructionRepository = deliveryInstructionRepository;
	}

	public String getNewWnRef(String type) {
        return weightNoteDao.getNewWnRef(type);
    }

    public int updateWN(WeightNote wn) {
        return weightNoteDao.updateWN(wn);
    }

    public WeightNote getWNByDI(int id, String wn_type, User user) {
        DeliveryInstruction di = deliveryService.getDiById(id);
        WeightNote wn = new WeightNote();
        wn.setDeliveryInstruction(di);
        wn.setGradeMaster(di.getGradeMaster());
        wn.setPackingMaster(di.getPackingMaster());
        wn.setType(wn_type);
        wn.setInstId(di.getId());
        wn.setCreatedDate(new Date());
        wn.setRefNumber(getNewWnRef(wn_type));
        wn.setStatus(Byte.parseByte("0"));
        wn.setUser(user);
        String[][] main_arr = {
            {"type", "update"},
            {"user", user.getUserName()},};
        wn.setLog(Common.convertToJson((Object) main_arr));
        return wn;
    }

    public WeightNote getWnById(int id) {
        if (id > 0) {
            return weightNoteDao.getWnById(id);
        }
        return null;
    }

    public WeightNote getWNByPI(int id, String wn_type, User user) {
        ProcessingInstruction pi = processingService.getPiById(id);
        WeightNote wn = new WeightNote();
        wn.setProcessingInstruction(pi);
        //wn.setGradeMaster(pi.getGradeMaster());
        wn.setPackingMaster(pi.getPackingMaster());
        wn.setType(wn_type);
        wn.setInstId(pi.getId());
        wn.setCreatedDate(new Date());
        wn.setRefNumber(getNewWnRef(wn_type));
        wn.setStatus(Byte.parseByte("0"));
        wn.setUser(user);
        String[][] main_arr = {
            {"type", "update"},
            {"user", user.getUserName()},};
        wn.setLog(Common.convertToJson((Object) main_arr));
        return wn;
    }

    public WeightNote getWNBySI(int id, String wn_type, User user) {
        ShippingInstruction si = shippingService.getSiById(id);
        WeightNote wn = new WeightNote();
        wn.setShippingInstruction(si);
        wn.setGradeMaster(si.getGradeMaster());
        wn.setPackingMaster(si.getPackingMaster());
        wn.setType(wn_type);
        wn.setInstId(si.getId());
        wn.setCreatedDate(new Date());
        wn.setRefNumber(getNewWnRef(wn_type));
        wn.setStatus(Byte.parseByte("0"));
        wn.setUser(user);
        String[][] main_arr = {
            {"type", "update"},
            {"user", user.getUserName()}};
        wn.setLog(Common.convertToJson((Object) main_arr));
        return wn;
    }

    public WeightNote getWnByType(byte type, int ins_id, User user) {
        String wn_type = Common.getMillType(type);
        switch (type) {
            case 1:
                return getWNByDI(ins_id, wn_type, user);
            case 2:
            case 3:
                return getWNByPI(ins_id, wn_type, user);
            case 4:
                return getWNBySI(ins_id, wn_type, user);
            default:
                return null;
        }
    }

    public String getDIRefFilterList(HttpServletRequest request, ServletContext context) throws Exception {
        ArrayList<DeliveryInstruction> list = deliveryService.getAllDIRefList();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateRefList(tpl, list, "ins_");
    }

    public String getPIRefFilterList(HttpServletRequest request, ServletContext context) throws Exception {
        ArrayList<ProcessingInstruction> list = processingService.getAllPIRefList();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateRefList(tpl, list, "ins_");
    }

    public Byte getQrStatus(int wn_id) {
        WeightNote wn = weightNoteDao.getWnById(wn_id);
        if (wn != null) {
            QualityReport qr = wn.getQualityReport();
            if (qr.getStatus() != null) {
                return qr.getStatus();
            }
        }
        return 0;
    }

    public String getSIRefFilterList(HttpServletRequest request, ServletContext context) throws Exception {
        ArrayList<ShippingInstruction> list = shippingService.getAllSIRefList();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateRefList(tpl, list, "ins_");
    }

    public String generateWeightNote(HttpServletRequest request, ServletContext context, WeightNote wn, boolean del_right, boolean save_right) throws Exception {
        Template tpl;
        DeliveryInstruction di;
        ProcessingInstruction pi;
        ShippingInstruction si;
        String wnr_content = this.generateWeightNoteReceipt(request, context, wn.getType(), weightNoteReceiptService.getWNRByWN(wn.getId()), del_right, save_right);
        WarehouseCell area = wn.getWarehouseCell();
        String str_area = (area != null) ? warehouseCellService.convertIdIntoCode(area) : "";
        int cell_id = (area != null) ? area.getId() : 0;
        switch (wn.getType()) {
            case "IM":
                di = deliveryService.getDiById(wn.getInstId());
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_info_import.html"));
                return new GenTemplate(request).generateWNImport(tpl, wn, di, wnr_content, str_area, cell_id);
            case "IP":
                pi = processingService.getPiById(wn.getInstId());
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_info_inprocess.html"));
                return new GenTemplate(request).generateWNInprocess(tpl, wn, pi, wnr_content);
            case "XP":
                pi = processingService.getPiById(wn.getInstId());
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_info_exprocess.html"));
                return new GenTemplate(request).generateWNExprocess(tpl, wn, pi, wnr_content, str_area, cell_id);
            case "EX":
                si = shippingService.getSiById(wn.getInstId());
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_info_export.html"));
                return new GenTemplate(request).generateWNExport(tpl, wn, si, wnr_content);
            default:
                return "";
        }
//        return null;
    }

    public String generateWeightNoteReceipt(HttpServletRequest request, ServletContext context, String type, ArrayList<WeightNoteReceipt> weightNoteReceipts, boolean del_right, boolean save_right) throws Exception {
        if (weightNoteReceipts != null && !weightNoteReceipts.isEmpty()) {
            ArrayList<PackingMaster> packings = packingService.getAllPackings();
            return new GenTemplate(request).generateWNRList(context, type, weightNoteReceipts, packings, del_right, save_right);
        }
        return "";
    }

    public ArrayList<Object[]> getWeightNoteRefListByDI(String searchString, int ins_id, int grade_id, byte status, int supplier_id, String type, Integer from) {
        from = (from != null) ? from : 0;
        return weightNoteDao.getWeightNoteRefListByDI(searchString, ins_id, grade_id, status, supplier_id, type, from);
    }

    public ArrayList<Object[]> getWeightNoteRefListByPI(String searchString, int ins_id, int grade_id, byte status, int supplier_id, String type) {
        return weightNoteDao.getWeightNoteRefListByPI(searchString, ins_id, grade_id, status, supplier_id, type);
    }

    public ArrayList<Object[]> getWeightNoteRefListBySI(String searchString, int ins_id, int grade_id, byte status, int supplier_id, String type) {
        return weightNoteDao.getWeightNoteRefListBySI(searchString, ins_id, grade_id, status, supplier_id, type);
    }

    public ArrayList<Object[]> getWeightNoteRefList(String type, String searchString, int ins_id, int grade_id, byte status, int supplier_id, int pledge_id, Integer from) {
        switch (type) {
            case "IM":
                return getWeightNoteRefListByDI(searchString, ins_id, grade_id, status, supplier_id, "IM", from);
            case "IP":
                return getWeightNoteRefListByPI(searchString, ins_id, grade_id, status, supplier_id, "IP");
            case "XP":
                return getWeightNoteRefListByPI(searchString, ins_id, grade_id, status, supplier_id, "XP");
            case "EX":
                return getWeightNoteRefListBySI(searchString, ins_id, grade_id, status, supplier_id, "EX");
            case "M":
                return getMovementRefList(searchString, grade_id, status, supplier_id, pledge_id);
            default:
                return null;
        }
    }

    public ArrayList<Object[]> getMovementRefList(String searchString, int grade_id, byte status, int client_id, int pledge_id) {
        return weightNoteDao.getMovementRefList(searchString, grade_id, status, client_id, pledge_id);
    }

    public ArrayList<WeightNote> getWeightNotesByArea(int area) {
        return weightNoteDao.getWeightNotesByArea(area);
    }

    public ArrayList<Allocation> searchAvailableWN(String searchTerm, String order, int start, int amount, String colName, int grade) {
        return weightNoteDao.searchAvailableWN(searchTerm, order, start, amount, colName, grade);
    }

    public ArrayList<Allocation> searchAllocatedWn(String searchTerm, String order, int start, int amount, String colName, int inst_id, String type) {
        return weightNoteDao.searchAllocatedWn(searchTerm, order, start, amount, colName, inst_id, type);
    }

    public long countRow() {
        return weightNoteDao.countRow();
    }

    public long getTotalAfterFilter() {
        return weightNoteDao.getTotalAfterFilter();
    }

    public long getTotalAvailable() {
        return weightNoteDao.getTotalAvailable();
    }

    public long getTotalAllocated() {
        return weightNoteDao.getTotalAllocated();
    }

    public WeightNote getWnByQrId(int id) {
        return weightNoteDao.getWnByQrId(id);
    }

    public ArrayList<WeightNote> getWeightNoteFromInst(int id, int type) {
        return weightNoteDao.getWeightNoteFromInst(id, type);
    }

    public ArrayList<Object[]> getAvailableWeightNoteForWR(int inst_id, String inst_type) {
        return weightNoteDao.getAvailableWeightNoteForWR(inst_id, inst_type);
    }

    public ArrayList<Object[]> getListInstructionByType(String inst_type) {
        return weightNoteDao.getListInstructionByType(inst_type);
    }

    public ArrayList<WeighingObj> searchWeightNote(String searchTerm, String order, int start, int amount, String colName, int grade, int inst_id, String type, Byte status) {
        return weightNoteDao.searchWeightNote(searchTerm, order, start, amount, colName, grade, inst_id, type, status);
    }

    public HashMap getWNSum(int id) {
        return weightNoteDao.getWNSum(id);
    }

    public Map countTotals(String searchTerm, int grade, int inst_id, String type, Byte status) {
        return weightNoteDao.countTotals(searchTerm, grade, inst_id, type, status);
    }

    public ArrayList<GradeMaster> getAllGrades() {
        return weightNoteDao.getAllGrades();
    }

    public ArrayList<GradeMaster> getAllGradesByType(String type) {
        return weightNoteDao.getAllGradesByType(type);
    }

    public ArrayList<HashMap> getGradeInStock2(int map_id, int client_id, int pledge_id) {
        return weightNoteDao.getGradeInStock2(map_id, client_id, pledge_id);
    }

    public ArrayList<HashMap> getGradeImportExportInStockOnSpecificDate(String from, String to, String type) {
        return weightNoteDao.getGradeImportExportInStockOnSpecificDate(from, to, type);
    }

    public Map countTotalAvailableWn(String searchTerm, int grade) {
        return weightNoteDao.countTotalAvailableWn(searchTerm, grade);
    }

    public Map countTotalAllocatedWn(String searchTerm, int inst_id, String type) {
        return weightNoteDao.countTotalAllocatedWn(searchTerm, inst_id, type);
    }

    public boolean delete_wn(WeightNote wn, String username, String reason) {
    	
    	
        if (!weightNoteDao.checkWnDeletable(wn.getId())) {
            return false;
        } else {
            if (wn.getWeightNoteReceipts() != null) {
                for (WeightNoteReceipt wnr : wn.getWeightNoteReceipts()) {
                    weightNoteReceiptService.delete_wnr(wn, wnr, username, reason);
                }
            }
            String[][] main_arr = {
                {"type", "delete"},
                {"user", username},
                {"element", ""},
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"reason", reason}
            };
            if (wn.getLog() == null) {
                wn.setLog("");
            }
            String log = wn.getLog() + "," + Common.convertToJson((Object) main_arr);
            wn.setLog(log);
            wn.setStatus(Constants.DELETED);

            if (wn.getQualityReport() != null) {
                QualityReport qr = wn.getQualityReport();
                qualityReportService.delete_qr(qr, username);
            }

            weightNoteDao.updateWN(wn);
            return true;
        }
    }

    public boolean checkWnEditable(int wn_id) {
        return weightNoteDao.checkWnDeletable(wn_id);
    }

    public JSONObject delete_wn_surround(WeightNote wn) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("title", "Cannot delete weight note " + wn.getRefNumber() + " because of following reasons:");
        JSONArray a_json = new JSONArray();
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
        json.put("msg", a_json);
        return json;
    }

    public JSONArray getContainers(int si_id) throws JSONException {
        JSONArray arr = new JSONArray();

        ArrayList<Integer> ids = weightNoteDao.getAllIdByType("EX", si_id);
        Integer count = 1;
        for (Integer id : ids) {
            JSONObject last = new JSONObject();
            JSONObject obj = weightNoteDao.getContainerByWnId(id);
            obj.put("num", count);
            last.put("elements", obj);
            arr.put(last);
            count++;
        }
        return arr;
    }

    public ArrayList<StockReportObj> getReportObject(int grade, String date, int map_id, int client_id) {
        return weightNoteDao.getReportObject(grade, date, map_id, client_id);
    }

    public QualityReport getweightedAverageFromQr(String wnId) {
        return weightNoteDao.getweightedAverageFromQr(wnId);
    }

    public ArrayList<WeightNote> getWeightNotesByWrcId(int id) {
        return weightNoteDao.getWeightNotesByWrcId(id);
    }

    public Map getWnrTotal(int wn_id) {
        return weightNoteDao.getWnrTotal(wn_id);
    }

    public ArrayList<PiReportAllocatedWn> getPiReportAllocatedWn(int pi_id) {
        return weightNoteDao.getPiReportAllocatedWn(pi_id);
    }

    public ArrayList<PiReportAllocatedWnr> getPiReportAllocatedWnr(int po_id) {
        return weightNoteDao.getPiReportAllocatedWnr(po_id);
    }

    public String convertToJson(WeightNote wn, boolean del_right, boolean save_right) {
        try {
            Map map = new HashMap();
            map.put("id", wn.getId());
            map.put("ref_number", wn.getRefNumber());
            map.put("qr", wn.getQualityReport().getRefNumber());
            map.put("date", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
            map.put("type", wn.getType());
            map.put("inst_id", wn.getInstId());
            switch (wn.getType()) {
                case "IM": {
                    DeliveryInstruction di = deliveryService.getDiById(wn.getInstId());
                    map.put("inst_ref", di.getRefNumber());
                    map.put("supplier", di.getCompanyMasterBySupplierId().getName());
                    map.put("supplier_ref", di.getSupplierRef());
                }
                break;
                case "IP":
                case "XP": {
                    ProcessingInstruction pi = processingService.getPiById(wn.getInstId());
                    map.put("inst_ref", pi.getRefNumber());
                    map.put("supplier", pi.getCompanyMasterByClientId().getName());
                    map.put("supplier_ref", pi.getClientRef());
                }
                break;
                case "EX": {
                    ShippingInstruction si = shippingService.getSiById(wn.getInstId());
                    map.put("inst_ref", si.getRefNumber());
                    map.put("supplier", (si.getCompanyMasterBySupplierId() == null) ? "": si.getCompanyMasterBySupplierId().getName());
                    map.put("supplier_ref", si.getSupplierRef());
                }
                break;
            }
            map.put("grade", (wn.getGradeMaster() != null) ? wn.getGradeMaster().getId() : -1);
            map.put("packing", wn.getPackingMaster() != null ? wn.getPackingMaster().getId() : -1);

            map.put("user", wn.getUser().getUserName());
            map.put("status", wn.getStatus());
            Map total = weightNoteDao.getWnrTotal(wn.getId());
            map.put("total", new JSONObject(total));
            map.put("del", del_right ? 1 : 0);
            map.put("sav", save_right ? 1 : 0);

            ArrayList<WeightNoteReceipt> wnrs = weightNoteReceiptService.getWNRByWN(wn.getId());
            int count = 1;

            ArrayList<Map> wnr_map_list = new ArrayList<>();
            for (WeightNoteReceipt wnr : wnrs) {
                Map wnr_map = new HashMap();
                wnr_map.put("no", count);
                wnr_map.put("id", wnr.getId());
                wnr_map.put("type", wn.getType());
                wnr_map.put("ref_number", wnr.getRefNumber());
                String status = "";

                if ((wn.getType().equals("IM") || wn.getType().equals("XP"))) {
                    if (wnr.getStatus() == 3) {
                        if (weightNoteReceiptService.checkWNRWeightedOut(wnr.getId())) {
                            status = "swo"; // status weighted out
                        } else {
                            status = "sa"; // status allocated
                        }
                    } else {
                        status = "sis"; // status in store
                    }
                }
                wnr_map.put("status", status);

                wnr_map.put("date", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
                wnr_map.put("packing", wnr.getPackingMaster().getId());
                wnr_map.put("bags", wnr.getNoOfBags());
                wnr_map.put("kgPerBag", wnr.getPackingMaster().getWeight());
                wnr_map.put("gross", wnr.getGrossWeight());
                wnr_map.put("tare", wnr.getTareWeight());
                wnr_map.put("net", wnr.getGrossWeight() - wnr.getTareWeight());
                wnr_map.put("options", (wnr.getOptions() != null) ? wnr.getOptions() : "");
                wnr_map.put("change", save_right ? "" : "readonly");
                wnr_map.put("del", del_right ? 1 : 0);
                wnr_map.put("sav", save_right ? 1 : 0);
                wnr_map_list.add(wnr_map);
                count++;
            }
            map.put("count", count);
            JSONArray json_array = new JSONArray(wnr_map_list);
            map.put("wnrs", json_array);
            JSONObject json = new JSONObject(map);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Map convertWnrToMap(WeightNoteReceipt wnr, boolean del_right, boolean save_right) {
        WeightNote wn = wnr.getWeightNote();
        Map wnr_map = new HashMap();
        wnr_map.put("id", wnr.getId());
        wnr_map.put("type", wn.getType());
        wnr_map.put("ref_number", wnr.getRefNumber());
        wnr_map.put("date", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
        wnr_map.put("packing", wnr.getPackingMaster().getId());
        wnr_map.put("bags", wnr.getNoOfBags());
        wnr_map.put("kgPerBag", wnr.getPackingMaster().getWeight());
        wnr_map.put("gross", wnr.getGrossWeight());
        wnr_map.put("tare", wnr.getTareWeight());
        wnr_map.put("net", wnr.getGrossWeight() - wnr.getTareWeight());
        wnr_map.put("pallet", wnr.getPalletName());
        wnr_map.put("pallet_weight", wnr.getPalletWeight());
        wnr_map.put("options", (wnr.getOptions() != null) ? wnr.getOptions() : "");
        wnr_map.put("change", save_right ? "" : "readonly");
        wnr_map.put("del", del_right ? 1 : 0);
        wnr_map.put("sav", save_right ? 1 : 0);
        return wnr_map;
    }

    public ArrayList<StockReportObj> getInprocessPiReport(int pi_id) {
        return weightNoteDao.getInprocessPiReport(pi_id);
    }

    public ArrayList<StockReportObj> getTodayReportObject(int grade_id, int map_id, int client_id) {
        return weightNoteDao.getTodayReportObject(grade_id, map_id, client_id);
    }

    public ArrayList<StockReportObj> getImportReportObject(int supplier_id, String from,
            String to, int grade_id) {
        return weightNoteDao.getImportReportObject(supplier_id, from, to, grade_id);
    }

    public ArrayList<StockReportObj> getExportReportObject(int client_id, String from,
            String to, int grade_id) {
        return weightNoteDao.getExportReportObject(client_id, from, to, grade_id);
    }

    public ArrayList<StockReportObj> getWnExprocessPiReport(int inst_id, int grade_id) {
        return weightNoteDao.getWnExprocessPiReport(inst_id, grade_id);
    }

    public ArrayList<StockReportInprocess> getReportObject_Inprocess(String date) {
        return weightNoteDao.getReportObject_Inprocess(date);
    }

    public ArrayList<StockReportInprocess> getTodayReportObject_Inprocess() {
        return weightNoteDao.getTodayReportObject_Inprocess();
    }

    public CompanyMaster getClientFromInst(int instId, String type) {
        return weightNoteDao.getClientFromInst(instId, type);
    }

    public CompanyMaster getPledgeFromInst(int instId) {
        return weightNoteDao.getPledgeFromInst(instId);
    }
}
