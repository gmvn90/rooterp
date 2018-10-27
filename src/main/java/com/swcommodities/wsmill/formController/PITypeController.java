package com.swcommodities.wsmill.formController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.hibernate.dto.PITypeItem;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.PITypeItemRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;

/**
 * Created by dunguyen on 10/21/16.
 */
@org.springframework.stereotype.Controller
@Transactional
public class PITypeController {
    @Autowired
    private PITypeRepository piTypeRepository;

    @Autowired
    private PITypeItemRepository piTypeItemRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired(required = true)
    private HttpServletRequest request;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping(value = "pi-types.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer client) {
        model.put("clients", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));

        if (client == null) {
            client = 107;
        }
        List<PIType> piTypeList = piTypeRepository.findAll();
        for(PIType piType: piTypeList) {
            Set<PITypeItem> piTypeItemSet = piType.getPiTypeItems();
            for(PITypeItem item: piTypeItemSet) {
                item.setCost(costRepository.findFirstByCompany_IdAndOption_Id(client, item.getOption().getId()));
            }
        }
        model.put("piTypes", piTypeRepository.findAll());
        model.put("client", client);

        return "pi-types";
    }

    @RequestMapping(value = "savePITypeItemProperties.htm", method = RequestMethod.POST)
    public String savePITypeItemProperties(Map<String, Object> model) {
        Integer clientId = Integer.parseInt(request.getParameter("clientId"));
        Double weightLoss = Double.parseDouble(request.getParameter("weightLoss"));
        Double rejectGrade3 = Double.parseDouble(request.getParameter("rejectGrade3"));
        Integer piTypeItemId = Integer.parseInt(request.getParameter("piTypeItemId"));

        PITypeItem piTypeItem = piTypeItemRepository.findOne(piTypeItemId);
        piTypeItem.setWeightLoss(weightLoss).setRejectGrade3(rejectGrade3).setUpdated(new Date());
        piTypeItem.setUpdater(getCurrentUser());
        piTypeItemRepository.save(piTypeItem);

        return "redirect:/pi-types.htm?client=" + clientId;
    }

    @RequestMapping(value = "pi-type.htm", method = RequestMethod.POST)
    public String addNew(Map<String, Object> model) {
        model.put("piType", piTypeRepository.findAll());
        return "pi-type";
    }


}
