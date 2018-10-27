/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.specification.pi;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
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
public class PendingCompletionStatus implements Specification<ProcessingInstruction> {
    
    private final CompletionStatus completionStatus;

    @Override
    public Predicate toPredicate(Root<ProcessingInstruction> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate res = cb.and();
        if (completionStatus != null && completionStatus != CompletionStatus.NO_STATUS) {
            res = cb.equal(root.<CompletionStatus>get("completionStatus"), completionStatus);
        }
        return res;
    }
    
}
