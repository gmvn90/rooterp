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
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.repository.PackingRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("packingMasterForm") // Specify attributes to be stored in the session
@Transactional
public class PackingMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private PackingRepository packingRepository;

    private boolean isEdit(ShippingLineCompanyMaster shippingLineCompanyMaster) {
        return shippingLineCompanyMaster.getId() != null;
    }

    @RequestMapping(value = "packing-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        PackingMasterForm form = new PackingMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            PackingMaster packingMaster = packingRepository.findOne(id);
            form.setItem(packingMaster);
        }
        model.put("packingMasterForm", form);
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "packing-master";
    }

    @RequestMapping(value = "packing-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("packingMasterForm") PackingMasterForm packingMasterForm,
                       Map<String, Object> model) {
        PackingMaster packingMaster = packingMasterForm.getItem();
        packingRepository.save(packingMaster);
        return "redirect:/packing-master.htm?id=" + packingMaster.getId();
    }


}