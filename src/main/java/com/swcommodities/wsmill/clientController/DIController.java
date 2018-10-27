package com.swcommodities.wsmill.clientController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.cache.DICache;
import com.swcommodities.wsmill.hibernate.dto.query.result.DeliveryInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.dto.serializer.DeliveryCardViewInstructionSerializer;
import com.swcommodities.wsmill.repository.DICacheRepository;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.service.DeliveryPagingService;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.utils.Constants;

@org.springframework.stereotype.Controller("clientController_dIController")
@RequestMapping("/millclient/dis")
public class DIController extends BaseOwnerInstructionController<DeliveryInstruction, Integer> {
	
	@Autowired private DICacheRepository dICacheRepository;
	@Autowired private DeliveryPagingService deliveryPagingService;
	@Autowired MyAsyncService myAsyncService;
	@Autowired DeliveryInsService deliveryInsService;
	
	@Autowired
	public DIController(DeliveryInstructionRepository repo) {
		super(repo);
	}
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("d-MMM-y"), true));   
	}

	public String getJsonStringCardView(DeliveryInstruction deliveryInstruction) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(DeliveryInstruction.class, new DeliveryCardViewInstructionSerializer());
		mapper.registerModule(module);
		return mapper.writeValueAsString(deliveryInstruction);
	}
	
	@RequestMapping(value = "/list.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<DICache>> getListView(HttpSession httpSession)
			throws JsonProcessingException {		
		int company_id = getCompanyId(httpSession);
        printAllSession(httpSession);
        
		List<Integer> ids = ((DeliveryInstructionRepository) this.repo).getPendingIds(company_id);
		List<DICache> diCaches = dICacheRepository.findByDeliveryInstructionIdIn(ids);
		if(diCaches == null) {
			diCaches = new ArrayList<>();
		}
		return new ResponseEntity<>(diCaches, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<DeliveryInstructionPagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer supplierId, @RequestParam(required = false) Integer gradeId, @RequestParam(required = false) Byte status, @RequestParam(required = false) String txtSearch, @RequestParam(required = false, name = "startDate") Date startDate, @RequestParam(required = false, name = "endDate") Date endDate)
			throws JsonProcessingException {		
		int clientId = getCompanyId(httpSession);
		DeliveryInstructionPagingResult result = deliveryPagingService.getAggregateInfo(clientId, supplierId, gradeId, txtSearch, status, startDate, endDate, pageNo, perPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tesasync.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getAsync(HttpSession httpSession)
			throws JsonProcessingException, InterruptedException {		
		myAsyncService.findUser("abc");
		return new ResponseEntity<>("abc", HttpStatus.OK);
	}
    
    private void printAllSession(HttpSession httpSession) {
        Enumeration keys = httpSession.getAttributeNames();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            System.out.println(key + ": " + httpSession.getAttribute(key) + "<br>");
        }

    }
	
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getSingle(@PathVariable Integer id, HttpSession httpSession)
			throws JsonProcessingException {
		System.out.println("ResponseEntitySingleGet");
        printAllSession(httpSession);
        
		DeliveryInstruction obj = this.repo.findOne(id);
		int client_id = obj.getCompanyMasterByClientId().getId();
		if (client_id != getCompanyId(httpSession)) {
			System.out.println("dangerous: " + client_id + " # " + getCompanyId(httpSession));
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} 
		System.out.println(getCompanyId(httpSession));
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(DeliveryInstruction.class, new DeliveryCardViewInstructionSerializer());
		mapper.registerModule(module);
		return new ResponseEntity<String>(mapper.writeValueAsString(obj), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}.json", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> update(HttpSession session,@PathVariable Integer id, @RequestBody DeliveryInstruction json)
			throws JsonProcessingException, InterruptedException {
		System.out.println("ResponseEntitySinglePut " + id + ". id from obj is: " + json.getCompanyMasterByClientId());
        printAllSession(session);
		System.out.println("put: " + json.getFirstDate());
		DeliveryInstruction entity = this.repo.findOne(id);
		try {
			BeanUtils.copyProperties(json, entity);
		} catch (Exception e) {
			System.out.println("while copying properties" + e);
			throw Throwables.propagate(e);
		}
		System.out.println("putorigin: " + entity.getFirstDate());

		//completion saving method
		entity.setUser((User) session.getAttribute("user"));
		entity.setUserUpdateDate(new Date());
		/////////////////////////

		DeliveryInstruction updated = this.repo.save(entity);
		myAsyncService.saveCacheDI(updated);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(DeliveryInstruction.class, new DeliveryCardViewInstructionSerializer());
		mapper.registerModule(module);
		return new ResponseEntity<String>(mapper.writeValueAsString(updated), HttpStatus.OK);
	}

	@RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody DeliveryInstruction json, HttpSession httpSession) throws JsonProcessingException, InterruptedException {
		System.out.println("ResponseEntityPOST all.json");
        printAllSession(httpSession);
        
		int company_id = getCompanyId(httpSession);
		System.out.println("company id in session is: " + company_id);
		if (company_id == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setId(getCompanyId(httpSession));
		json.setCompanyMasterByClientId(companyMaster);

		//Completion adding method
		json.setRefNumber(deliveryInsService.getNewContractRef());
		json.setDate(new Date());
		json.setUserByRequestUserId((User) httpSession.getAttribute("user"));
		json.setRequestDate(new Date());
		json.setRequestStatus(Constants.PENDING);
		json.setUserByUpdateUserId((User) httpSession.getAttribute("user"));
		json.setUpdateDate(new Date());
		json.setStatus(Constants.PENDING);
		json.setUserUpdateDate(new Date());
		json.setUser((User) httpSession.getAttribute("user"));
		//////////////////////////

		DeliveryInstruction created = this.repo.save(json);
		myAsyncService.saveCacheDI(created);
		return new ResponseEntity<String>(getJsonStringCardView(created), HttpStatus.CREATED);
	}

}
