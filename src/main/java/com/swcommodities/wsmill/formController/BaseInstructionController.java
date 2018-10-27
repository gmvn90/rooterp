package com.swcommodities.wsmill.formController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.repository.CompanyRepository;

/**
 * Created by dunguyen on 10/24/16.
 */
public abstract class BaseInstructionController extends PagingController {
    @Autowired
    private CompanyRepository companyRepository;

    @ModelAttribute("companies")
    List<CompanyMaster> getFoo() {
        return companyRepository.findAllByOrderByNameAsc();  // set modelMap["foo"] = "bar" on every request
    }
}
