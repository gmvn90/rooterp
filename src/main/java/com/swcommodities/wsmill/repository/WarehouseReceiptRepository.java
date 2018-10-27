package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt;

/**
 * Created by dunguyen on 11/10/16.
 */
@Transactional
public interface WarehouseReceiptRepository extends JpaRepository<WarehouseReceipt, Integer> {

    @Query("select distinct w.companyMasterByWeightControllerId.id from WarehouseReceipt w")
    List<Integer> getControllers1();

    @Query("select distinct w.companyMasterByQualityControllerId.id from WarehouseReceipt w")
    List<Integer> getControllers2();

}
