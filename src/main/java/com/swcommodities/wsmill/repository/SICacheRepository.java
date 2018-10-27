package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.SICache;

@Transactional
public interface SICacheRepository extends JpaRepository<SICache, Integer> {
	SICache findByShippingInstruction_Id(Integer id);

	SICache findByShippingInstruction(ShippingInstruction shippingInstruction);

	@Query(value = "from SICache where shippingInstruction.id in (:ids) order by loadingDate, firstDate desc")
	List<SICache> findByShippingInstructionIdIn(@Param("ids") List<Integer> ids);
}