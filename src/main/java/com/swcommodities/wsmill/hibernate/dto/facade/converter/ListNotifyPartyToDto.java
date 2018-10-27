/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import java.util.HashSet;
import java.util.Set;

import com.swcommodities.wsmill.domain.model.common.BaseIdAndNameImpl;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;

/**
 *
 * @author macOS
 */
public class ListNotifyPartyToDto {
    public Set<BaseIdAndNameImpl> toDto(Set<CompanyMaster> parties) {
        Set<BaseIdAndNameImpl> res = new HashSet<>();
        parties.forEach(com -> res.add(new BaseIdAndNameImpl(com.getId(), com.getName())));
        return res;
    }
}
