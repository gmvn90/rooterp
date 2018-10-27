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
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.PortRepository;


/**
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("portMasterForm") // Specify attributes to be stored in the session
@Transactional
public class PortMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(PortMaster portMaster) {
        return portMaster.getId() != null;
    }

    @RequestMapping(value = "port-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        PortMasterForm form = new PortMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            currentId = id;
            is_form_edit = true;
            PortMaster portMaster = portRepository.findOne(id);
            form.setItem(portMaster);
        }
        model.put("portMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "port-master";
    }

    @RequestMapping(value = "port-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("portMasterForm") PortMasterForm portMasterForm,
                       Map<String, Object> model) {
        PortMaster portMaster = portMasterForm.getItem();
        portRepository.save(portMaster);
        return "redirect:/port-master.htm?id=" + portMaster.getId();
    }


}