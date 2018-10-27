/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.MovementDao;
import com.swcommodities.wsmill.hibernate.dto.Movement;
import com.swcommodities.wsmill.hibernate.dto.view.MovementView;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class MovementService {
    private MovementDao movementDao;

    public void setMovementDao(MovementDao movementDao) {
        this.movementDao = movementDao;
    }
    
    public int saveMovementView(MovementView move){
        return movementDao.saveMovementView(move);
    }
    
    public String getNewMovementRef(){
        return movementDao.getNewMovementRef();
    }
    
    public ArrayList<HashMap> getMovementRefList(String searchString) {
        return movementDao.getMovementRefList(searchString);
    }
    
    public ArrayList<Movement> getMovementRefList(){
        return movementDao.getMovementRefList();
    }
    
    public Movement getMovementById(int id){
        return movementDao.getMovementById(id);
    }
    
    public HashMap getAllocatedMoved(int moveId) {
        return movementDao.getAllocatedMoved(moveId);
    }
}
