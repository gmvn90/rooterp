/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.Date;
import java.util.Set;

import com.swcommodities.wsmill.domain.model.common.AssertionConcern;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.hibernate.dto.QualityMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;

/**
 *
 * @author macOS
 */
public class ShippingInstructionSummary extends AssertionConcern {
    
    private CompanyMaster client;
    private ShippingLineMaster shippingLine;
    private String saleNo;
    private CompanyMaster buyer;
    private String buyerRef;
    private String SINumber;
    private Date dateOfStuffing;
    private PackingMaster packing;
    private QualityMaster quality;
    // in excel summary, it is status
    private String containerStatus;
    private String fumigation;
    private String weightCertificate;
    // Point of discharge
    private PortMaster destination;
    // etd ho chi minh;
    private Date feederEtd;
    // return place
    private Date fullContReturn;
    private String shippingMark;
    
    private Set<WeightNoteExportSummary> weightNotes;
    
    public ShippingInstructionSummary() {}
    
    public ShippingInstructionSummary(ShippingInstruction shippingInstruction) {
        this.assertArgumentNotNull(shippingInstruction, "shipping must not be null");
    }
    
}
