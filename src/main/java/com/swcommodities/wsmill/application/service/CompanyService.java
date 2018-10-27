/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.UserRepository;
import java.util.ArrayList;

/**
 *
 * @author macOS
 */

@Service("application_service_CompanyService")
public class CompanyService {
    
    @Autowired CompanyRepository companyRepository;
    @Autowired CompanyTypeMasterRepository companyTypeMasterRepository;
    @Autowired UserRepository userRepository;
    
    private List<CompanyMaster> findByCompanyTypes(String companyType) {
        return companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName(companyType).getId());
    }
    
    private List<CompanyMaster> addEmptyCompany(List<CompanyMaster> list) {
        List<CompanyMaster> result = new ArrayList<>(list);
        result.add(0, new CompanyMaster(-1, "----"));
        return result;
    }
    
    public List<CompanyMaster> findAllClients() {
        return findByCompanyTypes("Client");
    }
    
    public List<CompanyMaster> findAllClientsWithEmpty() {
        return addEmptyCompany(findAllClients());
    }
    
    public List<CompanyMaster> findAllBuyers() {
        return findByCompanyTypes("Buyer");
    }
    
    public List<CompanyMaster> findAllBuyersWithEmpty() {
        return addEmptyCompany(findByCompanyTypes("Buyer"));
    }
    
    public List<CompanyMaster> findAllShippers() {
        return findByCompanyTypes("Shipper");
    }
    
    public List<CompanyMaster> findAllShippersWithEmpty() {
        return addEmptyCompany(findAllShippers());
    }
    
    public List<CompanyMaster> findAllSuppliers() {
        return findByCompanyTypes("Supplier");
    }
    
    public List<CompanyMaster> findAllSuppliersWithEmpty() {
        return addEmptyCompany(findAllSuppliers());
    }
    
    public List<CompanyMaster> findAllNotifyParties() {
        return findByCompanyTypes("Notify Party");
    }
    
    public List<CompanyMaster> findAllConsignees() {
        return findByCompanyTypes("Consignee");
    }
    
    public String getName(int company) {
        return companyRepository.findOne(company).getName();
    }
    
    public Map<String, Boolean> getPermissionsMap(int id) {
        Map<String, Boolean> result = new HashMap<>();
        User user = userRepository.findOne(id);
        user.getAuthorizations().forEach(auth -> result.put("perm_" + auth.getPage().getId(), auth.getPermission().equals(Byte.valueOf("1"))));
        return result;
    }
    
}
