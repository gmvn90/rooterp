package com.swcommodities.wsmill.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.query.result.DeliveryInstructionPagingResult;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.utils.Common;

@Service
public class DeliveryPagingService {
	
	@Autowired DeliveryInstructionRepository deliveryInstructionRepository;
	@Autowired PagingService pagingService;
	private int defaultEmpty = -1;
	
	public DeliveryInstructionPagingResult getAggregateInfo(Integer clientId, Integer supplierId, Integer gradeId, String diRef, Byte status, Date fromDate, Date toDate, Integer page, Integer perPage) {
    	// page: 1-based
		DeliveryInstructionPagingResult result = new DeliveryInstructionPagingResult();
    	String whereClauseWithAnd = "";
    	if(!(clientId == defaultEmpty || clientId == null)) {
    		whereClauseWithAnd += " and clientInt=" + clientId;
    	}
    	if(!(gradeId == defaultEmpty || gradeId == null)) {
    		whereClauseWithAnd += " and gradeInt=" + gradeId;
    	}
    	if(!(supplierId == defaultEmpty || clientId == null)) {
    		whereClauseWithAnd += " and supplierInt=" + supplierId;
    	}
    	if(!(diRef == null || diRef.equals(""))) {
    		whereClauseWithAnd += " and diRef like '%" + diRef.replace("'", "''") + "%'";
    	}
    	
    	if(!(status == null || status == Byte.valueOf("-1"))) {
    		whereClauseWithAnd += " and statusInt=" + status;
    	}

		if(!(fromDate == null)) {
			whereClauseWithAnd += " and firstDate >= '" + Common.getDateFromDatabase(fromDate, Common.date_format_yyyyMMdd) + "'";
		}

		if(!(toDate == null)) {
			whereClauseWithAnd += " and firstDate <= '" + Common.getDateFromDatabase(toDate, Common.date_format_yyyyMMdd) + "'";
		}

    	if(perPage == null) {
    		perPage = 20;
    	}
    	int offset = (page - 1) * perPage;

    	result.setResults(deliveryInstructionRepository.getResult(whereClauseWithAnd, offset, perPage));
    	result.setInstructionResult(deliveryInstructionRepository.getAggregateInfo(whereClauseWithAnd));
    	Long totalRecords = deliveryInstructionRepository.countResult(whereClauseWithAnd);
    	
    	
    	int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
    	result.setPage(page);
    	result.setPerPage(perPage);
    	result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
    	result.setTotalRecords(totalRecords.intValue());
    	
    	result.setTotalPages(numberOfPage);
    	
    	
    	return result;
    }
	
	
	
}
