/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import com.swcommodities.wsmill.hibernate.dto.Claim;
import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author macOS
 */
public class ParentShippingInstructionBelongToClient implements Specification<Claim> {
    private Integer clientId;
    private static final int DEFAULT_EMPTY = -1;
    
    public ParentShippingInstructionBelongToClient(Integer clientId) {
        this.clientId = clientId;
    }
    
    @Override
    public Predicate toPredicate(Root<Claim> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(clientId != null && clientId != DEFAULT_EMPTY) {
            return cb.equal(root.get("shippingInstructionBySiId").get("companyMasterByClientId").get("id"), clientId);
        }
        return cb.and();
    }
}
