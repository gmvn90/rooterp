/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification.ss;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data 
public class SSApprovalStatus implements Specification<SampleSent> {
    
    private final ApprovalStatus approvalStatus;

    @Override
    public Predicate toPredicate(Root<SampleSent> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate res = cb.and();
        if (approvalStatus != null && approvalStatus != ApprovalStatus.NO_STATUS) {
            res = cb.equal(root.<ApprovalStatus>get("approvalStatusEnum"), approvalStatus);
        }
        return res;
    }
    
}