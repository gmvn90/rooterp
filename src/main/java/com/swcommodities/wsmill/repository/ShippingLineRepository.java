package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;

/**
 * Created by gmvn on 10/26/16.
 */
public interface ShippingLineRepository extends JpaRepository<ShippingLineMaster, Integer> {
}
