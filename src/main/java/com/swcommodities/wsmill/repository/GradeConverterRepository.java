/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.GradeConverter;

/**
 *
 * @author macOS
 */
public interface GradeConverterRepository extends JpaRepository<GradeConverter, Integer>{
    GradeConverter findByGradeMaster_Id(int id);
}
