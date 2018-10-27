package com.swcommodities.wsmill.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ViewResolver;

import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.hibernate.dto.PITypeItem;
import com.swcommodities.wsmill.infrastructure.PdfRenderService;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.OptionRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by dunguyen on 11/3/16.
 */
@Transactional
@Service
public class ReportService {

    private PITypeRepository pITypeRepository;
    private OptionRepository optionRepository;
    private CategoryRepository categoryRepository;
    private CostRepository costRepository;
    private CostCalculator costCalculator;
    private ExchangeRepository exchangeRepository;
    private PdfRenderService pdfRenderService;
    private ViewResolver viewResolver;
    private CompanyRepository companyRepository;

    @Autowired
    public ReportService(PITypeRepository pITypeRepository, OptionRepository optionRepository,
                         CategoryRepository categoryRepository, CostRepository costRepository,
                         CostCalculator costCalculator, ExchangeRepository exchangeRepository,
                         PdfRenderService pdfRenderService, ViewResolver viewResolver, CompanyRepository companyRepository) {
        Assert.notNull(pITypeRepository);
        Assert.notNull(optionRepository);
        Assert.notNull(categoryRepository);
        Assert.notNull(costRepository);
        Assert.notNull(costCalculator);
        Assert.notNull(exchangeRepository);

        this.pITypeRepository = pITypeRepository;
        this.optionRepository = optionRepository;
        this.categoryRepository = categoryRepository;
        this.costRepository = costRepository;
        this.costCalculator = costCalculator;
        this.exchangeRepository = exchangeRepository;
        this.pdfRenderService = pdfRenderService;
        this.viewResolver = viewResolver;
        this.companyRepository = companyRepository;
    }

    public List<PIType> getList() {
        return pITypeRepository.findAll();
    }

    public Map<String, Double> getPiTypeTotalInfo(int piTypeId, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        PIType piType = pITypeRepository.findOne(piTypeId);
        return getPiTypeTotalInfo(piType, companyId, tonPerContainer, numberOfContainer);
    }

    public Map<String, Double> getPiTypeTotalInfo(PIType piType, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        double totalPrice = 0;
        double totalRejectGrade3 = 0;
        double totalProcessingWeighLoss = 0;
        for(PITypeItem item: piType.getPiTypeItems()) {
            Cost cost = costRepository.findFirstByCompany_IdAndOption_Id(companyId, item.getOption().getId());
            totalPrice += costCalculator.getCostValueByCostAndTon(cost, tonPerContainer, numberOfContainer);
            totalProcessingWeighLoss += item.getWeightLoss();
            totalRejectGrade3 += item.getRejectGrade3();
        }
        Map<String, Double> res = new HashMap<>();
        res.put("totalPrice", totalPrice);
        res.put("totalProcessingWeighLoss", totalProcessingWeighLoss);
        res.put("totalRejectGrade3", totalRejectGrade3);
        System.out.println(String.format("%s-%s-%s-%s", piType.getName(), totalPrice, totalProcessingWeighLoss, totalRejectGrade3));
        return res;
    }

    public double getCostPerCategoryByInnerChildren(Integer categoryId, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Category category = categoryRepository.findOne(categoryId.longValue());
        SortedSet<OperationalCost> options = category.getChildOptions();
        double totalCost = 0;
        for(OperationalCost option: options) {
            totalCost += costCalculator.getCostValueByCostAndTon(costRepository.findFirstByCompany_IdAndOption_Id(companyId, option.getId()), tonPerContainer, numberOfContainer);
        }
        return totalCost;
    }
    public double getCostPerCategoryByDirectChildren(Integer categoryId, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Category category = categoryRepository.findOne(categoryId.longValue());
        SortedSet<OperationalCost> options = category.getOptions();
        double totalCost = 0;
        for(OperationalCost option: options) {
            totalCost += costCalculator.getCostValueByCostAndTon(costRepository.findFirstByCompany_IdAndOption_Id(companyId, option.getId()), tonPerContainer, numberOfContainer);
        }
        return totalCost;
    }


    private boolean isShownPiType(PIType piType) {
        return piType.getId() <= 6;
    }


