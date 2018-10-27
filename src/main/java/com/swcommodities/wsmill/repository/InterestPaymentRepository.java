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

import com.swcommodities.wsmill.hibernate.dto.InterestPayment;

/**
 *
 * @author macOS
 */
@Transactional
public interface InterestPaymentRepository extends JpaRepository<InterestPayment, Integer> {
    public String getNewInterestPaymentRef();

    @Query("select t from InterestPayment t where t.financeContract.id = ?1")
    public List<InterestPayment> getInterestPaymentsByFinanceContract(int financeContractId);

}
