package com.swcommodities.wsmill.hibernate.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * Created by dunguyen on 10/18/16.
 */


public class InstructionSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if(root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.greaterThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date) criteria.getValue());
            }
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }

        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if(root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.lessThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date) criteria.getValue());
            }
            return builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("=")) {
            Path path = null;
            for(String s: criteria.getKey().split("\\.") ) {
                if(path == null) {
                    path = root.get(s);
                } else {
                    path = path.get(s);
                }
            }
            return builder.equal(path, criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("!=")) {
            Path path = null;
            for(String s: criteria.getKey().split("\\.") ) {
                if(path == null) {
                    path = root.get(s);
                } else {
                    path = path.get(s);
                }
            }
            return builder.notEqual(path, criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("like")) {
            Path path = null;
            for(String s: criteria.getKey().split("\\.") ) {
                if(path == null) {
                    path = root.get(s);
                } else {
                    path = path.get(s);
                }
            }
            return builder.like(path, criteria.getValue().toString());
        }
        return null;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public InstructionSpecification<T> setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public InstructionSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public InstructionSpecification() {

    }

    public static InstructionSpecification<?> getClientEqualSpecification(Integer id) {
        return new InstructionSpecification(new SearchCriteria("companyMasterByClientId.id", "=", id));
    }

    public static InstructionSpecification getSupplierEqualSpecification(Integer id) {
        return new InstructionSpecification(new SearchCriteria("companyMasterBySupplierId.id", "=", id));
    }

    public static InstructionSpecification getGradeEqualSpecification(Integer id) {
        return new InstructionSpecification(new SearchCriteria("gradeMaster.id", "=", id));
    }

    public static InstructionSpecification getStatusEqualSpecification(Integer id) {
        return new InstructionSpecification(new SearchCriteria("status", "=", id));
    }
}