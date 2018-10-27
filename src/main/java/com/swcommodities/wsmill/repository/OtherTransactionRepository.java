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

import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;

@Transactional
public interface OtherTransactionRepository extends JpaRepository<OtherTransaction, Integer>{
    
    @Query("select t from OtherTransaction t where t.invoice.id = ?1")
    public List<OtherTransaction> getTransactionsByInvoice(int invoiceId);
}