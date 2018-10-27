package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.ProcessingType;

/**
 * Created by gmvn on 10/13/16.
 */
public interface ProcessingTypeRepository extends JpaRepository<ProcessingType, Integer> {
}
