/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientRepository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.clientDto.ClientExchangeHistory;

/**
 *
 * @author macOS
 */

@Transactional
@Repository
public interface ClientExchangeHistoryRepository extends JpaRepository<ClientExchangeHistory, Serializable>{
    ClientExchangeHistory findTopByOrderByIdDesc();
}
