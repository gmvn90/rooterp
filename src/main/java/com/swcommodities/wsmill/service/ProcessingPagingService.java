package com.swcommodities.wsmill.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionPagingResult;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.utils.Common;

@Service
public class ProcessingPagingService {
	
	@Autowired
	ProcessingInstructionRepository processingInstructionRepository;
	@Autowired PagingService pagingService;
	private int defaultEmpty = -1;
	
	public ProcessingInstructionPagingResult getAggregateInfo(Integer clientId, Integer gradeId, String piRef, Byte status, Date fromDate, Date toDate, Integer page, Integer perPage) {
    	// page: 1-based
		ProcessingInstructionPagingResult result = new ProcessingInstructionPagingResult();
    	String whereClauseWithAnd = "";
    	if(!(clientId == defaultEmpty || clientId == null)) {
    		whereClauseWithAnd += " and clientInt=" + clientId;
    	}
    	if(!(gradeId == defaultEmpty || gradeId == null)) {
    		whereClauseWithAnd += " and gradeInt=" + gradeId;
    	}
    	if(!(piRef == null || piRef.equals(""))) {
    		whereClauseWithAnd += " and piRef like '%" + piRef.replace("'", "''") + "%'";
    	}
    	
    	if(!(status == null || status == Byte.valueOf("-1"))) {
    		whereClauseWithAnd += " and statusInt=" + status;
    	}

		if(!(fromDate == null)) {
			whereClauseWithAnd += " and reqDate >= '" + Common.getDateFromDatabase(fromDate, Common.date_format_yyyyMMdd) + "'";
		}

		if(!(toDate == null)) {
			whereClauseWithAnd += " and reqDate <= '" + Common.getDateFromDatabase(toDate, Common.date_format_yyyyMMdd) + "'";
		}

    	if(perPage == null) {
    		perPage = 20;
    	}
    	
    	System.out.println(whereClauseWithAnd);
    	int offset = (page - 1) * perPage;
    	
    	result.setResults(processingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage));
    	result.setInstructionResult(processingInstructionRepository.getAggregateInfo(whereClauseWithAnd));
    	Long totalRecords = processingInstructionRepository.countResult(whereClauseWithAnd);
    	int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
    	result.setPage(page);
    	result.setPerPage(perPage);
    	result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
    	result.setTotalRecords(totalRecords.intValue());
    	
    	result.setTotalPages(numberOfPage);
    	
    	
    	return result;
    }
	
	
	
}
