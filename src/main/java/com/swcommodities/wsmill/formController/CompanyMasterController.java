package com.swcommodities.wsmill.formController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CountryRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("companyMasterForm") // Specify attributes to be stored in the session
@Transactional
public class CompanyMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    private boolean isEdit(CompanyMaster companyMaster) {
        return companyMaster.getId() != null;
    }

    @RequestMapping(value = "company-v2.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        CompanyMasterForm form = new CompanyMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            CompanyMaster companyMaster = companyRepository.findOne(id);
            form.setItem(companyMaster);
        }
        model.put("companyMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("types", companyTypeMasterRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "company-v2";
    }

    @RequestMapping(value = "company-v2.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("companyMasterForm") CompanyMasterForm companyMasterForm,
                       Map<String, Object> model) {
        CompanyMaster companyMaster = companyMasterForm.getItem();
        companyRepository.save(companyMaster);
        return "redirect:/company-v2.htm?id=" + companyMaster.getId();
    }


}