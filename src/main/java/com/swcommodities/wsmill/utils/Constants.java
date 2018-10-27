/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kiendn
 */
public class Constants {

    public static final byte PENDING = 0;
    public static final byte COMPLETE = 1;
    public static final byte DELETED = 2;
    public static final byte SENT = 3;
    public static final byte IM_TYPE = 1;
    public static final byte IP_TYPE = 2;
    public static final byte XP_TYPE = 3;
    public static final byte EX_TYPE = 4;
    public static final String IM = "IM";
    public static final String XP = "XP";
    public static final String IP = "IP";
    public static final String EX = "EX";
    public static final String MOVEMENT = "M";
    public static final String WR = "WR";
    public static final String WC = "WC";
    public static final String DI = "Delivery Instruction";
    public static final String PI = "Processing Instruction";
    public static final String SI = "Shipping Instruction";
    public static final byte AVAILABLE = 1;
    public static final byte ALLOCATED = 3;
    public static final byte MOVED = 4;
    public static final byte CONTAINER_FCL = 1;
    public static final byte CONTAINER_LCL = 2;
    public static final String DOCUMENT_SHIPPING_TYPE = "S";
    public static final String DOCUMENT_DELIVERING_TYPE = "D";
    public static final int ARABICA = 0;
    public static final int ROBUSTA = 1;
    public static final int ORIGIN = 1;
    //user access - start.
    public static final byte p_full = 0;
    public static final byte p_yes_no = 1;
    public static final byte NONE = 0;
    public static final byte READ_ONLY = 1;
    public static final byte FULL_ACCESS = 2;
    public static final Map<Byte, String> PERMISSION_FULL;
    public static final Map PERMISSION_YES_NO;
    public static final Map<Byte, Map<Byte, String>> PERMISSION;

    static {
        PERMISSION_FULL = new HashMap<>();
        PERMISSION_FULL.put((byte) 0, "None");
        PERMISSION_FULL.put((byte) 1, "Read Only");
        PERMISSION_FULL.put((byte) 2, "Full Access");

        PERMISSION_YES_NO = new HashMap<>();
        PERMISSION_YES_NO.put((byte) 0, "No");
        PERMISSION_YES_NO.put("No", 0);
        PERMISSION_YES_NO.put((byte) 1, "Yes");
        PERMISSION_YES_NO.put("Yes", 1);

        PERMISSION = new HashMap<>();
        PERMISSION.put(p_yes_no, PERMISSION_YES_NO);
        PERMISSION.put(p_full, PERMISSION_FULL);
    }
    //user access - end.
    public static final int CLIENT = 2;
    public static final int PLEDGE = 7;

    public static final String cma_stock_list = "cma_stock_list";
    public static final String cma_stock_detail = "cma_stock_detail";
    public static final String cma_update_client = "cma_update_client";
    public static final String cma_update_pledge = "cma_update_pledge";
    public static final String cma_update_area = "cma_update_area";
    public static final String cma_detail_update_client = "cma_detail_update_client";
    public static final String cma_detail_update_pledge = "cma_detail_update_pledge";
    public static final String cma_detail_update_area = "cma_detail_update_area";
    public static final String cma_detail_reweight = "cma_detail_reweight";
    public static final String authorize_url_check_connection = "authorize_url_check_connection";
    public static final String authorize_core_url = "core_url";
    
    public static final Byte ACTIVE = 1;

    public static final Byte APPROVAL_PENDING = 0;
    public static final Byte APPROVAL_APPROVED = 1;
    public static final Byte APPROVAL_REJECTED = 2;
}
