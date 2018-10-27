/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.object.Allocation;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
public class AllocationDataSource {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletContext context;
    @Autowired
    private WeightNoteService weightNoteService;

    @RequestMapping("available_wn_source.htm")
    public @ResponseBody
    void available_wn_source(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "wn.ref_number", "wn.allocated_date", "gm.name", "stock_tons", "qr.black", "qr.brown", "qr.foreign_matter", "qr.broken", "qr.moisture", "qr.old_crop", "qr.above_sc20", "qr.sc20", "qr.sc19", "qr.sc18", "qr.sc17", "qr.sc16", "qr.sc15", "qr.sc14", "qr.sc13", "qr.sc12", "qr.below_sc12"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sTitle = "Double Click To View Weight Note Receipt";
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
        }
        if (sEcho != null) {
            echo = Integer.parseInt(sEcho);
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

        String colName = aColumns[col];
        long total = weightNoteService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }

        ArrayList<Allocation> searchGlobe = weightNoteService.searchAvailableWN(searchTerm, sdir, start, amount, colName, grade_id);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (Allocation wn : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + wn.getWn_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", wn.getWn_ref());
                ja.put((i++) + "", Common.getDateFromDatabase(wn.getStock_date(), Common.date_format_a));
                ja.put((i++) + "", wn.getGrade());
                ja.put((i++) + "", wn.getStock_tons());
                ja.put((i++) + "", wn.getBlack());
                ja.put((i++) + "", wn.getBrown());
                ja.put((i++) + "", wn.getForeignMatter());
                ja.put((i++) + "", wn.getBroken());
                ja.put((i++) + "", wn.getMoisture());
                ja.put((i++) + "", wn.getOldCrop());
                ja.put((i++) + "", wn.getAboveSc20());
                ja.put((i++) + "", wn.getSc20());
                ja.put((i++) + "", wn.getSc19());
                ja.put((i++) + "", wn.getSc18());
                ja.put((i++) + "", wn.getSc17());
                ja.put((i++) + "", wn.getSc16());
                ja.put((i++) + "", wn.getSc15());
                ja.put((i++) + "", wn.getSc14());
                ja.put((i++) + "", wn.getSc13());
                ja.put((i++) + "", wn.getSc12());
                ja.put((i++) + "", wn.getBelowSc12());
                ja.put((i++) + "", "<input type='checkbox' id='available_ck_" + wn.getWn_id() + "' class='available_ck'>");
                ja.put((i++) + "", "<input type='button' class='available_detail_wn' value='Detail'/>");
                array.put(ja);
                count++;
            }
        }
        Map mtotal = weightNoteService.countTotalAvailableWn(searchTerm, grade_id);
        if (mtotal != null && !mtotal.isEmpty()) {
            int i = 0;
            JSONObject ja = new JSONObject();
            //ja.put(m);
            ja.put("DT_RowClass", "footer");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "Total");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");
            ja.put((i++) + "", (mtotal.get("stocks") != null) ? mtotal.get("stocks") : 0);
            ja.put((i++) + "", (mtotal.get("black") != null) ? mtotal.get("black") : 0);
            ja.put((i++) + "", (mtotal.get("brown") != null) ? mtotal.get("brown") : 0);
            ja.put((i++) + "", (mtotal.get("foreign_matter") != null) ? mtotal.get("foreign_matter") : 0);
            ja.put((i++) + "", (mtotal.get("broken") != null) ? mtotal.get("broken") : 0);
            ja.put((i++) + "", (mtotal.get("moisture") != null) ? mtotal.get("moisture") : 0);
            ja.put((i++) + "", (mtotal.get("old_crop") != null) ? mtotal.get("old_crop") : 0);
            ja.put((i++) + "", (mtotal.get("above_sc20") != null) ? mtotal.get("above_sc20") : 0);
            ja.put((i++) + "", (mtotal.get("sc20") != null) ? mtotal.get("sc20") : 0);
            ja.put((i++) + "", (mtotal.get("sc19") != null) ? mtotal.get("sc19") : 0);
            ja.put((i++) + "", (mtotal.get("sc18") != null) ? mtotal.get("sc18") : 0);
            ja.put((i++) + "", (mtotal.get("sc17") != null) ? mtotal.get("sc17") : 0);
            ja.put((i++) + "", (mtotal.get("sc16") != null) ? mtotal.get("sc16") : 0);
            ja.put((i++) + "", (mtotal.get("sc15") != null) ? mtotal.get("sc15") : 0);
            ja.put((i++) + "", (mtotal.get("sc14") != null) ? mtotal.get("sc14") : 0);
            ja.put((i++) + "", (mtotal.get("sc13") != null) ? mtotal.get("sc13") : 0);
            ja.put((i++) + "", (mtotal.get("sc12") != null) ? mtotal.get("sc12") : 0);
            ja.put((i++) + "", (mtotal.get("below_sc12") != null) ? mtotal.get("below_sc12") : 0);
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");
            array.put(ja);
        }
        totalAfterFilter = weightNoteService.getTotalAvailable();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping("allocate_wn_source.htm")
    public @ResponseBody
    void allocate_wn_source(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "wn.ref_number", "wn.created_date", "gm.name", "allocated_tons", "qr.black", "qr.brown", "qr.foreign_matter", "qr.broken", "qr.moisture", "qr.old_crop", "qr.above_sc20", "qr.sc20", "qr.sc19", "qr.sc18", "qr.sc17", "qr.sc16", "qr.sc15", "qr.sc14", "qr.sc13", "qr.sc12", "qr.below_sc12"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int inst_id = 0;

        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sIns = request.getParameter("ins");
        String sType = request.getParameter("type");
        String sTitle = "Double Click To View Weight Note Receipt";
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
        }
        if (sEcho != null) {
            echo = Integer.parseInt(sEcho);
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
        if (sIns != null) {
            inst_id = Integer.parseInt(sIns);
        }

        String colName = aColumns[col];
        long total = weightNoteService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");


        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }


        ArrayList<Allocation> searchGlobe = weightNoteService.searchAllocatedWn(searchTerm, sdir, start, amount, colName, inst_id, sType);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (Allocation wn : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "allocatedrow_" + wn.getWn_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", wn.getWn_ref());
                ja.put((i++) + "", Common.getDateFromDatabase(wn.getStock_date(), Common.date_format_a));
                ja.put((i++) + "", wn.getGrade());
                ja.put((i++) + "", wn.getStock_tons());
                ja.put((i++) + "", wn.getBlack());
                ja.put((i++) + "", wn.getBrown());
                ja.put((i++) + "", wn.getForeignMatter());
                ja.put((i++) + "", wn.getBroken());
                ja.put((i++) + "", wn.getMoisture());
                ja.put((i++) + "", wn.getOldCrop());
                ja.put((i++) + "", wn.getAboveSc20());
                ja.put((i++) + "", wn.getSc20());
                ja.put((i++) + "", wn.getSc19());
                ja.put((i++) + "", wn.getSc18());
                ja.put((i++) + "", wn.getSc17());
                ja.put((i++) + "", wn.getSc16());
                ja.put((i++) + "", wn.getSc15());
                ja.put((i++) + "", wn.getSc14());
                ja.put((i++) + "", wn.getSc13());
                ja.put((i++) + "", wn.getSc12());
                ja.put((i++) + "", wn.getBelowSc12());
                ja.put((i++) + "", "<input type='checkbox' id='allocated_ck_" + wn.getWn_id() + "' class='allocated_ck'>");
                ja.put((i++) + "", "<input type='button' class='allocated_detail_wn' value='Detail'/>");
                array.put(ja);
                count++;
            }
        }
        Map mtotal = weightNoteService.countTotalAllocatedWn(searchTerm, inst_id, sType);
        if (mtotal != null && !mtotal.isEmpty()) {
            int i = 0;
            JSONObject ja = new JSONObject();
            //ja.put(m);
            ja.put("DT_RowClass", "footer");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "Total");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");
            ja.put((i++) + "", (mtotal.get("stocks") != null) ? mtotal.get("stocks") : 0);
            ja.put((i++) + "", (mtotal.get("black") != null) ? mtotal.get("black") : 0);
            ja.put((i++) + "", (mtotal.get("brown") != null) ? mtotal.get("brown") : 0);
            ja.put((i++) + "", (mtotal.get("foreign_matter") != null) ? mtotal.get("foreign_matter") : 0);
            ja.put((i++) + "", (mtotal.get("broken") != null) ? mtotal.get("broken") : 0);
            ja.put((i++) + "", (mtotal.get("moisture") != null) ? mtotal.get("moisture") : 0);
            ja.put((i++) + "", (mtotal.get("old_crop") != null) ? mtotal.get("old_crop") : 0);
            ja.put((i++) + "", (mtotal.get("above_sc20") != null) ? mtotal.get("above_sc20") : 0);
            ja.put((i++) + "", (mtotal.get("sc20") != null) ? mtotal.get("sc20") : 0);
            ja.put((i++) + "", (mtotal.get("sc19") != null) ? mtotal.get("sc19") : 0);
            ja.put((i++) + "", (mtotal.get("sc18") != null) ? mtotal.get("sc18") : 0);
            ja.put((i++) + "", (mtotal.get("sc17") != null) ? mtotal.get("sc17") : 0);
            ja.put((i++) + "", (mtotal.get("sc16") != null) ? mtotal.get("sc16") : 0);
            ja.put((i++) + "", (mtotal.get("sc15") != null) ? mtotal.get("sc15") : 0);
            ja.put((i++) + "", (mtotal.get("sc14") != null) ? mtotal.get("sc14") : 0);
            ja.put((i++) + "", (mtotal.get("sc13") != null) ? mtotal.get("sc13") : 0);
            ja.put((i++) + "", (mtotal.get("sc12") != null) ? mtotal.get("sc12") : 0);
            ja.put((i++) + "", (mtotal.get("below_sc12") != null) ? mtotal.get("below_sc12") : 0);
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");
            array.put(ja);
        }
        totalAfterFilter = weightNoteService.getTotalAllocated();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }
}
