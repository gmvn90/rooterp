package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;

/**
 * Created by gmvn on 10/26/16.
 */
public interface SampleSentRepository extends JpaRepository<SampleSent, Integer>, JpaSpecificationExecutor {

    public List<SampleSent> findByShippingInstructionBySiId_Id(int shipping_instruction_id);

    public List<SampleSent> findByShippingInstructionBySiId_companyMasterByClientId_Id(int company_id);

    List<SampleSent> findByApprovalStatus(byte approvalStatus);
    
    SampleSent findFirstByRefNumber(String refNumber);

    Long countResult(String whereClause);

    List<SampleSentCache> getResult(String whereClause, int offset, int limit);

    @Query("select ss.id from SampleSent ss where ss.approvalStatus=0 and ss.shippingInstructionBySiId.companyMasterByClientId.id=:companyId")
    List<Integer> getPendingIds(@Param("companyId") int companyId);

    @Query("from SampleSent where year(createdDate) > (year(current_date) - 2)")
    List<SampleSent> getSampleSentsForUpdateCacheAll();

    @Query("select ss from SampleSentCache ss where ss.sampleSent.id=:id")
    SampleSentCache getBySS(@Param("id") int id);
    
    
}
