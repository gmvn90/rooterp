package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface WarehouseMapRepository extends ClientRefJpaRepository<WarehouseMap, Integer>, JpaSpecificationExecutor<WarehouseMap> {

}