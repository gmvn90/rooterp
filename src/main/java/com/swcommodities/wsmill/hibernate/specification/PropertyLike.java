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

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author macOS
 */
public class PropertyLike<T> implements Specification<T>{
    private String name;
    private String value;
    
    public PropertyLike(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(StringUtils.isEmpty(value)) {
            return cb.and();
        }
        return cb.equal(root.get(name), value);
    }
}
