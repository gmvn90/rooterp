package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.PITypeItem;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface PITypeItemRepository extends JpaRepository<PITypeItem, Integer> {
}