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
import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.OriginRepository;


/**
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("originMasterForm") // Specify attributes to be stored in the session
@Transactional
public class OriginMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(OriginMaster originMaster) {
        return originMaster.getId() != null;
    }

    @RequestMapping(value = "origin-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        OriginMasterForm form = new OriginMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            currentId = id;
            is_form_edit = true;
            OriginMaster originMaster = originRepository.findOne(id);
            form.setItem(originMaster);
        }
        model.put("originMasterForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "origin-master";
    }

    @RequestMapping(value = "origin-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("originMasterForm") OriginMasterForm originMasterForm,
                       Map<String, Object> model) {
        OriginMaster originMaster = originMasterForm.getItem();
        originRepository.save(originMaster);
        return "redirect:/origin-master.htm?id=" + originMaster.getId();
    }


}