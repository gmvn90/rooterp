/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.CityService;
import com.swcommodities.wsmill.bo.CommonService;
import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.DocumentMasterService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.MovementService;
import com.swcommodities.wsmill.bo.PackingService;
import com.swcommodities.wsmill.bo.PortMasterService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.ShippingLineService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.bo.WarehouseCellService;
import com.swcommodities.wsmill.bo.WarehouseMapService;
import com.swcommodities.wsmill.bo.WarehouseService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.City;
import com.swcommodities.wsmill.hibernate.dto.ClientUser;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.Movement;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingType;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.Warehouse;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private DocumentMasterService documentMasterService;
    @Autowired
    private PortMasterService portMasterService;
    @Autowired
    private DeliveryInsService deliveryInsService;
    @Autowired
    private ShippingLineService shippingLineService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private UserService userService;
    @Autowired
    private WarehouseMapService warehouseMapService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired
    private CityService cityService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private PackingService packingService;
    @Autowired
    private CompanyService companyService;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;
    @Autowired
    private WarehouseCellService warehouseCellService;
    @Autowired
    private MovementService movementService;

    @RequestMapping(value = "selection.htm", method = RequestMethod.POST)
    public @ResponseBody
    void selection(HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        String table = request.getParameter("type");
        JSONObject json = commonService.getSelect(table);
        if (json != null) {
            out.print(json.toString());
        } else {
            out.print("");
        }
    }

    @RequestMapping(value = "selection_grade.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_grade() throws Exception {
        ArrayList<GradeMaster> grades = gradeService.getAllGradeNames();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateGrades(tpl, grades, selected);
    }

    @RequestMapping(value = "selection_company.htm", method = RequestMethod.POST)
    public @ResponseBody
    void selection_company(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<CompanyMaster> companies = companyService
                .getAllCompanyNames();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        out.print(new GenTemplate(request).generateCompanies(tpl, companies,
                selected));
    }

    @RequestMapping(value = "selection_packing.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_packing() throws Exception {
        ArrayList<PackingMaster> packings = packingService.getAllPackings();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generatePackings(tpl, packings,
                selected);
    }

    @RequestMapping(value = "selection_processing.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_processing() throws Exception {
        ArrayList<ProcessingType> types = processingService
                .getAllProcessTypes();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateProcessingTypes(tpl, types,
                selected);
    }

    @RequestMapping(value = "selection_shipping_line.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_shipping_line() throws Exception {
        ArrayList<ShippingLineMaster> shippingLines = shippingLineService
                .getAllShippingLineName();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateShippingLines(tpl,
                shippingLines, selected);
    }

    @RequestMapping(value = "selection_port.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_port() throws Exception {
        ArrayList<PortMaster> ports = portMasterService.getAllPort();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generatePorts(tpl, ports, selected);
    }

    @RequestMapping(value = "selection_city.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_city() throws Exception {
        ArrayList<City> cities = cityService.getAllCity();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateCities(tpl, cities, selected);
    }

    @RequestMapping(value = "get_document_master.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_document_master() throws Exception {
        ArrayList<DocumentMaster> dms = documentMasterService
                .getAllDocument(Constants.DOCUMENT_SHIPPING_TYPE);
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/document_master.html"));
        return new GenTemplate(request).generateDocumentMaster(tpl, dms);
    }

    @RequestMapping(value = "get_pi_si_ref.htm")
    public @ResponseBody
    void get_pi_si_ref(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        char type = support.getCharValue("type");
        switch (type) {
            case 'P':// processing
                ArrayList<ProcessingInstruction> plist = processingService
                        .getAllPIRefList();
                int instId = support.getIntValue("id");
                if (instId == -1) {
                    instId = processingService.getLastestId();
                }
                Template ptpl = new Template(new File(context.getRealPath("/")
                        + "templates/common.html"));
                response.getWriter().print(
                        new GenTemplate(request).generateRefListWithSelected(ptpl,
                                plist, instId, "inst_"));
                break;
            case 'E': // shipping
                ArrayList<ShippingInstruction> slist = shippingService
                        .getAllSIRefList();
                int instId2 = support.getIntValue("id");
                if (instId2 == -1) {
                    instId2 = shippingService.getLastestId();
                }
                Template stpl = new Template(new File(context.getRealPath("/")
                        + "templates/common.html"));
                response.getWriter().print(
                        new GenTemplate(request).generateRefListWithSelected(stpl,
                                slist, instId2, "inst_"));
                break;
            case 'M':// movement
                ArrayList<Movement> mlist = movementService.getMovementRefList();
                Template mtpl = new Template(new File(context.getRealPath("/")
                        + "templates/common.html"));
                response.getWriter().print(
                        new GenTemplate(request).generateRefListWithSelected(mtpl,
                                mlist, support.getIntValue("id"), "inst_"));
                break;
            default:
                response.getWriter().print("");
        }
    }

    @RequestMapping(value = "get_di_si_ref.htm")
    public @ResponseBody
    void get_di_si_ref(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        String type = support.getStringRequest("type");
        int ins_id = support.getIntValue("id");
        switch (type) {
            case "IM":// processing
                ArrayList<DeliveryInstruction> dlist = deliveryInsService
                        .getAllDIRefList();
                Template dtpl = new Template(new File(context.getRealPath("/")
                        + "templates/common.html"));
                response.getWriter().print(
                        new GenTemplate(request).generateRefListWithSelected(dtpl,
                                dlist, ins_id, "ins_"));
                break;
            case "EX": // shipping
                ArrayList<ShippingInstruction> slist = shippingService
                        .getAllSIRefList();
                Template stpl = new Template(new File(context.getRealPath("/")
                        + "templates/common.html"));
                response.getWriter().print(
                        new GenTemplate(request).generateRefListWithSelected(stpl,
                                slist, ins_id, "ins_"));
                break;
            default:
                response.getWriter().print("");
        }
    }

    @RequestMapping(value = "selection_warehouse.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_warehouse() throws Exception {
        ArrayList<Warehouse> warehouses = warehouseService
                .getAllWarehouseNames();
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateWarehouses(tpl, warehouses,
                selected);
    }

    @RequestMapping(value = "selection_warehouse_map.htm", method = RequestMethod.POST)
    public @ResponseBody
    String selection_warehouse_map() throws Exception {
        ArrayList<WarehouseMap> warehouseMaps = warehouseMapService
                .getWarehouseMapListByWarehouseId(1);
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/option.html"));
        int selected = new ServletSupporter(request)
                .getIntValue("selected_value");
        return new GenTemplate(request).generateWarehouseMaps(tpl,
                warehouseMaps, selected);
    }

    @RequestMapping(value = "get_grade_by_ins_id.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_grade_by_ins_id(HttpServletResponse response) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/")
                + "templates/common.html"));
        ServletSupporter support = new ServletSupporter(request);
        char type = support.getCharValue("type");
        switch (type) {
            case 'P':// processing
                GradeMaster pgrade = processingService
                        .getPIGrades(processingService.getPiById(support
                                        .getIntValue("id")));
                ArrayList<GradeMaster> plist = weightNoteService.getAllGrades();
                // int pgrade_id = (pgrade != null) ? pgrade.getId() : -1;
                response.getWriter().print(
                        new GenTemplate(request)
                        .generateGradeFilterList(tpl, plist));
                break;
            case 'E': // shipping
                GradeMaster sgrade = shippingService.getSIGrades(shippingService
                        .getSiById(support.getIntValue("id")));
                ArrayList<GradeMaster> slist = weightNoteService.getAllGrades();
                // int sgrade_id = (sgrade != null) ? sgrade.getId() : -1;
                response.getWriter().print(
                        new GenTemplate(request)
                        .generateGradeFilterList(tpl, slist));
                break;
            case 'M':
                ArrayList<GradeMaster> mlist = new ArrayList<>();
                Movement mov = movementService.getMovementById(support
                        .getIntValue("id"));
                if (mov != null) {
                    GradeMaster mgrade = mov.getGradeMaster();
                    mlist.add(mgrade);
                    int mgrade_id = mgrade != null ? mgrade.getId() : 0;
                    response.getWriter().print(
                            new GenTemplate(request).generateGradeFilterList(tpl,
                                    mlist, mgrade_id));
                }
                break;
            default:
                response.getWriter().print("");
        }
    }

    @RequestMapping(value = "check_wn_complete.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wn_complete(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        String type = support.getStringRequest("type");
        byte b_type = Constants.IM_TYPE;
        if (type.equals("SI")) {
            b_type = Constants.EX_TYPE;
        }
        int inst_id = support.getIntValue("inst_id:");
        // default di
        boolean flag = true;
        ArrayList<WeightNote> wns = weightNoteService.getWeightNoteFromInst(
                inst_id, b_type);
        if (wns != null && !wns.isEmpty()) {
            for (WeightNote wn : wns) {
                if (wn.getStatus().equals(Constants.PENDING)) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            response.getWriter().print("[" + 1 + "]");
        } else {
            response.getWriter().print("[" + -1 + "]");
        }
    }

    @RequestMapping(value = "check_client_user.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_client_user(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int user_id = ((User) request.getSession().getAttribute("user"))
                .getId();
        ClientUser cu = userService.checkUserClient(user_id);
        if (cu == null) {
            String message = "[0]";
            response.getWriter().print(message);
        } else {
            JSONObject jso = new JSONObject();
            jso.put("id", cu.getCompanyMaster().getId().toString());
            jso.put("name", cu.getCompanyMaster().getName());
            response.getWriter().print("[" + jso.toString() + "]");
        }
    }

    // get_packing_map
    @RequestMapping(value = "get_packing_map.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_packing_map(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> map = packingService.getAllPackingsMap();
        if (map != null) {
            Map m = new HashMap();
            m.put("packing", new JSONArray(map));
            JSONObject json = new JSONObject(m);
            response.getWriter().print("[" + json.toString() + "]");
        } else {
            response.getWriter().print("[]");
        }
    }

	// get_warehouse
    @RequestMapping("get_warehouse.htm")
    public @ResponseBody
    void get_warehouse(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        WarehouseCell wc = warehouseCellService.getWarehouseCellById(supporter
                .getIntValue("area"));
        ArrayList<HashMap> maps = warehouseMapService.getWarehouseMaps();
        int map_id = (wc != null) ? wc.getWarehouseMap().getId() : 0;
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).get("id").equals(map_id)) {
                maps.get(i).put("selected", true);
            } else {
                maps.get(i).put("selected", false);
            }
        }
        JSONArray jArray = new JSONArray(maps);
        JSONObject json = new JSONObject();
        json.put("warehouse", jArray);
        response.getWriter().print(json.toString());
    }

    // getCoreInfo
    @RequestMapping("getCoreInfo.json")
    public @ResponseBody
    Object getCoreInfo(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int id = supporter.getIntValue("id");
        String table = supporter.getStringRequest("value");
        return commonService.getCoreInfo(id, table);
    }

    @RequestMapping("gradeInStock.htm")
    public @ResponseBody
    void gradeInStock(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> grade = gradeService.getGradeInStock();
        JSONArray jAr = new JSONArray(grade);
        JSONObject json = new JSONObject();
        json.put("select", jAr);
        response.getWriter().print(json.toString());
    }

    // loadPackingList
    @RequestMapping("loadPackingList.htm")
    public @ResponseBody
    void loadPackingList(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> map = packingService.getAllPackingsMap();
        JSONObject json = new JSONObject();
        json.put("packing", map);
        response.getWriter().print(json.toString());
    }
}
