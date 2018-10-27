/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoicePagingResult;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author macOS
 */

@Service
public class InvoicePagingService {
    
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired PagingService pagingService;
    @Autowired InvoiceService invoiceService;
    
    private final int defaultEmpty = -1;
    
    public InvoicePagingResult getAggregateInfoForClientSite(Integer clientId, String ref, Integer status, Date fromDate, 
        Date endDate, Integer page, Integer perPage) {
        
        InvoicePagingResult result = new InvoicePagingResult();
		String whereClauseWithAnd = "";
		if(!(clientId == defaultEmpty || clientId == null)) {
			whereClauseWithAnd += " and client=" + clientId;
		}
		if(!(ref == null || ref.equals(""))) {
			whereClauseWithAnd += " and refNumber like '%" + ref.replace("'", "''") + "%'";
		}

		if(!(status == null || status == defaultEmpty)) {
			whereClauseWithAnd += " and completionStatus=" + status;
		}
        System.out.println("Common.getDateFromDatabase");
        System.out.println(fromDate);
        System.out.println(endDate);
		if(!(fromDate == null)) {
			whereClauseWithAnd += " and dueDate >= '" + Common.getDateFromDatabase(fromDate, Common.date_format_yyyyMMdd) + "'";
		}

		if(!(endDate == null)) {
			whereClauseWithAnd += " and dueDate <= '" + Common.getDateFromDatabase(endDate, Common.date_format_yyyyMMdd) + "'";
		}

		if(perPage == null) {
			perPage = 20;
		}

		int offset = (page - 1) * perPage;
        List<Invoice> invoices = invoiceRepository.getResult(whereClauseWithAnd, offset, perPage);
        List<Invoice> fullInfos = new ArrayList<>();
        for(Invoice inv: invoices) {
            fullInfos.add(invoiceService.getInvoiceWithCostAndDueInfo(inv.getId()));
        }
        result.setResults(fullInfos);
		Long totalRecords = invoiceRepository.countResult(whereClauseWithAnd);
		int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
		result.setPage(page);
		result.setPerPage(perPage);
		result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
		result.setTotalRecords(totalRecords.intValue());
		result.setTotalPages(numberOfPage);
        result.setInvoiceAggregateInfo(invoiceRepository.findAggregateInfo(whereClauseWithAnd));
        
        //model.put("aggregateInfo", invoiceRepository.findAggregateInfo());
		return result;
    }
}
