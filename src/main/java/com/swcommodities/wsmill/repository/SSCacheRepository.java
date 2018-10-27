package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Transactional
public interface SSCacheRepository extends JpaRepository<SampleSentCache, Integer>, JpaSpecificationExecutor {
	SampleSentCache findBySampleSent_Id(Integer id);
	List<SampleSentCache> findBySampleSentIdIn(List<Integer> ids);
	SampleSentCache findBySampleSent(SampleSent sampleSent);
    SampleSentCache findBySampleRef(String refNumber);
}