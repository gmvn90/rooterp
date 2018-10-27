/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.Beneficiary;

/**
 *
 * @author macOS
 */
@Transactional
@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    
}
