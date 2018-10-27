package com.swcommodities.wsmill.service;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.query.result.SampleSentPagingResult;
import com.swcommodities.wsmill.hibernate.specification.ss.HomePageFilter;
import com.swcommodities.wsmill.hibernate.specification.ss.SSApprovalStatus;
import com.swcommodities.wsmill.repository.SSCacheRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.utils.Common;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;

@Service
public class SampleSentPagingService {
    
    @Autowired
    SampleSentRepository sampleSentRepository;
    @Autowired
    PagingService pagingService;
    private int defaultEmpty = -1;
    @Autowired SSCacheRepository sSCacheRepository;
    
    public List<SampleSentCache> getPendingSampleSentInHome() {
        return sSCacheRepository.findAll(Specifications.where(new HomePageFilter(ApprovalStatus.PENDING)), 
            new Sort(Sort.Direction.ASC, "firstDate").and(new Sort(Sort.Direction.ASC, "etaDate")));
    }
    
    public SampleSentPagingResult getAggregateInfo(Integer ssType, Integer clientId, Integer buyerId, String sampleRef, Byte sentStatus, Byte approvalStatus, Date startDate, Date endDate, Integer page, Integer perPage) {
        // page: 1-based
        SampleSentPagingResult result = new SampleSentPagingResult();
        String whereClauseWithAnd = "";
        if (!(clientId == defaultEmpty || clientId == null)) {
            whereClauseWithAnd += " and clientInt=" + clientId;
        }
        
        if (!(ssType == defaultEmpty || ssType == null)) {
            whereClauseWithAnd += " and typeInt=" + ssType;
        }
        
        if (!(buyerId == defaultEmpty || buyerId == null)) {
            whereClauseWithAnd += " and buyerInt=" + buyerId;
        }
        
        if (!(sampleRef == null || sampleRef.equals(""))) {
            whereClauseWithAnd += " and sampleRef like '%" + sampleRef.replace("'", "''") + "%'";
        }
        
        if (!(sentStatus == null || sentStatus == Byte.valueOf("-1"))) {
            whereClauseWithAnd += " and sentStatusInt=" + sentStatus;
        }
        
        if (!(approvalStatus == null || approvalStatus == Byte.valueOf("-1"))) {
            whereClauseWithAnd += " and approvalStatus='" + ApprovalStatus.getFromByte(approvalStatus).toString() + "'";
        }
        
        if (!(startDate == null)) {
            whereClauseWithAnd += " and firstDate >= '" + Common.getDateFromDatabase(startDate, Common.date_format_yyyyMMdd) + "'";
        }
        
        if (!(endDate == null)) {
            whereClauseWithAnd += " and firstDate <= '" + Common.getDateFromDatabase(endDate, Common.date_format_yyyyMMdd) + "'";
        }
        
        if (perPage == null) {
            perPage = 20;
        }
        
        System.out.println(whereClauseWithAnd);
        int offset = (page - 1) * perPage;
        
        result.setResults(sampleSentRepository.getResult(whereClauseWithAnd, offset, perPage));
        Long totalRecords = sampleSentRepository.countResult(whereClauseWithAnd);
        
        int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
        result.setPage(page);
        result.setPerPage(perPage);
        result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
        result.setTotalRecords(totalRecords.intValue());
        
        result.setTotalPages(numberOfPage);
        
        return result;
    }
    
    public SampleSentPagingResult getAggregateInfoForClientSite(Integer clientId, String sampleRef, Byte sentStatus, Byte approvalStatus,
        Date startDate, Date endDate, Integer page, Integer perPage) {
        // page: 1-based
        SampleSentPagingResult result = new SampleSentPagingResult();
        String whereClauseWithAnd = "";
        if (!(clientId == defaultEmpty || clientId == null)) {
            whereClauseWithAnd += " and clientInt=" + clientId;
        }
        
        if (!(sampleRef == null || sampleRef.equals(""))) {
            whereClauseWithAnd += " and sampleRef like '%" + sampleRef.replace("'", "''") + "%'";
        }
        
        if (!(sentStatus == null || sentStatus == Byte.valueOf("-1"))) {
            whereClauseWithAnd += " and sentStatusInt=" + sentStatus;
        }
        
        if (!(approvalStatus == null || approvalStatus == Byte.valueOf("-1"))) {
            whereClauseWithAnd += " and approvalStatusInt=" + approvalStatus;
        }
        
        if (!(startDate == null)) {
            whereClauseWithAnd += " and firstDate >= '" + Common.getDateFromDatabase(startDate, Common.date_format_yyyyMMdd) + "'";
        }
        
        if (!(endDate == null)) {
            whereClauseWithAnd += " and firstDate <= '" + Common.getDateFromDatabase(endDate, Common.date_format_yyyyMMdd) + "'";
        }
        
        if (perPage == null) {
            perPage = 20;
        }
        
        System.out.println(whereClauseWithAnd);
        int offset = (page - 1) * perPage;
        
        result.setResults(sampleSentRepository.getResult(whereClauseWithAnd, offset, perPage));
        Long totalRecords = sampleSentRepository.countResult(whereClauseWithAnd);
        
        int numberOfPage = (int) Math.ceil((double) totalRecords / perPage);
        result.setPage(page);
        result.setPerPage(perPage);
        result.setPagination(pagingService.getPagingHtml(numberOfPage, page));
        result.setTotalRecords(totalRecords.intValue());
        
        result.setTotalPages(numberOfPage);
        
        return result;
    }
    
}
