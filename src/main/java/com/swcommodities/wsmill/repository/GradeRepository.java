package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.repository.projection.IdAndName;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface GradeRepository extends ClientRefJpaRepository<GradeMaster, Integer>, JpaSpecificationExecutor<GradeMaster> {
    List<GradeMaster> findAllByOrderByNameAsc();
    default List<GradeMaster> findAllByOrderByNameAscWithEmpty() {
        List<GradeMaster> result = findAllByOrderByNameAsc();
        result.add(0, GradeMaster.fromIdAndName(-1, "---"));
        return result;
    }
    List<RefList> findRefListWithoutNonactive();
}