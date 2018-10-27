package com.swcommodities.wsmill.repository;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.hibernate.dto.cache.PICache;
import com.swcommodities.wsmill.hibernate.dto.contract.OwnerJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by gmvn on 10/12/16.
 */
@Transactional
public interface ProcessingInstructionRepository extends OwnerJpaRepository<ProcessingInstruction, Integer>, JpaSpecificationExecutor<ProcessingInstruction> {
    List<ProcessingInstruction> findByStatusNotOrderByRefNumberDesc(byte status);
    
    InstructionResult findTotalInfo(List<SearchCriteria> criteriaList);

    @Query("select distinct w.companyMasterByClientId.id from ProcessingInstruction w")
    List<Integer> getClients();

    List<RefList> findRefList();

    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.id in (select wna.weightNoteReceiptByWnrId.id from WnrAllocation wna where wna.status != 2 and wna.instId = :instruction_id and wna.instType = 'P') and wnr.status!=2")
    Double getProcessingAllocated(@Param("instruction_id") int instruction_id);

    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.weightNote.id in (select id from WeightNote wn where wn.processingInstruction.id=:instruction_id and wn.status !=2 and wn.type = 'IP') and wnr.status!=2")
    Double getProcessingInprocess(@Param("instruction_id") int instruction_id);

    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.weightNote.id in (select id from WeightNote wn where wn.processingInstruction.id=:instruction_id and wn.status !=2 and wn.type = 'XP') and wnr.status!=2")
    Double getProcessingExprocess(@Param("instruction_id") int instruction_id);

    List<ProcessingInstructionListViewResult> findOwnerListview(int company_id);
    
    List<ProcessingInstruction> findByStatus(byte status);
    
    @Query("select di.id from ProcessingInstruction di where di.status=0 and di.companyMasterByClientId.id=:companyId")
    List<Integer> getPendingIds(@Param("companyId") int companyId);

    InstructionResult getAggregateInfo(String whereClauseWithAnd);

    Long countResult(String whereClause);

    List<PICache> getResult(String whereClause, int offset, int limit);

    @Query(value = "select * from processing_instruction where year(created_date) = year(current_date) order by ref_number desc limit 50 offset :number", nativeQuery = true)
    List<ProcessingInstruction> getPiForUpdateCacheAll(@Param("number") int number);

    List<TransactionItem> getPiTransactionItemBasic(int piId);
    ProcessingInstruction findFirstByRefNumber(String refNumber);
    RefNumberCurrentInfoImpl findMaxProcessingInstructionNumber();
    
}
