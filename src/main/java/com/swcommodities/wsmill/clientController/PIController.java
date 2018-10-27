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
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.cache.PICache;
import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.dto.serializer.ProcessingCardViewInstructionSerializer;
import com.swcommodities.wsmill.repository.PICacheRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.service.MyAsyncService;
import com.swcommodities.wsmill.service.ProcessingPagingService;

@org.springframework.stereotype.Controller("clientController_pIController")
@RequestMapping("/millclient/pis")
public class PIController extends BaseOwnerInstructionController<ProcessingInstruction, Integer> {
	@Autowired
	private PICacheRepository piCacheRepository;

	@Autowired
	private ProcessingPagingService processingPagingService;

	@Autowired
	ProcessingService processingService;
	
	@Autowired private MyAsyncService myAsyncService;

	@Autowired
	public PIController(ProcessingInstructionRepository repo) {
		super(repo);
	}

	public String getJsonStringCardView(ProcessingInstruction processingInstruction) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(ProcessingInstruction.class, new ProcessingCardViewInstructionSerializer());
		mapper.registerModule(module);
		return mapper.writeValueAsString(processingInstruction);
	}

	public String getJsonStringListView(List<ProcessingInstructionListViewResult> processingInstructions)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(ProcessingInstruction.class, new ProcessingCardViewInstructionSerializer());
		mapper.registerModule(module);
		return mapper.writeValueAsString(processingInstructions);
	}

	@RequestMapping(value = "/list.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<PICache>> getListView(HttpSession httpSession)
			throws JsonProcessingException {
		int company_id = getCompanyId(httpSession);
		List<Integer> ids = ((ProcessingInstructionRepository) this.repo).getPendingIds(company_id);
		List<PICache> piCaches = piCacheRepository.findByProcessingInstructionIdIn(ids);
		if (piCaches == null) {
			piCaches = new ArrayList<>();
		}
		return new ResponseEntity<>(piCaches, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ProcessingInstructionPagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer gradeId, @RequestParam(required = false) Byte status, @RequestParam(required = false) String txtSearch, @RequestParam(required = false, name = "startDate") Date startDate, @RequestParam(required = false, name = "endDate") Date endDate)
			throws JsonProcessingException {
		int clientId = getCompanyId(httpSession);
		ProcessingInstructionPagingResult result = processingPagingService.getAggregateInfo(clientId, gradeId, txtSearch, status, startDate, endDate, pageNo, perPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getSingle(@PathVariable Integer id, HttpSession httpSession)
			throws JsonProcessingException {
		System.out.println("ResponseEntitySingleGet");
		ProcessingInstruction obj = this.repo.findOne(id);
		int client_id = obj.getCompanyMasterByClientId().getId();
		if (client_id != getCompanyId(httpSession)) {
			System.out.println("dangerous: " + client_id + " # " + getCompanyId(httpSession));

			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(ProcessingInstruction.class, new ProcessingCardViewInstructionSerializer());
		mapper.registerModule(module);
		return new ResponseEntity<String>(mapper.writeValueAsString(obj), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}.json", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> update(@PathVariable Integer id,
			@RequestBody ProcessingInstruction json, HttpSession httpSession) throws JsonProcessingException {
		System.out.println("ResponseEntitySinglePut " + id + ". id from obj is: " + json.getCompanyMasterByClientId());

		ProcessingInstruction entity = this.repo.findOne(id);
		try {
			BeanUtils.copyProperties(json, entity);
		} catch (Exception e) {
			System.out.println("while copying properties" + e);
			throw Throwables.propagate(e);
		}

		//Complete saving method
		entity.setUser((User) httpSession.getAttribute("user"));
		entity.setUpdatedDate(new Date());
		//////////

		ProcessingInstruction updated = this.repo.save(entity);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(ProcessingInstruction.class, new ProcessingCardViewInstructionSerializer());
		mapper.registerModule(module);
		try {
			myAsyncService.saveCachePI(updated);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("dangerous");
			e.printStackTrace();
		}
		return new ResponseEntity<String>(mapper.writeValueAsString(updated), HttpStatus.OK);
	}

	@RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody ProcessingInstruction json, HttpSession httpSession)
			throws JsonProcessingException {
		System.out.println("ResponseEntityPOST");
		Integer company_id = getCompanyId(httpSession);
		System.out.println("session is" + company_id);
		if (company_id == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Integer user_id = getUserId(httpSession);
		User user = new User(user_id);
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setId(getCompanyId(httpSession));
		json.setCompanyMasterByClientId(companyMaster);

		json.setRefNumber(processingService.getNewContractRef());
		json.setCreatedDate(new Date());
		json.setUserByUpdateCompletionUserId(user);
		json.setCompletionStatusDate(new Date());
		json.setStatus(Byte.valueOf("0"));
		json.setUserByUpdateRequestUserId(user);
		json.setRequestStatusDate(new Date());
		json.setRequestStatus(Byte.valueOf("0"));

		json.setUpdatedDate(new Date());
		json.setUser(user);


		ProcessingInstruction created = this.repo.save(json);
		try {
			myAsyncService.saveCachePI(created);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(getJsonStringCardView(created), HttpStatus.CREATED);
	}

}
