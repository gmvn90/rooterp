/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.clientDto.MarketFobDiffHistory;

/**
 *
 * @author macOS
 */

@Component
@Transactional
public interface MarketFobDiffHistoryRepository extends JpaRepository<MarketFobDiffHistory, Integer> {
    MarketFobDiffHistory findTopByOrderByIdDesc();
}
