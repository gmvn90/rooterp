package com.swcommodities.wsmill.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.model.ShippingCost;
import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.hibernate.dto.Cost;
import com.swcommodities.wsmill.hibernate.dto.CustomCost;
import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CostRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;

/**
 * Created by dunguyen on 9/8/16.
 */
@Component
public class CostCalculator {

    public static double singleDocumentCost = 50.0;
    @Autowired
    private CostRepository costRepository;
    @Autowired
    ShippingInstructionRepository shippingInstructionRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public CostCalculator() {
    }

    public static List<String> getUnits() {
        List<String> res = new ArrayList<>();
        res.add("1 Pallet");
        res.add("Per Bag - 60kg");
        res.add("Per Bag - Bulk");
        res.add("Per Bag - Big");
        res.add("20 Cont.");
        res.add("Per/BL");
        res.add("$/Mt.");
        return res;
    }

    public double getCostValueByCostAndTon(Cost cost, double tonPerContainer, int numberOfContainer) throws Exception {
        return cost.getCostPerMetricTon(tonPerContainer, numberOfContainer);
    }

    public Cost getCostByOptionName(List<Cost> costs, double tonPerContainer, String option, int numberOfContainer) throws Exception {
        for (Cost cost : costs) {
            if (cost.getOption().getOptionName().equals(option)) {
                cost.setValuePerMetricTon(cost.getCostPerMetricTon(tonPerContainer, numberOfContainer));
                return cost;
            }
        }
        return null;
    }

    public Map<String, Cost> getClientCostsIncludeCostPerTon(int companyId, double tonPerContainer, List<String> options, int numberOfContainer) throws Exception {
        Map<String, Cost> costMap = new HashMap<>();
        List<Cost> costs = costRepository.findByCompany_Id(companyId);
        for (Cost cost : costs) {
            for (String option : options) {
                if (cost.getOption().getOptionName().equals(option)) {
                    cost = getCostByOptionName(costs, tonPerContainer, option, numberOfContainer);
                    costMap.put(option, cost);
                }
            }
        }
        return costMap;
    }

    public Map<String, Cost> getAllClientCostsIncludeCostPerTon(int companyId, double tonPerContainer, int numberOfContainer) throws Exception {
        Map<String, Cost> costMap = new HashMap<>();
        List<Cost> costs = costRepository.findByCompany_Id(companyId);
        for (Cost cost : costs) {
            OperationalCost option = cost.getOption();
            cost = getCostByOptionName(costs, tonPerContainer, option.getOptionName(), numberOfContainer);
            costMap.put(option.getOptionName(), cost);
        }
        return costMap;
    }

    public double getCostByMapCost(Map<String, Cost> costMap) {
        double total = 0;
        total = costMap.entrySet().stream().map((entry) -> entry.getValue().getValuePerMetricTon()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }

    public double getCostPerMetricTon(int companyId, double tonPerContainer, List<String> options, int numberOfContainer) throws Exception {
        Map<String, Cost> costMap = getClientCostsIncludeCostPerTon(companyId, tonPerContainer, options, numberOfContainer);
        return getCostByMapCost(costMap);
    }

    public double getCustomCostPerMetricTon(double tonPerContainer, List<CustomCost> costs, int numberOfContainer) throws Exception {
        double total = 0;
        for (CustomCost cost : costs) {
            total += cost.getCostPerMetricTon(tonPerContainer, numberOfContainer);
        }
        return total;
    }

    public double getSICustomCostPerMetricTon(double tonPerContainer, List<SICustomCost> costs, int numberOfContainer) throws Exception {
        double total = 0;
        for (SICustomCost cost : costs) {
            total += cost.getCostPerMetricTon(tonPerContainer, numberOfContainer);
        }
        return total;
    }

    public double getOptionalDocumentcost(double tonPerContainer, int numberOfDocuments, int numberOfContainer) {
        if (numberOfContainer == 0 || tonPerContainer == 0) {
            return 0;
        }
        return singleDocumentCost * numberOfDocuments / tonPerContainer / numberOfContainer;
    }

    private List<String> getOptionsByCategory(Integer id) {
        if (id == null || id == -1 || id == 0) {
            return new ArrayList<>();
        }
        List<String> options = new ArrayList<>();
        Category category = categoryRepository.findOne(id.longValue());
        if(category != null) {
        	category.getOptions().forEach((option) -> {
                options.add(option.getOptionName());
            });
        }
        
        return options;
    }

    public double getInstoreCost(int clientId) {
        List<String> options = new ArrayList<>();
        options.add("fdw-to-instore__labor");
        options.add("fdw-to-instore__qualtiy-and-weight-control");
        try {
            return getCostPerMetricTon(clientId, 19.2, options, 1);
        } catch (Exception ex) {
            Logger.getLogger(CostCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double getStorageInContainerSizeLotCost(int clientId) {
        List<String> options = new ArrayList<>();
        options.add("warehousing__storage-in-container-size-lots");
        try {
            return getCostPerMetricTon(clientId, 19.2, options, 1);
        } catch (Exception ex) {
            Logger.getLogger(CostCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double getCostPerMetricTonForSI(int id) throws Exception {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(id);
        return getCostPerMetricTonForSINew(shippingInstruction);
    }

    private double getSampleSentCost(ShippingInstruction si) {
        double total = 0;
        total = si.getSampleSents().stream().map((ss) -> ss.getCourierMaster().getCost() / si.getQuantity()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }

    private double getCustomCostCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getSICustomCostPerMetricTon(shippingCost.getTonPerContainer(), shippingCost.getsICustomCosts(), shippingCost.getNumberOfContainer());
    }

    private double getOptionalDocumentcostForSi(ShippingInstruction si) {
        ShippingCost shippingCost = si.getShippingCost();
        return getOptionalDocumentcost(shippingCost.getTonPerContainer(), shippingCost.getOptionalDocumentNumber(), shippingCost.getNumberOfContainer());
    }

    private double getCourierCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getSampleSentCost(si) + getOptionalDocumentcostForSi(si);
    }

    private double getPackingCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), getPackingCostList(shippingCost.getPackingCostCategory()), shippingCost.getNumberOfContainer());
    }

    private double getLoadingAndTransportCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), getLoadingTransportCostList(), shippingCost.getNumberOfContainer());
    }

