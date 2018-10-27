/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification.di;

import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author macOS
 */

@Data
public class DIPendingCompletionStatus implements Specification<DeliveryInstruction> {
        
    @Override
    public Predicate toPredicate(Root<DeliveryInstruction> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate res = cb.equal(root.<DeliveryInstruction>get("status"), (byte) 0);
        return res;
    }
    
}
