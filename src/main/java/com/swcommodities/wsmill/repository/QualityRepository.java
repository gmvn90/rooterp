package com.swcommodities.wsmill.repository;

import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.QualityMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface QualityRepository extends ClientRefJpaRepository<QualityMaster, Long> {
    QualityMaster findByName(String name);
}