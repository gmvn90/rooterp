/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.CityDao;
import com.swcommodities.wsmill.hibernate.dto.City;

/**
 *
 * @author duhc
 */
public class CityService {
    private CityDao cityDao;

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }
    
    public ArrayList<City> getAllCity() {
        return cityDao.getAllCity();
    }
    
    public City getCityById(int id){
        return cityDao.getCityById(id);
    }
}
