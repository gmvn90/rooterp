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
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.QualityRepository;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("gradeMasterForm") // Specify attributes to be stored in the session
@Transactional
public class GradeMasterController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private QualityRepository qualityRepository;

    private boolean isEdit(ShippingLineCompanyMaster shippingLineCompanyMaster) {
        return shippingLineCompanyMaster.getId() != null;
    }

    @RequestMapping(value = "grade-master.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        GradeMasterForm form = new GradeMasterForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            GradeMaster gradeMaster = gradeRepository.findOne(id);
            form.setItem(gradeMaster);
        }
        model.put("gradeMasterForm", form);
        model.put("qualities", qualityRepository.findAll());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "grade-master";
    }

    @RequestMapping(value = "grade-master.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("gradeMasterForm") GradeMasterForm gradeMasterForm,
                       Map<String, Object> model) {
        GradeMaster gradeMaster = gradeMasterForm.getItem();
        gradeRepository.save(gradeMaster);
        return "redirect:/grade-master.htm?id=" + gradeMaster.getId();
    }


}