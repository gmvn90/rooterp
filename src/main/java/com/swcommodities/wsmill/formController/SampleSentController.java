package com.swcommodities.wsmill.formController;

import java.util.Date;
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

import com.swcommodities.wsmill.bo.QualityReportService;
import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.repository.UserRepository;
import com.swcommodities.wsmill.service.InstructionService;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.utils.Constants;


/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("sampleSentForm") // Specify attributes to be stored in the session
@Transactional
public class SampleSentController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private SampleSentRepository sampleSentRepository;

    @Autowired
    private CourierMasterRepository courierMasterRepository;


    @Autowired
    private QualityReportService qualityReportService;

    @Autowired
    private InstructionService instructionService;

    @Autowired private MyAsyncService myAsyncService;
    
    @Autowired private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;


    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private boolean isEdit(SampleSent sampleSent) {
        return sampleSent.getId() != null;
    }

//    @RequestMapping(value = "sample-sent-v2.htm", method = RequestMethod.GET)
//    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id, Model formModel) throws Exception{
//        
//        
//        SampleSent sampleSent = sampleSentRepository.findOne(id);
//        SampleSentAssembler assembler = new SampleSentAssembler();
//        UpdateSampleSentForm form = assembler.toForm(sampleSent);
//        formModel.addAttribute("approvalStatusForm", new ApprovalStatusForm(sampleSent.getId(), sampleSent.findApprovalStatusUsername(), sampleSent.getSaveApprovalStatusDate(), sampleSent.getApprovalStatusEnum()));
//        formModel.addAttribute("sendingStatusForm", new SendingStatusForm(sampleSent.getId(), sampleSent.findApprovalStatusUsername(), sampleSent.getSaveApprovalStatusDate(), sampleSent.getSendingStatusEnum()));
//        model.put("sampleSentForm", form);
//        model.put("couriers", courierMasterRepository.findAll());
//        model.put("sendingStatuses", InstructionStatus.getSendingStatuses());
//        model.put("approvalStatuses", InstructionStatus.getApprovalStatuses());
//        model.put("weightCertificate", instructionService.getWeightCertificateNameByOption(sampleSent.getShippingInstructionBySiId().getWeightQualityCertificate()));
//        model.put("fumigationCertificate", instructionService.getFumigationNameByOptionName(sampleSent.getShippingInstructionBySiId().getFumigation()));
//        
//        return "sample-sent-v2";
//    }


//    @RequestMapping(value = "sample-sent-v2.htm", method = RequestMethod.POST)
//    public String post(@ModelAttribute("sampleSentForm") SampleSentForm sampleSentForm,
//                       Map<String, Object> model, HttpServletRequest req, Model formModel) {
//        SampleSent sampleSent = sampleSentForm.getItem();
//        sampleSent.setUser(getCurrentUser());
//        sampleSent.setUpdatedDate(new Date());
//        sampleSent.setType(InstructionStatus.SampleSentType.PSS);
//        sampleSentRepository.save(sampleSent);
//        try {
//            myAsyncService.saveCacheSampleSent(sampleSent);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            myAsyncService.saveCacheSI(sampleSent.getShippingInstructionBySiId());
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "redirect:/sample-sent-v2.htm?id=" + sampleSent.getId();
//    }

    //For new sample sent type
    
//    
    
//    
//    @RequestMapping(value = "sample-sent-remark.json", method = RequestMethod.POST)
//    public @ResponseBody String updateSampleSentRemark(Map<String, Object> model, HttpServletRequest req) throws JSONException {
//        int id = Integer.parseInt(req.getParameter("id"));
//        String remark = request.getParameter("remark");
//        SampleSent ss = sampleSentRepository.findOne(id);
//        ss.setSaveRemarkDate(new Date());
//        ss.setSaveRemarkUser(userRepository.findOne(getCurrentUser().getId()));
//        
//        
//        ss.setRemark(remark);
//        sampleSentRepository.save(ss);
//        JSONObject object = new JSONObject();
//        object.put("id", id);
//        object.put("remark", remark);
//        object.put("remarkUser", ss.getSaveRemarkUser().getUserName());
//        object.put("remarkDate", ss.getSaveRemarkDate().getTime());
//        
//        return object.toString();
//    }
//
//    @RequestMapping(value = "sampleSent_saveSendingStatus.htm", method = RequestMethod.POST)
//    public String updateStatus(@ModelAttribute("sampleSentForm") SampleSentForm sampleSentForm, final RedirectAttributes redirectAttributes,
//                                      Map<String, Object> model) {
//        SampleSent sampleSent = sampleSentForm.getItem();
//        sampleSent.setSaveSendingStatusUser(getCurrentUser());
//        sampleSent.setSaveSendingStatusDate(new Date());
//
//        sampleSentRepository.save(sampleSent);
//        try {
//            myAsyncService.saveCacheSampleSent(sampleSent);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            myAsyncService.saveCacheSI(sampleSent.getShippingInstructionBySiId());
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "redirect:/sample-sent-v2.htm?id=" + sampleSent.getId();
//    }
//
//    @RequestMapping(value = "sampleSent_saveApprovalStatus.htm", method = RequestMethod.POST)
//    public String updateRequestStatus(@ModelAttribute("sampleSentForm") SampleSentForm sampleSentForm, final RedirectAttributes redirectAttributes,
//                               Map<String, Object> model, Model formModel) {
//        SampleSent sampleSent = sampleSentForm.getItem();
//        sampleSent.setSaveApprovalStatusUser(getCurrentUser());
//        sampleSent.setSaveApprovalStatusDate(new Date());
//        
//        
//
//        sampleSentRepository.save(sampleSent);
//        try {
//            myAsyncService.saveCacheSampleSent(sampleSent);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            myAsyncService.saveCacheSI(sampleSent.getShippingInstructionBySiId());
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        if (sampleSent.getApprovalStatus().equals(Byte.parseByte("2"))) {
//
//            SampleSent ss = new SampleSent();
//
//            ss.setRefNumber(qualityReportService.getNewSampleSentRef());
//            ss.setCreatedDate(new Date());
//            ss.setShippingInstructionBySiId(sampleSent.getShippingInstructionBySiId());
//            ss.setUser(getCurrentUser());
//            ss.setTrackingNo("");
//            ss.setUpdatedDate(new Date());
//            ss.setSendingStatus(Constants.PENDING);
//            ss.setApprovalStatus(Constants.APPROVAL_PENDING);
//            ss.setRemark("");
//            ss.setCourierMaster(sampleSent.getCourierMaster());
//            ss = qualityReportService.newSampleSent(ss);
//            try {
//                myAsyncService.saveCacheSampleSent(ss);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            try {
//                myAsyncService.saveCacheSI(ss.getShippingInstructionBySiId());
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return "redirect:/sample-sent-v2.htm?id=" + sampleSent.getId();
//    }

}