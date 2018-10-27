/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.repository.projection.IdAndName;
import com.swcommodities.wsmill.repository.projection.RefNumber;

/**
 *
 * @author trung
 */

@Transactional
public interface WeightNoteRepository extends JpaRepository<WeightNote, Integer>, JpaSpecificationExecutor<WeightNote> {
	
	@Query("select new com.swcommodities.wsmill.repository.projection.RefNumber(refNumber as refNumber) from WeightNote where (type = 'IM' or type = 'EX') and status = 1")
	List<RefNumber> getAvailableRefNumber();
	
}
