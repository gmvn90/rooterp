/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swcommodities.wsmill.bo.AllocationService;
import com.swcommodities.wsmill.bo.CommonService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.MovementService;
import com.swcommodities.wsmill.bo.PalletMasterService;
import com.swcommodities.wsmill.bo.WarehouseCellService;
import com.swcommodities.wsmill.bo.WeightNoteReceiptService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.Movement;
import com.swcommodities.wsmill.hibernate.dto.PalletMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.hibernate.dto.view.MovementView;
import com.swcommodities.wsmill.json.gson.deserializer.DoubleDeserializer;
import com.swcommodities.wsmill.json.gson.deserializer.IntegerDeserializer;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class MovementController {

    @Autowired(required = true)
    private ServletContext context;
    @Autowired()
    private MovementService movementService;
    @Autowired()
    private GradeService gradeService;
    @Autowired()
    private CommonService commonService;
    @Autowired()
    private WeightNoteReceiptService weightNoteReceiptService;
    @Autowired()
    private AllocationService allocationService;
    @Autowired
    private PalletMasterService palletMasterService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired
    private WarehouseCellService warehouseCellService;
    @Autowired(required = true)
    private HttpServletRequest request;

    @RequestMapping(value = "update_movement.json", method = RequestMethod.POST)
    public @ResponseBody
    MovementView update_movement(HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            String data = request.getParameter("data");
            Gson gson = new GsonBuilder()
                    .setDateFormat(Common.date_format_ddMMyyyy_dash)
                    .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                    .registerTypeAdapter(Double.class, new DoubleDeserializer())
                    .create();
            if (data != null) {
                System.out.println(data);
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(data).getAsJsonObject();
                MovementView movement = gson.fromJson(json, MovementView.class);
                if (movement.getId() == null) {
                    movement.setRefNumber(movementService.getNewMovementRef());
                }
                if (movement.getPledge().equals(0)) {
                    movement.setPledge(null);
                }
                String log;
                data = gson.toJson(movement);
                if (movement.getLog() != null) {
                    JSONArray jArr = new JSONArray(movement.getLog());
                    jArr.join(data);
                    log = jArr.toString();
                } else {
                    JSONArray jArr = new JSONArray("[" + data + "]");
                    log = jArr.toString();
                }
                movement.setLog(log);
                movement.setUser(user.getId());
                movement.setStatus(Constants.PENDING);
                movementService.saveMovementView(movement);
                return movement;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "load_mov_ref_list.htm", method = RequestMethod.POST)
    public @ResponseBody
    void load_mov_ref_list(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        String search = support.getStringRequest("search");
        ArrayList<HashMap> list = movementService.getMovementRefList(search);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("id").equals(id)) {
                list.get(i).put("selected", true);
            } else {
                list.get(i).put("selected", false);
            }
        }
        JSONArray jArr = new JSONArray(list);
        response.getWriter().print(jArr.toString());
    }

    @RequestMapping(value = "loadMovInfo.json", method = RequestMethod.POST)
    public @ResponseBody
    MovementView loadMovInfo(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int id = supporter.getIntValue("id");
        String table = supporter.getStringRequest("value");
        MovementView move = (MovementView) commonService.getCoreInfo(id, table);
        //WarehouseCell cell = warehouseCellService.getWarehouseCellById(move.getArea());
        //move.setAreaCode(warehouseCellService.convertIdIntoCode(cell));
        return move;
    }

    @RequestMapping(value = "gradeInMov.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInMov(HttpServletResponse response) throws Exception {
        ArrayList<HashMap> grades = gradeService.getGradeInMovement();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    //getMovWnr
    @RequestMapping(value = "getMovWnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getMovWnr(HttpServletResponse response) throws Exception {
        int id = new ServletSupporter(request).getIntValue("id");
        response.getWriter().print(weightNoteReceiptService.getWNRByMovId(id));
    }

    @RequestMapping(value = "check_movement_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_movement_wnr(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        String type = "M";
        String ref = supporter.getStringRequest("ref");
        int inst_id = supporter.getIntValue("mov");
        int wnr_id = allocationService.isAllocated(ref, inst_id, type);
        if (wnr_id > 0) {
            response.getWriter().print(wnr_id);
        } else {
            response.getWriter().print("-1");
        }
    }

    @RequestMapping(value = "update_movement_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void update_movement_wnr(HttpServletResponse response) throws Exception {
        String msg = "Update Failed";
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ServletSupporter supporter = new ServletSupporter(request);
            int mov_id = supporter.getIntValue("mov");
            int area = supporter.getIntValue("area");
            String areaCode = supporter.getStringRequest("areaCode");
            Map map = new HashMap();
            map.put("type", "Moved");
            map.put("Move_id", mov_id);
            Byte type = supporter.getByteValue("type");
            WeightNoteReceipt wnr = weightNoteReceiptService.getWNRById(supporter.getIntValue("wnr_id"));
            float out_weight = 0;
            PalletMaster pallet;
            int out_id = supporter.getIntValue("wnr_id");
            if (wnr != null) {
                WnrAllocation allocation = allocationService.findByWnrRef(wnr.getRefNumber(), mov_id);
                if (allocation != null) {
                    allocation.setWeightNoteReceiptByOutWnrId(wnr);
                    if (type == 0) {        //not re-weight
                        //update allocation to complete with the weight out and id the same as input wnr
                        map.put("move_type", "standard");
                        allocation.setWeightNoteReceiptByOutWnrId(wnr);
                        allocation.setWeightOut(wnr.getGrossWeight() - wnr.getTareWeight());
                        allocation.setStatus(Constants.COMPLETE);
                    } else {
                        map.put("move_type", "Re-weight");
                        map.put("new weight", supporter.getFloatValue("gross"));
                        out_weight = supporter.getFloatValue("gross");
                        pallet = palletMasterService.getPalletByRef(supporter.getStringRequest("pallet"));
                        if (pallet != null) {
                            map.put("new pallet", pallet.getName());
                        } else {
                            response.getWriter().print("Pallet Does Not Exist");
                        }
                        float newTare = wnr.getTareWeight() - wnr.getPalletWeight() + pallet.getValue();
                        wnr.setTareWeight(newTare);
                        wnr.setPalletWeight(pallet.getValue());

                        allocation.setWeightOut(wnr.getGrossWeight() - wnr.getTareWeight());
                        allocation.setStatus(Constants.COMPLETE);
                    }
                    if (allocationService.updateAllocation(allocation) > 0) {
                        //get set movement
                        Set<Movement> moves = new HashSet<Movement>();
                        moves.add(movementService.getMovementById(mov_id));
                        map.put("status", "moved");
                        map.put("user", user.getUserName());
                        map.put("area", areaCode);
                        map.put("date", Common.getDateFromDatabase(new Date(), Common.date_format));
//                        wnr.setMovementId((wnr.getMovementId() != null) ? wnr.getMovementId() + "," + mov_id : mov_id + "");
                        wnr.setMovements(moves);
                        wnr.setStatus(Constants.MOVED);
                        wnr.setWarehouseCell(warehouseCellService.getWarehouseCellById(area));
                        String log = wnr.getLog() + "," + (new JSONObject(map)).toString();
                        wnr.setLog(log);

                        if (weightNoteReceiptService.updateWNR(wnr) == 0) {
                            //return wnr json
                            JSONObject json = new JSONObject(weightNoteService.convertWnrToMap(wnr, false, false));
                            msg = json.toString();
                        }
                    }
                }
            }
        }
        response.getWriter().print(msg);
    }

    @RequestMapping(value = "loadAllocatedMoved.htm", method = RequestMethod.POST)
    public @ResponseBody
    void loadAllocatedMoved(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int mov_id = supporter.getIntValue("id");
        HashMap map = movementService.getAllocatedMoved(mov_id);
        JSONObject json = new JSONObject(map);
        response.getWriter().print(json.toString());
    }
}
