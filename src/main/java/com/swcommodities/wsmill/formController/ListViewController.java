package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.SIClaimService;
import com.swcommodities.wsmill.formController.form.ClaimSearchForm;
import com.swcommodities.wsmill.formController.form.SISearchForm;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.swcommodities.wsmill.hibernate.dto.*;
import com.swcommodities.wsmill.hibernate.dto.query.result.ClaimPagingResult;
import com.swcommodities.wsmill.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swcommodities.wsmill.hibernate.dto.query.result.DeliveryInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.SampleSentPagingResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionPagingResult;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import com.swcommodities.wsmill.service.DeliveryPagingService;
import com.swcommodities.wsmill.service.ProcessingPagingService;
import com.swcommodities.wsmill.service.SampleSentPagingService;
import com.swcommodities.wsmill.service.ShippingPagingService;
import java.util.Properties;
import javax.annotation.Resource;

/**
 * Created by dunguyen on 7/18/16.
 */

@org.springframework.stereotype.Controller
public class ListViewController extends BaseInstructionController {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ShippingLineCompanyMasterRepository shippingLineCompanyMasterRepository;

	@Autowired
	private PortRepository portRepository;

	@Autowired
	private LocationMasterRepository locationMasterRepository;

	@Autowired
	private CourierMasterRepository courierMasterRepository;

	@Autowired
	private WarehouseMasterRepository warehouseMasterRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private PackingRepository packingRepository;

	@Autowired
	private OriginRepository originRepository;

	@Autowired
	private CompanyTypeMasterRepository companyTypeMasterRepository;

	@Autowired
	private DeliveryPagingService deliveryPagingService;

	@Autowired
	private ProcessingPagingService processingPagingService;

	@Autowired
	private ShippingPagingService shippingPagingService;

	@Autowired
	private SampleSentPagingService sampleSentPagingService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private LuCafeHistoryRepository luCafeHistoryRepository;


	@Autowired
	private FinanceContractRepository financeContractRepository;
    @Autowired CompanyService companyService;
    @Autowired SIClaimService sIClaimService;
    
    @Resource(name = "configConfigurer") Properties configConfigurer;

