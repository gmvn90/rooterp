/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.swcommodities.wsmill.formController.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.domain.event.si.SSRejectedEvent;
import com.swcommodities.wsmill.domain.model.ContractWeightNote;
import com.swcommodities.wsmill.domain.model.FileUploadType;
import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.model.ShippingCost;
import com.swcommodities.wsmill.domain.model.WeightNoteComparingAggegrate;
import com.swcommodities.wsmill.domain.model.common.BaseIdAndNameImpl;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.domain.model.exceptions.NotEnoughInfoException;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import com.swcommodities.wsmill.domain.policy.SampleSentFullInfoPolicy;
import com.swcommodities.wsmill.domain.service.ShippingInstructionDomainService;
import com.swcommodities.wsmill.el.DateConstant;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdvice;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdviceSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.ContractWeightNoteDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionAdviceDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionSummaryDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.WeightNoteComparingAggegrateDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ContractWeightNoteAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.RemarkUpdateSampleSentAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.SampleSentAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.SampleSentItemAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ShippingInstructionFormAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ShippingInstructionSummaryAssembler;
import com.swcommodities.wsmill.hibernate.specification.ItemNotDeleted;
import com.swcommodities.wsmill.hibernate.specification.WeightNoteNotAllocated;
import com.swcommodities.wsmill.infrastructure.ExcelReportService;
import com.swcommodities.wsmill.infrastructure.HtmlReportService;
import com.swcommodities.wsmill.infrastructure.PathFinderService;
import com.swcommodities.wsmill.infrastructure.PdfRenderService;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.repository.WeightNoteRepository;

import freemarker.template.TemplateException;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @author macOS
 */
@Service("application_ShippingInstructionService")
public class ShippingInstructionService {

