/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;

/**
 *
 * @author macOS
 */
public interface DailyBasisRepository extends JpaRepository<DailyBasis, Integer>{
    // terminal month have format Feb-17
    DailyBasis findFirstByTerminalMonth(String terminalMonth);
    List<DailyBasis> findTop6ByOrderByTmCodeDesc();
    List<DailyBasis> findMostRecentDailyBasisesDesc();
}
