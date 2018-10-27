package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface OriginRepository extends ClientRefJpaRepository<OriginMaster, Integer>, JpaSpecificationExecutor<OriginMaster> {
    List<OriginMaster> findByCountry_Iso2(String iso2);
}