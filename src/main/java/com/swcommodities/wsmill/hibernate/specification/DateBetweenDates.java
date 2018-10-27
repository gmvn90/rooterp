/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author macOS
 */
public class DateBetweenDates<T> implements Specification<T> {
    
    private Date startDate;
    private Date endDate;
    private String propertyToCompare = "created";
        
    public DateBetweenDates(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public DateBetweenDates(Date startDate, Date endDate, String propertyToCompare) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.propertyToCompare = propertyToCompare;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate res = cb.and();
        if(startDate != null) {
            res = cb.greaterThanOrEqualTo(root.<Date>get(propertyToCompare), startDate);
        } 
        if(endDate != null) {
            res = cb.and(res, cb.lessThanOrEqualTo(root.<Date>get(propertyToCompare), endDate));
        }
        return res;
    }
}
