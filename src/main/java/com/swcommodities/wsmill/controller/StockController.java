/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.CellTypeService;
import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.MovementService;
import com.swcommodities.wsmill.bo.StockHistoricalService;
import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.bo.WarehouseCellService;
import com.swcommodities.wsmill.bo.WarehouseMapService;
import com.swcommodities.wsmill.bo.WarehouseService;
import com.swcommodities.wsmill.bo.WeightNoteReceiptService;
import com.swcommodities.wsmill.exels.StockReportExcel;
import com.swcommodities.wsmill.hibernate.dao.CommonDao;
import com.swcommodities.wsmill.hibernate.dto.CellType;
import com.swcommodities.wsmill.hibernate.dto.ClientUser;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.view.ClientUserView;
import com.swcommodities.wsmill.hibernate.dto.view.MovementView;
import com.swcommodities.wsmill.hibernate.dto.view.WnrAllocationView;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

//import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;

/**
 *
 * @author kiendn
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class StockController {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MovementService movementService;
    @Autowired
    private CellTypeService cellTypeService;
    @Autowired
    private WarehouseMapService warehouseMapService;
    @Autowired
    private WarehouseCellService warehouseCellService;
    @Autowired
    private UserService userService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;
    @Autowired
    private WeightNoteReceiptService weightNoteReceiptService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private StockHistoricalService stockHistoricalService;

    @RequestMapping(value = "bind_map_data.htm", method = RequestMethod.POST)
    public @ResponseBody
    String bind_map_data() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);

        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(supporter.getIntValue("ss_id"));
        //WarehouseMap wm = warehouseMapService.getWarehouseMapById(supporter.getIntValue("ss_id"));
        int cli_ple = supporter.getIntValue("client");
        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), cli_ple, -1, -1);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_cell.html"));
        return new GenTemplate(request).generateWarehouseCells(tpl, warehousecells, total);
    }

    @RequestMapping(value = "bind_map_data_detail.htm", method = RequestMethod.POST)
    public @ResponseBody
    String bind_map_data_detail() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);

        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(supporter.getIntValue("ss_id"));
        //WarehouseMap wm = warehouseMapService.getWarehouseMapById(supporter.getIntValue("ss_id"));
        int clientid = supporter.getIntValue("client");
        int pledgeid = supporter.getIntValue("pledge");
        int gradeid = supporter.getIntValue("grade");

        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), clientid, pledgeid, gradeid);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_cell.html"));
        return new GenTemplate(request).generateWarehouseCells(tpl, warehousecells, total);
    }

    @RequestMapping(value = "view_area_weight_note_receipts", method = RequestMethod.POST)
    public @ResponseBody
    String generate_area_weight_notes() throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int cli_ple = support.getIntValue("client");
        ArrayList<Map> weightnotereceipts = weightNoteReceiptService.getWeightNoteReceiptsByArea(support.getIntValue("id_cell_view"), cli_ple);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipts_area.html"));
        if (weightnotereceipts.isEmpty()) {
            return "Nothing here!";
        }
        return new GenTemplate(request).generateWeightNoteReceiptsArea(tpl, weightnotereceipts);
    }

    @RequestMapping(value = "edit_warehouse_map", method = RequestMethod.POST)
    public @ResponseBody
    String edit_warehouse_map() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(supporter.getIntValue("ss_id"));
        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), -1, -1, -1);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_edit.html"));
        return new GenTemplate(request).generateWarehouseCellsForEdit(tpl, warehousecells, total);
    }

    @RequestMapping(value = "get_list_cell_type", method = RequestMethod.POST)
    public @ResponseBody
    String getListCellType() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        String current_cell_type = supporter.getStringRequest("current");
        ArrayList<CellType> cellTypes = cellTypeService.getListCellType();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/listCellType.html"));
        return new GenTemplate(request).generateListCellType(tpl, cellTypes, current_cell_type);
    }

    @RequestMapping(value = "open_area_selection", method = RequestMethod.POST)
    public @ResponseBody
    String open_area_selection() throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/area_selection.html"));
        return new GenTemplate(request).generateAreaSelectionDialog(tpl);
    }

    @RequestMapping(value = "apply_cell_changes", method = RequestMethod.POST)
    public @ResponseBody
    String apply_cell_changes() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int map_id = supporter.getIntValue("ss_id");
        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(map_id);
        WarehouseMap wm = warehouseMapService.getWarehouseMapById(map_id);
        User user = (User) request.getSession().getAttribute("user");
        wm.setUser(user);
        String[][] main_arr = {
            {"type", "delete"},
            {"user", user.getUserName()},
            {"element", ""},
            {"date", Common.getDateFromDatabase(new Date(), Common.date_format_a)}
        };
        if (wm.getLog() == null) {
            wm.setLog("");
        }
        String log = wm.getLog() + "," + Common.convertToJson((Object) main_arr);
        wm.setLog(log);
        warehouseMapService.updateWarehouseMap(wm);

        JSONObject cellsJ = new JSONObject(supporter.getStringRequest("strJSON"));

        for (WarehouseCell cell : warehousecells) {
            for (int i = 0; i < cellsJ.length(); i++) {
                if (cellsJ.has(cell.getId().toString())) {
                    cell.setCellType(cellTypeService.getCellTypeByName(cellsJ.getString(cell.getId().toString())));
                }
            }
        }

        for (int i = 0; i < warehousecells.size(); i++) {
            warehouseCellService.updateWarehouseCell(warehousecells.get(i));
        }

        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), -1, -1, -1);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_cell.html"));
        return new GenTemplate(request).generateWarehouseCells(tpl, warehousecells, total);
    }

    @RequestMapping(value = "add_new_map.htm", method = RequestMethod.POST)
    public @ResponseBody
    String add_new_map() throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/new_warehousemap_input.html"));
        return new GenTemplate(request).generateCreateWarehouseDialog(tpl);
    }

    @RequestMapping(value = "create_map_and_cell.htm", method = RequestMethod.POST)
    public @ResponseBody
    String create_map_and_cell() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);

        int cols = supporter.getIntValue("col"); //width
        int rows = supporter.getIntValue("row"); //height
        String wallpoint_ver = supporter.getStringRequest("wall_ver");
        String wallpoint_hor = supporter.getStringRequest("wall_hor");
        String name = supporter.getStringRequest("name");

        WarehouseMap wm = new WarehouseMap(null, warehouseService.getWarehouseById(1), name, cols, rows, wallpoint_hor, wallpoint_ver, (byte) 1, "", null);
        wm.setUser((User) request.getSession().getAttribute("user"));
        int warehousemap_id = warehouseMapService.createNewMap(wm);
        //Prepare data
        ArrayList<WarehouseCell> cells = new ArrayList<>();
        String[] wpv = wallpoint_ver.split(",");
        String[] wph = wallpoint_hor.split(",");

        for (int x = 1; x <= cols; x++) {
            for (int y = 1; y <= rows; y++) {
                WarehouseCell cell = new WarehouseCell();
                cell.setWarehouseMap(warehouseMapService.getWarehouseMapById(warehousemap_id));
                cell.setOrdinateX(x);
                cell.setOrdinateY(y);
                cell.setCellType(cellTypeService.getCellTypeById(1));
                cells.add(cell);
            }
        }
        for (int i = 0; i < cells.size(); i++) {
            warehouseCellService.updateWarehouseCell(cells.get(i));
        }

        cells = warehouseCellService.getListWarehouseCellById(warehousemap_id);
        for (int a = 0; a < wpv.length; a++) {
            for (WarehouseCell cell : cells) {
                if (cell.getOrdinateX().toString().equals(wpv[a])) {
                    cell.setCellType(cellTypeService.getCellTypeById(8));
                }
            }
        }

        for (int a = 0; a < wph.length; a++) {
            for (WarehouseCell cell : cells) {
                if (cell.getOrdinateY().toString().equals(wph[a])) {
                    cell.setCellType(cellTypeService.getCellTypeById(7));
                }
            }
        }

        for (WarehouseCell cell : cells) {
            for (int a = 0; a < wph.length; a++) {
                for (int b = 0; b < wpv.length; b++) {
                    if (cell.getOrdinateX().toString().equals(wpv[b]) && cell.getOrdinateY().toString().equals(wph[a])) {
                        cell.setCellType(cellTypeService.getCellTypeById(6));
                    }
                }
            }
        }

        for (int i = 0; i < cells.size(); i++) {
            warehouseCellService.updateWarehouseCell(cells.get(i));
        }

        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(warehousemap_id);
        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), -1, -1, -1);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_cell.html"));
        return new GenTemplate(request).generateWarehouseCells(tpl, warehousecells, total);
    }

    @RequestMapping(value = "delete_warehouse_map.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_warehouse_map(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int map_id = support.getIntValue("map_id");
        boolean doDelete = warehouseMapService.checkDeletable(map_id);
        if (!doDelete) {
            String message = "2";
            response.getWriter().print(message);
        } else {
            WarehouseMap wm = warehouseMapService.getWarehouseMapById(map_id);
            wm.setStatus((byte) 0);
            warehouseMapService.updateWarehouseMap(wm);
            String message = "1";
            response.getWriter().print(message);
        }
    }

    @RequestMapping("wnr_list_source.htm")
    public @ResponseBody
    void processing_list_source(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "ref_number", "net_weight", "grade", "pledge", "client", ""};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 20;
        int start = 0;
        int col = 0;
        int grade_id = 0;
        int cli_id = 0;
        int ple_id = 0;
        int area_id = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sCli = request.getParameter("cli");
        String sPle = request.getParameter("ple");
        String sArea = request.getParameter("area");
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol + "");
            if (col < 0 || col > 25) {
                col = 0;
            }
        }
        if (sdir != null) {
            if (sdir.equals("asc")) {
                dir = "desc";
            } else {
                dir = "asc";
            }
        }
        if (sGrade != null) {
            grade_id = Integer.parseInt(sGrade);
        }
        if (sCli != null) {
            cli_id = Integer.parseInt(sCli);
        }
        if (sPle != null) {
            ple_id = Integer.parseInt(sPle);
        }
        if (sArea != null) {
            area_id = Integer.parseInt(sArea);
        }

        String colName = aColumns[col];
        long total = warehouseMapService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }
        ArrayList<Map> searchGlobe = warehouseMapService.searchWnrsByAreaId(searchTerm, dir, start, amount, colName, grade_id, cli_id, ple_id, area_id);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (Map wnr : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                JSONObject jb = new JSONObject(wnr);
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + wnr.get("wnr_id"));
                ja.put((i++) + "", count);
                ja.put((i++) + "", wnr.get("ref_number"));
                ja.put((i++) + "", wnr.get("net_weight"));
                ja.put((i++) + "", wnr.get("grade"));
                ja.put((i++) + "", wnr.get("pledge"));
                ja.put((i++) + "", wnr.get("client"));
                ja.put((i++) + "", "<input info='" + jb.toString() + "' type=\"button\" class=\"button_select\" id=\"selectwnr_" + wnr.get("wnr_id") + "\" value=\"Select\"/>");
                array.put(ja);
                count++;
            }
        }

//        Map mtotal = processingService.countTotals(searchTerm, grade_id, cli_id, stt);
//
//        int i = 0;
//        JSONObject ja = new JSONObject();
//        //ja.put(m);
//        ja.put("DT_RowClass", "footer");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", (mtotal.get("allocated") != null ? mtotal.get("allocated") : "0"));
//        ja.put((i++) + "", (mtotal.get("inprocess") != null ? mtotal.get("inprocess") : "0"));
//        ja.put((i++) + "", (mtotal.get("exprocess") != null ? mtotal.get("exprocess") : "0"));
//        ja.put((i++) + "", (mtotal.get("pending") != null ? mtotal.get("pending") : "0"));
//        ja.put((i++) + "", "");
//        ja.put((i++) + "", "");
//        array.put(ja);
        totalAfterFilter = warehouseMapService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "get_map_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_map_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        ArrayList<WarehouseMap> list = warehouseMapService.getWarehouseMapListByWarehouseId(1);
        response.getWriter().print(new GenTemplate(request).generateWarehouseMapFilterList(tpl, list, "map"));
    }
    
    //loadFilterStock
    @RequestMapping(value = "loadFilterStock.htm", method = RequestMethod.POST)
    public @ResponseBody
    void loadFilterStock(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        String type = new ServletSupporter(request).getStringRequest("type");
        if (user != null){
            ClientUser clientUser = userService.checkUserClient(user.getId());
            switch(type){
                case "client":
                    response.getWriter().print(stockHistoricalService.getClientFilter(clientUser).toString());break;
                case "pledge":
                    response.getWriter().print(stockHistoricalService.getPledgeFilter(clientUser).toString());break;
                case "grade":
                    response.getWriter().print(stockHistoricalService.getGradeFilter(clientUser).toString());break;
                case "warehouse":
                    response.getWriter().print(stockHistoricalService.getWarehouseFilter().toString());break;
            }
        }
    }

	@RequestMapping(value = "loadFilterStockList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void loadFilterStockList(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        ArrayList<HashMap> pledges = new ArrayList<>();
        ArrayList<HashMap> clients = new ArrayList<>();
        ArrayList<HashMap> grades = new ArrayList<>();
        ArrayList<HashMap> warehouses = warehouseMapService.getWarehouseMaps();
        if (user != null) {
            ClientUser clientUser = userService.checkUserClient(user.getId());
            if (clientUser != null) { //is client -- > implements later
                CompanyMaster comp = clientUser.getCompanyMaster();
                int company_id = comp.getId();
                HashMap company = new HashMap();
                company.put("id", company_id);
                company.put("name", comp.getName());
                company.put("class", "chosen");
                if (companyService.checkRole(company_id, 2)) { //check is Client?
                    try {

                        clients.add(0, company);
                        pledges = companyService.getCompaniesOf("Client", company_id);
                        grades = companyService.getGradesOfCompany("Client", company_id);

                        HashMap map = new HashMap();
                        map.put("id", -1);
                        map.put("name", "All");
                        map.put("class", "chosen");

                        pledges.add(0, map);
                        grades.add(0, map);
                        warehouses.add(0, map);

                        HashMap none = new HashMap();

                        none.put("id", 0);
                        none.put("name", "None");
                        none.put("class", "");

                        clients.add(1, none);
                        pledges.add(1, none);

                        JSONArray pledges_arr = new JSONArray(pledges);
                        JSONArray clients_arr = new JSONArray(clients);
                        JSONArray grades_arr = new JSONArray(grades);
                        JSONArray warehouses_arr = new JSONArray(warehouses);

                        JSONObject json = new JSONObject();
                        json.put("pledge", pledges_arr);
                        json.put("client", clients_arr);
                        json.put("grade", grades_arr);
                        json.put("warehouse", warehouses_arr);

                        response.getWriter().print(json.toString());

                    } catch (JSONException ex) {
                        Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                        response.getWriter().print("");
                    }

                } else if (companyService.checkRole(company_id, 7)) { //check is Pledge
                    try {

                        pledges.add(0, company);
                        clients = companyService.getCompaniesOf("Pledge", company_id);
                        grades = companyService.getGradesOfCompany("Pledge", company_id);

                        HashMap map = new HashMap();
                        map.put("id", -1);
                        map.put("name", "All");
                        map.put("class", "chosen");

                        clients.add(0, map);
                        grades.add(0, map);
                        warehouses.add(0, map);

                        HashMap none = new HashMap();

                        none.put("id", 0);
                        none.put("name", "None");
                        none.put("class", "");

                        clients.add(1, none);
                        pledges.add(1, none);

                        JSONArray pledges_arr = new JSONArray(pledges);
                        JSONArray clients_arr = new JSONArray(clients);
                        JSONArray grades_arr = new JSONArray(grades);
                        JSONArray warehouses_arr = new JSONArray(warehouses);

                        JSONObject json = new JSONObject();
                        json.put("pledge", pledges_arr);
                        json.put("client", clients_arr);
                        json.put("grade", grades_arr);
                        json.put("warehouse", warehouses_arr);

                        response.getWriter().print(json.toString());

                    } catch (JSONException ex) {
                        Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                        response.getWriter().print("");
                    }

                }
            } else { //not client --> get all
                try {

                    pledges = companyService.getCompaniesInStockByType("Pledge");
                    clients = companyService.getCompaniesInStockByType("Client");
                    grades = gradeService.getGradeInStock(-1, -1, -1);

                    HashMap map = new HashMap();
                    map.put("id", -1);
                    map.put("name", "All");
                    map.put("class", "chosen");

                    pledges.add(0, map);
                    clients.add(0, map);
                    grades.add(0, map);
                    warehouses.add(0, map);

                    HashMap none = new HashMap();

                    none.put("id", 0);
                    none.put("name", "None");
                    none.put("class", "");

                    clients.add(1, none);
                    pledges.add(1, none);

                    JSONArray pledges_arr = new JSONArray(pledges);
                    JSONArray clients_arr = new JSONArray(clients);
                    JSONArray grades_arr = new JSONArray(grades);
                    JSONArray warehouses_arr = new JSONArray(warehouses);

                    JSONObject json = new JSONObject();
                    json.put("pledge", pledges_arr);
                    json.put("client", clients_arr);
                    json.put("grade", grades_arr);
                    json.put("warehouse", warehouses_arr);

                    response.getWriter().print(json.toString());

                } catch (JSONException ex) {
                    Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                    response.getWriter().print("");
                }

            }
        }
    }

    @RequestMapping(value = "searchStock.htm", method = RequestMethod.POST)
    public @ResponseBody
    void searchStock(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        ClientUserView clientUser = userService.getUserClient(user.getId());
        boolean flag = clientUser != null;
        ServletSupporter support = new ServletSupporter(request);
        int client = support.getIntValue("client");
        int pledge = support.getIntValue("pledge");
        int grade = support.getIntValue("grade");
        int warehouse = support.getIntValue("warehouse");
        long dateCode = support.getLongValue("date");
        if (dateCode > 0) {
            response.getWriter().print(stockHistoricalService.searchStockReport(dateCode, grade, client, pledge, warehouse,flag).toString());
        } else {
            response.getWriter().print((new JSONObject(stockHistoricalService.getStockReport(grade, client, pledge, warehouse,flag))).toString());
        }
    }

    @RequestMapping(value = "getUpdateStockPermission.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getUpdateStockPermission(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            HashMap map = stockHistoricalService.getUserPermission(user.getId(), context);
            JSONObject json = new JSONObject(map);
            response.getWriter().print(json.toString());
        }
    }
    
    @RequestMapping(value = "getUpdateStockPermission_Detail.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getUpdateStockPermission_Detail(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            HashMap map = stockHistoricalService.getUserPermission_Detail(user.getId(), context);
            JSONObject json = new JSONObject(map);
            response.getWriter().print(json.toString());
        }
    }

    @RequestMapping(value = "searchStock_report.htm", method = RequestMethod.POST)
    public @ResponseBody
    void searchStock_report(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        User user = (User) request.getSession().getAttribute("user");
        ClientUserView clientUser = userService.getUserClient(user.getId());
        boolean flag = clientUser != null;
        int client = support.getIntValue("client");
        int pledge = support.getIntValue("pledge");
        int grade = support.getIntValue("grade");
        int warehouse = support.getIntValue("warehouse");
        long dateCode = support.getLongValue("date");
        String webPath = context.getContextPath() + "/";
        Map report;
        StockReportExcel sre;
        if (dateCode > 0) {
            report = stockHistoricalService.searchStockReportHash(dateCode, grade, client, pledge, warehouse, flag);
        } else {
            report = stockHistoricalService.getStockReport_hash(grade, client, pledge, warehouse, flag);
        }
        if (report != null) {
            sre = new StockReportExcel(context.getRealPath(""), report);
            response.getWriter().print(sre.generateStockReport_1(webPath));
        }
    }

    @RequestMapping(value = "reloadGradeFilterList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void reloadGradeFilterList(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);

        ArrayList<HashMap> grades = new ArrayList<>();

        int client_id = support.getIntValue("client_id");
        int map_id = support.getIntValue("map_id");

        try {

            grades = gradeService.getGradeInStock(map_id, client_id, -1);

            HashMap map = new HashMap();
            map.put("id", -1);
            map.put("name", "All");
            map.put("class", "chosen");

            grades.add(0, map);

            JSONArray grades_arr = new JSONArray(grades);

            JSONObject json = new JSONObject();
            json.put("grade", grades_arr);

            response.getWriter().print(json.toString());

        } catch (JSONException ex) {
            Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print("");
        }
    }

    @RequestMapping(value = "reloadGradeAndClientFilterList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void reloadGradeAndClientFilterList(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);

        User user = (User) request.getSession().getAttribute("user");

        ArrayList<HashMap> grades = new ArrayList<>();
        ArrayList<HashMap> clients = new ArrayList<>();

        int map_id = support.getIntValue("map_id");

        if (user != null) {
            ClientUser clientUser = userService.checkUserClient(user.getId());
            if (clientUser != null) { //is client -- > implements later
                int company_id = clientUser.getCompanyMaster().getId();
                HashMap company = new HashMap();
                company.put("id", company_id);
                company.put("name", clientUser.getCompanyMaster().getName());
                company.put("class", "chosen");
                if (companyService.checkRole(company_id, 2)) {
                    try {

                        clients.add(company);
                        grades = gradeService.getGradeInStock(map_id, company_id, -1);

                        HashMap map = new HashMap();
                        map.put("id", -1);
                        map.put("name", "All");
                        map.put("class", "chosen");

                        grades.add(0, map);

                        JSONArray grades_arr = new JSONArray(grades);
                        JSONArray clients_arr = new JSONArray(clients);

                        JSONObject json = new JSONObject();
                        json.put("grade", grades_arr);
                        json.put("client", clients_arr);

                        response.getWriter().print(json.toString());

                    } catch (JSONException ex) {
                        Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                        response.getWriter().print("");
                    }
                } else if (companyService.checkRole(company_id, 7)) {
                    try {

                        clients = companyService.getCompaniesOf("Pledge", company_id);
                        grades = gradeService.getGradeInStock(map_id, -1, company_id);

                        HashMap map = new HashMap();
                        map.put("id", -1);
                        map.put("name", "All");
                        map.put("class", "chosen");

                        clients.add(0, map);
                        grades.add(0, map);

                        JSONArray grades_arr = new JSONArray(grades);
                        JSONArray clients_arr = new JSONArray(clients);

                        JSONObject json = new JSONObject();
                        json.put("grade", grades_arr);
                        json.put("client", clients_arr);

                        response.getWriter().print(json.toString());

                    } catch (JSONException ex) {
                        Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                        response.getWriter().print("");
                    }
                }

            } else {
                try {

                    clients = companyService.getCompaniesInStockByMapAndType("Client", map_id);
                    grades = gradeService.getGradeInStock(map_id, -1, -1);

                    HashMap map = new HashMap();
                    map.put("id", -1);
                    map.put("name", "All");
                    map.put("class", "chosen");

                    clients.add(0, map);
                    grades.add(0, map);

                    JSONArray grades_arr = new JSONArray(grades);
                    JSONArray clients_arr = new JSONArray(clients);

                    JSONObject json = new JSONObject();
                    json.put("grade", grades_arr);
                    json.put("client", clients_arr);

                    response.getWriter().print(json.toString());

                } catch (JSONException ex) {
                    Logger.getLogger(StockHistoricalService.class.getName()).log(Level.SEVERE, null, ex);
                    response.getWriter().print("");
                }
            }
        }
    }

    @RequestMapping(value = "getClientList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getClientList(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> comp = new ArrayList<>();
        HashMap blank = new HashMap();
        blank.put("id", 0);
        blank.put("name", "None");
        comp.add(blank);
        comp.addAll(1, companyService.getCompaniesByTypeMaster(companyTypeMasterRepository.findByLocalName("Client").getId()));
        JSONArray jArr = new JSONArray(comp);

        JSONObject json = new JSONObject();
        json.put("select", jArr);
        response.getWriter().print(json.toString());
    }
    
	@RequestMapping(value = "getClientListInSystem.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getClientListInSystem(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> comp = companyService.getClientListInSystem();
        JSONArray jArr = new JSONArray(comp);

        JSONObject json = new JSONObject();
        json.put("select", jArr);
        response.getWriter().print(json.toString());
    }
    
    @RequestMapping(value = "getPledgeList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getPledgeList(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> comp = new ArrayList<>();
        HashMap blank = new HashMap();
        blank.put("id", 0);
        blank.put("name", "None");
        comp.add(blank);
        comp.addAll(1, companyService.getCompaniesByTypeMaster(companyTypeMasterRepository.findByLocalName("Bank").getId()));
        JSONArray jArr = new JSONArray(comp);
        JSONObject json = new JSONObject();
        json.put("select", jArr);
        response.getWriter().print(json.toString());
    }
    //updateWnrInfo

    @RequestMapping(value = "updateWeightNoteInfo.htm", method = RequestMethod.POST)
    public @ResponseBody
    void updateWeightNoteInfo(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ServletSupporter support = new ServletSupporter(request);
            int client = support.getIntValue("client");
            int pledge = support.getIntValue("pledge");
            int area = support.getIntValue("area");
            String wns = support.getStringRequest("wns");

            response.getWriter().print(weightNoteReceiptService.updateWnrInfo(client, pledge, area, wns, user.getUserName(), false)); //true means update by id instead of wn_id and otherwise
        }
    }

    @RequestMapping(value = "updateWeightNoteReceiptInfo.htm", method = RequestMethod.POST)
    public @ResponseBody
    void updateWeightNoteReceiptInfo(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ServletSupporter support = new ServletSupporter(request);
            boolean isReweightAll = support.getBooleanValue("isReweightAll");
            String r_wnrs = support.getStringRequest("r_wnrs");
            int client = support.getIntValue("client");
            int pledge = support.getIntValue("pledge");
            int area = support.getIntValue("area");
            String code = support.getStringRequest("code");
            String wnrs = support.getStringRequest("wnrs");
            boolean updatewnr = weightNoteReceiptService.updateWnrInfo(client, pledge, area, wnrs, user.getUserName(), true);

            if (updatewnr) {
                if (isReweightAll) {

                    MovementView mv = new MovementView(movementService.getNewMovementRef(), client, (pledge == -1 || pledge == 0) ? null : pledge, (area == 0) ? null : area, new Date(), user.getId(), Constants.PENDING, code);
                    int mv_id = movementService.saveMovementView(mv);
                    String[] arr_wnrs = wnrs.split(",");
                    for (int i = 0; i < arr_wnrs.length; i++) {
                        int wnr_id = Integer.parseInt(arr_wnrs[i]);

                        WeightNoteReceipt wnr = weightNoteReceiptService.getWNRById(wnr_id);

                        if (!wnr.getStatus().equals(Constants.ALLOCATED)) {
                            wnr.setStatus(Constants.ALLOCATED);
                            weightNoteReceiptService.updateWNR(wnr);
                            int wn_id = wnr.getWeightNote().getId();
                            WnrAllocationView wnav = new WnrAllocationView(user.getId(), wnr_id, mv_id, Constants.MOVEMENT, wn_id, new Date(), Constants.PENDING);
                            weightNoteReceiptService.saveWnaView(wnav);
                        }
                    }
                } else {
                    if (!r_wnrs.equals("")) {
                        MovementView mv = new MovementView(movementService.getNewMovementRef(), client, (pledge == -1 || pledge == 0) ? null : pledge, (area == 0) ? null : area, new Date(), user.getId(), Constants.PENDING, code);
                        int mv_id = movementService.saveMovementView(mv);
                        String[] arr_wnrs = r_wnrs.split(",");
                        for (int i = 0; i < arr_wnrs.length; i++) {
                            int wnr_id = Integer.parseInt(arr_wnrs[i]);

                            WeightNoteReceipt wnr = weightNoteReceiptService.getWNRById(wnr_id);
                            if (!wnr.getStatus().equals(Constants.ALLOCATED)) {
                                wnr.setStatus(Constants.ALLOCATED);
                                weightNoteReceiptService.updateWNR(wnr);
                                int wn_id = wnr.getWeightNote().getId();
                                WnrAllocationView wnav = new WnrAllocationView(user.getId(), wnr_id, mv_id, Constants.MOVEMENT, wn_id, new Date(), Constants.PENDING);
                                weightNoteReceiptService.saveWnaView(wnav);
                            }

                        }
                    }
                }
            }
            response.getWriter().print(updatewnr);
        }
    }

    @RequestMapping(value = "check_wnr_movable.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wnr_movable(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wnr_id = support.getIntValue("wnr_id");
        if (weightNoteReceiptService.getWNRById(wnr_id).getStatus().equals(Constants.ALLOCATED)) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }
    }
}
