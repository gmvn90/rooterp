/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.CompanyDao;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.view.CompanyView;

/**
 *
 * @author kiendn
 */
@SuppressWarnings("rawtypes")
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyService {
    private CompanyDao companyDao;

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    
    public ArrayList<CompanyMaster> getAllCompanyNames() {
        return companyDao.getAllCompanyNames();
    }
    
    public CompanyMaster getCompanyById(int id){
        return companyDao.getCompanyById(id);
    }
    
    public void update(CompanyMaster company) {
        companyDao.update(company);
    }
    
    public long countRow() {
        return companyDao.countRow();
    }
    
    public long getTotalAfterFilter() {
        return companyDao.getTotalAfterFilter();
    }
    
    public ArrayList<CompanyMaster> searchGlobe(String searchTerm, String order, int start, int amount, String colName, Byte active, int company_type) {
        return companyDao.searchGlobe(searchTerm, order, start, amount, colName, active, company_type);
    }
    
    public String getCountryShortNameById(int id) {
        return companyDao.getCountryShortNameById(id);
    }
    
    public CompanyView getLazyCompanyById(int id) {
        return companyDao.getLazyCompanyById(id);
    }
    
    public ArrayList<HashMap> getCompanyInMovement(){
        return companyDao.getCompanyInMovement();
    }
    
    public ArrayList<CompanyMaster> getRefListCompanies() {
        return companyDao.getRefListCompanies();
    }
    
    public ArrayList<HashMap> getCompaniesMap(){
        return companyDao.getCompaniesMap();
    }
    
    public String getCompanyTypeList() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("companytype_list", companyDao.getCompanyTypeList());
        
        return json.toString();
    }
    
    public String getCompanyTypes() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("select", companyDao.getCompanyTypeList());
        
        return json.toString();
    }
    
    public ArrayList<Map> getTypes(int comp_id){
        return companyDao.getTypes(comp_id);
    }
    
    public void deleteCompanyRoles(int company_id, int role_id) {
        companyDao.deleteCompanyRoles(company_id, role_id);
    }
    
    public void UpdateCompanyRoles(int company_id, int role_id) {
        companyDao.updateCompanyRoles(company_id, role_id);
    }
    
    public boolean checkRole(int company_id, int role_id) {
        return companyDao.checkRole(company_id, role_id);
    }
    
    public ArrayList<HashMap> getCompaniesOf(String type, int company_id) {
        return companyDao.getCompaniesOf(type, company_id);
    }
    
    public ArrayList<HashMap> getGradesOfCompany(String type, int company_id) {
        return companyDao.getGradesOfCompany(type, company_id);
    }
    
	public ArrayList<HashMap> getCompaniesInStockByType(String type) {
        return companyDao.getCompaniesInStockByType(type);
    }
    
    public ArrayList<HashMap> getCompaniesByType(int type) {
        return companyDao.getCompaniesByType(type);
    }

    public ArrayList<HashMap> getCompaniesByTypeMaster(Integer companyTypeMasterId) {
        return companyDao.getCompaniesByTypeMaster(companyTypeMasterId);
    }
    
    public ArrayList<HashMap> getCompaniesInStockByMapAndType(String type, int map_id) {
        return companyDao.getCompaniesInStockByMapAndType(type, map_id);
    }
    
    public ArrayList<HashMap> getClientListInSystem() {
    	return companyDao.getClientListInSystem();
    }
    
    public ArrayList<HashMap> getTodayProcessingClient() {
    	return companyDao.getTodayProcessingClient();
    }
}
