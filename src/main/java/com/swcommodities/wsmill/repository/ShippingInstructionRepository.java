package com.swcommodities.wsmill.repository;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.SICache;
import com.swcommodities.wsmill.hibernate.dto.contract.OwnerJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface ShippingInstructionRepository extends OwnerJpaRepository<ShippingInstruction, Integer>, JpaSpecificationExecutor<ShippingInstruction> {

    List<ShippingInstruction> findByStatusNotOrderByRefNumberDesc(byte status);
    List<ShippingInstruction> findByCompanyMasterByClientId_Id(Integer id);

    InstructionResult findTotalInfo(List<SearchCriteria> criteriaList);

    @Query("select distinct w.companyMasterByClientId.id from ShippingInstruction w")
    List<Integer> getClients();

    @Query("from Claim ")
    List<Claim> getAllClaims();

    @Query("select distinct w.companyMasterByBuyerId.id from ShippingInstruction w")
    List<Integer> getBuyers1();

    @Query("select distinct w.companyMasterByConsigneeId.id from ShippingInstruction w")
    List<Integer> getBuyers2();

    @Query("select distinct w.companyMasterByShipperId.id from ShippingInstruction w")
    List<Integer> getShippers();

    @Query("select distinct w.companyMasterBySupplierId.id from ShippingInstruction w")
    List<Integer> getSuppliers();

    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.id in (select wna.weightNoteReceiptByWnrId.id from WnrAllocation wna where wna.status != 2 and wna.instId = :instruction_id and wna.instType = 'E') and wnr.status!=2")
    Double getShippingAllocated(@Param("instruction_id") int instruction_id);

    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.weightNote.id in (select id from WeightNote wn where wn.shippingInstruction.id=:instruction_id and wn.status !=2) and wnr.status!=2")
    Double getShippingDeliverd(@Param("instruction_id") int instruction_id);

    List<ShippingInstructionListViewResult> findOwnerListview(int company_id);
    
    List<ShippingInstruction> findByStatus(byte status);
    
    @Query("select si.id from ShippingInstruction si where si.status=0 and si.companyMasterByClientId.id=:companyId order by si.loadDate, si.fromDate desc")
    List<Integer> getPendingIds(@Param("companyId") int companyId);

    InstructionResult getAggregateInfo(String whereClauseWithAnd);

    Long countResult(String whereClause);

    List<SICache> getResult(String whereClause, int offset, int limit);
    List<SICache> getResult(String whereClause, int offset, int limit, String orderBy);

    @Query(value = "select * from shipping_instruction where year(date) = year(current_date) order by ref_number desc limit 50 offset :number", nativeQuery = true)
    List<ShippingInstruction> getSiForUpdateCacheAll(@Param("number") int number);
    
    @Query("select di.id from ShippingInstruction di where di.shippingCost.clientSiCostListJson is not null")
    List<Integer> getCostableIds();
    
    ShippingInstruction findFirstByRefNumber(String refNumber);
    
    RefNumberCurrentInfoImpl findMaxShippingAdviceNumber();
    RefNumberCurrentInfoImpl findMaxSampleSentNumber();
    RefNumberCurrentInfoImpl findMaxShippingInstructionNumber();
    RefNumberCurrentInfoImpl findMaxClaimNumber();
}