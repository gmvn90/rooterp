/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientRepository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.clientDto.ClientDailyBasisHistory;

/**
 *
 * @author macOS
 */
public interface ClientDailyBasisHistoryRepository extends JpaRepository<ClientDailyBasisHistory, Serializable>{
    ClientDailyBasisHistory findTopByOrderByIdDesc();
}
