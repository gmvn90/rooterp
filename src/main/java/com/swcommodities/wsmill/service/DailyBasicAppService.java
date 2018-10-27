/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;
import com.swcommodities.wsmill.hibernate.dto.service.TerminalMonthYear;
import com.swcommodities.wsmill.repository.DailyBasisRepository;

/**
 *
 * @author macOS
 */

@Service
public class DailyBasicAppService {
    
    @Autowired private DailyBasisRepository dailyBasisRepository;
    
    public void createNextTerminalMonthsIfNotExisted() {
        TerminalMonthYear terminalMonthYear = com.swcommodities.wsmill.hibernate.dto.service.DailyBasisService.getCurrentTerminalMonthYear();
        List<TerminalMonthYear> list = com.swcommodities.wsmill.hibernate.dto.service.DailyBasisService.getNextTerminalMonthIncludeCurrentAsc(terminalMonthYear);
        for(int i = 0; i < list.size(); i++) {
            DailyBasis dailyBasis = dailyBasisRepository.findFirstByTerminalMonth(list.get(i).getThreeLetterMonthYearValue());
            if(dailyBasis == null) {
                dailyBasis = new DailyBasis(list.get(i).getThreeLetterMonthYearValue(), 0, 0, list.get(i).getValue(), 0, new Date());
                dailyBasisRepository.save(dailyBasis);
            }
        }
    }
}
