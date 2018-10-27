package com.swcommodities.wsmill.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Iterables;
import com.swcommodities.wsmill.bo.CostService;
import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CompanyOption;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.ExchangeHistory;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.serializer.CompanyCostSerializer;
import com.swcommodities.wsmill.hibernate.dto.serializer.CostCalculatorSerializer;
import com.swcommodities.wsmill.hibernate.dto.view.CompanyJsonView;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CompanyOptionRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.ExchangeHistoryRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.OptionRepository;

@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class PriceListController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;
    
    @Autowired
    private OptionRepository optionRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private CompanyOptionRepository companyOptionRepository;
    
    @Autowired
    private CostRepository costRepository;
    
    @Autowired
    private CostCalculator costCalculator;

    @Autowired
    private CostService costService;

    
    @RequestMapping(value = "renderCostJson.json")
    ResponseEntity<Iterable<Category>> get() {
        Iterable<Category> categories = categoryRepository.findByParentIsNull(new Sort(Sort.Direction.ASC, "id"));
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "getCurExchange.json")
    ResponseEntity<Exchange> getExchange() {
        ExchangeHistory exchangeHistory = exchangeHistoryRepository.findTopByOrderByIdDesc();
        Exchange exchange = exchangeRepository.getFirstObject();
        exchange.setExchangeHistory(exchangeHistory);
        return new ResponseEntity<>(exchange, HttpStatus.OK);
    }
    
    @RequestMapping(value = "updateOptionCost/{id}.json")
    ResponseEntity<OperationalCost> updateOption(HttpServletRequest request, @RequestBody Map<String, Object> payload, @PathVariable long id) {
        OperationalCost option = optionRepository.findOne(id);
        String name = (String) payload.get("name");
        Double value = Double.valueOf((String) payload.get("send_value"));
        if(! ("".equals(name) || name == null)) {
            option.setName(name);
        }
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch(Exception e) {
            
        }
        option.setUsername(username);
        option.setValue(value, exchangeRepository.getFirstObject().getRatio());
        option = optionRepository.saveAndUpdateRelation(option);
        return new ResponseEntity<>(option, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllCompanyCostMap.json")
    ResponseEntity<List<CompanyCostSerializer>> update(HttpServletRequest request) throws Exception {
        return new ResponseEntity<>(costService.getAllCompanyCostMap(19.2, 1), HttpStatus.OK);
    }
    
    @RequestMapping(value = "updateCurExchange/{id}.json")
    ResponseEntity<Exchange> update(HttpServletRequest request, @RequestBody Map<String, Object> payload, @PathVariable long id) {
        Exchange exchange = exchangeRepository.getFirstObject();
        ExchangeHistory exchangeHistory = new ExchangeHistory();
        exchangeHistory.setUsername(exchange.getUsername());
        exchangeHistory.setExchangeCreated(exchange.getCreated());
        exchangeHistory.setRatio(exchange.getRatio());
        exchangeHistoryRepository.save(exchangeHistory);
        int ratio = Integer.valueOf((String) payload.get("send_value"));
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch(Exception e) {
            
        }
        exchange.setRatio(ratio);
        exchange.setUsername(username);
        exchangeRepository.save(exchange);
        Iterator<OperationalCost> options = optionRepository.findAll().iterator();
        while (options.hasNext()) {
            OperationalCost option = options.next();
            option.setValue(option.getValue(), ratio);
            option.setUsername(username);
            optionRepository.save(option);
        }

        return new ResponseEntity<>(exchange, HttpStatus.OK);
    }
    
    @RequestMapping(value = "getAllCompanies.json")
    @JsonView(CompanyJsonView.PublicView.class)
    ResponseEntity<Iterable<CompanyMaster>> getAllCompanies() throws Exception {
        Iterable<CompanyMaster> companies = companyRepository.findAllByOrderByNameAsc();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    
    @RequestMapping(value = "getFixedMargin/{id}.json")
    ResponseEntity<CompanyOption> getFixedMargin(HttpServletRequest request, @PathVariable int id) throws Exception {
        CompanyOption companyOption = companyRepository.findOne(id).getFixedMarginObject();
        return new ResponseEntity<>(companyOption, HttpStatus.OK);
    }
    
    @RequestMapping(path = "updateFixedMargin/{id}.json")
    ResponseEntity<CompanyOption> addOrUpdateFixedMargin(HttpServletRequest request, @RequestBody Map<String, String> payload, @PathVariable int id) {
        Iterable<Cost> costs = costRepository.findByCompany_Id(id);
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch(Exception e) {
            System.out.println("updateFixedMargin: dangerous username not found");
        }
        int margin = Integer.valueOf(payload.get("margin"));
        CompanyMaster company = companyRepository.findOne(id);
        CompanyOption companyOption = company.getFixedMarginObject();
        if(companyOption != null) {
            companyOption.setValue(String.valueOf(margin));
            companyOption.setUsername(username);
        }
        else {
            companyOption = company.makeEmptyCompanyOption();
            companyOption.setValue(String.valueOf(margin));
            companyOption.setUsername(username);
        }
        companyOptionRepository.save(companyOption);
        Iterable<OperationalCost> options = optionRepository.findAll();
        if(Iterables.size(costRepository.findByCompany_Id(id)) == 0) {
            for(OperationalCost opt: options) {
                Cost cost = new Cost();
                cost.setCompany(company);
                cost.setOption(opt);
                cost.setValue(opt.getValueInUsd() * (1 + margin * 1.0 / 100));
                cost.setUsername(username);
                costRepository.save(cost);
            }
        } else {
            costs = costRepository.findByCompany_Id(id);
            for(Cost cost: costs) {
                cost.setValue(cost.getOption().getValueInUsd() * (1 + margin * 1.0 / 100));
                cost.setUsername(username);
                costRepository.save(cost);
            }
        }
        companyOption = companyOptionRepository.findOne(companyOption.getId());
        return new ResponseEntity<CompanyOption>(companyOption, HttpStatus.OK);
    }
    
    @RequestMapping(value = "getAllOptions.json")
    ResponseEntity<Iterable<OperationalCost>> getAllOptions() {
        Iterable<OperationalCost> options = optionRepository.findAll();
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllOptionCosts/{id}.json")
    ResponseEntity<Iterable<Cost>> get(@PathVariable int id) {
        Iterable<Cost> costs = costRepository.findByCompany_Id(id);
        return new ResponseEntity<>(costs, HttpStatus.OK);
    }

    @RequestMapping(value = "updateOneCost/{id}.json")
    ResponseEntity<Cost> updateCost(HttpServletRequest request, 
            @RequestBody Map<String, Object> payload, @PathVariable long id) {
        Cost cost = costRepository.findOne(id);
        double value = Double.valueOf((String) payload.get("value"));
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch(Exception e) {
            
        }
        cost.setValue(value);
        cost.setUsername(username);
        costRepository.save(cost);
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }

    @RequestMapping(value = "getCostsIncludingCostPerTon/{id}.json")
    ResponseEntity<CostCalculatorSerializer> getTotalCost(HttpServletRequest request, @PathVariable int id, 
            @RequestParam("tonPerContainer") double tonPerContainer, @RequestParam("numberOfContainer") int numberOfContainer,
            @RequestParam("options") String options) throws Exception {
        CostCalculatorSerializer costCalculatorSerializer = new CostCalculatorSerializer();
        String defaultOptions = "processing__grading-cleaning,processing__color-sorting,processing__polishing,processing__drying,handling__re-bagging,handling__re-weighing,handling__quality-control,handling__from-normal-storage-to-nestle,fdw-to-instore__labor,fdw-to-instore__qualtiy-and-weight-control,warehousing__storage-in-silo,warehousing__storage-in-container-size-lots,warehousing__storage-nestle-conditions,export__bulk,export__big-bag,export__jute-bag,export__pp-bag,import__from-truck-to-silo,import__from-truck-to-big-bag,import__from-truck-to-pallet,bulk__labor,bulk__bulk-bag,bulk__bulk-loading-electricity,big-bag__labor,big-bag__big-bag,big-bag__big-bag-pallet,jute-bag-60kg__labor,jute-bag-60kg__jute-bag,jute-bag-60kg__labor-from-big-bag-to-jute-bag,pp-bag-60kg__labor,pp-bag-60kg__pp-bags,pp-bag-60kg__labor-from-big-bag-to-jute-bag,loading-transport__trucking-from-swc-bd-to-cat-lai,loading-transport__thc,loading-transport__lift-on-lift-off,loading-transport__coffee-tax-vicofa-fee,loading-transport__seal-fee,documents__phyto-sanitary-certificate,documents__customs,documents__ico,documents__bl-fee,documents__ifs-form-usa-shipment,documents__ens-europe-and-usa-shipment,qualtiy-weight-certificate__swcbd,qualtiy-weight-certificate__vccc,qualtiy-weight-certificate__cafecontol,qualtiy-weight-certificate__fcc,qualtiy-weight-certificate__omic-inc-cup-test,vfc__photoxin-9g,vfc__methyl-bromide-100g,vfc__methyl-bromide-80g,vfc__methyl-bromide-50g,vfc__fumigation-in-store,tcfc__photoxin-9g,tcfc__methyl-bromide-100g,tcfc__methyl-bromide-80g,tcfc__methyl-bromide-48g,tcfc__fumigation-in-store,optional-packing-items__dry-bags,optional-packing-items__kraft-paper,optional-packing-items__carton-board,optional-packing-items__tally,printed-paper__printing,printed-paper__plastic-bag,printed-paper__nylon,printed-paper__sewing-than-long,stencil__make-stencil,stencil__ink,stencil__alcohol,stencil__sewing-than-long,printed-jute-bag-single-color__trucking,printed-jute-bag-single-color__silk-screen,printed-jute-bag-single-color__printing,printed-cloth-label__printing,printed-cloth-label__nylon,printed-cloth-label__sewing-than-long";
        if(options.equals("")) {
            options = defaultOptions;
        }
        List<String> optionList = Arrays.asList(options.split(","));
        Map<String, Cost> costMap = costCalculator.getClientCostsIncludeCostPerTon(id, tonPerContainer, optionList, numberOfContainer);
        costCalculatorSerializer.setValue(costCalculator.getCostByMapCost(costMap));
        costCalculatorSerializer.setCostMap(costMap);
        return new ResponseEntity<>(costCalculatorSerializer, HttpStatus.OK);
    }
}
