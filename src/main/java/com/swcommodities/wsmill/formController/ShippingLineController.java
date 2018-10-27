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
import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.ShippingLineCompanyMasterRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("shippingLineForm") // Specify attributes to be stored in the session
@Transactional
public class ShippingLineController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private ShippingLineCompanyMasterRepository shippingLineCompanyMasterRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(ShippingLineCompanyMaster shippingLineCompanyMaster) {
        return shippingLineCompanyMaster.getId() != null;
    }

    @RequestMapping(value = "shipping-line.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        ShippingLineForm form = new ShippingLineForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            ShippingLineCompanyMaster shippingLineCompanyMaster = shippingLineCompanyMasterRepository.findOne(id);
            form.setItem(shippingLineCompanyMaster);
        }
        model.put("shippingLineForm", form);
        model.put("countries", countryRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "shipping-line";
    }

    @RequestMapping(value = "shipping-line.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("shippingLineForm") ShippingLineForm shippingLineForm,
                       Map<String, Object> model) {
        ShippingLineCompanyMaster shippingLineCompanyMaster = shippingLineForm.getItem();
        shippingLineCompanyMasterRepository.save(shippingLineCompanyMaster);
        return "redirect:/shipping-line.htm?id=" + shippingLineCompanyMaster.getId();
    }


}