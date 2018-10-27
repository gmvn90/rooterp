package com.swcommodities.wsmill.service;

import com.swcommodities.wsmill.formController.form.SISearchForm;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionPagingResult;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.utils.Common;

@Service
public class ShippingPagingService {
	
	@Autowired
	ShippingInstructionRepository shippingInstructionRepository;
	@Autowired PagingService pagingService;
	private int defaultEmpty = -1;
	
	public ShippingInstructionPagingResult getAggregateInfo(Integer clientId, Integer buyerId, Integer gradeId, String siRef, Byte status, Byte shipmentStatus, Date fromDate, Date toDate, Integer page, Integer perPage) {
    	// page: 1-based
		ShippingInstructionPagingResult result = new ShippingInstructionPagingResult();
    	String whereClauseWithAnd = "";
    	if(!(clientId == defaultEmpty || clientId == null)) {
    		whereClauseWithAnd += " and clientInt=" + clientId;
    	}
    	if(!(gradeId == defaultEmpty || gradeId == null)) {
    		whereClauseWithAnd += " and gradeInt=" + gradeId;
    	}
    	if(!(buyerId == defaultEmpty || buyerId == null)) {
    		whereClauseWithAnd += " and buyerInt=" + buyerId;
    	}
    	if(!(siRef == null || siRef.equals(""))) {
    		whereClauseWithAnd += " and siRef like '%" + siRef.replace("'", "''") + "%'";
    	}

    	if(!(status == null || status == Byte.valueOf("-1"))) {
    		whereClauseWithAnd += " and statusInt=" + status;
    	}

		if(!(shipmentStatus == null || shipmentStatus == Byte.valueOf("-1"))) {
			whereClauseWithAnd += " and shipmentStatusInt=" + shipmentStatus;
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
    	if(status == Byte.valueOf("0")) {
            result.setResults(shippingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage, " order by si.loadingDate, si.closingDate"));
        } else {
            result.setResults(shippingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage, " order by si.loadingDate, si.closingDate"));
        }
    	
    	result.setInstructionResult(shippingInstructionRepository.getAggregateInfo(whereClauseWithAnd));
    	Long totalRecords = shippingInstructionRepository.countResult(whereClauseWithAnd);
    	int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
    	result.setPage(page);
    	result.setPerPage(perPage);
    	result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
    	result.setTotalRecords(totalRecords.intValue());
    	
    	result.setTotalPages(numberOfPage);
    	
    	
    	return result;
    }
    
    public ShippingInstructionPagingResult getAggregateInfo(SISearchForm form) {
    	// page: 1-based
		return getAggregateInfo(form.getClientId(), form.getBuyerId(), form.getGradeId(), form.getTxtSearch(), (byte) form.getCompletionStatus().getOrder(), 
            (byte) form.getShipmentStatus().getOrder(), form.getStartDate(), form.getEndDate(), form.getPageNo(), form.getPerPage());
    }


	public ShippingInstructionPagingResult getAggregateInfoForClientSite(Integer clientId, Integer buyerId, Integer gradeId, String siRef, Byte status, Date fromDate, Date toDate, Integer page, Integer perPage) {
		// page: 1-based
		ShippingInstructionPagingResult result = new ShippingInstructionPagingResult();
		String whereClauseWithAnd = "";
		if(!(clientId == defaultEmpty || clientId == null)) {
			whereClauseWithAnd += " and clientInt=" + clientId;
		}
		if(!(gradeId == defaultEmpty || gradeId == null)) {
			whereClauseWithAnd += " and gradeInt=" + gradeId;
		}
		if(!(buyerId == defaultEmpty || buyerId == null)) {
			whereClauseWithAnd += " and buyerInt=" + buyerId;
		}
		if(!(siRef == null || siRef.equals(""))) {
			whereClauseWithAnd += " and siRef like '%" + siRef.replace("'", "''") + "%'";
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
        if(status == Byte.valueOf("0")) {
            result.setResults(shippingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage, " order by si.loadingDate, si.closingDate"));
        } else {
            result.setResults(shippingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage, " order by si.id desc"));
        }
		//result.setResults(shippingInstructionRepository.getResult(whereClauseWithAnd, offset, perPage));
		result.setInstructionResult(shippingInstructionRepository.getAggregateInfo(whereClauseWithAnd));
		Long totalRecords = shippingInstructionRepository.countResult(whereClauseWithAnd);
		int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
		result.setPage(page);
		result.setPerPage(perPage);
		result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
		result.setTotalRecords(totalRecords.intValue());

		result.setTotalPages(numberOfPage);


		return result;
	}
	
}
