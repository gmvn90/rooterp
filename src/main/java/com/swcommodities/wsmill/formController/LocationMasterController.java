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
import com.swcommodities.wsmill.hibernate.dto.LocationMaster;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.LocationMasterRepository;


/**
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("locationMasterForm") // Specify attributes to be stored in the session
@Transactional
public class LocationMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private LocationMasterRepository locationMasterRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(PortMaster portMaster) {
        return portMaster.getId() != null;
    }

    @RequestMapping(value = "location-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        LocationMasterForm form = new LocationMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            currentId = id;
            is_form_edit = true;
            LocationMaster locationMaster = locationMasterRepository.findOne(id);
            form.setItem(locationMaster);
        }
        model.put("locationMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "location-master";
    }

    @RequestMapping(value = "location-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("locationMasterForm") LocationMasterForm locationMasterForm,
                       Map<String, Object> model) {
        LocationMaster locationMaster = locationMasterForm.getItem();
        locationMasterRepository.save(locationMaster);
        return "redirect:/location-master.htm?id=" + locationMaster.getId();
    }


}