	@RequestMapping(method = RequestMethod.GET, value = "delivery-v2-list.htm")
	public String getDeliveryList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer clientId,
			@RequestParam(required = false) Integer supplierId, @RequestParam(required = false) Integer gradeId,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) String txtSearch,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate) {
		System.out.println("date : " + startDate + "." + endDate);
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);

		if (status == null) {
			status = 0;
		}
		if (clientId == null) {
			clientId = -1;
		}
		if (supplierId == null) {
			supplierId = -1;
		}
		if (gradeId == null) {
			gradeId = -1;
		}
		if (txtSearch == null) {
			txtSearch = "";
		}

		DeliveryInstructionPagingResult result = deliveryPagingService.getAggregateInfo(clientId, supplierId, gradeId,
				txtSearch, status.byteValue(), startDate, endDate, pageNo, perPage);

		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("suppliers", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Supplier").getId()));
		model.put("grades", gradeRepository.findAllByOrderByNameAsc());
		model.put("txtSearch", txtSearch);
		model.put("deliveryInstructions", result.getResults());
		model.put("clientId", clientId);
		model.put("supplierId", supplierId);
		model.put("gradeId", gradeId);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("statuses", Status.getAllStatuses());
		model.put("status", status);
		model.put("startDate", startDate);
		model.put("endDate", endDate);

		model.put("aggregate", result.getInstructionResult());
		model.put("pagination", result.getPagination());

		return "delivery-v2-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "processing-v2-list.htm")
	public String getProcessingList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer clientId,
			@RequestParam(required = false) Integer gradeId, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String txtSearch,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate) {
		System.out.println("date : " + startDate + "." + endDate);
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);

		if (status == null) {
			status = 0;
		}
		if (clientId == null) {
			clientId = -1;
		}
		if (gradeId == null) {
			gradeId = -1;
		}
		if (txtSearch == null) {
			txtSearch = "";
		}

		ProcessingInstructionPagingResult result = processingPagingService.getAggregateInfo(clientId, gradeId,
				txtSearch, status.byteValue(), startDate, endDate, pageNo, perPage);

		model.put("txtSearch", txtSearch);
		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("grades", gradeRepository.findAllByOrderByNameAsc());
		model.put("processingInstructions", result.getResults());
		model.put("clientId", clientId);
		model.put("gradeId", gradeId);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("statuses", Status.getAllStatuses());
		model.put("status", status);
		model.put("startDate", startDate);
		model.put("endDate", endDate);

		model.put("aggregate", result.getInstructionResult());
		model.put("pagination", result.getPagination());

		return "processing-v2-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "shipping-v2-list.htm")
	public String getShippingList(Map<String, Object> model, SISearchForm sISearchForm) {

		ShippingInstructionPagingResult result = shippingPagingService.getAggregateInfo(sISearchForm);
		model.put("clients", companyService.findAllClientsWithEmpty());
        model.put("buyers", companyService.findAllBuyersWithEmpty());
		model.put("grades", gradeRepository.findAllByOrderByNameAscWithEmpty());
		model.put("shippingInstructions", result.getResults());
		model.put("perPages", getPerPages());
		model.put("statuses", Status.getAllStatuses());
		model.put("aggregate", result.getInstructionResult());
		model.put("pagination", result.getPagination());
        
        model.put("searchForm", sISearchForm);

		return "si/shipping-v2-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "sample-sent-v2-list.htm")
	public String getSampleSentList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer ssType,
			@RequestParam(required = false) Integer clientId, @RequestParam(required = false) Integer buyerId,
			@RequestParam(required = false) Integer sentStatus, @RequestParam(required = false) Integer approvalStatus,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);

		if (sentStatus == null) {
			sentStatus = -1;
		}

		if (approvalStatus == null) {
			approvalStatus = 0;
		}

		if (clientId == null) {
			clientId = -1;
		}

		if (buyerId == null) {
			buyerId = -1;
		}

		if (ssType == null) {
			ssType = -1;
		}

		SampleSentPagingResult result = sampleSentPagingService.getAggregateInfo(ssType, clientId, buyerId, "",
				sentStatus.byteValue(), approvalStatus.byteValue(), startDate, endDate, pageNo, perPage);

		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("buyers", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Buyer").getId()));
		model.put("ssType", ssType);
		model.put("ssTypes", Status.getAllSampleSentTypes());
		model.put("sampleSents", result.getResults());
		model.put("clientId", clientId);
		model.put("buyerId", buyerId);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("sentStatuses", Status.getAllSendingStatuses());
		model.put("sentStatus", sentStatus);
		model.put("approvalStatuses", Status.getAllApprovalStatuses());
		model.put("approvalStatus", approvalStatus);
		model.put("startDate", startDate);
		model.put("endDate", endDate);

		model.put("pagination", result.getPagination());

		return "sample-sent-v2-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "transaction-list.htm")
	public String getTransactionList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer clientId,
			@RequestParam(required = false) Integer approvalStatus, @RequestParam(required = false) Integer transType,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate,
			@RequestParam(required = false) String txtSearch) throws Exception {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "refNumber", Sort.Direction.DESC);
		List<SearchCriteria> criterias = new ArrayList<>();

		if (clientId == null) {
			clientId = -1;
		}
		if (clientId != -1) {
			criterias.add(new SearchCriteria("client", "=", clientId, "1"));
		}

		if (approvalStatus == null) {
			approvalStatus = 0;
		}
		if (approvalStatus != -1) {
			criterias.add(new SearchCriteria("approvalStatus", "=", approvalStatus, "2"));
		}

		if (transType == null) {
			transType = -1;
		}
		if (transType != -1) {
			criterias.add(new SearchCriteria("type", "=", transType, "3"));
		}

		if (startDate != null) {
			criterias.add(new SearchCriteria("created", ">", startDate, "4"));
		}

		if (endDate != null) {
			criterias.add(new SearchCriteria("created", "<", endDate, "5"));
		}

		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("refNumber", "like", "%" + txtSearch + "%", "6"));

		Specifications<Transaction> specifications = getSpecificationsByCriteriasTransaction(criterias);
		Page<Transaction> page;
		List<Transaction> transactions;
		if (specifications == null) {
			page = transactionRepository.findAll(pageableCriteria);
			transactions = page.getContent();
		} else {
			page = transactionRepository.findAll(specifications, pageableCriteria);
			transactions = page.getContent();
		}

		model.put("txtSearch", txtSearch);
		model.put("transactions", transactions);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		model.put("approvalStatuses", Status.getAllApprovalStatuses());
		model.put("approvalStatusesInList", InstructionStatus.getApprovalStatusesInteger());
		model.put("invoicedStatusesInList", InstructionStatus.getInvoicedStatus());
		model.put("approvalStatus", approvalStatus);
		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("clientId", clientId);
		model.put("transTypes", Status.getAllTransTypes());
		model.put("transType", transType);
		model.put("startDate", startDate);
		model.put("endDate", endDate);

		return "transaction-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "invoice-list.htm")
	public String getTransactionList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer clientId,
			@RequestParam(required = false) Integer completionStatus,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate,
			@RequestParam(required = false) String txtSearch) throws Exception {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "refNumber", Sort.Direction.DESC);
		List<SearchCriteria> criterias = new ArrayList<>();

		if (clientId == null) {
			clientId = -1;
		}
		if (clientId != -1) {
			criterias.add(new SearchCriteria("client.id", "=", clientId, "1"));
		}

		if (completionStatus == null) {
			completionStatus = 0;
		}
		if (completionStatus != -1) {
			criterias.add(new SearchCriteria("completionStatus", "=", completionStatus, "2"));
		}

		if (startDate != null) {
			criterias.add(new SearchCriteria("created", ">", startDate, "4"));
		}

		if (endDate != null) {
			criterias.add(new SearchCriteria("created", "<", endDate, "5"));
		}

		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("refNumber", "like", "%" + txtSearch + "%", "6"));

		Specifications<Invoice> specifications = getSpecificationsByCriteriasInvoice(criterias);
		Page<Invoice> page;
		List<Invoice> invoices;
		if (specifications == null) {
			page = invoiceRepository.findAll(pageableCriteria);
			invoices = page.getContent();
		} else {
			page = invoiceRepository.findAll(specifications, pageableCriteria);
			invoices = page.getContent();
		}

		model.put("txtSearch", txtSearch);
		model.put("invoices", invoices);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		model.put("completionStatuses", InstructionStatus.getInvoiceCompletionStatuses());
		model.put("completionStatus", completionStatus);
		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("clientId", clientId);
		model.put("startDate", startDate);
		model.put("endDate", endDate);
		model.put("aggregateInfo", invoiceRepository.findAggregateInfo(criterias));

		return "invoice-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "lu-daily-list.htm")
	public String getLuDailyList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
									 @RequestParam(required = false) Integer perPage,
									 @RequestParam(required = false, name = "startDate") Date startDate,
									 @RequestParam(required = false, name = "endDate") Date endDate,
									 @RequestParam(required = false) String txtSearch) throws Exception {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "refNumber", Sort.Direction.DESC);
		List<SearchCriteria> criterias = new ArrayList<>();

		if (startDate != null) {
			criterias.add(new SearchCriteria("created", ">", startDate, "4"));
		}

		if (endDate != null) {
			criterias.add(new SearchCriteria("created", "<", endDate, "5"));
		}

		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("refNumber", "like", "%" + txtSearch + "%", "6"));

		Specifications<LuCafeHistory> specifications = getSpecificationsByCriteriasLuCafeHistory(criterias);
		Page<LuCafeHistory> page;
		List<LuCafeHistory> lus;
		if (specifications == null) {
			page = luCafeHistoryRepository.findAll(pageableCriteria);
			lus = page.getContent();
		} else {
			page = luCafeHistoryRepository.findAll(specifications, pageableCriteria);
			lus = page.getContent();
		}

		model.put("txtSearch", txtSearch);
		model.put("lus", lus);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		model.put("approvalStatuses", InstructionStatus.getApprovalStatusesInteger());
		model.put("startDate", startDate);
		model.put("endDate", endDate);
		model.put("aggregateInfo", luCafeHistoryRepository.findAggregateInfo(criterias));

		return "lu/lu-daily-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "finance-contract-list.htm")
	public String getFinanceContractList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer clientId,
			@RequestParam(required = false) Integer approvalStatus,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate,
			@RequestParam(required = false) String txtSearch) throws Exception {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "refNumber", Sort.Direction.DESC);
		List<SearchCriteria> criterias = new ArrayList<>();

		if (clientId == null) {
			clientId = -1;
		}
		if (clientId != -1) {
			criterias.add(new SearchCriteria("client", "=", clientId, "1"));
		}

		if (approvalStatus == null) {
			approvalStatus = 0;
		}
		if (approvalStatus != -1) {
			criterias.add(new SearchCriteria("approvalStatus", "=", approvalStatus, "2"));
		}

		if (startDate != null) {
			criterias.add(new SearchCriteria("created", ">", startDate, "4"));
		}

		if (endDate != null) {
			criterias.add(new SearchCriteria("created", "<", endDate, "5"));
		}

		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("refNumber", "like", "%" + txtSearch + "%", "6"));

		Specifications<FinanceContract> specifications = getSpecificationsByCriteriasFinanceContract(criterias);
		Page<FinanceContract> page;
		List<FinanceContract> financeContracts;
		if (specifications == null) {
			page = financeContractRepository.findAll(pageableCriteria);
			financeContracts = page.getContent();
		} else {
			page = financeContractRepository.findAll(specifications, pageableCriteria);
			financeContracts = page.getContent();
		}

		model.put("txtSearch", txtSearch);
		model.put("financeContracts", financeContracts);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		model.put("approvalStatuses", Status.getAllApprovalStatuses());
		model.put("approvalStatusesInList", InstructionStatus.getApprovalStatusesInteger());
		model.put("approvalStatus", approvalStatus);
		model.put("clients", companyRepository
				.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
		model.put("clientId", clientId);
		model.put("startDate", startDate);
		model.put("endDate", endDate);

		return "finance-contract-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "claim-list.htm")
	public String getClaimList(Map<String, Object> model, ClaimSearchForm claimSearchForm) {

		ClaimPagingResult claimPagingResult = sIClaimService.getPagingResult(claimSearchForm);
		model.put("clients", companyService.findAllClientsWithEmpty());
		model.put("claims", claimPagingResult.getResults());
		model.put("perPages", getPerPages());
		model.put("statuses", Status.getAllApprovalStatuses());
		model.put("pagination", claimPagingResult.getPagination());

		model.put("searchForm", claimSearchForm);

		return "si/claim-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "company-v2-list.htm")
	public String getCompanyList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer companyType,
			@RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		if (companyType == null) {
			companyType = -1;
		}

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		if (!"".equals(txtSearch)) {
			criterias.add(new SearchCriteria("name", "like", "%" + txtSearch + "%", "1"));
		}

		Specifications<CompanyMaster> specifications = getSpecificationsByCriteriasCompanyMaster(criterias);
		final int companyTypeFinal = companyType;
		if (companyType != -1) {
			Specification<CompanyMaster> additional = new Specification<CompanyMaster>() {
				@Override
				public Predicate toPredicate(Root<CompanyMaster> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
					List<Integer> list = new ArrayList<>();
					list.add(companyTypeFinal);
					Join join = root.join("companyTypes");
					return join.get("id").in(list);
				}
			};

			if (specifications == null) {
				specifications = Specifications.where(additional);
			} else {
				specifications = specifications.and(additional);
			}

		}

		Page<CompanyMaster> page;
		List<CompanyMaster> companies;
		List<CompanyMaster> companiesForShow = new ArrayList<>();
		if (specifications == null) {
			page = companyRepository.findAll(pageableCriteria);
			companies = page.getContent();
		} else {
			page = companyRepository.findAll(specifications, pageableCriteria);
			companies = page.getContent();
		}
		model.put("txtSearch", txtSearch);
		model.put("companies", companies);
		model.put("companyType", companyType);
		model.put("companyTypes", companyTypeMasterRepository.findAll());
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "company-v2-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "shipping-line-list.htm")
	public String getShippingLineList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("fullName", "like", "%" + txtSearch + "%", "1"));

		Specifications<ShippingLineCompanyMaster> specifications = getSpecificationsByCriteriasShippingLineCompanyMaster(
				criterias);
		Page<ShippingLineCompanyMaster> page;
		Iterator<ShippingLineCompanyMaster> shippingLines;
		if (specifications == null) {
			page = shippingLineCompanyMasterRepository.findAll(pageableCriteria);
			shippingLines = page.iterator();
		} else {
			page = shippingLineCompanyMasterRepository.findAll(specifications, pageableCriteria);
			shippingLines = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("shippingLines", shippingLines);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "shipping-line-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "port-master-list.htm")
	public String getPortMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("name", "like", "%" + txtSearch + "%", "1"));

		Specifications<PortMaster> specifications = getSpecificationsByCriteriasPortMaster(criterias);
		Page<PortMaster> page;
		Iterator<PortMaster> ports;
		if (specifications == null) {
			page = portRepository.findAll(pageableCriteria);
			ports = page.iterator();
		} else {
			page = portRepository.findAll(specifications, pageableCriteria);
			ports = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("ports", ports);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "port-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "origin-master-list.htm")
	public String getOriginMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "country.shortName", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}

		Specifications<OriginMaster> specifications = getSpecificationsByCriteriasOriginMaster(criterias);
		Page<OriginMaster> page;
		Iterator<OriginMaster> origins;
		if (specifications == null) {
			page = originRepository.findAll(pageableCriteria);
			origins = page.iterator();
		} else {
			page = originRepository.findAll(specifications, pageableCriteria);
			origins = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("origins", origins);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "origin-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "warehouse-master-list.htm")
	public String getWarehouseMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("fullName", "like", "%" + txtSearch + "%", "1"));

		Specifications<WarehouseMaster> specifications = getSpecificationsByCriteriasWarehouseMaster(criterias);
		Page<WarehouseMaster> page;
		Iterator<WarehouseMaster> warehouses;
		if (specifications == null) {
			page = warehouseMasterRepository.findAll(pageableCriteria);
			warehouses = page.iterator();
		} else {
			page = warehouseMasterRepository.findAll(specifications, pageableCriteria);
			warehouses = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("warehouses", warehouses);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "warehouse-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "location-master-list.htm")
	public String getLocationMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("name", "like", "%" + txtSearch + "%", "1"));

		Specifications<LocationMaster> specifications = getSpecificationsByCriteriasLocationMaster(criterias);
		Page<LocationMaster> page;
		Iterator<LocationMaster> locations;
		if (specifications == null) {
			page = locationMasterRepository.findAll(pageableCriteria);
			locations = page.iterator();
		} else {
			page = locationMasterRepository.findAll(specifications, pageableCriteria);
			locations = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("locations", locations);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "location-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "courier-master-list.htm")
	public String getCourierMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("fullName", "like", "%" + txtSearch + "%", "1"));

		Specifications<CourierMaster> specifications = getSpecificationsByCriteriasCourierMaster(criterias);
		Page<CourierMaster> page;
		Iterator<CourierMaster> couriers;
		if (specifications == null) {
			page = courierMasterRepository.findAll(pageableCriteria);
			couriers = page.iterator();
		} else {
			page = courierMasterRepository.findAll(specifications, pageableCriteria);
			couriers = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("couriers", couriers);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "courier-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "grade-master-list.htm")
	public String getGradeMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("name", "like", "%" + txtSearch + "%", "1"));

		Specifications<GradeMaster> specifications = getSpecificationsByCriteriasGradeMaster(criterias);
		Page<GradeMaster> page;
		Iterator<GradeMaster> grades;
		if (specifications == null) {
			page = gradeRepository.findAll(pageableCriteria);
			grades = page.iterator();
		} else {
			page = gradeRepository.findAll(specifications, pageableCriteria);
			grades = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("grades", grades);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "grade-master-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "packing-master-list.htm")
	public String getPackingMasterList(Map<String, Object> model, @RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer perPage, @RequestParam(required = false) String txtSearch) {
		pageNo = getPageNo(pageNo);
		perPage = getPerPage(perPage);
		Pageable pageableCriteria = getPageableCriteria(pageNo, perPage, "name", Sort.Direction.ASC);

		List<SearchCriteria> criterias = new ArrayList<>();
		if (txtSearch == null) {
			txtSearch = "";
		}
		criterias.add(new SearchCriteria("name", "like", "%" + txtSearch + "%", "1"));

		Specifications<PackingMaster> specifications = getSpecificationsByCriteriasPackingMaster(criterias);
		Page<PackingMaster> page;
		Iterator<PackingMaster> packings;
		if (specifications == null) {
			page = packingRepository.findAll(pageableCriteria);
			packings = page.iterator();
		} else {
			page = packingRepository.findAll(specifications, pageableCriteria);
			packings = page.iterator();
		}

		model.put("txtSearch", txtSearch);
		model.put("packings", packings);
		model.put("perPage", perPage);
		model.put("perPages", getPerPages());
		model.put("totalPages", page.getTotalPages());
		model.put("currentPage", page.getNumber());
		model.put("testhtml", getPagingHtml(page.getTotalPages(), pageNo));

		return "packing-master-list";
	}

    @Transactional(readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "home-page.htm")
	public String getAllListView(Map<String, Object> model) {
        model.put("refresh_disabled", configConfigurer.getProperty("refresh_disabled", "0"));
        
		DeliveryInstructionPagingResult diResult = deliveryPagingService.getAggregateInfo(-1, -1, -1, "", (byte) 0,
				null, null, 0, 1000);
		model.put("deliveryInstructions", diResult.getResults());

		ProcessingInstructionPagingResult piResult = processingPagingService.getAggregateInfo(-1, -1, "", (byte) 0,
				null, null, 0, 1000);
		model.put("processingInstructions", piResult.getResults());

		ShippingInstructionPagingResult siResult = shippingPagingService.getAggregateInfo(-1, -1, -1, "", (byte) -1,
				(byte) 0, null, null, 0, 1000);
		model.put("shippingInstructions", siResult.getResults());

		SampleSentPagingResult ssResult = sampleSentPagingService.getAggregateInfo(-1, -1, -1, "", (byte) -1, (byte) 0, null, null, 0, 1000);
        //model.put("sampleSents", ssResult.getResults());
		model.put("sampleSents", sampleSentPagingService.getPendingSampleSentInHome());

		model.put("piAggregate", piResult.getInstructionResult());
		model.put("diAggregate", diResult.getInstructionResult());
		model.put("siAggregate", siResult.getInstructionResult());
		return "home-page";
	}

}
