/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import java.util.List;

import com.swcommodities.wsmill.domain.model.SICustomCost;

/**
 *
 * @author macOS
 */
public class ListCustomCostToCustomCost {
    public List<SICustomCost> fromObject(List<SICustomCost> source) {
        return source;
    }
}
