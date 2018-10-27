package com.swcommodities.wsmill.application.service;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.domain.model.ClaimWeightNote;
import com.swcommodities.wsmill.domain.model.FileUploadType;
import com.swcommodities.wsmill.domain.model.common.GenericBuilder;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.domain.model.exceptions.DomainException;
import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.hibernate.dto.*;
import com.swcommodities.wsmill.hibernate.specification.*;
import com.swcommodities.wsmill.repository.WarehouseMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.ShippingInstructionDomainService;
import com.swcommodities.wsmill.formController.form.ApprovalStatusForm;
import com.swcommodities.wsmill.formController.form.ClaimForm;
import com.swcommodities.wsmill.formController.form.ClaimSearchForm;
import com.swcommodities.wsmill.formController.form.UpdateClaimForm;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ClaimAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ShippingInstructionSummaryAssembler;
import com.swcommodities.wsmill.hibernate.dto.query.result.ClaimPagingResult;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.ClaimRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.service.PagingService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SIClaimService {
	
	@Autowired ShippingInstructionRepository shippingInstructionRepository;
	@Autowired ShippingInstructionDomainService shippingInstructionDomainService;
    @Autowired ClaimRepository claimRepository;
    @Autowired PagingService pagingService;
    @Autowired StorageService storageService;
    @Autowired WarehouseMasterRepository warehouseMasterRepository;

	@Transactional
    public String updateClaim(UpdateClaimForm form, String siRef, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        Claim claim = shippingInstruction.findChildClaim(form.getRefNumber());
        ClaimAssembler assembler = new ClaimAssembler();
        Claim saveClaim = assembler.fromDto(form, claim);
        saveClaim.updateTimeAndUser(user);
        shippingInstructionRepository.save(shippingInstruction);
        return form.getRefNumber();
    }
	
	@Transactional
    public String newClaim(ClaimForm form, User user) throws ApplicationException {
        ClaimAssembler assembler = new ClaimAssembler();
        Claim claim = assembler.fromDto(form);
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(form.getSiId());
        String refNumber = "";
        try {
            refNumber = shippingInstructionDomainService.addNewClaim(shippingInstruction, claim, user);
        } catch (ActionNotPermitted ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return refNumber;
    }

    public Map<String, Object> getClaimDetail(String siRef, String claimRefNumber) throws DomainException {
        Map<String, Object> result = new HashMap<>();
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        Claim claim = shippingInstruction.getClaims().stream().filter(cl -> cl.getRefNumber().equals(claimRefNumber)).findFirst().get();
        result.put("approvalStatusForm", new ApprovalStatusForm(claim.getRefNumber(), claim.findApprovalStatusUsername(), claim.getClaimStatusUpdateDate(), claim.getClaimStatus()));
        ShippingInstructionSummaryAssembler shippingInstructionSummaryAssembler = new ShippingInstructionSummaryAssembler();
        ClaimAssembler claimAssembler = new ClaimAssembler();
        result.put("si", shippingInstructionSummaryAssembler.toAdviceDto(shippingInstruction));
        result.put("claimForm", claimAssembler.toUpdateForm(claim));
        result.put("warehouses", warehouseMasterRepository.findAll());
        result.put("claimWeightNotes", claim.getClaimWeightNotes());
        result.put("aggegrate", shippingInstruction.findClaimAggegrate(claimRefNumber));
        return result;
    }
    
    @Transactional
    public void updateClaimWeightNote(String siRef, String claimRef, List<ClaimWeightNote> claimWeightNotes, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        Claim claim = shippingInstruction.findChildClaim(claimRef);
        claimWeightNotes.forEach(cwn -> claim.addClaimWeightNote(cwn));
        claim.updateUpdateArrivalWeightNoteInfo(user);
        shippingInstructionRepository.save(shippingInstruction);
    }

    @Transactional
    public int updateClaimApprovalStatus(String siRef, String claimRef, ApprovalStatus status, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        Claim claim = shippingInstruction.updateClaimApprovalStatus(claimRef, status, user);
        shippingInstructionRepository.save(shippingInstruction);
        return claim.getId();
    }
    
    @Transactional
    public void addClaimDocument(String siRef, String claimRef, MultipartFile uploadfile, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        Claim claim = shippingInstruction.findChildClaim(claimRef);
        Optional<FileSent> fileSent = storageService.saveFileSentFromFile(uploadfile, "", "", user);
        if (fileSent.isPresent()) {
            claim.addDocument(fileSent.get());
        } else {
            System.out.println("No file " + uploadfile.getName());
        }
        shippingInstructionRepository.save(shippingInstruction);
    }

    public ClaimPagingResult getPagingResult(ClaimSearchForm form) {
        Specification<Claim> spec = Specifications
            .where(new ParentShippingInstructionBelongToClient(form.getClientId()))
            .and(new ClaimDateBetweenDates(form.getStartDate(), form.getEndDate()))
            .and(new ApprovalStatusEqual<>(form.getClaimStatus()))
            .and(new ClaimPropertyLike("refNumber", form.getSearchText()));
        Pageable pageable = new PageRequest(form.getPage(), form.getPerPage());
        Page<Claim> pageResult = claimRepository.findAll(spec, pageable);
        return GenericBuilder.of(ClaimPagingResult::new)
            .with(ClaimPagingResult::setPage, form.getPage())
            .with(ClaimPagingResult::setPagination, pagingService.getPagingHtml(pageResult.getTotalPages(), form.getPage() + 1))
            .with(ClaimPagingResult::setPerPage, form.getPerPage())
            .with(ClaimPagingResult::setResults, pageResult.getContent())
            .with(ClaimPagingResult::setTotalPages, pageResult.getTotalPages())
            .with(ClaimPagingResult::setTotalRecords, (int) pageResult.getTotalElements())
            .build();
    }
	
}
