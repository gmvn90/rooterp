/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 *
 * @author macOS
 */
public class ApprovalStatusEqual<T> implements Specification<T> {

    private ApprovalStatus approvalStatus;

    public ApprovalStatusEqual(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate res = cb.and();
        if (approvalStatus != null && approvalStatus != ApprovalStatus.NO_STATUS) {
            res = cb.equal(root.<ApprovalStatus>get("claimStatus"), approvalStatus);
        }
        return res;
    }
}
