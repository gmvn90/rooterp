package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by gmvn on 12/1/16.
 */
public interface CourierMasterRepository extends ClientRefJpaRepository<CourierMaster, Integer>, JpaSpecificationExecutor<CourierMaster> {
}
