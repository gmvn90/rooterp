/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author macOS
 */

public interface SampleTypeRepository extends JpaRepository<SampleType, String>, JpaSpecificationExecutor<SampleType> {
    
    public RefNumberCurrentInfoImpl findMaxSampleTypeNumber();
    public SampleType findByRefNumber(String refNumber);
    
}