    private double getMarkingCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), getMarkingCostList(shippingCost.getMarkingCategory()), shippingCost.getNumberOfContainer());
    }

    private double getDocumentCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        List<String> options = new ArrayList<>(shippingCost.getDocumentCosts());
        options.addAll(Arrays.asList(shippingCost.getFumigationDetailCost(), shippingCost.getFumigationInStore()));
        return getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), options, shippingCost.getNumberOfContainer());
    }

    private double getPackingItemCost(ShippingInstruction si) throws Exception {
        ShippingCost shippingCost = si.getShippingCost();
        return getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), shippingCost.getPackingItemCosts(), shippingCost.getNumberOfContainer());
    }

    public double getCostPerMetricTonForSINew(ShippingInstruction si) {
        try {
        	System.out.println("getcouriercost " + getCourierCost(si));
            
            System.out.println("getpackingcost " + getPackingCost(si));
            System.out.println("getLoadingAndTransportCost " + getLoadingAndTransportCost(si));
            System.out.println("getDocumentCost " + getDocumentCost(si));
            System.out.println("getPackingItemCost " + getPackingItemCost(si));
            System.out.println("getMarkingCost " + getMarkingCost(si));
            System.out.println("getCustomCostCost " + getCustomCostCost(si));
        } catch (Exception e) {
			// TODO: handle exception
		}
    	

        ShippingCost shippingCost = si.getShippingCost();
        List<String> options = getLoadingTransportCostList();
        options.add(shippingCost.getFumigationDetailCost());
        options.add(shippingCost.getCertificateCost());
        options.add(shippingCost.getFumigationInStore());
        options.addAll(getPackingCostList(shippingCost.getPackingCostCategory()));
        options.addAll(getMarkingCostList(shippingCost.getMarkingCategory()));
        options.addAll(shippingCost.getDocumentCosts());
        options.addAll(shippingCost.getPackingItemCosts());
        try {
        	return getSampleSentCost(si) + getCostPerMetricTon(si.getCompanyMasterByClientId().getId(), si.getQuantity() / shippingCost.getNumberOfContainer(), options, shippingCost.getNumberOfContainer()) + getCustomCostCost(si) + getOptionalDocumentcostForSi(si);
        } catch (Exception e) {
			// TODO: handle exception
        	return 0;
		}

        
    }

    private List<String> getLoadingTransportCostList() {
        return getOptionsByCategory(17);
    }

    private List<String> getPackingCostList(Integer packingCostCategory) {
        return getOptionsByCategory(packingCostCategory);
    }

    private List<String> getMarkingCostList(Integer markingCategory) {
        return getOptionsByCategory(markingCategory);
    }

    public String getStorageCost(int clientId, String lang) {
        assert lang.equals("en") || lang.equals("vi");
        List<String> options = new ArrayList<>();
        options.add("warehousing__storage-in-container-size-lots");
        double cost = 0;
        try {
            cost = getCostPerMetricTon(clientId, 19.2, options, 1);
        } catch (Exception ex) {
            Logger.getLogger(CostCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        String costString = String.format("%.2f", cost);
        String result;
        if (lang.equals("en")) {
            result = "US$ " + costString + "/Mt./Mth.";
        } else {
            result = costString + " USD/tấn/tháng";
        }
        return result;
    }
}
