package com.swcommodities.wsmill.formController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Iterables;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CompanyOption;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyOptionRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.OptionRepository;
import com.swcommodities.wsmill.service.ReportService;

/**
 * Created by dunguyen on 10/21/16.
 */
@org.springframework.stereotype.Controller
@Transactional
public class CostPriceController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired
    private CompanyOptionRepository companyOptionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @RequestMapping(method= RequestMethod.GET, value = "getPriceReport.htm")
    public ResponseEntity<byte[]> getFileContent(@RequestParam(required = false) Integer companyId) throws Exception {
        byte[] res = reportService.writeAllInfoToPdf(companyId);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition",
                "attachment; filename=PriceReport.pdf");
        header.setContentLength(res.length);
        return new ResponseEntity<byte[]>(res, header, HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value = "cost_price_list_company.htm")
    public String addNewCompany(HttpServletRequest request) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));

        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch(Exception e) {
            System.out.println("updateFixedMargin: dangerous username not found");
        }
        int margin = Integer.valueOf(request.getParameter("margin"));
        CompanyMaster company = companyRepository.findOne(id);
        CompanyOption companyOption = company.getFixedMarginObject();
        if(companyOption != null) {
            return "redirect:/cost_price_list_company.htm";
        }
        else {
            companyOption = company.makeEmptyCompanyOption();
            companyOption.setValue(String.valueOf(margin));
            companyOption.setUsername(username);
        }
        companyOptionRepository.save(companyOption);
        Iterable<OperationalCost> options = optionRepository.findAll();
        if(Iterables.size(costRepository.findByCompany_Id(id)) == 0) {
            for(OperationalCost opt: options) {
                Cost cost = new Cost();
                cost.setCompany(company);
                cost.setOption(opt);
                cost.setValue(opt.getValueInUsd() * (1 + margin * 1.0 / 100));
                cost.setUsername(username);
                costRepository.save(cost);
            }
        } else {
            Iterable<Cost> costs = costRepository.findByCompany_Id(id);
            for(Cost cost: costs) {
                cost.setValue(cost.getOption().getValueInUsd() * (1 + margin * 1.0 / 100));
                cost.setUsername(username);
                costRepository.save(cost);
            }
        }
        return "redirect:/cost_price_list_company.htm";
    }

    @RequestMapping(method= RequestMethod.GET, value = "cost_price_list_company.htm")
    public String showCompanyCostList(Map<String, Object> model) {

        model.put("companies", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
        return "cost_price_list_company";
    }
}
