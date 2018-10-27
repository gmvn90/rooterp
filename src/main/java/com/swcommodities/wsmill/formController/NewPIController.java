/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.application.exception.ErrorMessage;
import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.PIService;
import com.swcommodities.wsmill.application.service.SIClaimService;
import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.domain.model.ClaimWeightNote;
import com.swcommodities.wsmill.domain.model.FileUploadType;
import com.swcommodities.wsmill.domain.model.common.BaseIdAndNameImpl;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.el.DateConstant;
import com.swcommodities.wsmill.formController.form.*;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.ContractWeightNoteDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.PIAssembler;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.utils.Constants;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trung
 */
@Controller
public class NewPIController {

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.dateOnlyWithTwoNumberYear);
        PropertyEditorSupport dateSupport = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateSupport);
    }

    @Autowired PIService piService;
    @Autowired CompanyService companyService;
    @Autowired(required = true) private HttpServletRequest request;
    @Autowired ProcessingInstructionRepository processingInstructionRepository;
    @Autowired GradeRepository gradeRepository;
    @Autowired PITypeRepository pITypeRepository;
    @Autowired PackingRepository packingRepository;
    
    private static final Logger logger = Logger.getLogger(NewPIController.class);
    private User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }
    
    @RequestMapping(value = "processing_saveRequestStatus.htm", method = RequestMethod.POST)
    public String updateRequestStatus(RequestStatusForm form,
                                      Map<String, Object> model, final RedirectAttributes redirectAttributes) {
        try {
			piService.updateRequestStatus(form, getCurrentUser());
		} catch (ApplicationException ex) {
			redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
		}
        return "redirect:/processing-v2.htm?id=" + form.getRefNumber();
    }
    
    @RequestMapping(value = "processing_saveCompletionStatus.htm", method = RequestMethod.POST)
    public String updateStatus(CompletionStatusForm form, final RedirectAttributes redirectAttributes,
                               Map<String, Object> model) {
        try {
            piService.updateCompletionStatus(form, getCurrentUser());
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
        }
        return "redirect:/processing-v2.htm?id=" + form.getRefNumber();
    }
    
    @RequestMapping(value = "processing-v2.htm", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public String get(Map<String, Object> model, @RequestParam(required = false) String id) {
        ProcessingInstruction processingInstruction = new ProcessingInstruction();
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            is_form_edit = true;
            if (StringUtils.isNumeric(id)) {
                currentId = Integer.valueOf(id);
                processingInstruction = piService.findOneById(currentId);
            } else {
                processingInstruction = piService.findOneByRefNumber(id);
                currentId = processingInstruction.getId();
            }
            Double allocated = processingInstructionRepository.getProcessingAllocated(currentId);
            Double inprocess = processingInstructionRepository.getProcessingInprocess(currentId);
            Double exprocess = processingInstructionRepository.getProcessingExprocess(currentId);

            if (allocated == null) {
                allocated = 0.0;
            }
            if (inprocess == null) {
                inprocess = 0.0;
            }
            if (exprocess == null) {
                exprocess = 0.0;
            }
            
            processingInstruction.setAllocatedWeight(allocated);
            processingInstruction.setInProcessWeight(inprocess);
            processingInstruction.setExProcessWeight(exprocess);
            processingInstruction.setPendingWeight(inprocess - exprocess);
            model.put("requestStatusForm", new RequestStatusForm(processingInstruction.getRefNumber(),
                processingInstruction.getUserByUpdateRequestUserId() != null ? processingInstruction.getUserByUpdateRequestUserId().getUserName(): "", processingInstruction.getRequestStatusDate(),
                processingInstruction.getRequestStatusEnum()));
            model.put("completionStatusForm", new CompletionStatusForm(processingInstruction.getRefNumber(), processingInstruction.getUserByUpdateCompletionUserId() != null ? processingInstruction.getUserByUpdateCompletionUserId().getUserName(): "",
                processingInstruction.getCompletionStatusDate(), processingInstruction.getCompletionStatus()));
        }
        PIAssembler assembler = new PIAssembler();
        NewPIForm form = assembler.fromModel(processingInstruction);
        model.put("piForm", form);
        model.put("clients", companyService.findAllClients());
        model.put("grades", gradeRepository.findAllByOrderByNameAsc());
        model.put("piTypes", pITypeRepository.findAll());
        model.put("packings", packingRepository.findAllByOrderByNameAsc());
        model.put("completionStatus", InstructionStatus.getCompletionStatuses());
        model.put("pis", processingInstructionRepository.findRefList());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        
        
        
        return "pi/processing-v21";
    }

    @RequestMapping(value = "processing-v2.htm", method = RequestMethod.POST)
    public String post(NewPIForm form,
        Map<String, Object> model) {
        String refNumber = piService.newOrUpdatePI(form, getCurrentUser());
        return "redirect:/processing-v2.htm?id=" + refNumber;
    }

    @RequestMapping(value = "get_pi_info/{id}.json")
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, String>> getPIInfo(@PathVariable int id) throws Exception {
        DecimalFormat df = new DecimalFormat("0.000");
        Map<String, String> valueMap = new HashMap<>();
        ProcessingInstruction processingInstruction = processingInstructionRepository.findOne(id);
        Double allocated = processingInstructionRepository.getProcessingAllocated(id);
        Double inprocess = processingInstructionRepository.getProcessingInprocess(id);
        Double exprocess = processingInstructionRepository.getProcessingExprocess(id);
        Double quantity = (double)processingInstruction.getQuantity();

        if (allocated == null) {
            allocated = 0.0;
        }
        if (inprocess == null) {
            inprocess = 0.0;
        }
        if (exprocess == null) {
            exprocess = 0.0;
        }
        if (quantity == null) {
            quantity = 0.0;
        }
        valueMap.put("quantity", df.format(quantity));
        valueMap.put("allocated", df.format(allocated));
        valueMap.put("inprocess", df.format(inprocess));
        valueMap.put("exprocess", df.format(exprocess));
        valueMap.put("pending", df.format(inprocess - exprocess));
        return new ResponseEntity<>(valueMap, HttpStatus.OK);
    }

}
