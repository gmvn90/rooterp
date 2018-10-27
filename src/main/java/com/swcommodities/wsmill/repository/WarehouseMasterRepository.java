package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swcommodities.wsmill.hibernate.dto.WarehouseMaster;

/**
 * Created by gmvn on 11/28/16.
 */
public interface WarehouseMasterRepository extends JpaRepository<WarehouseMaster, Integer>, JpaSpecificationExecutor {
}
