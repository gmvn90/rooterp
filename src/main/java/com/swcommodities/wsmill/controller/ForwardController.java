/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
public class ForwardController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

//	@RequestMapping(value = "*.htm")
//	public Object redirect() {
//		String path = request.getServletPath();
//		path = path.substring(1, path.length() - 4);
//		switch (path) {
//		case "allocation_detail": {
//			ModelAndView model = new ModelAndView("allocation_detail");
//			model.addObject("ins_type",
//					(request.getParameter("ins_type") != null) ? request.getParameter("ins_type")
//							: "P");
//			model.addObject("ins",
//					(request.getParameter("ins_id") != null) ? request.getParameter("ins_id")
//							: "-1");
//			return model;
//		}
//		case "processing_instruction": return "processing_listview";
//		case "delivery_instruction": return "delivery_listview";
//		case "shipping_instruction": return "shipping_listview";
//		case "shipping_instruction_v2": return "shipping_listview_v2";
//		default:
//			return path;
//		}
//	}
    @RequestMapping(value = "pending.htm")
    public String redirect_pending() {
        return "pending";
    }

    @RequestMapping(value = "stock.htm")
    public String redirect_stock() {
        return "stock";
    }

    @RequestMapping(value = "reports.htm")
    public String redirect_report() {
        return "reports";
    }

    @RequestMapping(value = "processing_instruction.htm")
    public String redirect_processing() {
        return "processing_listview";
    }

    @RequestMapping(value = "delivery_instruction.htm")
    public String redirect_delivery() {
        return "delivery_listview";
    }

    @RequestMapping(value = "shipping_instruction.htm")
    public String redirect_shipping() {
        return "shipping_listview";
    }

    @RequestMapping(value = "weighing_listview.htm")
    public String redirect_weighing_listview() {
        return "weighing_listview";
    }

    @RequestMapping(value = "weighing_detail.htm")
    public String redirect_weighing_detail() {
        return "weighing_detail";
    }

    @RequestMapping(value = "allocation_detail.htm")
    public ModelAndView redirect_allocation_detail() {
        ModelAndView model = new ModelAndView("allocation_detail");
        model.addObject("ins_type",
            (request.getParameter("ins_type") != null)
            ? request.getParameter("ins_type") : "P");
        model.addObject("ins",
            (request.getParameter("ins_id") != null) ? request.getParameter("ins_id")
            : "-1");
        return model;
    }

    @RequestMapping(value = "quality_listview.htm")
    public String redirect_quality_list() {
        return "quality_listview";
    }

    @RequestMapping(value = "allocation_listview.htm")
    public String redirect_allocation_list() {
        return "allocation_listview";
    }

    @RequestMapping(value = "warehouse_receipt.htm")
    public String redirect_warehouse_receipt() {
        return "warehouse_receipt";
    }

    @RequestMapping(value = "user_view.htm")
    public String redirect_account() {
        return "user_view";
    }

    @RequestMapping(value = "update_pallet.htm")
    public String redirect_update_pallet() {
        return "update_pallet";
    }

    @RequestMapping(value = "print_pallet.htm")
    public String redirect_print_pallet() {
        return "print_pallet";
    }

    @RequestMapping(value = "profile.htm")
    public String redirect_profile() {
        return "profile";
    }

    @RequestMapping(value = "movement_detail.htm")
    public String redirect_movement_detail() {
        return "movement_detail";
    }

    @RequestMapping(value = "company.htm")
    public String redirect_company() {
        return "company";
    }

    @RequestMapping(value = "stock_listview.htm")
    public String redirect_stockList() {
        return "stock_listview";
    }

    @RequestMapping(value = "stock_detailview.htm")
    public String redirect_stockDetail() {
        return "stock_detailview";
    }

    @RequestMapping(value = "cma_stock_list.htm")
    public String redirect_cma_stock_list() {
        return "cma_stock_list";
    }

    @RequestMapping(value = "cma_stock_detail.htm")
    public String redirect_cma_stock_detail() {
        return "cma_stock_detail";
    }

    @RequestMapping(value = "price_list.htm")
    public String redirect_price_list() {
        return "price_list";
    }

    @RequestMapping(value = "stock_invoice.htm")
    public String stock_invoice() {
        return "stock_invoice";
    }

    @RequestMapping(value = "formula.htm")
    public String formula() {
        System.out.println(request.getServletPath());
        return "formula";
    }
    
    @RequestMapping(value = "cost_list.htm")
    public String costList() {
        System.out.println(request.getServletPath());
        return "cost_list";
    }
    
    @RequestMapping(value = "admin-functions.htm")
    public String admin() {
        System.out.println(request.getServletPath());
        return "admin/admin-functions";
    }
    
    @RequestMapping(value = "cost_price_input.htm")
    public String costPriceInput() {
        System.out.println(request.getServletPath());
        return "cost_price_input";
    }
    
    @RequestMapping(value = "weighing/detail.htm")
    public ModelAndView weighingDetail() {
        ModelAndView model;
        String check = "weighing_detail";
        model = new ModelAndView(check);
        model.addObject("initial_id", (request.getParameter("fw_id") != null) ? request.getParameter("fw_id") : "");
        model.addObject("initial_type", (request.getParameter("fw_wntype") != null) ? request.getParameter("fw_wntype") : "");
        return model;
    }
    
    @RequestMapping(value = "Allocation/detail.htm")
    public ModelAndView allocationDetail() {
        ModelAndView model;
        model = new ModelAndView("allocation_detail");
        model.addObject("ins_type", (request.getParameter("ins_type") != null) ? request.getParameter("ins_type") : "P");
        model.addObject("ins", (request.getParameter("ins_id") != null) ? request.getParameter("ins_id") : "-1");
        return model;
    }
    
    @RequestMapping(value = "quality_report/detail.htm")
    public ModelAndView qualityReportDetail() {
        ModelAndView model;
        String check = "quality_detail";
        model = new ModelAndView(check);
        model.addObject("initial_id", (request.getParameter("fw_id") != null) ? request.getParameter("fw_id") : "");
        model.addObject("initial_type", (request.getParameter("fw_qrtype") != null) ? request.getParameter("fw_qrtype") : "");
        return model;
    }
    
    
}
