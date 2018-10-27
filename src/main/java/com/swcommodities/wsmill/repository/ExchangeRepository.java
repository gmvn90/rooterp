package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.Exchange;

/**
 * Created by dunguyen on 9/16/16.
 */
@Transactional
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    /**
     * Return the user having the passed email or null if no user is found.
     *
     */
    Exchange getFirstObject();

}