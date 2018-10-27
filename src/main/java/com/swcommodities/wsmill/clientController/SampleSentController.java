package com.swcommodities.wsmill.clientController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Throwables;
import com.swcommodities.wsmill.bo.QualityReportService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.query.result.SampleSentPagingResult;
import com.swcommodities.wsmill.hibernate.dto.serializer.SampleSentCardViewInstructionSerializer;
import com.swcommodities.wsmill.hibernate.dto.serializer.SampleTypeCardViewSerializer;
import com.swcommodities.wsmill.repository.SSCacheRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.service.SampleSentPagingService;
import com.swcommodities.wsmill.utils.Constants;

@RequestMapping("/millclient/sample_sents")
@Controller("clientController_sampleSentController")
@Transactional
public class SampleSentController {

    @Autowired
    private SampleSentRepository sampleSentRepository;
    @Autowired
    private QualityReportService qualityReportService;
    @Autowired
    private SampleSentPagingService sampleSentPagingService;
    @Autowired
    private SSCacheRepository ssCacheRepository;
    @Autowired
    private MyAsyncService myAsyncService;

    protected Integer getCompanyId(HttpSession httpSession) {
        Integer company_id = (Integer) httpSession.getAttribute("company_id");
        return company_id;
    }

    @RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<String> create(@RequestBody SampleSent json, HttpSession httpSession) throws JsonProcessingException, InterruptedException {
        Integer company_id = getCompanyId(httpSession);
        if (company_id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (json.getType().compareTo(InstructionStatus.SampleSentType.TYPE) == 0) {
        } else {
        }
        json.setRefNumber(qualityReportService.getNewSampleSentRef());
        json.setCreatedDate(new Date());
        json.setUser((User) httpSession.getAttribute("user"));
        json.setUpdatedDate(new Date());
        json.setSendingStatus(Constants.PENDING);
        json.setApprovalStatus(Constants.APPROVAL_PENDING);
        json.setTrackingNo("");

        SampleSent created = sampleSentRepository.save(json);
        try {
            myAsyncService.saveCacheSampleSent(created);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);
        return new ResponseEntity<String>(mapper.writeValueAsString(created), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}.json", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<String> update(HttpSession httpSession, @PathVariable Integer id, @RequestBody SampleSent json)
            throws JsonProcessingException, InterruptedException {
        SampleSent entity = sampleSentRepository.findOne(id);
        User user = (User) httpSession.getAttribute("user");
        boolean changeUser = false;
        if (entity.getApprovalStatus().compareTo(json.getApprovalStatus()) != 0) {
            changeUser = true;
        }
        try {
            BeanUtils.copyProperties(json, entity);
            if (changeUser) {
                entity.setSaveApprovalStatusDate(new Date());
                entity.setSaveApprovalStatusUser(user);
            }
        } catch (Exception e) {
            System.out.println("while copying properties" + e);
            throw Throwables.propagate(e);
        }

        //completion saving method
        Integer company_id = getCompanyId(httpSession);
        entity.setUser(user);
        entity.setUpdatedDate(new Date());
        SampleSent updated = sampleSentRepository.save(entity);
        try {
            myAsyncService.saveCacheSampleSent(updated.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            myAsyncService.saveCacheSI(updated.getShippingInstructionBySiId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);
        return new ResponseEntity<String>(mapper.writeValueAsString(updated), HttpStatus.OK);
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<SampleSentCache>> getListView(HttpSession httpSession)
            throws JsonProcessingException {
        int company_id = getCompanyId(httpSession);
        List<Integer> ids = sampleSentRepository.getPendingIds(company_id);
        System.out.println(ids);
        List<SampleSentCache> ssCaches = ssCacheRepository.findBySampleSentIdIn(ids);
        System.out.println(ssCaches);
        if (ssCaches == null) {
            ssCaches = new ArrayList<>();
        }
        return new ResponseEntity<>(ssCaches, HttpStatus.OK);
    }

    @RequestMapping(value = "/all.json", method = RequestMethod.GET)
    public ResponseEntity<String> get(HttpSession httpSession) throws JsonProcessingException {
        System.out.println("ResponseEntityGET SampleSentListViewSerializer");
        List<SampleSent> all = new ArrayList<>();
        Integer company_id = getCompanyId(httpSession);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);

        if (company_id > 0) {
            all = sampleSentRepository.findByShippingInstructionBySiId_companyMasterByClientId_Id(company_id);
        }
        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @RequestMapping(value = "/allBySi.json", method = RequestMethod.GET)
    public ResponseEntity<String> getAllBySi(HttpSession httpSession, @RequestParam(required = true) Integer siId) throws JsonProcessingException {
        List<SampleSent> all = new ArrayList<>();
        Integer company_id = getCompanyId(httpSession);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);

        if (company_id > 0) {
            all = sampleSentRepository.findByShippingInstructionBySiId_Id(siId);
        }
        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<SampleSentPagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Byte sentStatus,
            @RequestParam(required = false) Byte approvalStatus, @RequestParam(required = false) String txtSearch,
            @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate)
            throws JsonProcessingException {
        int clientId = getCompanyId(httpSession);
        SampleSentPagingResult result = sampleSentPagingService.getAggregateInfoForClientSite(clientId, txtSearch, sentStatus, approvalStatus,
                startDate, endDate, pageNo, perPage);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> getSingle(@PathVariable Integer id, HttpSession httpSession)
            throws JsonProcessingException {
        SampleSent obj = sampleSentRepository.findOne(id);
        if (obj.getType() == InstructionStatus.SampleSentType.PSS) {
            ShippingInstruction si = obj.getShippingInstructionBySiId();
            CompanyMaster cm = si.getCompanyMasterByClientId();
            int client_id = cm.getId();
            if (client_id != getCompanyId(httpSession)) {
                System.out.println("dangerous: " + client_id + " # " + getCompanyId(httpSession));
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
            mapper.registerModule(module);
            return new ResponseEntity<String>(mapper.writeValueAsString(obj), HttpStatus.OK);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(SampleSent.class, new SampleTypeCardViewSerializer());
            mapper.registerModule(module);
            return new ResponseEntity<String>(mapper.writeValueAsString(obj), HttpStatus.OK);
        }
    }
}
