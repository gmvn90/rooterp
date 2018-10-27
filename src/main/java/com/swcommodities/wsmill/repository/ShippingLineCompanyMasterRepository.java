package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by gmvn on 10/26/16.
 */
public interface ShippingLineCompanyMasterRepository extends ClientRefJpaRepository<ShippingLineCompanyMaster, Integer>, JpaSpecificationExecutor<ShippingLineCompanyMaster> {
    List<ShippingLineCompanyMaster> findAllByOrderByNameAsc();
}
