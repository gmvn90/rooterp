package com.swcommodities.wsmill.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dunguyen on 8/30/16.
 */
public class CompanyRequest extends Request {
    public CompanyRequest(String baseUrl) {
        super(baseUrl);
    }

    public String getFixedMargin(int id) throws Exception {
        return getContent("/companies/" + id + "/fixedMargin");
    }

    public String getAllCompany() throws Exception {
        return getContent("/companies");
    }

    public String updateFixedMargin(int id, int margin, String username) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("margin", Integer.valueOf(margin));
        data.put("username", username);
        return putContent("/costs/company/" + id + "/fixedMargin", data);
    }

    public String getAllCosts(int id) throws Exception {
        return getContent("/costs/company/" + id);
    }

    public String updateOneCost(int id, float value, String username) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("value", value);
        data.put("username", username);
        return putContent("/costs/" + id, data);
    }

    public String getCostsIncludingCostPerTon(int id) throws Exception {
        String options = "processing__grading-cleaning,processing__color-sorting,processing__polishing,processing__drying,handling__re-bagging,handling__re-weighing,handling__quality-control,handling__from-normal-storage-to-nestle,fdw-to-instore__labor,fdw-to-instore__qualtiy-and-weight-control,warehousing__storage-in-silo,warehousing__storage-in-container-size-lots,warehousing__storage-nestle-conditions,export__bulk,export__big-bag,export__jute-bag,export__pp-bag,import__from-truck-to-silo,import__from-truck-to-big-bag,import__from-truck-to-pallet,bulk__labor,bulk__bulk-bag,bulk__bulk-loading-electricity,big-bag__labor,big-bag__big-bag,big-bag__big-bag-pallet,jute-bag-60kg__labor,jute-bag-60kg__jute-bag,jute-bag-60kg__labor-from-big-bag-to-jute-bag,pp-bag-60kg__labor,pp-bag-60kg__pp-bags,pp-bag-60kg__labor-from-big-bag-to-jute-bag,loading-transport__trucking-from-swc-bd-to-cat-lai,loading-transport__thc,loading-transport__lift-on-lift-off,loading-transport__coffee-tax-vicofa-fee,loading-transport__seal-fee,documents__phyto-sanitary-certificate,documents__customs,documents__ico,documents__bl-fee,documents__ifs-form-usa-shipment,documents__ens-europe-and-usa-shipment,qualtiy-weight-certificate__swcbd,qualtiy-weight-certificate__vccc,qualtiy-weight-certificate__cafecontol,qualtiy-weight-certificate__fcc,qualtiy-weight-certificate__omic-inc-cup-test,vfc__photoxin-9g,vfc__methyl-bromide-100g,vfc__methyl-bromide-80g,vfc__methyl-bromide-50g,vfc__fumigation-in-store,tcfc__photoxin-9g,tcfc__methyl-bromide-100g,tcfc__methyl-bromide-80g,tcfc__methyl-bromide-48g,tcfc__fumigation-in-store,optional-packing-items__dry-bags,optional-packing-items__kraft-paper,optional-packing-items__carton-board,optional-packing-items__tally,printed-paper__printing,printed-paper__plastic-bag,printed-paper__nylon,printed-paper__sewing-than-long,stencil__make-stencil,stencil__ink,stencil__alcohol,stencil__sewing-than-long,printed-jute-bag-single-color__trucking,printed-jute-bag-single-color__silk-screen,printed-jute-bag-single-color__printing,printed-cloth-label__printing,printed-cloth-label__nylon,printed-cloth-label__sewing-than-long";
        return getContent("/costs/company/" + id + "/totalCost?tonPerContainer=19.2&options=" + options);
    }
}
