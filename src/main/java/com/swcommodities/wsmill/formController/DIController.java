package com.swcommodities.wsmill.formController;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.LocationMasterRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.QualificationCompanyRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.service.TransactionService;
import com.swcommodities.wsmill.utils.Constants;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("diForm") // Specify attributes to be stored in the session
@Transactional
public class DIController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired
    private DeliveryInstructionRepository deliveryInstructionRepository;

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PackingRepository packingRepository;

    @Autowired
    private QualificationCompanyRepository qualificationCompanyRepository;

    @Autowired
    private LocationMasterRepository locationMasterRepository;

    @Autowired
    DeliveryInsService deliveryInsService;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired private MyAsyncService myAsyncService;

    @Autowired private TransactionService transactionService;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private boolean isEdit(DeliveryInstruction deliveryInstruction) {
        return deliveryInstruction.getId() != null;
    }

    @RequestMapping(value = "delivery-v2.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        DIForm form = new DIForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            DeliveryInstruction deliveryInstruction = deliveryInstructionRepository.findOne(id);
            // lazy load: no session fixed
//            try {
//                deliveryInstruction.getUserByRequestUserId().getUserName();
//            } catch (Exception e) {
//
//            }
            try {
                deliveryInstruction.getUserByUpdateUserId().getUserName();
            } catch (Exception e) {

            }
            try {
                deliveryInstruction.getUser().getUserName();
            } catch (Exception e) {

            }
            Double deliverd = deliveryInstructionRepository.getDeliveryDeliverd(id);
            if(deliverd == null) {
                deliverd = 0.0;
            }
            deliveryInstruction.setDeliverd(deliverd);
            deliveryInstruction.setPending(deliveryInstruction.getTons() - deliverd);
            form.setItem(deliveryInstruction);
        } else {

        }
        model.put("diForm", form);

        model.put("clients", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
        //model.put("banks", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Bank").getId()));
        model.put("suppliers", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Supplier").getId()));
        //model.put("controllers", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Controller").getId()));

        model.put("origins", originRepository.findAll());
        model.put("qualities", qualityRepository.findAll());
        model.put("grades", gradeRepository.findAllByOrderByNameAsc());
        model.put("packings", packingRepository.findAllByOrderByNameAsc());
        //model.put("locations", locationMasterRepository.findAllByOrderByNameAsc());
        //model.put("requestStatus", InstructionStatus.getRequestStatuses());
        model.put("completionStatus", InstructionStatus.getCompletionStatuses());
        model.put("dis", deliveryInstructionRepository.findRefList());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "delivery-v2";
    }

    @RequestMapping(value = "delivery-v2.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("diForm") DIForm diForm,
                       Map<String, Object> model) {
        DeliveryInstruction deliveryInstruction = diForm.getItem();
        if(! isEdit(deliveryInstruction)) {
            deliveryInstruction.setRefNumber(deliveryInsService.getNewContractRef());
            deliveryInstruction.setDate(new Date());

//            deliveryInstruction.setUserByRequestUserId(getCurrentUser());
//            deliveryInstruction.setRequestDate(new Date());
//            deliveryInstruction.setRequestStatus(Constants.PENDING);

            deliveryInstruction.setUserByUpdateUserId(getCurrentUser());
            deliveryInstruction.setUpdateDate(new Date());
            deliveryInstruction.setStatus(Constants.PENDING);
        }
        deliveryInstruction.setUser(getCurrentUser());
        deliveryInstruction.setUserUpdateDate(new Date());
        deliveryInstructionRepository.save(deliveryInstruction);
        try {
            myAsyncService.saveCacheDI(deliveryInstruction);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/delivery-v2.htm?id=" + deliveryInstruction.getId();
    }

    @RequestMapping(value = "delivery_saveCompletionStatus.htm", method = RequestMethod.POST)
    public String updateStatus(@ModelAttribute("diForm") DIForm diForm,
                                      Map<String, Object> model) {

        DeliveryInstruction deliveryInstruction = diForm.getItem();
        if (deliveryInstruction.getCompletable()) {
            deliveryInstruction.setUserByUpdateUserId(getCurrentUser());
            deliveryInstruction.setUpdateDate(new Date());
            Double deliverd = deliveryInstructionRepository.getDeliveryDeliverd(deliveryInstruction.getId());
            if(deliverd == null) {
                deliverd = 0.0;
            }
            double pending = Math.round((deliveryInstruction.getTons() - deliverd) * 1000.0) / 1000.0;
            deliveryInstruction.setDeliverd(deliverd);
            deliveryInstruction.setPending(pending);
            deliveryInstructionRepository.save(deliveryInstruction);
            try {
                myAsyncService.saveCacheDI(deliveryInstruction);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //Create a transaction on completing an Ins
//            if (deliveryInstruction.getStatus().equals(Byte.valueOf("1"))) {
//                transactionService.createTransactionWhenCompletingDI(deliveryInstruction, getCurrentUser());
//            }
        }

        return "redirect:/delivery-v2.htm?id=" + deliveryInstruction.getId();
    }
    
    @RequestMapping(value = "get_di_info/{id}.json")
    @Transactional(readOnly=true)
    public ResponseEntity<Map<String, String>> getDIInfo(@PathVariable int id) throws Exception {
        DecimalFormat df = new DecimalFormat("0.000");
        Map<String, String> valueMap = new HashMap<>();
        DeliveryInstruction deliveryInstruction = deliveryInstructionRepository.findOne(id);
        Double deliverd = deliveryInstructionRepository.getDeliveryDeliverd(id);
        if(deliverd == null) {
            deliverd = 0.0;
        }
        valueMap.put("deliverd", df.format(deliverd));
        valueMap.put("tons", df.format(deliveryInstruction.getTons()));
        double pending = Math.round((deliveryInstruction.getTons() - deliverd) * 1000.0) / 1000.0;;
        valueMap.put("pending", df.format(pending));
        return new ResponseEntity<>(valueMap, HttpStatus.OK);
    }

}