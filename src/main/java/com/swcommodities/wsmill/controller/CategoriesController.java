package com.swcommodities.wsmill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.repository.CategoryRepository;

/**
 * Created by dunguyen on 7/18/16.
 */

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryDao;

    @RequestMapping(method= RequestMethod.GET, value = "/categories")
    ResponseEntity<Iterable<Category>> get() {
        Iterable<Category> categories = categoryDao.findByParentIsNull(new Sort(Sort.Direction.ASC, "id"));
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }
}