    public String writeAllInfoWithHtmlTag(int companyId) throws Exception {
        int handlingCat = 3;
        int storageCat = 4;
        int optionalPackageCat = 23;

        int packingBulkCat = 13;
        int documentCat = 18;

        int packingBigBag = 14;

        int packingJuteBag = 15;

        int packingPPBag = 16;
        int loadingAndTransportCat = 17;
        double tonPerContainer = 20;
        int numberOfContainer = 1;
        
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("WEB-INF/templates/priceReport.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("clientName", companyRepository.findOne(companyId).getName());
            data.put("clientAddress", companyRepository.findOne(companyId).getAddress1());
            data.put("totalCol", 10);
            data.put("updateOn", (new Date()));
            int ratio = exchangeRepository.getFirstObject().getRatio();
            Exchange ex = exchangeRepository.getFirstObject();
            data.put("ratio", ratio);
            data.put("ratioObj", ex);
            Map<String, Map<String, Double>> costMap = new HashMap<>();

            List<PIType> piTypes = pITypeRepository.findAll();
            List<PIType> shownPiTypes = new ArrayList<>();
            for(PIType piType: piTypes) {
                if(isShownPiType(piType)) {
                    Map<String, Double> info = getPiTypeTotalInfo(piType, companyId, tonPerContainer, numberOfContainer);
                    piType.setTotalPrice(info.get("totalPrice"));
                    piType.setTotalProcessingWeighLoss(info.get("totalProcessingWeighLoss"));
                    piType.setTotalRejectGrade3(info.get("totalRejectGrade3"));
                    shownPiTypes.add(piType);
                }
            }


            data.put("piTypes", shownPiTypes);
            data.put("handlingCat", getCategory(handlingCat, companyId, tonPerContainer, numberOfContainer));
            for(OperationalCost _c: getCategory(handlingCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                System.out.println(_c.getCost().getCostValue());
            }
            data.put("storageCat", getCategoryWithDescedent(storageCat, companyId, tonPerContainer, numberOfContainer));
            data.put("optionalPackageCat", getCategory(optionalPackageCat, companyId, tonPerContainer, numberOfContainer));

            tonPerContainer = 21.6;

            Map<String, Double> cat216 = new HashMap<>();
            for(OperationalCost option: getCategory(documentCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat216.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            for(OperationalCost option: getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat216.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            costMap.put("cat216", cat216);

            data.put("packingBulkCat", getCategory(packingBulkCat, companyId, tonPerContainer, numberOfContainer));
            data.put("documentCat216", getCategory(documentCat, companyId, tonPerContainer, numberOfContainer));
            data.put("loadingAndTransport216", getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));
            data.put("total216", getCostPerCategoryByDirectChildren(packingBulkCat, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(documentCat, companyId, tonPerContainer, numberOfContainer)
                + getCostPerCategoryByDirectChildren(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));

            tonPerContainer = 20;

            Map<String, Double> cat20 = new HashMap<>();
            for(OperationalCost option: getCategory(documentCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat20.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            for(OperationalCost option: getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat20.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            costMap.put("cat20", cat20);


            data.put("packingBigCat", getCategory(packingBigBag, companyId, tonPerContainer, numberOfContainer));
            data.put("documentCat20", getCategory(documentCat, companyId, tonPerContainer, numberOfContainer));
            data.put("loadingAndTransport20", getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));
            data.put("total20", getCostPerCategoryByDirectChildren(packingBigBag, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(documentCat, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));

            tonPerContainer = 19.2;

            Map<String, Double> cat192Jute = new HashMap<>();
            for(OperationalCost option: getCategory(documentCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat192Jute.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            for(OperationalCost option: getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat192Jute.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            costMap.put("cat192Jute", cat192Jute);

            data.put("packingJuteBag", getCategory(packingJuteBag, companyId, tonPerContainer, numberOfContainer));
            data.put("documentCat192Jute", getCategory(documentCat, companyId, tonPerContainer, numberOfContainer));
            data.put("loadingAndTransport192Jute", getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));
            data.put("total192Jute", getCostPerCategoryByDirectChildren(packingJuteBag, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(documentCat, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));

            tonPerContainer = 19.2;

            Map<String, Double> cat192PP = new HashMap<>();
            for(OperationalCost option: getCategory(documentCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat192PP.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            for(OperationalCost option: getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer).getOptions()) {
                cat192PP.put(option.getOptionName(), option.getCost().getCostPerTon());
            }
            costMap.put("cat192PP", cat192PP);


            data.put("packingPPBag", getCategory(packingPPBag, companyId, tonPerContainer, numberOfContainer));
            data.put("documentCat192PP", getCategory(documentCat, companyId, tonPerContainer, numberOfContainer));
            data.put("loadingAndTransport192PP", getCategory(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));
            data.put("total192PP", getCostPerCategoryByDirectChildren(packingPPBag, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(documentCat, companyId, tonPerContainer, numberOfContainer)
                    + getCostPerCategoryByDirectChildren(loadingAndTransportCat, companyId, tonPerContainer, numberOfContainer));
            data.put("costMap", costMap);

            // Console output
            Writer out = new StringWriter();
            template.process(data, out);
            out.flush();
            System.out.println(out.toString());
            return out.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Category getCategory(int categoryId, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Category cat = categoryRepository.findOne(Long.valueOf(categoryId));
        for(OperationalCost option: cat.getOptions()) {
            Cost cost = costRepository.findFirstByCompany_IdAndOption_Id(companyId, option.getId());
            double _costValue = costCalculator.getCostValueByCostAndTon(cost, tonPerContainer, numberOfContainer);
            cost.setCostPerTon(_costValue);
            option.setCost(cost);
            System.out.println(String.format("%s - value per ton: %s - value: %s", option.getOptionName(), _costValue, cost.getCostValue()));
        }
        return cat;
    }

    private Category getCategoryWithDescedent(int categoryId, int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Category cat = categoryRepository.findOne(Long.valueOf(categoryId));
        for(OperationalCost option: cat.getChildOptions()) {
            Cost cost = costRepository.findFirstByCompany_IdAndOption_Id(companyId, option.getId());
            cost.setCostPerTon(costCalculator.getCostValueByCostAndTon(cost, tonPerContainer, numberOfContainer));
            option.setCost(cost);
        }
        return cat;
    }

    public byte[] writeAllInfoToPdf(int companyId) throws Exception {
        return pdfRenderService.renderAsByte(writeAllInfoWithHtmlTag(companyId));
    }

}
