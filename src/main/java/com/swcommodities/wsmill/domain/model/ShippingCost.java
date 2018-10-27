/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swcommodities.wsmill.hibernate.dto.CustomCost;

/**
 *
 * @author trung
 */
@Embeddable
public class ShippingCost {

    @JsonIgnore
    private String clientSiCostListJson = "";
    @JsonIgnore
    private String costNames;
    @JsonIgnore
    private String weightQualityCertificate;
    @JsonIgnore
    private String fumigation;
    private int packingCostCategory = 13;
    private int loadingAndTransportCategory = 17;
    private int documentCategory = 18;
    private int certificateCategory = 19;
    private int fumigationCategory = 20;
    private int fumigationProviderCategory = 21;
    private int optionalCategory = 23;
    private int allMarkingCategory = 24;
    private int markingCategory = -1;
    private String fumigationDetailCost = "";
    private String fumigationInStore = "";
    private String certificateCost = "";
    private double tonPerContainer = 19.2;
    private Float quantity = 0F;
    private int numberOfContainer = 1;
    private List<CustomCost> customCosts = new ArrayList<>();
    private int optionalDocumentNumber = 0;
    List<String> documentCosts = new ArrayList<>();
    List<String> packingItemCosts = new ArrayList<>();
    List<SICustomCost> sICustomCosts = new ArrayList<>();

    public ShippingCost() {
    }

