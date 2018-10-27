package com.swcommodities.wsmill.clientController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.domain.service.ShippingInstructionDomainService;
import com.swcommodities.wsmill.formController.form.CustomCostForm;
import com.swcommodities.wsmill.formController.form.NewSampleSentForm;
import com.swcommodities.wsmill.formController.form.ShippingInstructionForm;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.cache.SICache;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ShippingInstructionFormAssembler;
import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.dto.serializer.SampleSentCardViewInstructionSerializer;
import com.swcommodities.wsmill.hibernate.dto.serializer.ShippingCardViewInstructionSerializer;
import com.swcommodities.wsmill.repository.SICacheRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.service.ShippingPagingService;

@org.springframework.stereotype.Controller("clientController_sIController")
@RequestMapping("/millclient/sis")
@Transactional
public class SIController extends BaseOwnerInstructionController<ShippingInstruction, Integer> {

    @Autowired
    SICacheRepository sICacheRepository;

    @Autowired
    ShippingPagingService shippingPagingService;

    @Autowired
    ShippingService shippingService;

    @Autowired
    CostCalculator costCalculator;
    @Autowired
    ShippingInstructionService shippingInstructionService;
    @Autowired
    ShippingInstructionDomainService shippingInstructionDomainService;

    @Autowired
    public SIController(ShippingInstructionRepository repo) {
        super(repo);
    }

    public String getJsonStringCardView(ShippingInstruction shippingInstruction) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ShippingInstruction.class, new ShippingCardViewInstructionSerializer());
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(shippingInstruction);
    }

    public String getJsonStringListView(List<ShippingInstructionListViewResult> shippingInstructions) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ShippingInstruction.class, new ShippingCardViewInstructionSerializer());
        module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(shippingInstructions);

    }

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<SICache>> getListView(HttpSession httpSession)
            throws JsonProcessingException {
        int company_id = getCompanyId(httpSession);
        List<Integer> ids = ((ShippingInstructionRepository) this.repo).getPendingIds(company_id);
        List<SICache> siCaches = sICacheRepository.findByShippingInstructionIdIn(ids);
        if (siCaches == null) {
            siCaches = new ArrayList<>();
        }
        return new ResponseEntity<>(siCaches, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ShippingInstructionPagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer buyerId, @RequestParam(required = false) Integer gradeId, @RequestParam(required = false) Byte status, @RequestParam(required = false) String txtSearch, @RequestParam(required = false, name = "startDate") Date startDate, @RequestParam(required = false, name = "endDate") Date endDate)
            throws JsonProcessingException {
        int clientId = getCompanyId(httpSession);
        ShippingInstructionPagingResult result = shippingPagingService.getAggregateInfoForClientSite(clientId, buyerId, gradeId, txtSearch, status, startDate, endDate, pageNo, perPage);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> getSingle(@PathVariable Integer id, HttpSession httpSession)
            throws JsonProcessingException {
        ShippingInstruction obj = this.repo.findOne(id);
        List<SICustomCost> sICustomCosts = shippingInstructionDomainService.getCustomCostsForSIWithVNDInfo(obj);
        obj.getShippingCost().setsICustomCosts(sICustomCosts);
        int client_id = obj.getCompanyMasterByClientId().getId();
        if (client_id != getCompanyId(httpSession)) {
            System.out.println("dangerous: " + client_id + " # " + getCompanyId(httpSession));
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        ObjectMapper mapper = new ObjectMapper();
        ShippingInstructionFormAssembler assembler = new ShippingInstructionFormAssembler();
        return new ResponseEntity<>(mapper.writeValueAsString(assembler.toForm(obj)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}.json", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<String> update(HttpSession httpSession, @PathVariable Integer id, @RequestBody ShippingInstructionForm shippingInstructionForm)
            throws JsonProcessingException, Exception {
        Integer company_id = getCompanyId(httpSession);
        if (company_id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        shippingInstructionService.updateOrSaveShippingInstructionForClient(shippingInstructionForm, (User) httpSession.getAttribute("user"), company_id);
        return new ResponseEntity<String>(getIdJson(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<String> create(@RequestBody ShippingInstructionForm json, HttpSession httpSession) throws JsonProcessingException, Exception {
        Integer company_id = getCompanyId(httpSession);
        if (company_id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int id = shippingInstructionService.updateOrSaveShippingInstructionForClient(json, (User) httpSession.getAttribute("user"), company_id);
        return new ResponseEntity<String>(getIdJson(id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/ss.json", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<String> createFromCLientForm(@RequestBody NewSampleSentForm json, HttpSession httpSession) throws JsonProcessingException, Exception {
        Integer company_id = getCompanyId(httpSession);
        if (company_id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int id = shippingInstructionService.newSampleSent(json, (User) httpSession.getAttribute("user"));
        return new ResponseEntity<String>(getIdJson(id), HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/customcost.json", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<String> addCustomCost(@RequestBody CustomCostForm cost, HttpSession httpSession) throws JsonProcessingException, Exception {
        Integer company_id = getCompanyId(httpSession);
        if (company_id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        shippingInstructionService.addCustomCost(cost.getSiId(), cost);
        return new ResponseEntity<>(getIdJson(cost.getSiId()), HttpStatus.CREATED);
    }

}
