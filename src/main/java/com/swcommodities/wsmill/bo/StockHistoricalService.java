/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.AuthorizationDao;
import com.swcommodities.wsmill.hibernate.dao.CommonDao;
import com.swcommodities.wsmill.hibernate.dao.GradeDao;
import com.swcommodities.wsmill.hibernate.dao.ProcessingDao;
import com.swcommodities.wsmill.hibernate.dao.StockHistoricalDao;
import com.swcommodities.wsmill.hibernate.dto.ClientUser;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.StockHistories;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author duhc
 */
@Transactional(propagation = Propagation.REQUIRED)
public class StockHistoricalService {

    private StockHistoricalDao stockHistoricalDao;
    private ProcessingDao processingDao;
    private CommonDao commonDao;
    private GradeDao gradeDao;
    private AuthorizationDao authorizationDao;
    private CompanyService companyService;
    private GradeService gradeService;
    private WarehouseMapService warehouseMapService;

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public void setWarehouseMapService(WarehouseMapService warehouseMapService) {
        this.warehouseMapService = warehouseMapService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setAuthorizationDao(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    public void setGradeDao(GradeDao gradeDao) {
        this.gradeDao = gradeDao;
    }

    public void setStockHistoricalDao(StockHistoricalDao stockHistoricalDao) {
        this.stockHistoricalDao = stockHistoricalDao;
    }

    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public void setProcessingDao(ProcessingDao processingDao) {
        this.processingDao = processingDao;
    }

    @Transactional
    public void saveStockHistoricalReport() {
        stockHistoricalDao.saveStockHistoricalReport();
    }

    public HashMap searchStockReportHash(long dateCode, int grade_id, int client_id, int pledge_id, int warehouse_id, boolean flag) {
        return Common.toHashMap(searchStockReport(dateCode, grade_id, client_id, pledge_id, warehouse_id, flag));
    }

    public JSONObject searchStockReport(long dateCode, int grade_id, int client_id, int pledge_id, int warehouse_id, boolean flag) {
        StockHistories stock = stockHistoricalDao.getHistoryByCode(dateCode);
        if (stock != null) {
            try {
                JSONObject json = new JSONObject(stock.getContent());
                if (!flag) {
                    JSONArray pis = json.getJSONArray("pis");
                    if (client_id > 0) {
                        ArrayList<JSONObject> pi_list = new ArrayList<>();
                        for (int i = 0; i < pis.length(); i++) {
                            JSONObject pi = pis.getJSONObject(i);
                            if (pi.getInt("client_id") == client_id) {
                                pi_list.add(pi);
                            }
                        }
                        json.put("pis", new JSONArray(pi_list));
                        json.put("client", false);
                    }
                } else {
                    json.put("pis", "");
                    json.put("client", true);
                }
                JSONArray storage = json.getJSONArray("storage");
                ArrayList<JSONObject> grade_list = new ArrayList<>();
                if (grade_id > 0) {
                    for (int i = 0; i < storage.length(); i++) {
                        JSONObject grade = storage.getJSONObject(i);
                        if (grade.getInt("id") == grade_id) {
                            ArrayList<JSONObject> wn_list = new ArrayList<>();
                            JSONArray wns = grade.getJSONArray("wns");
                            for (int j = 0; j < wns.length(); j++) {
                                boolean choose = true;
                                JSONObject wn = wns.getJSONObject(j);
                                int client = wn.getInt("client_id");
                                int pledge = wn.getInt("pledge_id");
                                int warehouse = wn.getInt("warehouse_id");
                                if (client_id > 0 && client != client_id) {
                                    choose = false;
                                } else if (client_id == 0 && !wn.getString("client").equals("")) {
                                    choose = false;
                                }
                                if (pledge_id > 0 && pledge != pledge_id) {
                                    choose = false;
                                } else if (pledge_id == 0 && !wn.getString("pledge").equals("")) {
                                    choose = false;
                                } else if (pledge_id == -2 && wn.getString("pledge").equals("")) {
                                    choose = false;
                                }
                                if (warehouse_id > 0 && warehouse != warehouse_id) {
                                    choose = false;
                                }
                                if (choose) {
                                    wn.put("wn_id", "");
                                    wn_list.add(wn);
                                }
                            }
                            if (!wn_list.isEmpty()) {
                                grade.put("wns", new JSONArray(wn_list));
                                grade_list.add(grade);
                            }
                        }
                    }
                    json.put("storage", new JSONArray(grade_list));
                } else {
                    for (int i = 0; i < storage.length(); i++) {
                        JSONObject grade = storage.getJSONObject(i);
                        ArrayList<JSONObject> wn_list = new ArrayList<>();
                        JSONArray wns = grade.getJSONArray("wns");
                        for (int j = 0; j < wns.length(); j++) {
                            boolean choose = true;
                            JSONObject wn = wns.getJSONObject(j);
                            int client = wn.getInt("client_id");
                            int pledge = wn.getInt("pledge_id");
                            int warehouse = wn.getInt("warehouse_id");
                            if (client_id >= 0 && client != client_id) {
                                choose = false;
                            }
                            if (pledge_id >= 0 && pledge != pledge_id) {
                                choose = false;
                            }
                            if (warehouse_id > 0 && warehouse != warehouse_id) {
                                choose = false;
                            }
                            if (choose) {
                                wn.put("wn_id", "");
                                wn_list.add(wn);
                            }
                        }
                        if (!wn_list.isEmpty()) {
                            grade.put("wns", new JSONArray(wn_list));
                            grade_list.add(grade);
                        }
                    }
                    json.put("storage", new JSONArray(grade_list));
                }
                return json;
            } catch (JSONException ex) {
                Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new JSONObject();
    }

    public HashMap getStockReport(int grade_id, int client_id, int pledge_id, int warehouse_id, boolean flag) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        ArrayList<HashMap<String, Object>> storageList = new ArrayList<>();
        if (grade_id == -1) {
            ArrayList<HashMap> grades = gradeDao.getGradeInStock(-1, -1, -1);

            for (HashMap gr : grades) {
                try {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("id", gr.get("id"));
                    map.put("grade", gr.get("name"));
                    ArrayList<HashMap> wn_list = stockHistoricalDao.getStockInfo(Common.getIntegerValue(gr.get("id")), client_id, pledge_id, warehouse_id);
                    if (!wn_list.isEmpty()) {
                        map.put("wns", wn_list);
                        storageList.add(map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(gr.get("name"));
                }
            }
        } else {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", grade_id);
            map.put("grade", gradeDao.getGradeNameById(grade_id));
            ArrayList<HashMap> wn_list = stockHistoricalDao.getStockInfo(grade_id, client_id, pledge_id, warehouse_id);
            if (!wn_list.isEmpty()) {
                map.put("wns", wn_list);
                storageList.add(map);
            }
        }
        if (!flag) {
            ArrayList<Object[]> objs = processingDao.getPendingPiFromClient(client_id);
            ArrayList<HashMap<String, Object>> piList = new ArrayList<HashMap<String,Object>>();
            if (objs != null && !objs.isEmpty()) {
                for (Object[] obj : objs) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("id", Common.getIntegerValue(obj[0]));
                    map.put("ref", Common.getStringValue(obj[1]));
                    map.put("client_name", Common.getStringValue(obj[2]));
                    map.put("client_id", Common.getIntegerValue(obj[3]));
                    map.put("inprocess", processingDao.getInprocessTonsByPi(Common.getIntegerValue(obj[0])));
                    map.put("exprocess", processingDao.getXprocessFromPi(Common.getIntegerValue(obj[0])));
                    piList.add(map);
                }
            }
            res.put("pis", piList);
            res.put("client", false);
        } else {
            res.put("client", true);
        }
        res.put("storage", storageList);
        return res;
    }

    public Map getStockReport_hash(int grade_id, int client_id, int pledge_id, int warehouse_id, boolean flag) {
        Map res = new HashMap<>();
        try {
            Map json = new HashMap<>();
            ArrayList<HashMap> storageList = new ArrayList<>();
            if (grade_id == -1) {
                ArrayList<HashMap> grades = gradeDao.getGradeInStock();

                for (HashMap gr : grades) {
                    try {
                        HashMap map = new HashMap();
                        map.put("id", gr.get("id"));
                        map.put("grade", gr.get("name"));
                        ArrayList<HashMap> wn_list = stockHistoricalDao.getStockInfo(Common.getIntegerValue(gr.get("id")), client_id, pledge_id, warehouse_id);
                        if (!wn_list.isEmpty()) {
                            map.put("wns", wn_list);
                            storageList.add(map);
                        }
                    } catch (Exception e) {
                        System.out.println(gr.get("name"));
                    }
                }
            } else {
                HashMap map = new HashMap();
                map.put("id", grade_id);
                map.put("grade", gradeDao.getGradeNameById(grade_id));
                ArrayList<HashMap> wn_list = stockHistoricalDao.getStockInfo(grade_id, client_id, pledge_id, warehouse_id);
                if (!wn_list.isEmpty()) {
                    map.put("wns", wn_list);
                    storageList.add(map);
                }
            }

//            storageList = stockHistoricalDao.getStockInfo(grade_id, client_id, pledge_id, warehouse_id);
            if (!flag) {
            	ArrayList<Object[]> objs = processingDao.getPendingPiFromClient(client_id);
                ArrayList<HashMap<String, Object>> piList = new ArrayList<HashMap<String,Object>>();
                if (objs != null && !objs.isEmpty()) {
                    for (Object[] obj : objs) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("id", Common.getIntegerValue(obj[0]));
                        map.put("ref", Common.getStringValue(obj[1]));
                        map.put("client_name", Common.getStringValue(obj[2]));
                        map.put("client_id", Common.getIntegerValue(obj[3]));
                        map.put("inprocess", processingDao.getInprocessTonsByPi(Common.getIntegerValue(obj[0])));
                        map.put("exprocess", processingDao.getXprocessFromPi(Common.getIntegerValue(obj[0])));
                        piList.add(map);
                    }
                }
                res.put("pis", piList);
                res.put("client", true);
            } else {
                json.put("client", false);
            }

            ArrayList<HashMap> storage = new ArrayList<>(storageList);
            json.put("storage", storage);

            res = json;
        } catch (Exception ex) {
            Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public String stockFilterGetAll() {
        try {
            HashMap map = new HashMap();
            map.put("id", -1);
            map.put("name", "All");
            map.put("class", "chosen");

            ArrayList<HashMap> pledges = commonDao.getSelect("CompanyMaster");
            ArrayList<HashMap> clients = pledges;
            ArrayList<HashMap> grades = commonDao.getSelect("GradeMaster");
            ArrayList<HashMap> warehouses = commonDao.getSelect("WarehouseMap");

            pledges.add(0, map);
//            clients.add(0,map);
            grades.add(0, map);
            warehouses.add(0, map);

            JSONArray pledges_arr = new JSONArray(pledges);
            JSONArray clients_arr = new JSONArray(clients);
            JSONArray grades_arr = new JSONArray(grades);
            JSONArray warehouses_arr = new JSONArray(warehouses);

            JSONObject json = new JSONObject();
            json.put("pledge", pledges_arr);
            json.put("client", clients_arr);
            json.put("grade", grades_arr);
            json.put("warehouse", warehouses_arr);

            return json.toString();

        } catch (JSONException ex) {
            Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public StockHistories updateHistory(StockHistories st) {
        return stockHistoricalDao.updateHistory(st);
    }

    public HashMap getUserPermission(int user_id, ServletContext context) {
        HashMap map = new HashMap();
        try {
            int client = Common.getPermissionId(Constants.cma_update_client, context);
            int pledge = Common.getPermissionId(Constants.cma_update_pledge, context);
            int area = Common.getPermissionId(Constants.cma_update_area, context);

            map.put("client", authorizationDao.isAuthorized(user_id, client));
            map.put("pledge", authorizationDao.isAuthorized(user_id, pledge));
            map.put("area", authorizationDao.isAuthorized(user_id, area));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public HashMap getUserPermission_Detail(int user_id, ServletContext context) {
        HashMap map = new HashMap();
        try {
            int client = Common.getPermissionId(Constants.cma_detail_update_client, context);
            int pledge = Common.getPermissionId(Constants.cma_detail_update_pledge, context);
            int area = Common.getPermissionId(Constants.cma_detail_update_area, context);
            int reweight = Common.getPermissionId(Constants.cma_detail_reweight, context);

            map.put("client", authorizationDao.isAuthorized(user_id, client));
            map.put("pledge", authorizationDao.isAuthorized(user_id, pledge));
            map.put("area", authorizationDao.isAuthorized(user_id, area));
            map.put("reweight", authorizationDao.isAuthorized(user_id, reweight));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public JSONArray getGradeFilter(ClientUser clientUser) {
        HashMap map = new HashMap();
        ArrayList<HashMap> grades = new ArrayList<>();
        if (clientUser != null) {
            CompanyMaster comp = clientUser.getCompanyMaster();
            int company_id = comp.getId();
            if (companyService.checkRole(company_id, 2)) {
                grades = companyService.getGradesOfCompany("Client", company_id);
            } else if (companyService.checkRole(company_id, 7)) {
                grades = companyService.getGradesOfCompany("Pledge", company_id);
            }
        } else {
            grades = gradeService.getGradeInStock(-1, -1, -1);
        }
        map.put("id", -1);
        map.put("name", "All");
        map.put("class", "chosen");
        grades.add(0, map);
        return new JSONArray(grades);
    }

    public JSONArray getGradeFilterForClient(Integer client) {
        HashMap allItem = new HashMap();
        allItem.put("id", -1);
        allItem.put("name", "All");
        allItem.put("class", "chosen");

        ArrayList<HashMap> grades = new ArrayList<>();
        grades = companyService.getGradesOfCompany("Client", client);

        grades.add(0, allItem);
        return new JSONArray(grades);
    }

    public JSONArray getWarehouseFilter() {
        HashMap map = new HashMap();
        ArrayList<HashMap> warehouses = warehouseMapService.getWarehouseMaps();
        map.put("id", -1);
        map.put("name", "All");
        map.put("class", "chosen");
        warehouses.add(0, map);
        return new JSONArray(warehouses);
    }

    public JSONArray getClientFilter(ClientUser clientUser) {
        HashMap map = new HashMap();
        map.put("id", -1);
        map.put("name", "All");
        map.put("class", "chosen");

        HashMap none = new HashMap();
        none.put("id", 0);
        none.put("name", "None");
        none.put("class", "");

        ArrayList<HashMap> clients = new ArrayList<>();
        if (clientUser != null) {
            CompanyMaster comp = clientUser.getCompanyMaster();
            int company_id = comp.getId();
            HashMap company = new HashMap();
            company.put("id", company_id);
            company.put("name", comp.getName());
            company.put("class", "chosen");
            if (companyService.checkRole(company_id, 2)) {
                clients.add(0, company);
                clients.add(1, none);
            } else if (companyService.checkRole(company_id, 7)) {
                clients = companyService.getCompaniesOf("Pledge", company_id);
                clients.add(0, map);
                clients.add(1, none);
            }
        } else {
            clients = companyService.getCompaniesInStockByType("Client");
            clients.add(0, map);
            clients.add(1, none);
        }

        return new JSONArray(clients);
    }
    
    public JSONArray getPledgeFilter(ClientUser clientUser) {
        HashMap map = new HashMap();
        map.put("id", -1);
        map.put("name", "All");
        map.put("class", "chosen");
        
        HashMap cma = new HashMap();
        cma.put("id", -2);
        cma.put("name", "-CMA-");
        cma.put("class", "");

        HashMap none = new HashMap();
        none.put("id", 0);
        none.put("name", "None");
        none.put("class", "");

        ArrayList<HashMap> pledges = new ArrayList<>();
        if (clientUser != null) {
            CompanyMaster comp = clientUser.getCompanyMaster();
            int company_id = comp.getId();
            HashMap company = new HashMap();
            company.put("id", company_id);
            company.put("name", comp.getName());
            company.put("class", "chosen");
            if (companyService.checkRole(company_id, 2)) {
                pledges = companyService.getCompaniesOf("Client", company_id);
                pledges.add(0, map);
                pledges.add(1, none);
            } else if (companyService.checkRole(company_id, 7)) {
                pledges.add(0, company);
                pledges.add(1, none);
                
            }
        } else {
            pledges = companyService.getCompaniesInStockByType("Pledge");
            pledges.add(0, map);
            pledges.add(1, none);
            pledges.add(2, cma);
        }

        return new JSONArray(pledges);
    }

    public JSONArray getPledgeFilterForClient(Integer client) {
        HashMap allItem = new HashMap();
        allItem.put("id", -1);
        allItem.put("name", "All");
        allItem.put("class", "chosen");

        HashMap cmaItem = new HashMap();
        cmaItem.put("id", -2);
        cmaItem.put("name", "-CMA-");
        cmaItem.put("class", "");

        HashMap noneItem = new HashMap();
        noneItem.put("id", 0);
        noneItem.put("name", "None");
        noneItem.put("class", "");

        ArrayList<HashMap> pledges = new ArrayList<>();

        pledges = companyService.getCompaniesOf("Client", client);

        pledges.add(0, allItem);
        pledges.add(1, cmaItem);
        pledges.add(2, noneItem);

        return new JSONArray(pledges);
    }
}
