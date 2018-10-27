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
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.CourierMasterRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("courierMasterForm") // Specify attributes to be stored in the session
@Transactional
public class CourierMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private CourierMasterRepository courierMasterRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(CourierMaster courierMaster) {
        return courierMaster.getId() != null;
    }

    @RequestMapping(value = "courier-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        CourierMasterForm form = new CourierMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            CourierMaster courierMaster = courierMasterRepository.findOne(id);
            form.setItem(courierMaster);
        }
        model.put("courierMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "courier-master";
    }

    @RequestMapping(value = "courier-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("courierMasterForm") CourierMasterForm courierMasterForm,
                       Map<String, Object> model) {
        CourierMaster courierMaster = courierMasterForm.getItem();
        courierMasterRepository.save(courierMaster);
        return "redirect:/courier-master.htm?id=" + courierMaster.getId();
    }


}