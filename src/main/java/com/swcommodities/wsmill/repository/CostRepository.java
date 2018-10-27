/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trung
 */
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;

/**
 * Created by dunguyen on 8/12/16.
 */
@Transactional
public interface CostRepository extends JpaRepository<Cost, Long> {
    List<Cost> findByCompany_Id(int id);

    Cost findFirstByCompany_IdAndOption_Id(int companyId, long optionId);
    
    @Query("select u from Cost u where u.option = ?1")
    Cost findByABC(OperationalCost option);
    
    @Query("select u from Cost u where u.id = ?1")
    List<Cost> findDEF(Long id);
    
    List<CompanyMaster> findCompaniesThatHasCost();


}
