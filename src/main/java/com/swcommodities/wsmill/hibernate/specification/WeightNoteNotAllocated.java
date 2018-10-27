package com.swcommodities.wsmill.hibernate.specification;

import java.util.Arrays;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;

public class WeightNoteNotAllocated implements Specification<WeightNote> {

	@Override
    public Predicate toPredicate(Root<WeightNote> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return root.<WeightNote>get("type").in(Arrays.asList("IM", "XP"));
    }
	
}
