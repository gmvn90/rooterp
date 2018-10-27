package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.hibernate.dto.serializer.CompanyCostSerializer;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.OptionRepository;

/**
 * Created by dunguyen on 9/28/16.
 */
@Transactional(propagation= Propagation.REQUIRED)
public class CostService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CostCalculator costCalculator;

    @Autowired
    private OptionRepository optionRepository;

    private static final Map<Long, String> title;

    static {
        title = new HashMap<>();
        title.put(1L, "FactoryCosts");
        title.put(4L, "StorageCosts");
        title.put(7L, "LaborCosts");
        title.put(11L, "CostsToFOB");
    }

    public CostService() {

    }

    public long getRootCategoryIdOfOptionName(String optionName) {
        OperationalCost option = optionRepository.findFirstByOptionName(optionName);
        return getRootCategoryOfOption(option).getId();
    }

    public Category getRootCategoryOfOption(OperationalCost option) {
        List<Category> categories = categoryRepository.findAll();
        return getRootCategoryOfOption(option, categories);
    }

    public Category getRootCategoryOfOption(OperationalCost option, List<Category> categories) {
        Map<Long, Category> categoryMap = new HashMap<>();
        for(Category category: categories) {
            categoryMap.put(category.getId(), category);
        }

        Category category = option.getCategory();
        if(category.getParent() == null) {
            return category;
        }

        while (category.getParent() != null) {
            category = category.getParent();
        }

        return category;
    }
    // return {rootcat: value}
    public Map<Long, Double> getCostMap(int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Map<String, Cost> costMap = costCalculator.getAllClientCostsIncludeCostPerTon(companyId, tonPerContainer, numberOfContainer);
        Map<Long, Double> res = new HashMap<>();
        for(Map.Entry<String, Cost> entry: costMap.entrySet()) {
            long key = getRootCategoryOfOption(entry.getValue().getOption()).getId();
            if(res.containsKey(key)) {
                res.put(key, res.get(key) + entry.getValue().getCostValue());
            } else {
                res.put(key, entry.getValue().getCostValue());
            }
        }
        return res;
    }

    public Map<String, Double> getStringCostMap(int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Map<Long, Double> costMap = getCostMap(companyId, tonPerContainer, numberOfContainer);
        Map<String, Double> res = new HashMap<>();
        for(Map.Entry<Long, Double> entry: costMap.entrySet()) {
            res.put(title.get(entry.getKey()), entry.getValue());
        }
        return res;
    }

    public List<CompanyCostSerializer> getAllCompanyCostMap(double tonPerContainer, int numberOfContainer) throws Exception {
        List<CompanyMaster> companyMasters = costRepository.findCompaniesThatHasCost();
        List<CompanyCostSerializer> res = new ArrayList<>();
        for(CompanyMaster companyMaster: companyMasters) {
            CompanyCostSerializer companyCostSerializer = new CompanyCostSerializer();
            companyCostSerializer.setId(companyMaster.getId());
            companyCostSerializer.setName(companyMaster.getName());
            companyCostSerializer.setCosts(getStringCostMap(companyMaster.getId(), tonPerContainer, numberOfContainer));
            res.add(companyCostSerializer);
        }
        return res;
    }



}
