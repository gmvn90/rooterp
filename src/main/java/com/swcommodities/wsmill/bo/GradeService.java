/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;

import com.swcommodities.wsmill.hibernate.dao.GradeDao;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;

/**
 *
 * @author kiendn
 */
public class GradeService {
    
    
    private GradeDao gradeDao;

    public void setGradeDao(GradeDao gradeDao) {
        this.gradeDao = gradeDao;
    }
    
    public ArrayList<GradeMaster> getAllGradeNames() {
        return gradeDao.getAllGradeNames();
    }
    
    public GradeMaster getGradeById(int id){
        return gradeDao.getGradeById(id);
    }
    
    public ArrayList<HashMap> getAllGradeNamesMap() {
        return gradeDao.getAllGradeNamesMap();
    }
    
    public ArrayList<HashMap> getGradeInStock(){
        return gradeDao.getGradeInStock();
    }
    
    public ArrayList<HashMap> getGradeInMovement(){
        return gradeDao.getGradeInMovement();
    }
    
    public ArrayList<HashMap> getGradeInWeightNote(String type){
        return gradeDao.getGradeInWeightNote(type);
    }
    
    public ArrayList<HashMap> getGradeInStock(int map_id, int client_id, int pledge_id){
        return gradeDao.getGradeInStock(map_id, client_id, pledge_id);
    }
    
    public ArrayList<HashMap> allocatedGrade(int id) {
        return gradeDao.allocatedGrade(id);
    }
}
