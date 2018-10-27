/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.OperationalCost;

/**
 * Created by trung on 7/27/16.
 */
@Component
public class OptionRepositoryImpl implements OptionCustom {

    @Autowired
    private OptionRepository optionDao;

    @Autowired
    private ExchangeRepository exchangeDao;
    
    public static Map<Long, List<Long>> getRelationFromJsonString(String json) throws Exception {
        Map<Long, List<Long>> res = new HashMap<>();
        JSONObject obj = new JSONObject(json);
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String k = keys.next();
            JSONArray _v = obj.getJSONArray(k);
            List<Long> v = new ArrayList<>();
            for(int i = 0; i < _v.length(); i++) {
                v.add(_v.getLong(i));
            }
            res.put(Long.valueOf(k), v);
        }
        return res;
    }
    
    public static List<Long> getAllKeys(Map<Long, List<Long>> map) {
        List<Long> res = new ArrayList<>();
        for(Map.Entry<Long, List<Long>> item: map.entrySet()) {
            res.add(item.getKey());
        }
        return res;
    }

    private Map<String, String> relations = new HashMap<String, String>() {
        {
            put("abc", "def");
        }
    };

    String jsonString = OperationalCost.relationships;

    Map<Long, List<Long>> oldMap;

    public OptionRepositoryImpl() throws Exception {
        this.oldMap = getRelationFromJsonString(jsonString);
    }

    @Override
    @Transactional

    public void updateRelation(OperationalCost option) {
        List<Long> keys = getAllKeys(oldMap);
        if (keys.contains(Long.valueOf(option.getLocalId()))) {
            for (Long dependantLocalId : oldMap.get(Long.valueOf(option.getLocalId()))) {
                OperationalCost depOption = optionDao.findFirstByLocalId(dependantLocalId.intValue());
                if (depOption != null) {
                    depOption.setValueByForcing(option.getValue(), exchangeDao.getFirstObject().getRatio(), option.getOptionType());
                    optionDao.save(depOption);
                }

            }
        }
    }

    @Override
    @Transactional
    public <S extends OperationalCost> S saveAndUpdateRelation(S option) {
        option = optionDao.save(option);
        updateRelation(option);
        return option;
    }
}
