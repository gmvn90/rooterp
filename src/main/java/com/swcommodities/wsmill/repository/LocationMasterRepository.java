package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swcommodities.wsmill.hibernate.dto.LocationMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by gmvn on 11/30/16.
 */
public interface LocationMasterRepository extends ClientRefJpaRepository<LocationMaster, Integer>, JpaSpecificationExecutor<LocationMaster> {
    List<LocationMaster> findAllByOrderByNameAsc();
}
