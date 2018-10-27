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
import com.swcommodities.wsmill.hibernate.dto.WarehouseMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.WarehouseMasterRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("warehouseMasterForm") // Specify attributes to be stored in the session
@Transactional
public class WarehouseMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private WarehouseMasterRepository warehouseMasterRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(WarehouseMaster warehouseMaster) {
        return warehouseMaster.getId() != null;
    }

    @RequestMapping(value = "warehouse-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        WarehouseMasterForm form = new WarehouseMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            WarehouseMaster warehouseMaster = warehouseMasterRepository.findOne(id);
            form.setItem(warehouseMaster);
        }
        model.put("warehouseMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "warehouse-master";
    }

    @RequestMapping(value = "warehouse-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("warehouseMasterForm") WarehouseMasterForm warehouseMasterForm,
                       Map<String, Object> model) {
        WarehouseMaster warehouseMaster = warehouseMasterForm.getItem();
        warehouseMasterRepository.save(warehouseMaster);
        return "redirect:/warehouse-master.htm?id=" + warehouseMaster.getId();
    }


}