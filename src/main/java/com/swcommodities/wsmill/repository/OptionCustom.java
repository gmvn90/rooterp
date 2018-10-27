/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.OperationalCost;

/**
 * Created by trung on 7/27/16.
 */
@Component
public interface OptionCustom {
    public void updateRelation(OperationalCost option);
    <S extends OperationalCost> S saveAndUpdateRelation(S option);
}
