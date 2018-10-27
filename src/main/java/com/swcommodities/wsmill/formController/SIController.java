package com.swcommodities.wsmill.formController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.domain.service.ShippingInstructionDomainService;
import com.swcommodities.wsmill.formController.form.CompletionStatusForm;
import com.swcommodities.wsmill.formController.form.RequestStatusForm;
import com.swcommodities.wsmill.formController.form.ShippingInstructionForm;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ClaimAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.FileSentAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ShippingInstructionFormAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.TransactionShortAssembler;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.PortRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.repository.ShippingLineCompanyMasterRepository;
import com.swcommodities.wsmill.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
public class SIController extends PagingController {

    private static final Logger logger = Logger.getLogger(SIController.class);

    @Autowired
    private ShippingInstructionRepository shippingInstructionRepository;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private ShippingLineCompanyMasterRepository shippingLineCompanyMasterRepository;

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PackingRepository packingRepository;

    @Resource(name = "configConfigurer")
    Properties configConfigurer;
    @Autowired
    DeliveryInsService deliveryInsService;

    @Autowired
    ShippingService shippingService;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired private ExchangeRepository exchangeRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CourierMasterRepository courierMasterRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired ShippingInstructionService shippingInstructionService;
    @Autowired CompanyService companyService;
    @Autowired ShippingInstructionDomainService shippingInstructionDomainService;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private boolean isEdit(ShippingInstruction shippingInstruction) {
        return shippingInstruction.getId() != null;
    }

    @RequestMapping(value = "shipping-v2.htm", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public String get(Map<String, Object> model, @RequestParam(required = false) String id, Model formModel) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ShippingInstruction shippingInstruction;
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            if(StringUtils.isNumeric(id)) {
                currentId = Integer.valueOf(id);
                shippingInstruction = shippingInstructionRepository.findOne(currentId);
            } else {
                shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(id);
                currentId = shippingInstruction.getId();
            }
            is_form_edit = true;
            String baseStaticShippingFolder = configConfigurer.getProperty("base_static_host") + "/ShippingAdvice/";
            model.put("baseStaticShippingFolder", baseStaticShippingFolder);
            model.put("isCompleted", shippingInstruction.completed());
            TransactionShortAssembler assembler = new TransactionShortAssembler();
            model.put("transactions", assembler.fromDto(transactionRepository.getTransactionsBySiId(currentId)));
            
            CompletionStatusForm shipmentStatusForm = new CompletionStatusForm(shippingInstruction.getRefNumber(),
                Optional.ofNullable(shippingInstruction.getShipmentStatusUpdateUser()).map(u -> u.getUserName()).orElse(""), shippingInstruction.getShipmentStatusUpdateDate(), shippingInstruction.getShipmentStatusEnum());
            formModel.addAttribute("shipmentStatusForm", shipmentStatusForm);
            
            RequestStatusForm requestStatusForm = new RequestStatusForm(shippingInstruction.getRefNumber(), 
                Optional.ofNullable(shippingInstruction.getRequestUser()).map(u -> u.getUserName()).orElse(""), shippingInstruction.getRequestDate(), 
                shippingInstruction.getRequestStatusEnum());
            formModel.addAttribute("requestStatusForm", requestStatusForm);
            
            CompletionStatusForm completionStatusForm = new CompletionStatusForm(shippingInstruction.getRefNumber(),
                Optional.ofNullable(shippingInstruction.getCompletionStatusUser()).map(u -> u.getUserName()).orElse(""), shippingInstruction.getUpdateCompletionDate(), 
                shippingInstruction.getCompletionStatus());
            formModel.addAttribute("completionStatusForm", completionStatusForm);
            
            FileSentAssembler fileSentAssembler = new FileSentAssembler();
            model.put("referenceFileSents", fileSentAssembler.fromObject(shippingInstruction.getReferenceFileSents()));
            model.put("internalReferenceFileSents", fileSentAssembler.fromObject(shippingInstruction.getInternalReferenceFileSents()));
            
            List<SICustomCost> sICustomCosts = shippingInstructionDomainService.getCustomCostsForSIWithVNDInfo(shippingInstruction);
            shippingInstruction.getShippingCost().setsICustomCosts(sICustomCosts);
            
            model.put("customCosts", mapper.writeValueAsString(sICustomCosts));
            model.put("customCostsJSP", sICustomCosts);
            ClaimAssembler claimAssembler = new ClaimAssembler();
            model.put("claims", claimAssembler.toUpdateFormList(shippingInstruction.getClaims()));
                        
        } else {
            model.put("isCompleted", false);
            shippingInstruction = new ShippingInstruction();
        }
        ShippingInstructionFormAssembler assembler = new ShippingInstructionFormAssembler();
        ShippingInstructionForm shippingInstructionForm = assembler.toForm(shippingInstruction);
        formModel.addAttribute("shippingForm", shippingInstructionForm);
        model.put("shippingFormJS", mapper.writeValueAsString(shippingInstructionForm));

        model.put("exchangeString", mapper.writeValueAsString(exchangeRepository.getFirstObject()));
        model.put("categoryString", mapper.writeValueAsString(categoryRepository.findAll()));

        model.put("clients", companyService.findAllClients());
        model.put("buyers", companyService.findAllBuyers());
        model.put("shippers", companyService.findAllShippers());
        model.put("suppliers", companyService.findAllSuppliers());
        model.put("notifiers", companyService.findAllNotifyParties());
        model.put("consignees", companyService.findAllConsignees());

        model.put("origins", originRepository.findAll());
        model.put("qualities", qualityRepository.findAll());
        model.put("grades", gradeRepository.findAllByOrderByNameAsc());
        model.put("packings", packingRepository.findAllByOrderByNameAsc());
        model.put("ports", portRepository.findAllByOrderByNameAsc());
        model.put("shippingLines", shippingLineCompanyMasterRepository.findAllByOrderByNameAsc());
        model.put("containerStatus", InstructionStatus.getContainerStatuses());
        model.put("requestStatus", InstructionStatus.getRequestStatuses());
        model.put("sis", shippingInstructionRepository.findRefList());
        model.put("couriers", courierMasterRepository.findAll());
        
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        model.put("units", new JSONArray(CostCalculator.getUnits()));
        model.put("singleDocumentCost", CostCalculator.singleDocumentCost);
        return "si/shipping-v2_2";
    }

    
    
    

}