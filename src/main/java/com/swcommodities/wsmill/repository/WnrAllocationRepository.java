package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import java.util.List;

/**
 * Created by dunguyen on 10/24/16.
 */
public interface WnrAllocationRepository extends JpaRepository<WnrAllocation, Integer> {
    WnrAllocation findByWeightNoteReceiptByWnrId_Id(Integer id);
    List<WnrAllocation> findByWeightNoteReceiptByWnrId_IdIn(List<Integer> ids);
}
