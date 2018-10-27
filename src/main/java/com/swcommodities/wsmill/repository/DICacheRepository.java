package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.DICache;

@Transactional
public interface DICacheRepository extends JpaRepository<DICache, Integer> {
	DICache findByDeliveryInstruction_Id(Integer id);
	DICache findByDeliveryInstruction(DeliveryInstruction deliveryInstruction);
	List<DICache> findByDeliveryInstructionIdIn(List<Integer> ids);
}