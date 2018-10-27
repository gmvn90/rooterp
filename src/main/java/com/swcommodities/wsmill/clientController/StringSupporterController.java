package com.swcommodities.wsmill.clientController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.swcommodities.wsmill.bo.StockHistoricalService;
import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.StockListClientViewHistory;
import com.swcommodities.wsmill.hibernate.dto.serializer.CostCalculatorSerializer;
import com.swcommodities.wsmill.hibernate.dto.serializer.SampleSentCardViewInstructionSerializer;
import com.swcommodities.wsmill.hibernate.dto.serializer.ShippingCardViewInstructionSerializer;
import com.swcommodities.wsmill.hibernate.dto.view.StockReportHistoryByGrade;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.OptionRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.repository.WeightNoteReceiptRepository;
import com.swcommodities.wsmill.service.ReportService;
import com.swcommodities.wsmill.service.StockReportHistoryService;

/**
 * Created by macOS on 1/17/17.
 */
@RequestMapping("/millclient/stringsupporter")
@Controller
public class StringSupporterController {
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SampleSentRepository sampleSentRepository;

    @Autowired
    private CostCalculator costCalculator;

    @Autowired
    private StockHistoricalService stockHistoricalService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StockReportHistoryService stockReportHistoryService;

    @Autowired
    private WeightNoteReceiptRepository weightNoteReceiptRepository;

    protected Integer getCompanyId(HttpSession httpSession) {
        Integer company_id = (Integer) httpSession.getAttribute("company_id");
        return company_id;
    }

    @RequestMapping(value = "/exchangestring.json", method = RequestMethod.GET)
    public @ResponseBody String getExchangeString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(exchangeRepository.getFirstObject());
    }

    @RequestMapping(value = "/optionstring.json", method = RequestMethod.GET)
    public @ResponseBody String getOptionString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(optionRepository.findAll());
    }

    @RequestMapping(value = "/categorystring.json", method = RequestMethod.GET)
    public @ResponseBody String getCategoryString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(categoryRepository.findAll());
    }

    @RequestMapping(value = "/get_by_shipping.json", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getByShippingInstructions(@RequestParam("shipping") Integer shipping) throws JsonProcessingException {
        List<SampleSent> sampleSents = sampleSentRepository.findByShippingInstructionBySiId_Id(shipping);
        
        ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(ShippingInstruction.class, new ShippingCardViewInstructionSerializer());
		module.addSerializer(SampleSent.class, new SampleSentCardViewInstructionSerializer());
		mapper.registerModule(module);
		
		String result = mapper.writeValueAsString(sampleSents);
		
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCostsIncludingCostPerTon/{id}.json")
    public @ResponseBody ResponseEntity<CostCalculatorSerializer> getTotalCost(HttpServletRequest request, @PathVariable int id,
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

    @RequestMapping(value = "/getStock.json")
    public @ResponseBody String getStock(HttpSession httpSession, @RequestParam("grade") Integer grade,
                                         @RequestParam("pledge") Integer pledge, @RequestParam("warehouse") Integer warehouse) {
        Integer client = getCompanyId(httpSession);
        return (new JSONObject(stockHistoricalService.getStockReport(grade, client, pledge, warehouse, true))).toString();
    }

    @RequestMapping(value = "/getPledgeForClient.json")
    public @ResponseBody String getPledgeForClient(HttpSession httpSession) {
        Integer client = getCompanyId(httpSession);
        return stockHistoricalService.getPledgeFilterForClient(client).toString();
    }

    @RequestMapping(value = "/getGradeForClient.json")
    public @ResponseBody String getGradeForClient(HttpSession httpSession) {
        Integer client = getCompanyId(httpSession);
        return stockHistoricalService.getGradeFilterForClient(client).toString();
    }

    @RequestMapping(value = "/getWarehouseForClient.json")
    public @ResponseBody String getWarehouseForClient() {
        return stockHistoricalService.getWarehouseFilter().toString();
    }

    @RequestMapping(method= RequestMethod.GET, value = "/getPriceReport.json")
    public @ResponseBody byte[] getFileContent(HttpSession httpSession) throws Exception {
        Integer client = getCompanyId(httpSession);
        byte[] res = reportService.writeAllInfoToPdf(client);
        res = Base64.encodeBase64(res);
        return res;
    }

    @RequestMapping(value = "/getStockReport.json", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<StockReportHistoryByGrade>> getStockReport(HttpSession httpSession, @RequestParam("viewTypeId") Integer viewTypeId)
            throws JsonProcessingException, ParseException {
        int clientId = getCompanyId(httpSession);
        if(viewTypeId == null) {
            viewTypeId = 0;
        }
        List<StockReportHistoryByGrade> grades = new ArrayList<>();
        if (viewTypeId == 0) {
            grades = stockReportHistoryService.getStockReportHistoryDayView(clientId);
        } else {
            grades = stockReportHistoryService.getStockReportHistoryMonthView(clientId);
        }

        if(grades == null) {
            grades = new ArrayList<>();
        }
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @RequestMapping(value = "/getStockPlan.json", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<StockListClientViewHistory>> getStockPlan(HttpSession httpSession, @RequestParam("gradeId") Integer gradeId, @RequestParam("warehouseId") Integer warehouseId)
            throws JsonProcessingException, ParseException {
        int clientId = getCompanyId(httpSession);
        if(gradeId == null) {
            gradeId = 0;
        }
        if(warehouseId == null) {
            warehouseId = 0;
        }
        List<StockListClientViewHistory> grades = new ArrayList<>();

        grades = weightNoteReceiptRepository.getCurrentStockListClientView(clientId, gradeId, warehouseId);

        if(grades == null) {
            grades = new ArrayList<>();
        }
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

}
