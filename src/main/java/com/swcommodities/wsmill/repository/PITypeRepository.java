package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface PITypeRepository extends ClientRefJpaRepository<PIType, Integer>, JpaSpecificationExecutor<PIType> {
    List<PIType> findAllByOrderByNameAsc();
}