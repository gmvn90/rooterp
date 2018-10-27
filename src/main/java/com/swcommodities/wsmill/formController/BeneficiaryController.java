/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.swcommodities.wsmill.hibernate.dto.BankAccount;
import com.swcommodities.wsmill.hibernate.dto.Beneficiary;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CurrencyType;
import com.swcommodities.wsmill.repository.BeneficiaryRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CountryRepository;

/**
 *
 * @author macOS
 */

@Controller
@SessionAttributes("beneficiaryForm") // Specify attributes to be stored in the session
@Transactional
@RequestMapping("/beneficiaries")
public class BeneficiaryController {
    @Autowired BeneficiaryRepository beneficiaryRepository;
    @Autowired CompanyRepository companyRepository;
    @Autowired CountryRepository countryRepository;
    @Autowired CompanyTypeMasterRepository companyTypeMasterRepository;
    
    @RequestMapping(value = "/show.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id, @RequestParam(required = false) Integer tryId, HttpSession httpSession) {
        
        BeneficiaryForm form = new BeneficiaryForm();
        int currentId = -1;
        boolean is_form_edit = false;
        boolean canShowData = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            Beneficiary beneficiary = beneficiaryRepository.findOne(id);
            form.setItem(beneficiary);
            Map<Integer, String> currs = CurrencyType.getAllReverse();
            for(BankAccount acc: beneficiary.getBankAccounts()) {
                acc.setCurrencyString(currs.get(acc.getCurrency()));
            }
            httpSession.setAttribute("beneficiary", currentId);
        } else if(tryId != null) {
            CompanyMaster company = companyRepository.findOne(tryId);
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setRepresentative(company.getRepresentative());
            beneficiary.setRepresentativeRole(company.getRepresentativeRole());
            beneficiary.setEmail(company.getEmail());
            beneficiary.setFax(company.getFax());
            beneficiary.setTelephone(company.getTelephone());
            beneficiary.setCompanyMaster(company);
            System.out.println("companyid: " + company.getId());
            form.setItem(beneficiary);
        }
        if(id != null || tryId != null) {
            canShowData = true;
        }
        model.put("canShowData", canShowData);
        model.put("beneficiaryForm", form);
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        model.put("companies", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Beneficiary").getId()));
        model.put("countries", countryRepository.findAll());
        model.put("banks", companyRepository.findByLocalName("Bank"));
        model.put("currencies", CurrencyType.getAllReverse());
        return "company-beneficiary";
    }
    
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String post(Map<String, Object> model, @ModelAttribute("beneficiaryForm") BeneficiaryForm beneficiaryForm) {
        Beneficiary beneficiary = beneficiaryForm.getItem();
        beneficiaryRepository.save(beneficiary);
        return "redirect:/beneficiaries/show.htm?id=" + beneficiary.getId();
    }

    @RequestMapping(value = "/all.htm", method = RequestMethod.GET)
    public String showAll(Map<String, Object> model) {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        model.put("beneficiaries", beneficiaries);
        return "company-beneficiary-list";
    }
}