    @Autowired
    ShippingInstructionRepository shippingInstructionRepository;
    @Autowired
    ExcelReportService excelReportService;
    private final String excelLoadingReportName = "si-summary";
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    HtmlReportService htmlReportService;
    @Autowired
    PathFinderService pathFinderService;
    @Autowired
    PdfRenderService pdfRenderService;
    @Autowired
    ShippingInstructionDomainService shippingInstructionDomainService;
    @Autowired
    StorageService storageService;
    @Autowired
    CourierMasterRepository courierMasterRepository;
    @Autowired WeightNoteRepository weightNoteRepository;
    private SampleSentFullInfoPolicy sampleSentFullInfoPolicy = new SampleSentFullInfoPolicy();
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void generateShippingInstructionContractWeight(int id) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(id);
        generateShippingInstructionContractWeight(shippingInstruction);
    }

    private void generateShippingInstructionContractWeight(ShippingInstruction shippingInstruction) {
        shippingInstruction.generateContractWeightNote();
        shippingInstructionRepository.save(shippingInstruction);
    }

    @Transactional
    public void updateContractWeightNote(int shippingInstructionId, ContractWeightNoteDTO dto) {
        ShippingInstruction instruction = shippingInstructionRepository.findOne(shippingInstructionId);
        ContractWeightNoteAssembler assembler = new ContractWeightNoteAssembler();
        ContractWeightNote contractWeightNote = assembler.fromDto(dto);
        instruction.updateContractWeightNote(contractWeightNote);
        shippingInstructionRepository.save(instruction);
    }

    @Transactional
    public void updateContractWeightNotes(int shippingInstructionId, List<ContractWeightNoteDTO> dtos) {
        ShippingInstruction instruction = shippingInstructionRepository.findOne(shippingInstructionId);
        ContractWeightNoteAssembler assembler = new ContractWeightNoteAssembler();
        dtos.forEach(dto -> {
            ContractWeightNote contractWeightNote = assembler.fromDto(dto);
            instruction.updateContractWeightNote(contractWeightNote);
        });
    }

    @Transactional
    public void generateAndAttachLoadingReport(int shippingInstructionId, String updater) throws IOException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        Map<String, Object> data = getLoadingReportDto(shippingInstruction);
        String outputFileName = shippingInstruction.getRefNumber() + ".xls";
        excelReportService.generateReportAndStoreInBaseDir(excelLoadingReportName + ".xls", data, outputFileName);
        FileSent fileSent = fileUploadService.createFileSentFromBaseDir(updater, outputFileName);
        shippingInstruction.getInternalReferenceFileSents().add(fileSent);
    }

    public ShippingInstructionSummaryDTO getSummaryInfo(int shippingInstructionId) {
        ShippingInstruction instruction = shippingInstructionRepository.findOne(shippingInstructionId);
        ShippingInstructionSummaryAssembler assembler = new ShippingInstructionSummaryAssembler();
        return assembler.toDto(instruction);
    }

    public ShippingInstructionAdviceDTO getAdviceDto(int shippingInstructionId) {
        ShippingInstruction instruction = shippingInstructionRepository.findOne(shippingInstructionId);
        ShippingInstructionSummaryAssembler assembler = new ShippingInstructionSummaryAssembler();
        return assembler.toAdviceDto(instruction);
    }

    // may be optional here. TODO
    @Transactional
    public String generateShippingSummaryReport(int shippingInstructionId, String webPath) throws Exception {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        String excelOutputFileName = "Loading-report-" + shippingInstruction.getRefNumber();
        Map<String, Object> data = getLoadingReportDto(shippingInstruction);
        return excelReportService.generateImportReport(webPath, excelLoadingReportName, data, excelOutputFileName);
    }

    public Map<String, Object> getLoadingReportDto(int shippingInstructionId) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        return getLoadingReportDto(shippingInstruction);
    }

    private Map<String, Object> getLoadingReportDto(ShippingInstruction shippingInstruction) {
        Map<String, Object> data = new HashMap<>();
        ShippingInstructionSummaryAssembler shippingAssembler = new ShippingInstructionSummaryAssembler();
        ContractWeightNoteAssembler contractWeightNoteAssembler = new ContractWeightNoteAssembler();
        List<ContractWeightNoteDTO> cwns = contractWeightNoteAssembler.toListDto(shippingInstruction.getContractWeightNotesWithAddtionalInfo());
        data.put("si", shippingAssembler.toAdviceDto(shippingInstruction));
        data.put("contractWeightNotes", cwns);
        return data;
    }

    public Map<String, Object> getLoadingReportDtoForShowing(int shippingInstructionId) {
        Map<String, Object> data = getLoadingReportDto(shippingInstructionId);
        data.put("aggegrate", getAdviceAggegrateInfo(shippingInstructionId));
        return data;
    }

    public WeightNoteComparingAggegrate getAdviceAggegrateInfo(int shippingInstructionId) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        if (! shippingInstruction.isWeightNoteGeneratedAlready()) {
            throw new NotEnoughInfoException("It seems that you have not generated the contract weight note");
        }
        WeightNoteComparingAggegrateDTO dto = new WeightNoteComparingAggegrateDTO(shippingInstruction.getAdviceInfo());
        return dto;
    }

    @Transactional
    public ShippingAdvice createShippingAdvice(int shippingInstructionId, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        generateShippingInstructionContractWeight(shippingInstruction);
        return shippingInstructionDomainService.createShippingAdvice(shippingInstruction, user);
    }

    @Transactional
    public int updateShipmentStatus(String siRef, CompletionStatus status, User user) throws ApplicationException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        try {
            shippingInstruction.updateShipmentStatus(status, user);
        } catch (ActionNotPermitted ex) {
            throw new ApplicationException(ex.getMessage());
        }
        shippingInstructionRepository.save(shippingInstruction);
        return shippingInstruction.getId();
    }
    
    @Transactional
    public void updateRequestStatus(String refNumber, RequestStatus status, User user) throws ApplicationException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(refNumber);
        try {
            shippingInstruction.updateRequestStatus(status, user);
        } catch (ActionNotPermitted ex) {
            throw new ApplicationException(ex.getMessage());
        }
        shippingInstructionRepository.save(shippingInstruction);
    }

    @Transactional
    public int updateCompletionStatus(String siRef, CompletionStatus status, User user) throws ApplicationException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        try {
            shippingInstructionDomainService.updateShippingInstructionCompletionStatus(shippingInstruction, status, user);
        } catch (ActionNotPermitted ex) {
            throw new ApplicationException(ex.getMessage());
        }
        shippingInstructionRepository.save(shippingInstruction);
        return shippingInstruction.getId();
    }

    @Transactional
    public String updateOrSaveShippingInstruction(ShippingInstructionForm form, User user) throws ActionNotPermitted  {
        ShippingInstructionFormAssembler assembler = new ShippingInstructionFormAssembler();
        if (form.getId() != null) {
            ShippingInstruction fromDataBase = shippingInstructionRepository.findOne(form.getId());
            if(! fromDataBase.getVersion().equals(form.getVersion())) {
                throw new ActionNotPermitted("Someone has updated the si before you. Please do it again");
            }
            fromDataBase = assembler.fromDto(form, fromDataBase);
            shippingInstructionDomainService.updateSICost(fromDataBase);
            shippingInstructionDomainService.updatePacking(fromDataBase);
            fromDataBase.initUpdateSelf(user);
            shippingInstructionRepository.save(fromDataBase);
            return fromDataBase.getRefNumber();
        }
        ShippingInstruction shippingInstruction = assembler.fromDto(form, new ShippingInstruction());
        shippingInstructionDomainService.addNewSI(shippingInstruction, user);
        shippingInstruction = shippingInstructionRepository.save(shippingInstruction);
        return shippingInstruction.getRefNumber();
    }
    
    @Transactional
    public String updateSIForAdmin(ShippingInstructionForm form, User user) throws ActionNotPermitted  {
        ShippingInstructionFormAssembler assembler = new ShippingInstructionFormAssembler();
        if (form.getId() != null) {
            ShippingInstruction fromDataBase = shippingInstructionRepository.findOne(form.getId());
            if(! fromDataBase.getVersion().equals(form.getVersion())) {
                throw new ActionNotPermitted("Someone has updated the si before you. Please do it again");
            }
            fromDataBase = assembler.fromDto(form, fromDataBase);
            shippingInstructionDomainService.updateSICost(fromDataBase);
            shippingInstructionDomainService.updatePacking(fromDataBase);
            fromDataBase.initUpdateSelfForAdmin();
            shippingInstructionRepository.save(fromDataBase);
            return fromDataBase.getRefNumber();
        }
        return "";
    }

    @Transactional
    public int updateOrSaveShippingInstructionForClient(ShippingInstructionForm form, User user, int client) throws Exception {
        ShippingInstructionFormAssembler assembler = new ShippingInstructionFormAssembler();
        if (form.getId() != null) {
            ShippingInstruction fromDataBase = shippingInstructionRepository.findOne(form.getId());
            fromDataBase = assembler.fromDto(form, fromDataBase);
            shippingInstructionDomainService.updateSICost(fromDataBase);
            shippingInstructionDomainService.updatePacking(fromDataBase);
            fromDataBase.initUpdateSelf(user);
            shippingInstructionRepository.save(fromDataBase);
            return form.getId();
        }
        ShippingInstruction shippingInstruction = assembler.fromDto(form, new ShippingInstruction());
        shippingInstructionDomainService.addNewSI(shippingInstruction, user, client);
        shippingInstructionRepository.save(shippingInstruction);
        return shippingInstruction.getId();
    }

    @Transactional
    public String generatePdfShippingAdvice(int shippingInstructionId, User user) throws IOException, TemplateException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        ShippingInstructionSummaryAssembler shippingAssembler = new ShippingInstructionSummaryAssembler();
        Map<String, Object> data = new HashMap<>();
        data.put("si", shippingAssembler.toAdviceDto(shippingInstruction));
        data.put("dateTimeAMPMFormat", DateConstant.dateTimeAMPM);
        data.put("dateTimeFormat", DateConstant.dateTime);
        data.put("dateFormat", DateConstant.dateOnly);
        data.put("aggegrate", new WeightNoteComparingAggegrateDTO(shippingInstruction.getAdviceInfo()));
        String xhtml = htmlReportService.toHtmlString("shippingAdvice.ftl", data);
        ShippingAdviceSent shippingAdviceSent = shippingInstructionDomainService.createShippingAdviceSent(shippingInstruction, user);
        return pdfRenderService.renderAsTraceableUrl("ShippingAdvice", shippingAdviceSent.getFileName(), xhtml);
    }

    @Transactional
    public int newSampleSent(NewSampleSentForm form, User user) {
        SampleSentAssembler assembler = new SampleSentAssembler();
        SampleSent sampleSent = assembler.fromDto(form);
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(form.getSiId());
        shippingInstructionDomainService.addNewSampleSent(shippingInstruction, sampleSent, user);
        shippingInstructionRepository.save(shippingInstruction);
        return shippingInstruction.getId();
    }

    @Transactional
    public String updateSampleSent(UpdateSampleSentForm form, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(form.getSiRef());
        SampleSent ss = shippingInstruction.findChildSampleSent(form.getRefNumber());
        SampleSentAssembler assembler = new SampleSentAssembler();
        SampleSent sampleSent = assembler.fromDto(form, ss);
        sampleSent.updateTimeAndUser(user);
        shippingInstructionRepository.save(shippingInstruction);
        return form.getRefNumber();
    }

    public Map<String, Object> getSSDetail(String siRef, String ssRef) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        SampleSent ss = shippingInstruction.findChildSampleSent(ssRef);
        Map<String, Object> result = new HashMap<>();
        ShippingInstructionSummaryAssembler shippingInstructionFormAssembler = new ShippingInstructionSummaryAssembler();
        SampleSentAssembler sampleSentAssembler = new SampleSentAssembler();
        result.put("si", shippingInstructionFormAssembler.toDto(shippingInstruction));
        result.put("ss", sampleSentAssembler.toForm(ss));
        result.put("approvalStatusForm", new ApprovalStatusForm(ss.getRefNumber(), ss.findApprovalStatusUsername(), ss.getSaveApprovalStatusDate(), ss.getApprovalStatusEnum()));
        result.put("sendingStatusForm", new SendingStatusForm(ss.getRefNumber(), ss.findSendingStatusUsername(), ss.getSaveSendingStatusDate(), ss.getSendingStatusEnum()));
        result.put("couriers", courierMasterRepository.findAll());
        result.put("sendingStatuses", InstructionStatus.getSendingStatuses());
        result.put("approvalStatuses", InstructionStatus.getApprovalStatuses());
        SampleSentItemAssembler assembler = new SampleSentItemAssembler();
        result.put("sampleSentItems", assembler.fromObject(ss.getSampleSentItems()));
        
        //result.put("wns", weightNoteRepository.findAll(Specifications.where(new ItemNotDeleted<WeightNote>()).and(new WeightNoteNotAllocated())));
        result.put("wns", weightNoteRepository.getAvailableRefNumber());
        return result;
    }

    @Transactional
    public int updateSampleSentRemark(int shippingInstructionId, RemarkUpdateSampleSentForm form, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        Set<SampleSent> sampleSents = shippingInstruction.getSampleSents();
        SampleSent thisSampleSent = sampleSents.stream().filter(ss -> form.getId() == ss.getId()).findFirst().get();
        RemarkUpdateSampleSentAssembler assembler = new RemarkUpdateSampleSentAssembler();
        thisSampleSent = assembler.fromDto(form, thisSampleSent);
        thisSampleSent.updateUserRemark(user);
        return shippingInstruction.getId();
    }

    @Transactional
    public void updateAllSIsToNewEmbeddedModel() {
        List<Integer> ids = shippingInstructionRepository.getCostableIds();
        ids.stream().map((id) -> shippingInstructionRepository.findOne(id)).map((instruction) -> {
            instruction.setShippingCost(new ShippingCost(instruction.getClientSiCostListJson()));
            instruction.setRequestStatusEnum(RequestStatus.PENDING);
            if (instruction.getRequestStatus() == null) {
                instruction.setRequestStatus(InstructionStatus.InstructionRequestStatus.APPROVED);
                instruction.setRequestStatusEnum(RequestStatus.APPROVED);
            } else {
                try {
                    instruction.setRequestStatusEnum(RequestStatus.values[Integer.valueOf(String.valueOf(instruction.getRequestStatus()))]);
                } catch (Exception e) {

                }
            }
            try {
                instruction.setShipmentStatusEnum(CompletionStatus.values[instruction.getShipmentStatus()]);
            } catch (Exception e) {
            }

            if (instruction.getStatus() == null) {
                instruction.setStatus(InstructionStatus.InstructionCompletionStatus.COMPLETED);
                instruction.setCompletionStatus(CompletionStatus.COMPLETED);
            } else {
                try {
                    instruction.setCompletionStatus(CompletionStatus.values[Integer.valueOf(String.valueOf(instruction.getStatus()))]);
                } catch (Exception e) {

                }
            }
            return instruction;
        }).forEachOrdered((instruction) -> {
            updateSI(instruction);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSI(ShippingInstruction instruction) {
        shippingInstructionRepository.save(instruction);
    }

    @Transactional
    public FileSent newFileUpload(int shippingInstructionId, MultipartFile uploadfile, String name, String emails, User user, FileUploadType type) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        Optional<FileSent> fileSent = storageService.saveFileSentFromFile(uploadfile, name, emails, user);
        if (fileSent.isPresent()) {
            if (type.equals(FileUploadType.Internal)) {
                shippingInstruction.addInternalReferenceFileSent(fileSent.get());
            } else {
                shippingInstruction.addReferenceFileSent(fileSent.get());
            }
            return fileSent.get();
        }
        return null;
    }

    @Transactional
    public FileSent updateFileSent(int shippingInstructionId, UpdateFileSentForm form) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        FileSent file = shippingInstruction.updateFileSentRemindName(form.getId(), form.getRemindName());
        return file;
    }

    @Transactional
    public void addCustomCost(int shippingInstructionId, CustomCostForm form) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        SICustomCost sICustomCost = new SICustomCost(form.getOptionUnit(), form.getOptionName(), form.getCostValue());
        shippingInstruction.addSiCustomCost(sICustomCost);
        shippingInstructionRepository.save(shippingInstruction);
    }

    @Transactional
    public void removeCustomCost(int shippingInstructionId, CustomCostForm form) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        shippingInstruction.removeSiCustomCost(form.getId());
        shippingInstructionRepository.save(shippingInstruction);
    }

    @Transactional
    public BaseIdAndNameImpl addNotifyParty(int shippingInstructionId, int company) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(shippingInstructionId);
        BaseIdAndNameImpl res = shippingInstruction.addNotifyParty(company);
        shippingInstructionRepository.save(shippingInstruction);
        return res;
    }

    @Transactional(rollbackFor = {ApplicationException.class})
    public int updateSampleSentApprovalStatus(String siRef, String ssRef, ApprovalStatus status, User user) throws ApplicationException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        SampleSent sampleSent = shippingInstruction.updateSampleSentApprovalStatus(ssRef, status, user);
        if(sampleSent.getApprovalStatusEnum() == ApprovalStatus.REJECTED) {
            applicationEventPublisher.publishEvent(new SSRejectedEvent(shippingInstruction, sampleSent, user));
        } 
        if(sampleSent.getApprovalStatusEnum() == ApprovalStatus.APPROVED) {
            if(! sampleSentFullInfoPolicy.isAllowedToApprove(sampleSent)) {
                throw new ApplicationException("Sample sent policy: document, recipient and lotref can not be empty");
            }
        }
        shippingInstructionRepository.save(shippingInstruction);
        return sampleSent.getId();
    }

    @Transactional
    public void _updateSampleSentApprovalStatus(ShippingInstruction shippingInstruction, String ssRef, ApprovalStatus status, User user) {
        shippingInstruction.updateSampleSentApprovalStatus(ssRef, status, user);
    }

    @Transactional
    public int updateSampleSentSendingStatus(String siRef, String ssRef, SendingStatus status, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        SampleSent ss = shippingInstruction.updateSampleSentSendingStatus(ssRef, status, user);
        shippingInstructionRepository.save(shippingInstruction);
        return ss.getId();
    }

    public ShippingInstruction getByRef(String refNumber) {
        return shippingInstructionRepository.findFirstByRefNumber(refNumber);
    }

}
