package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.TransactionItem;

/**
 * Created by gmvn on 11/30/16.
 */
@Transactional
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Integer>, JpaSpecificationExecutor<TransactionItem> {

}
