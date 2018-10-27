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

import com.swcommodities.wsmill.hibernate.dto.Payment;

/**
 *
 * @author macOS
 */
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public String getNewPaymentRef();
    
    @Query("select t from Payment t where t.invoice.id = ?1")
    public List<Payment> getPaymentsByInvoice(int invoiceId);

    @Query("select t from Payment t where t.financeContract.id = ?1")
    public List<Payment> getPaymentsByFinanceContract(int financeContractId);

}
