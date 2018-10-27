/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.MarketFobDiff;
import com.swcommodities.wsmill.repository.MarketFobDiffRepository;

/**
 *
 * @author macOS
 */

@Service
public class MarketFobService {
    @Autowired private final MarketFobDiffRepository marketFobDiffRepository;
    
    public MarketFobService(MarketFobDiffRepository marketFobDiffRepository) {
        this.marketFobDiffRepository = marketFobDiffRepository;
    }
    
    public List<MarketFobDiff> getAllExceptUnused() {
        List<MarketFobDiff> all = marketFobDiffRepository.findAll();
        List<MarketFobDiff> result = new ArrayList<>();
        for(MarketFobDiff marketFobDiff: all) {
            GradeMaster gradeMaster = marketFobDiff.getGradeMaster();
            if(gradeMaster != null && ! gradeMaster.getName().startsWith("Z")) {
                result.add(marketFobDiff);
            }
        }
        Collections.sort(result, new Comparator<MarketFobDiff>() {
            @Override
            public int compare(MarketFobDiff o1, MarketFobDiff o2) {
                if(o2.getGradeMaster() == null || o2.getGradeMaster().getName() == null) {
                    return 1;
                }
                if(o1.getGradeMaster() == null || o1.getGradeMaster().getName() == null) {
                    return -1;
                }
                return o1.getGradeMaster().getName().compareTo(o2.getGradeMaster().getName());
            }
        });
        return result;
    }
}
