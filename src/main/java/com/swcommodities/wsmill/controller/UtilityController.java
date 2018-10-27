/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.domain.model.RefNumberInfo;
import com.swcommodities.wsmill.domain.service.ClaimRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.PIRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.SampleSentRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.SampleTypeRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.ShippingAdviceRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.ShippingInstructionRefNumberProviderService;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author macOS
 */
@RequestMapping(value = "/utility")
@org.springframework.stereotype.Controller
public class UtilityController {

    @Autowired
    ShippingAdviceRefNumberProviderService shippingAdviceRefNumberProviderService;
    @Autowired
    SampleSentRefNumberProviderService sampleSentRefNumberProviderService;
    @Autowired
    ShippingInstructionRefNumberProviderService shippingInstructionRefNumberProviderService;
    @Autowired
    ClaimRefNumberProviderService claimRefNumberProviderService;
    @Autowired PIRefNumberProviderService pIRefNumberProviderService;
    @Autowired SampleTypeRefNumberProviderService sampleTypeRefNumberProviderService;

    @RequestMapping(value = "/readProperties.htm")
    public @ResponseBody
    String update_quality_report(HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException {
        ServletContext context = httpServletRequest.getServletContext();
        return Common.readProperties("cma_detail_update_client", context);

    }

    @RequestMapping(value = "/refInfo.htm")
    public String show_ref(HttpServletResponse response,
        HttpServletRequest httpServletRequest, Map<String, Object> model) throws IOException {
        RefNumberInfo info = new RefNumberInfo();
        info.setShippingAdviceNumber(shippingAdviceRefNumberProviderService.getNumberWithoutIncreasing());
        info.setSampleSentNumber(sampleSentRefNumberProviderService.getNumberWithoutIncreasing());
        info.setShippingInstructionNumber(shippingInstructionRefNumberProviderService.getNumberWithoutIncreasing());
        info.setClaimNumber(claimRefNumberProviderService.getNumberWithoutIncreasing());
        info.setProcessingInstructionNumber(pIRefNumberProviderService.getNumberWithoutIncreasing());
        info.setSampleTypeRefNumber(sampleTypeRefNumberProviderService.getNumberWithoutIncreasing());
        model.put("info", info);
        return "utility";
    }

}