    public ShippingCost(String clientSiCostListJson) {
        this.clientSiCostListJson = clientSiCostListJson;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject = new JSONObject(clientSiCostListJson);
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            packingCostCategory = jSONObject.getInt("packingCostCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadingAndTransportCategory = jSONObject.getInt("loadingAndTransportCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            documentCategory = jSONObject.getInt("documentCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            certificateCategory = jSONObject.getInt("certificateCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fumigationCategory = jSONObject.getInt("fumigationCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fumigationProviderCategory = jSONObject.getInt("fumigationProviderCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            optionalCategory = jSONObject.getInt("optionalCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            allMarkingCategory = jSONObject.getInt("allMarkingCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            markingCategory = jSONObject.getInt("markingCategory");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fumigationDetailCost = jSONObject.getString("fumigationDetailCost");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fumigationInStore = jSONObject.getString("fumigationInstore");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            certificateCost = jSONObject.getString("certificateCost");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tonPerContainer = jSONObject.getDouble("tonPerContainer");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            numberOfContainer = jSONObject.getInt("numberOfContainer");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            customCosts = getCustomCostFromJson(jSONObject.getString("customCosts"));
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            optionalDocumentNumber = jSONObject.getInt("optionalDocumentNumber");
        } catch (JSONException ex) {
            optionalDocumentNumber = -1;
        }
        JSONArray optionsJSONArray = new JSONArray();
        try {
            optionsJSONArray = new JSONArray(jSONObject.getString("costNames"));
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < optionsJSONArray.length(); i++) {
            String option = "";
            try {
                option = optionsJSONArray.getString(i);
            } catch (JSONException ex) {
                Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (isCostBelongToDocumentCost(option)) {
                documentCosts.add(option);
            }
            if (isCostBelongToOptionalPackingItem(option)) {
                packingItemCosts.add(option);
            }
        }
        try {
            costNames = jSONObject.getString("costNames");
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sICustomCosts = getSiCustomCostFromJson(jSONObject.getString("customCosts"));
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @ElementCollection
    @CollectionTable(name = "shipping_instruction_si_custom_cost")
    public List<SICustomCost> getsICustomCosts() {
        return sICustomCosts;
    }

    public void setsICustomCosts(List<SICustomCost> sICustomCosts) {
        this.sICustomCosts = sICustomCosts;
    }
    
    @ElementCollection
    @CollectionTable(name = "shipping_instruction_document_cost")
    public List<String> getDocumentCosts() {
        return documentCosts;
    }

    public void setDocumentCosts(List<String> documentCosts) {
        this.documentCosts = documentCosts;
    }

    @ElementCollection
    @CollectionTable(name = "shipping_instruction_packing_item_cost")
    public List<String> getPackingItemCosts() {
        return packingItemCosts;
    }

    public void setPackingItemCosts(List<String> packingItemCosts) {
        this.packingItemCosts = packingItemCosts;
    }

    private List<CustomCost> getCustomCostFromJson(String jsonString) throws JSONException {
        List<CustomCost> costs = new ArrayList<>();
        JSONArray jSONArray = new JSONArray(jsonString);
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject obj = jSONArray.getJSONObject(i);
            CustomCost cost = new CustomCost();
            cost.setOptionUnit(obj.getString("optionUnit"));
            cost.setOptionName(obj.getString("optionName"));
            cost.setValue(obj.getDouble("value"));
            costs.add(cost);
        }
        return costs;
    }
    
    private List<SICustomCost> getSiCustomCostFromJson(String jsonString) {
        List<SICustomCost> costs = new ArrayList<>();
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray = new JSONArray(jsonString);
        } catch (JSONException ex) {
            Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject obj = new JSONObject();
            try {
                obj = jSONArray.getJSONObject(i);
            } catch (JSONException ex) {
                Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
            }
            SICustomCost cost = new SICustomCost();
            
            try {
                cost.setOptionUnit(obj.getString("optionUnit"));
            } catch (JSONException ex) {
                Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                cost.setOptionName(obj.getString("optionName"));
            } catch (JSONException ex) {
                Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                cost.setCostValue(obj.getDouble("value"));
            } catch (JSONException ex) {
                Logger.getLogger(ShippingCost.class.getName()).log(Level.SEVERE, null, ex);
            }
            costs.add(cost);
        }
        return costs;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<CustomCost> getCustomCosts() {
        return customCosts;
    }

    public void setCustomCosts(List<CustomCost> customCosts) {
        this.customCosts = customCosts;
    }

    @Column(name = "client_si_cost_list_json", columnDefinition = "TEXT")
    public String getClientSiCostListJson() {
        return clientSiCostListJson;
    }

    public void setClientSiCostListJson(String clientSiCostListJson) {
        this.clientSiCostListJson = clientSiCostListJson;
    }

    @Column(name = "cost_names", columnDefinition = "TEXT")
    public String getCostNames() {
        return costNames;
    }

    public void setCostNames(String costNames) {
        this.costNames = costNames;
    }

    @Column(name = "weight_quality_certificate")
    public String getWeightQualityCertificate() {
        return weightQualityCertificate;
    }

    public void setWeightQualityCertificate(String weightQualityCertificate) {
        this.weightQualityCertificate = weightQualityCertificate;
    }

    @Column(name = "fumigation")
    public String getFumigation() {
        return fumigation;
    }

    @Column(name = "shipping_cost_quantity")
    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    private boolean isCostBelongToDocumentCost(String cost) {
        return cost.startsWith("documents__");
    }

    private boolean isCostBelongToOptionalPackingItem(String cost) {
        return cost.startsWith("optional-packing-items__");
    }

    public void setFumigation(String fumigation) {
        this.fumigation = fumigation;
    }

    public int getPackingCostCategory() {
        return packingCostCategory;
    }

    public void setPackingCostCategory(int packingCostCategory) {
        this.packingCostCategory = packingCostCategory;
    }

    @Transient
    public int getLoadingAndTransportCategory() {
        return loadingAndTransportCategory;
    }

    public void setLoadingAndTransportCategory(int loadingAndTransportCategory) {
        this.loadingAndTransportCategory = loadingAndTransportCategory;
    }

    @Transient
    public int getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(int documentCategory) {
        this.documentCategory = documentCategory;
    }

    @Transient
    public int getCertificateCategory() {
        return certificateCategory;
    }

    public void setCertificateCategory(int certificateCategory) {
        this.certificateCategory = certificateCategory;
    }

    @Transient
    public int getFumigationCategory() {
        return fumigationCategory;
    }

    public void setFumigationCategory(int fumigationCategory) {
        this.fumigationCategory = fumigationCategory;
    }

    public int getFumigationProviderCategory() {
        return fumigationProviderCategory;
    }

    public void setFumigationProviderCategory(int fumigationProviderCategory) {
        this.fumigationProviderCategory = fumigationProviderCategory;
    }

    @Transient
    public int getOptionalCategory() {
        return optionalCategory;
    }

    public void setOptionalCategory(int optionalCategory) {
        this.optionalCategory = optionalCategory;
    }

    @Transient
    public int getAllMarkingCategory() {
        return allMarkingCategory;
    }

    public void setAllMarkingCategory(int allMarkingCategory) {
        this.allMarkingCategory = allMarkingCategory;
    }

    public int getMarkingCategory() {
        return markingCategory;
    }

    public void setMarkingCategory(int markingCategory) {
        this.markingCategory = markingCategory;
    }

    public String getFumigationDetailCost() {
        return fumigationDetailCost;
    }

    public void setFumigationDetailCost(String fumigationDetailCost) {
        this.fumigationDetailCost = fumigationDetailCost;
    }
    
    @Column(name="fumigationInStore", columnDefinition="Varchar(50) default ''")
    public String getFumigationInStore() {
        return fumigationInStore;
    }

    public void setFumigationInStore(String fumigationInStore) {
        this.fumigationInStore = fumigationInStore;
    }

    public String getCertificateCost() {
        return certificateCost;
    }

    public void setCertificateCost(String certificateCost) {
        this.certificateCost = certificateCost;
    }

    public double getTonPerContainer() {
        return tonPerContainer;
    }

    public void setTonPerContainer(double tonPerContainer) {
        this.tonPerContainer = tonPerContainer;
    }

    public int getNumberOfContainer() {
        return numberOfContainer;
    }

    public void setNumberOfContainer(int numberOfContainer) {
        this.numberOfContainer = numberOfContainer;
    }

    public int getOptionalDocumentNumber() {
        return optionalDocumentNumber;
    }

    public void setOptionalDocumentNumber(int optionalDocumentNumber) {
        this.optionalDocumentNumber = optionalDocumentNumber;
    }

    public void addSiCustomCost(SICustomCost cost) {
        SICustomCost thisCost = this.getsICustomCosts().stream().filter(scc -> scc.getId().equals(cost.getId())).findAny().map(cc -> cc).orElse(null);
        if(thisCost == null) {
            this.getsICustomCosts().add(cost);
        }else {
            
            thisCost.setCostValue(cost.getCostValue());
            thisCost.setOptionName(cost.getOptionName());
            thisCost.setOptionUnit(cost.getOptionUnit());
        }
    }
    
    public void removeCustomCost(String id) {
        this.getsICustomCosts().removeIf(cc -> cc.getId().equals(id));
    }
}
