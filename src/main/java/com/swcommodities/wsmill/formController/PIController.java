package com.swcommodities.wsmill.formController;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swcommodities.wsmill.application.service.PIService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import com.swcommodities.wsmill.formController.form.CompletionStatusForm;
import com.swcommodities.wsmill.formController.form.RequestStatusForm;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.service.TransactionService;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("piForm") // Specify attributes to be stored in the session
@Transactional
public class PIController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PITypeRepository piTypeRepository;

    @Autowired
    private PackingRepository packingRepository;

    @Autowired
    private MyAsyncService myAsyncService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProcessingInstructionRepository processingInstructionRepository;

    @Autowired
    ProcessingService processingService;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired
    PIService piService;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private boolean isEdit(ProcessingInstruction processingInstruction) {
        return processingInstruction.getId() != null;
    }

//    @RequestMapping(value = "processing-v2.htm", method = RequestMethod.GET)
//    @Transactional(readOnly = true)
//    public String get(Map<String, Object> model, @RequestParam(required = false) String id) {
//        PIForm form = new PIForm();
//        boolean is_form_edit = false;
//        int currentId = -1;
//        ProcessingInstruction processingInstruction;
//        if (id != null) {
//            if (StringUtils.isNumeric(id)) {
//                currentId = Integer.valueOf(id);
//                processingInstruction = piService.findOneById(currentId);
//            } else {
//                processingInstruction = piService.findOneByRefNumber(id);
//                currentId = processingInstruction.getId();
//            }
//            is_form_edit = true;
//            // lazy load: no session fixed
//            try {
//                processingInstruction.getUser().getUserName();
//            } catch (Exception e) {
//
//            }
//            try {
//                processingInstruction.getUserByUpdateCompletionUserId().getUserName();
//            } catch (Exception e) {
//
//            }
//            try {
//                processingInstruction.getUserByUpdateRequestUserId().getUserName();
//            } catch (Exception e) {
//
//            }
//            Double allocated = processingInstructionRepository.getProcessingAllocated(currentId);
//            Double inprocess = processingInstructionRepository.getProcessingInprocess(currentId);
//            Double exprocess = processingInstructionRepository.getProcessingExprocess(currentId);
//
//            if (allocated == null) {
//                allocated = 0.0;
//            }
//            if (inprocess == null) {
//                inprocess = 0.0;
//            }
//            if (exprocess == null) {
//                exprocess = 0.0;
//            }
//            processingInstruction.setAllocatedWeight(allocated);
//            processingInstruction.setInProcessWeight(inprocess);
//            processingInstruction.setExProcessWeight(exprocess);
//            processingInstruction.setPendingWeight(inprocess - exprocess);
//            form.setItem(processingInstruction);
//            model.put("requestStatusForm", new RequestStatusForm(processingInstruction.getRefNumber(),
//                processingInstruction.getUser().getUserName(), processingInstruction.getRequestStatusDate(),
//                processingInstruction.getRequestStatusEnum()));
//            model.put("completionStatusForm", new CompletionStatusForm(processingInstruction.getRefNumber(), processingInstruction.getUser().getUserName(),
//                processingInstruction.getCompletionStatusDate(), processingInstruction.getCompletionStatus()));
//
//        } else {
//
//        }
//
//        model.put("piForm", form);
//        model.put("clients", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
//        model.put("grades", gradeRepository.findAllByOrderByNameAsc());
//        model.put("piTypes", piTypeRepository.findAll());
//        model.put("packings", packingRepository.findAllByOrderByNameAsc());
//        model.put("completionStatus", InstructionStatus.getCompletionStatuses());
//        model.put("pis", processingInstructionRepository.findRefList());
//        model.put("is_form_edit", is_form_edit);
//        model.put("currentId", currentId);
//        return "pi/processing-v2";
//    }
//
//    @RequestMapping(value = "processing-v2.htm", method = RequestMethod.POST)
//    public String post(@ModelAttribute("piForm") PIForm piForm,
//        Map<String, Object> model) {
//        ProcessingInstruction processingInstruction = piForm.getItem();
//        if (!isEdit(processingInstruction)) {
//            processingInstruction.setRefNumber(processingService.getNewContractRef());
//            processingInstruction.setCreatedDate(new Date());
//
//            processingInstruction.setUserByUpdateCompletionUserId(getCurrentUser());
//            processingInstruction.setCompletionStatusDate(new Date());
//            processingInstruction.setStatus(Constants.PENDING);
//
//            processingInstruction.setUserByUpdateRequestUserId(getCurrentUser());
//            processingInstruction.setRequestStatusDate(new Date());
//            processingInstruction.setRequestStatus(Constants.PENDING);
//        }
//        processingInstruction.setUpdatedDate(new Date());
//        processingInstruction.setUser(getCurrentUser());
//        processingInstructionRepository.save(processingInstruction);
//        try {
//            myAsyncService.saveCachePI(processingInstruction);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "redirect:/processing-v2.htm?id=" + processingInstruction.getId();
//    }

}
