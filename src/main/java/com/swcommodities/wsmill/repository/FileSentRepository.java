/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.FileSent;

@Transactional
public interface FileSentRepository extends JpaRepository<FileSent, Long> {
}