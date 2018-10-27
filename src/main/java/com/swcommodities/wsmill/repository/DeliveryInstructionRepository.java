package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.DICache;
import com.swcommodities.wsmill.hibernate.dto.contract.OwnerJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 9/24/16.
 */

@Transactional
public interface DeliveryInstructionRepository extends OwnerJpaRepository<DeliveryInstruction, Integer>, JpaSpecificationExecutor<DeliveryInstruction> {
    List<DeliveryInstruction> findByStatusNotOrderByRefNumberDesc(byte status);
    Long countByStatus(byte status);

    @Query("SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(tons) as tons, SUM(tons) as deliverd, SUM(tons) as pending) FROM DeliveryInstruction di WHERE di.companyMasterByClientId.id=:clientId")
    public InstructionResult findAvgRatingByClientId(@Param("clientId") int clientId);

    InstructionResult findTotalInfo(List<SearchCriteria> criteriaList);

    @Query("select distinct w.companyMasterByPledger.id from DeliveryInstruction w")
    List<Integer> getPledgers();

    @Query("select distinct w.companyMasterByClientId.id from DeliveryInstruction w")
    List<Integer> getClients();

    @Query("select distinct w.qualificationCompany.id from DeliveryInstruction w")
    List<Integer> getControllers();

    @Query("select distinct w.companyMasterBySupplierId.id from DeliveryInstruction w")
    List<Integer> getSuppliers();
    
    @Query(value = "select coalesce(sum(wnr.grossWeight - wnr.tareWeight) / 1000,0) from WeightNoteReceipt wnr where wnr.weightNote.id in (select id from WeightNote wn where wn.deliveryInstruction.id=:instruction_id and wn.status !=2) and wnr.status!=2")
    Double getDeliveryDeliverd(@Param("instruction_id") int instruction_id);
        
    List<DeliveryInstruction> findByStatus(byte status);
    
    @Query("select di.id from DeliveryInstruction di where di.status=0")
    List<Integer> getPendingIds();
    
    @Query("select di.id from DeliveryInstruction di where di.status=0 and di.companyMasterByClientId.id=:companyId")
    List<Integer> getPendingIds(@Param("companyId") int companyId);
    
    InstructionResult getAggregateInfo(String whereClauseWithAnd);
    
    Long countResult(String whereClause);
    
    List<DICache> getResult(String whereClause, int offset, int limit);

    @Query(value = "select * from delivery_instruction where year(date) = year(current_date) order by ref_number desc limit 50 offset :number", nativeQuery = true)
    List<DeliveryInstruction> getDiForUpdateCacheAll(@Param("number") int number);
    
    
}