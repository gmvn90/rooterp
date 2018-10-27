package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

/**
 * Created by gmvn on 11/30/16.
 */
@Transactional
public interface TransactionRepository extends ClientRefJpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    public List<RefList> findRefList();

    public String getNewTransactionRef();
    
    @Query("select t from Transaction t where t.client.id = ?1 and t.type = ?2 and t.approvalStatus=1 and t.invoicedStatus = 0")
    public List<Transaction> getTransactionsByClientAndType(int clientId, int type);
    
    @Query("select t from Transaction t where t.invoice.id = ?1 and t.type = ?2 and t.approvalStatus=1")
    public List<Transaction> getTransactionsByInvoiceAndType(int invoiceId, int type);

    @Query("select t from Transaction t where t.si.id = ?1")
    public List<Transaction> getTransactionsBySiId(int siId);
}
