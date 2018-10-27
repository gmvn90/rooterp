package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.PICache;

@Transactional
public interface PICacheRepository extends JpaRepository<PICache, Integer> {
	PICache findByProcessingInstruction_Id(Integer id);
	List<PICache> findByProcessingInstructionIdIn(List<Integer> ids);
	PICache findByProcessingInstruction(ProcessingInstruction processingInstruction);
}
