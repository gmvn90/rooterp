/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swcommodities.wsmill.formController.form.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.application.exception.ErrorMessage;
import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.SIClaimService;
import com.swcommodities.wsmill.application.service.SISampleSentService;
import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.domain.model.ClaimWeightNote;
import com.swcommodities.wsmill.domain.model.FileUploadType;
import com.swcommodities.wsmill.domain.model.common.BaseIdAndNameImpl;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.domain.model.exceptions.DomainException;
import com.swcommodities.wsmill.el.DateConstant;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.ContractWeightNoteDTO;
import freemarker.template.TemplateException;
import java.util.logging.Level;

/**
 *
 * @author trung
 */
@Controller
public class NewSIController {

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.dateOnlyWithTwoNumberYear);
        PropertyEditorSupport dateSupport = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateSupport);
    }

    @Autowired ShippingInstructionService shippingInstructionService;
    @Autowired SIClaimService sIClaimService;
    @Autowired CompanyService companyService;
    @Autowired SISampleSentService sISampleSentService;
    @Autowired(required = true) private HttpServletRequest request;
    private static final Logger logger = Logger.getLogger(NewSIController.class);

    private User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping(value = "shipping_saveShipmentStatus.htm", method = RequestMethod.POST)
    public String updateShipmentStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            @ModelAttribute("statusForm") CompletionStatusForm statusForm, HttpServletRequest request) {
        try {
            shippingInstructionService.updateShipmentStatus(statusForm.getRefNumber(), statusForm.getStatus(), getCurrentUser(request));
        } catch (ApplicationException e) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(e.getMessage())));
        }
        return "redirect:/shipping-v2.htm?id=" + statusForm.getRefNumber();
    }

    @RequestMapping(value = "shipping_saveRequestStatus.htm", method = RequestMethod.POST)
    public String updateRequestStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            @ModelAttribute("requestStatusForm") RequestStatusForm statusForm, HttpServletRequest request) {
        try {
            shippingInstructionService.updateRequestStatus(statusForm.getRefNumber(), statusForm.getStatus(), getCurrentUser(request));
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
        }
        return "redirect:/shipping-v2.htm?id=" + statusForm.getRefNumber();
    }

    @RequestMapping(value = "shipping_saveCompletionStatus.htm", method = RequestMethod.POST)
    public String updateCompletionStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            @ModelAttribute("completionStatusForm") CompletionStatusForm statusForm, HttpServletRequest request) {
        try {
            shippingInstructionService.updateCompletionStatus(statusForm.getRefNumber(), statusForm.getStatus(), getCurrentUser(request));
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
        }
        return "redirect:/shipping-v2.htm?id=" + statusForm.getRefNumber();
    }

    @RequestMapping(value = "shipping-v2.htm", method = RequestMethod.POST)
    public String updateSI(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            @ModelAttribute("shippingForm") ShippingInstructionForm shippingInstructionForm, HttpServletRequest request)  {
        String refNumber;
        try {
            refNumber = shippingInstructionService.updateOrSaveShippingInstruction(shippingInstructionForm, getCurrentUser(request));
        } catch (ActionNotPermitted ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
            refNumber = shippingInstructionForm.getRefNumber();
        }
        return "redirect:/shipping-v2.htm?id=" + refNumber;
    }

    @RequestMapping(value = "new_addSingleSampleSent.htm", method = RequestMethod.POST)
    public String addSampleSent(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            NewSampleSentForm sampleSentForm, HttpServletRequest request) {
        shippingInstructionService.newSampleSent(sampleSentForm, getCurrentUser(request));
        return "redirect:/shipping-v2.htm?id=" + sampleSentForm.getSiId();
    }
    
    @RequestMapping(value = "sample-sent-v2.htm", method = RequestMethod.POST)
    public String updateSampleSent(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            UpdateSampleSentForm sampleSentForm, HttpServletRequest request) {
        shippingInstructionService.updateSampleSent(sampleSentForm, getCurrentUser(request));
        return "redirect:/shipping-instruction/" + sampleSentForm.getSiRef() + "/sample-sent/" + sampleSentForm.getRefNumber() + ".htm";
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/sample-sent/{ssRef}.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @PathVariable String siRef, @PathVariable String ssRef, Model formModel) throws Exception {
        Map<String, Object> ss = shippingInstructionService.getSSDetail(siRef, ssRef);
        model.putAll(ss);
        return "si/ss/sample-sent-v2";
    }

    @RequestMapping(value = "new_addSingleClaim.htm", method = RequestMethod.POST)
    public String addClaim(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            ClaimForm claimForm, HttpServletRequest request) {
        String refNumber;
        try {
            refNumber = sIClaimService.newClaim(claimForm, getCurrentUser(request));
            return "redirect:/shipping-instruction/" + claimForm.getSiRef() + "/claim-detail/" + refNumber + ".htm";
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
            return "redirect:/shipping-v2.htm?id=" + claimForm.getSiRef();
        }
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/claim-detail/{refNumber}.htm", method = RequestMethod.POST)
    public String updateClaim(final RedirectAttributes redirectAttributes, Map<String, Object> model,
                              UpdateClaimForm claimForm,@PathVariable String siRef, @PathVariable String refNumber, HttpServletRequest request) {
    		sIClaimService.updateClaim(claimForm, siRef, getCurrentUser(request));
        return "redirect:/shipping-instruction/" + claimForm.getSiRef() + "/claim-detail/" + claimForm.getRefNumber() + ".htm";
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/claim-detail/{refNumber}.htm", method = RequestMethod.GET)
    public String getDetailClaim(Map<String, Object> model, final RedirectAttributes redirectAttributes,
            @PathVariable String siRef, @PathVariable String refNumber) {
        try {
            model.putAll(sIClaimService.getClaimDetail(siRef, refNumber));
        } catch (DomainException ex) {
            java.util.logging.Logger.getLogger(NewSIController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
            return "redirect:/shipping-v2.htm?id=" + siRef;
        }
        return "si/claim-detail";
    }
    
    @RequestMapping(value = "shipping-instruction/{siRef}/claim-weight-note/{refNumber}.htm", method = RequestMethod.POST)
    public String updateClaimWeightNote(Map<String, Object> model, HttpServletRequest request,
            @PathVariable String siRef, @PathVariable String refNumber, final RedirectAttributes redirectAttributes,
            @RequestBody List<ClaimWeightNote> dtos) {
        sIClaimService.updateClaimWeightNote(siRef, refNumber, dtos, getCurrentUser(request));
        return "redirect:/shipping-instruction/" + siRef + "/claim-detail/" + refNumber + ".htm";
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/update-claim-approval-status.htm", method = RequestMethod.POST)
    public String updateClaimApprovalStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
                                                 ApprovalStatusForm form, HttpServletRequest request, @PathVariable String siRef) {
        sIClaimService.updateClaimApprovalStatus(siRef, form.getRefNumber(), form.getStatus(), getCurrentUser(request));
        return "redirect:/shipping-instruction/"+siRef+"/claim-detail/"+form.getRefNumber()+".htm";
    }
    
    @RequestMapping(value = "shipping-instruction/{siRef}/claim/{claimRef}/add-document.htm", method = RequestMethod.POST)
    public String addClaimDocument(final RedirectAttributes redirectAttributes, Map<String, Object> model,
                                                 @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request, 
                                                 @PathVariable String siRef, @PathVariable String claimRef) {
        sIClaimService.addClaimDocument(siRef, claimRef, uploadfile, getCurrentUser(request));
        return "redirect:/shipping-instruction/" + siRef + "/claim-detail/" + claimRef + ".htm";
    }
    
    @RequestMapping(value = "shipping-instruction/{siRef}/sample-sent/{ssRef}/add-document.htm", method = RequestMethod.POST)
    public String addSampleSentDocument(final RedirectAttributes redirectAttributes, Map<String, Object> model,
                                                 @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request, 
                                                 @PathVariable String siRef, @PathVariable String ssRef) {
        sISampleSentService.addSampleSentDocument(siRef, ssRef, uploadfile, getCurrentUser(request));
        return "redirect:/shipping-instruction/" + siRef + "/sample-sent/" + ssRef + ".htm";
    }

    @RequestMapping(value = "shipping-instruction/{id}/update-sample-sent-user-remark.htm", method = RequestMethod.POST)
    public String updateSampleSentUserRemark(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            RemarkUpdateSampleSentForm sampleSentForm, HttpServletRequest request, @PathVariable int id) {
        shippingInstructionService.updateSampleSentRemark(id, sampleSentForm, getCurrentUser(request));
        return "redirect:/shipping-v2.htm?id=" + id;
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/update-sample-sent-approval-status.htm", method = RequestMethod.POST)
    public String updateSampleSentApprovalStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            ApprovalStatusForm form, HttpServletRequest request, @PathVariable String siRef) {
        try {
            shippingInstructionService.updateSampleSentApprovalStatus(siRef, form.getRefNumber(), form.getStatus(), getCurrentUser(request));
        } catch (ApplicationException ex) {
            redirectAttributes.addFlashAttribute("messages", Arrays.asList(new ErrorMessage(ex.getMessage())));
        }
        return "redirect:/shipping-instruction/"+siRef+"/sample-sent/"+form.getRefNumber()+".htm";
    }

    @RequestMapping(value = "shipping-instruction/{siRef}/update-sample-sent-sending-status.htm", method = RequestMethod.POST)
    public String updateSampleSentSendingStatus(final RedirectAttributes redirectAttributes, Map<String, Object> model,
            SendingStatusForm form, HttpServletRequest request, @PathVariable String siRef) {
        shippingInstructionService.updateSampleSentSendingStatus(siRef, form.getRefNumber(), form.getStatus(), getCurrentUser(request));
        return "redirect:/shipping-instruction/" +siRef+"/sample-sent/"+form.getRefNumber()+".htm";
    }

    @RequestMapping(value = "shipping-instruction/{id}/update-file-sent.json", method = RequestMethod.POST)
    public ResponseEntity<FileSent> updateFile(
            UpdateFileSentForm form, @PathVariable("id") int SiId) {
        FileSent fileSent = shippingInstructionService.updateFileSent(SiId, form);
        return new ResponseEntity<>(fileSent, HttpStatus.CREATED);
    }

    @RequestMapping(value = "shipping-instruction/{id}/add-custom-cost.htm", method = RequestMethod.POST)
    public String addCustomCost(
            CustomCostForm form, @PathVariable("id") int SiId) {
        shippingInstructionService.addCustomCost(SiId, form);
        return "redirect:/shipping-v2.htm?id=" + SiId;
    }

    @RequestMapping(value = "shipping-instruction/{id}/remove-custom-cost.htm", method = RequestMethod.POST)
    public String removeCustomCost(
            CustomCostForm form, @PathVariable("id") int SiId) {
        shippingInstructionService.removeCustomCost(SiId, form);
        return "redirect:/shipping-v2.htm?id=" + SiId;
    }

    @RequestMapping(value = "shipping-instruction/{id}/generate_contract_weight_note.htm", method = RequestMethod.POST)
    public String generateShippingInstructionContractWeightNote(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id) {
        shippingInstructionService.generateShippingInstructionContractWeight(id);
        return "redirect:/shipping-instruction/" + id + "/loading_report.htm";
    }

    @RequestMapping(value = "shipping-instruction/{id}/update_contract_weight_note.htm", method = RequestMethod.POST)
    public String updateContractWeightNote(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id, @RequestBody ContractWeightNoteDTO dto) {
        shippingInstructionService.updateContractWeightNote(id, dto);
        return "redirect:/shipping-v2.htm?id=" + id;
    }

    @RequestMapping(value = "shipping-instruction/{id}/update_contract_weight_notes.htm", method = RequestMethod.POST)
    public String updateContractWeightNotes(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id, @RequestBody List<ContractWeightNoteDTO> dtos) {
        shippingInstructionService.updateContractWeightNotes(id, dtos);
        return "redirect:/shipping-instruction/" + id + "/loading_report.htm";
    }

    @RequestMapping(value = "shipping-instruction/{id}/generate_summary_report.htm", method = RequestMethod.POST)
    public @ResponseBody
    void generateShippingSummaryReport(HttpServletResponse response, @PathVariable int id) throws IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String webPath = request.getServletContext().getRealPath("") + "/";
        response.getWriter().print(shippingInstructionService.generateShippingSummaryReport(id, webPath));
    }

    @RequestMapping(value = "shipping-instruction/{id}/attach-loading-report.htm", method = RequestMethod.POST)
    public String attachLoadingReport(HttpServletResponse response, @PathVariable int id) throws IOException, Exception {
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("Not accept without user");
        }
        shippingInstructionService.generateAndAttachLoadingReport(id, username);
        return "redirect:/shipping-v2.htm?id=" + id;
    }

    @RequestMapping(value = "shipping-instruction/{id}/loading_report.htm", method = RequestMethod.GET)
    public String showLoadingReport(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id) {
        Map<String, Object> data = shippingInstructionService.getLoadingReportDtoForShowing(id);
        model.putAll(data);
        model.put("id", id);
        return "si/loading_report";
    }

    @RequestMapping(value = "shipping-instruction/{id}/shipping_advice.htm", method = RequestMethod.GET)
    public String showShippingAdvice(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id) {
        model.put("aggegrate", shippingInstructionService.getAdviceAggegrateInfo(id));
        model.put("si", shippingInstructionService.getAdviceDto(id));
        return "si/shipping_advice_2";
    }

    @RequestMapping(value = "shipping-instruction/{id}/shipping_advice.htm", method = RequestMethod.POST)
    public String addShippingAdvice(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id) throws IOException {
        User user = getCurrentUser();
        shippingInstructionService.createShippingAdvice(id, user);
        return "redirect:/shipping-instruction/" + id + "/shipping_advice.htm";
    }

    @RequestMapping(value = "shipping-instruction/{id}/generate_shipping_advice_pdf.htm", method = RequestMethod.POST)
    public @ResponseBody
    String getShippingAdvicePdf(final RedirectAttributes redirectAttributes,
            Map<String, Object> model, @PathVariable int id) throws IOException, TemplateException {
        return shippingInstructionService.generatePdfShippingAdvice(id, getCurrentUser());
    }

    @RequestMapping(value = "shipping-instruction/{id}/upload-file-sent.json", method = RequestMethod.POST)
    public ResponseEntity<FileSent> uploadFile(
            @PathVariable int id,
            @RequestParam("file") MultipartFile uploadfile,
            @RequestParam("name") String name,
            @RequestParam("emails") String emails,
            @RequestParam("type") FileUploadType type) {
        FileSent fileSent = shippingInstructionService.newFileUpload(id, uploadfile, name, emails, getCurrentUser(), type);
        return new ResponseEntity<>(fileSent, HttpStatus.CREATED);
    }

    @RequestMapping(value = "shipping-instruction/{shippingId}/add-notify-party.json", method = RequestMethod.POST)
    public ResponseEntity<BaseIdAndNameImpl> addNotifyParty(
            @PathVariable int shippingId,
            @RequestBody IdForm form) {
        BaseIdAndNameImpl result = shippingInstructionService.addNotifyParty(shippingId, form.getId());
        result.setName(companyService.getName(form.getId()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
