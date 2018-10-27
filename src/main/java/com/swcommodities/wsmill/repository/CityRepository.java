package com.swcommodities.wsmill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.City;

/**
 * Created by dunguyen on 9/15/16.
 */
@Transactional
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

}

