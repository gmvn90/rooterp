/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;

/**
 *
 * @author macOS
 */

public class OwnerSpecification implements Specification<FinanceContract> {
    
    private Integer clientId;
    
    public OwnerSpecification(Integer clientId) {
        this.clientId = clientId;
    }
    
    @Override
    public Predicate toPredicate(Root<FinanceContract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(clientId != null) {
            return cb.equal(root.get("client").get("id"), clientId);
        }
        return cb.and();
    }
}
