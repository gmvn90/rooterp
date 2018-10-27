package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.Category;


/**
 * A DAO for the entity User is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author trung
 */

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Return the user having the passed email or null if no user is found.
     *
     */
    List<Category> findByParentIsNull(Sort sort);

}