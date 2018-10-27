/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.application.exception.ErrorMessage;
import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.SampleTypeService;
import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.el.DateConstant;
import com.swcommodities.wsmill.formController.form.ApprovalStatusForm;
import com.swcommodities.wsmill.formController.form.SendingStatusForm;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleTypeDTO;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author macOS
 */

@Controller
public class SampleTypeController {
    
    private static Logger logger = Logger.getLogger(SampleTypeController.class);
    
    @Autowired
    private CourierMasterRepository courierMasterRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private GradeRepository gradeRepository;
    
    @Autowired CompanyService companyService;
    @Autowired SampleTypeService sampleTypeService;
    
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.dateOnlyWithTwoNumberYear);
        dateFormat.setLenient(false);

        PropertyEditorSupport dateSupport = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateSupport);
    }
    
    private User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    
    @RequestMapping(value = "sample-sent-type.htm", method = RequestMethod.GET)
    public String getTypeSample(Map<String, Object> model, @RequestParam(required = false) String id) throws Exception{
        String currentId = "";
        boolean is_form_edit = false;
        SampleTypeDTO dto;
        if (id != null) {
            currentId = id;
            is_form_edit = true;
            dto = sampleTypeService.fromObjectId(id);
            SendingStatusForm sendingStatusForm = new SendingStatusForm(dto.getTypeSampleRef(), dto.getSaveSendingStatusUser(), dto.getSaveSendingStatusDate(), dto.getSendingStatus());
            model.put("sendingStatusForm", sendingStatusForm);
            ApprovalStatusForm approvalStatusForm = new ApprovalStatusForm(dto.getTypeSampleRef(), dto.getSaveApprovalStatusUser(), dto.getSaveApprovalStatusDate(), dto.getApprovalStatus());
            model.put("approvalStatusForm", approvalStatusForm);
        } else {
            dto = new SampleTypeDTO();
        }
        model.put("item", dto);
        model.put("clients", companyService.findAllClientsWithEmpty());
        model.put("buyers", companyService.findAllBuyersWithEmpty());
        model.put("shippers", companyService.findAllShippersWithEmpty());
        model.put("suppliers", companyService.findAllSuppliersWithEmpty());
        model.put("origins", originRepository.findAll());
        model.put("qualities", qualityRepository.findAll());
        model.put("grades", gradeRepository.findAllByOrderByNameAsc());

        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        model.put("couriers", courierMasterRepository.findAll());
        return "st/sample-sent-type";
    }

    //For new sample sent type
    @RequestMapping(value = "sample-sent-type.htm", method = RequestMethod.POST)
    public String postTypeSample(Map<String, Object> model, HttpServletRequest req, SampleTypeDTO dto) {
        SampleType st = sampleTypeService.updateOrAddSampleType(dto, getCurrentUser(req));
        return "redirect:/sample-sent-type.htm?id=" + st.getId();
    }
    
    @RequestMapping(value = "sampleType_saveApprovalStatus.htm", method = RequestMethod.POST)
    public String updateApprovalStatus(Map<String, Object> model, HttpServletRequest req, ApprovalStatusForm form, final RedirectAttributes redirectAttributes) {
        SampleType st;
        try {
            st = sampleTypeService.updateApprovalStatus(form, getCurrentUser(req));
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
        }
        return "redirect:/sample-sent-type.htm?id=" + form.getRefNumber();
    }
    
    @RequestMapping(value = "sampleType_saveSendingStatus.htm", method = RequestMethod.POST)
    public String updateSendingStatus(Map<String, Object> model, HttpServletRequest req, SendingStatusForm form) {
        SampleType st = sampleTypeService.updateSendingStatus(form, getCurrentUser(req));
        return "redirect:/sample-sent-type.htm?id=" + st.getId();
    }
    
    @RequestMapping(value = "sampleType/{stRef}/add-document.htm", method = RequestMethod.POST)
    public String addClaimDocument(final RedirectAttributes redirectAttributes, Map<String, Object> model,
                                                 @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request, @PathVariable String stRef) {
        sampleTypeService.addSampleTypeDocument(stRef, uploadfile, getCurrentUser(request));
        return "redirect:/sample-sent-type.htm?id=" + stRef;
    }
    
}
