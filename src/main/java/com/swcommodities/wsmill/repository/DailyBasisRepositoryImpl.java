/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;

/**
 *
 * @author macOS
 */

@Component
public class DailyBasisRepositoryImpl {
    
    @Autowired DailyBasisRepository dailyBasisRepository;
    
    public List<DailyBasis> findMostRecentDailyBasisesDesc() {
        List<DailyBasis> dailyBasises = dailyBasisRepository.findTop6ByOrderByTmCodeDesc();
        Collections.sort(dailyBasises, new Comparator<DailyBasis>(){
            @Override
            public int compare(DailyBasis o1, DailyBasis o2) {
                if(o1.getTmCode() == o2.getTmCode()) {
                    return 0;
                }
                return o1.getTmCode() > o2.getTmCode() ? 1 : -1;
            }
            
        });
        return dailyBasises;
    }
}
