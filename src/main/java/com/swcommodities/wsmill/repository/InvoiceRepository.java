package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by gmvn on 11/30/16.
 */
@Transactional
public interface InvoiceRepository extends ClientRefJpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    public List<RefList> findRefList();

    public String getNewInvoiceRef();
    
    List<Invoice> getResult(String whereClause, int offset, int limit);
    Long countResult(String whereClause);
    
    InvoiceAggregateInfo findAggregateInfo();
    InvoiceAggregateInfo findAggregateInfo(int clientId);
    InvoiceAggregateInfo findAggregateInfo(List<SearchCriteria> list);
    InvoiceAggregateInfo findAggregateInfo(String whereClauseWithAnd);
}
