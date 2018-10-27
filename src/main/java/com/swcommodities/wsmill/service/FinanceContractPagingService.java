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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.FinanceContractAssembler;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractPagingResult;
import com.swcommodities.wsmill.hibernate.specification.FinanceContractDateBetweenDates;
import com.swcommodities.wsmill.hibernate.specification.FinanceContractPropertyLike;
import com.swcommodities.wsmill.hibernate.specification.OwnerSpecification;
import com.swcommodities.wsmill.repository.FinanceContractRepository;

/**
 *
 * @author macOS
 */

@Service
public class FinanceContractPagingService {
    
    @Autowired PagingService pagingService;
    @Autowired FinanceContractRepository financeContractRepository;
    @Autowired FinanceContractService financeContractService;
    
    public FinanceContractPagingResult getAggregateInfo(Integer clientId, Date startDate, Date endDate, String searchText, Integer page, Integer perPage) {
        FinanceContractPagingResult result = new FinanceContractPagingResult();
        if(perPage == null) {
    		perPage = 20;
    	}
        if(page == null) {
            page = 0;
        } else {
            page = page - 1;
        }
        if(searchText == null) {
            searchText = "";
        }
        FinanceContractAssembler assembler = new FinanceContractAssembler();
        Specification<FinanceContract> spec = Specifications
            .where(new OwnerSpecification(clientId))
            .and(new FinanceContractDateBetweenDates(startDate, endDate))
            .and(new FinanceContractPropertyLike("refNumber", searchText));
        Pageable pageable = new PageRequest(page, perPage);
        Page<FinanceContract> pageResult = financeContractRepository.findAll(spec, pageable);
        
        result.setPerPage(perPage);
        result.setPage(page);
        result.setTotalPages(pageResult.getTotalPages());
        result.setTotalRecords((int) pageResult.getTotalElements());
        result.setPagination(pagingService.getPagingHtml(pageResult.getTotalPages(), page + 1)); // 1-based
        List<FinanceContractDTO> list = new ArrayList<>();
        for(FinanceContract contract: pageResult.getContent()) {
            FinanceContract fullInfo = financeContractService.getFinanceContractWithDetailNumer(contract.getId());
            list.add(assembler.toDto(fullInfo));
        }
        result.setResults(list);  
        
        return result;
        
    }
    
}
