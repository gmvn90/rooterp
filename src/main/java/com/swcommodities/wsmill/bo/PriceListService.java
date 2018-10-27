package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swcommodities.wsmill.hibernate.dao.PriceListDao;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.view.PriceListView;
import com.swcommodities.wsmill.json.gson.deserializer.FloatDeserializer;
import com.swcommodities.wsmill.json.gson.deserializer.IntegerDeserializer;
import com.swcommodities.wsmill.utils.Common;

@Transactional(propagation = Propagation.REQUIRED)
public class PriceListService {

    private PriceListDao priceListDao;

    public void setPriceListDao(PriceListDao priceListDao) {
        this.priceListDao = priceListDao;
    }

    public PriceListView updatePriceList(String data, User user) {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat(Common.date_format_ddMMyyyy_dash)
                    .registerTypeAdapter(Integer.class,
                            new IntegerDeserializer())
                    .registerTypeAdapter(Float.class, new FloatDeserializer())
                    .create();
            PriceListView price = gson.fromJson(data, PriceListView.class);
            price.setUserName(user.getUserName());
            price.setUpdatedDate(new Date());
            data = gson.toJson(price);
            if (price.getId() == null) {
                // check client
                Integer id = priceListDao.checkClient(price.getClient());
                if (id != null) { // already exist in database
                    price.setId(id);
                    JSONArray js_arr = new JSONArray(getLog(id));
                    js_arr.put(new JSONObject(data));
                    price.setLog(js_arr.toString());
                    return priceListDao.updatePriceList(price);
                }
                price.setLog("[" + data + "]");
                return priceListDao.newPriceList(price);
            }
            JSONArray js_arr = new JSONArray(getLog(price.getId()));
            js_arr.put(new JSONObject(data));
            price.setLog(js_arr.toString());
            return priceListDao.updatePriceList(price);
        } catch (Exception e) {
            return null;
        }
    }

    public String getLog(int id) {
        return priceListDao.getLog(id);
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<HashMap> searchGlobe(String searchTerm, String order,
            int start, int amount, String colName) {
        return priceListDao.searchGlobe(searchTerm, order, start, amount, colName);
    }

    public long countRow() {
        return priceListDao.countRow();
    }

    public long getTotalAfterFilter() {
        return priceListDao.getTotalAfterFilter();
    }

    public PriceListView getPriceListById(int id) {
        return priceListDao.getPriceListById(id);
    }
}
