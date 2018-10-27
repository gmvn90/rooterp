/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.clientRepository.ClientDailyBasisHistoryRepository;
import com.swcommodities.wsmill.clientRepository.MarketFobDiffHistoryRepository;
import com.swcommodities.wsmill.hibernate.dto.DailyBasis;
import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.GradeConverter;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.MarketFobDiff;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.repository.DailyBasisRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.GradeConverterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.MarketFobDiffRepository;
import com.swcommodities.wsmill.service.DailyBasicAppService;
import com.swcommodities.wsmill.service.MarketFobService;

/**
 *
 * @author macOS
 */
@Controller
@Transactional
public class MarketController {
    
    @Autowired MarketFobDiffHistoryRepository marketFobDiffHistoryRepository;
    @Autowired ClientDailyBasisHistoryRepository clientDailyBasisHistoryRepository;
    @Autowired MarketFobDiffRepository marketFobDiffRepository;
    @Autowired MarketFobService marketFobService;
    @Autowired GradeRepository gradeRepository;
    @Autowired GradeConverterRepository gradeConverterRepository;
    @Autowired ExchangeRepository exchangeRepository;
    @Autowired DailyBasisRepository dailyBasisRepository;
    @Autowired DailyBasicAppService dailyBasicAppService;

    @RequestMapping(value = "daily-basic.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model) {
        List<DailyBasis> dailyBasises = dailyBasisRepository.findMostRecentDailyBasisesDesc();
        List<MarketFobDiff> markets = marketFobService.getAllExceptUnused();
        Exchange exchange = exchangeRepository.findAll().get(0);
        model.put("markets", markets);
        model.put("exchange", exchange);
        model.put("dailyBasises", dailyBasises);
        return "daily-basic";
    }
    
    @RequestMapping(value = "update-daily-basis.htm", method = RequestMethod.POST)
    public String updateDailyBasis(Map<String, Object> model, HttpServletRequest request) throws IOException {
        float currentBasis = Float.valueOf(request.getParameter("currentBasis"));
        int id = Integer.valueOf(request.getParameter("id"));
        DailyBasis dailyBasis = dailyBasisRepository.findOne(id);
        dailyBasis.setPreviousDate(dailyBasis.getUpdatedDate());
        dailyBasis.setPreviousBasis(dailyBasis.getCurrentBasis());
        dailyBasis.setCurrentBasis(currentBasis);
        dailyBasis.setUser(getUser(request));
        dailyBasis.setUpdatedDate(new Date());
        try {
            dailyBasis.setLiffeLow(Float.valueOf(request.getParameter("liffeLow")));
        } catch (Exception e) {
        }
        try {
            dailyBasis.setLiffeHigh(Float.valueOf(request.getParameter("liffeHigh")));
        } catch (Exception e) {
        }
        
        
        dailyBasisRepository.save(dailyBasis);
        return "redirect:/daily-basic.htm";
    }
    
    @RequestMapping(value = "show-fob-mapping.htm", method = RequestMethod.GET)
    public String showMapping(Map<String, Object> model) {
        List<GradeConverter> gradeConverters = gradeConverterRepository.findAll();
        List<RefList> trades = new ArrayList<>();
        for(GradeConverter gradeConverter: gradeConverters) {
            RefList item = new RefList();
            item.setId(gradeConverter.getTradeId());
            item.setValue(gradeConverter.getTradeName());
            trades.add(item);
        }
        model.put("trades", trades);
        model.put("gradeConverters", gradeConverters);
        return "show-fob-mapping";
    }
    
    @RequestMapping(value = "update-fob-mapping.htm", method = RequestMethod.POST)
    public String updateMapping(Map<String, Object> model, HttpServletRequest request) {
        List<GradeConverter> gradeConverters = gradeConverterRepository.findAll();
        Map<Integer, String> tradeMap = new HashMap<>();
        for(GradeConverter gradeConverter: gradeConverters) {
            tradeMap.put(gradeConverter.getTradeId(), gradeConverter.getTradeName());
        }
        int millId = Integer.valueOf(request.getParameter("millId"));
        Integer tradeId = null;
        try {
            tradeId = Integer.valueOf(request.getParameter("tradeId"));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            String tradeName = tradeMap.get(tradeId);
            GradeConverter item = gradeConverterRepository.findByGradeMaster_Id(millId);
            item.setTradeId(tradeId);
            item.setTradeName(tradeName);
            gradeConverterRepository.save(item);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return "redirect:/show-fob-mapping.htm";
    }
    
    @RequestMapping(value = "update-fob.htm", method = RequestMethod.POST)
    public String updateFob(Map<String, Object> model, HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        float value = Float.valueOf((String) request.getParameter("diff"));
        MarketFobDiff marketFobDiff = marketFobDiffRepository.findOne(id);
        marketFobDiff.setLastDate(new Date());
        marketFobDiff.setLastDiff(marketFobDiff.getDiff());
        marketFobDiff.setDiff(value);
        marketFobDiff.setUser((User) request.getSession().getAttribute("user"));
        return "redirect:/daily-basic.htm";
    }
    
    @RequestMapping(value = "init_fob_diff.htm", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> initFob(Map<String, Object> model) throws JSONException {
        List<GradeMaster> grades = gradeRepository.findAll();
        User minhUser = new User(10);
        for(GradeMaster gradeMaster: grades) {
            MarketFobDiff marketFobDiff = new MarketFobDiff();
            marketFobDiff.setGradeMaster(gradeMaster);
            marketFobDiff.setLastDate(new Date());
            marketFobDiff.setDiff(0.0f);
            marketFobDiff.setLastDiff(0.0f);
            marketFobDiff.setUpdatedDate(new Date());
            marketFobDiff.setLog("");
            marketFobDiff.setUser(minhUser);
            marketFobDiffRepository.save(marketFobDiff);
            
        }
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "init_terminal_month.htm", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> initTerminalMonthYear(Map<String, Object> model) throws JSONException {
        dailyBasicAppService.createNextTerminalMonthsIfNotExisted();
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "override_market_fob_with_value_from_trade.htm", method = RequestMethod.POST)
    public String overrideWithTrade(Map<String, Object> model, HttpServletRequest request) throws JSONException {
        JSONArray jSONArray = new JSONArray(marketFobDiffHistoryRepository.findTopByOrderByIdDesc().getValue());
        List<GradeConverter> gradeConverters = gradeConverterRepository.findAll();
        User currentUser = getUser(request);
        List<MarketFobDiff> marketFobDiffs = marketFobDiffRepository.findAll();
        Map<Integer, Float> tradeMap = getTradeMap(gradeConverters, jSONArray);
        Map<Integer, MarketFobDiff> marketMap = getMarketMap(marketFobDiffs);
        for(Map.Entry<Integer, Float> entry: tradeMap.entrySet()) {
            if(marketMap.get(entry.getKey()) != null) {
                MarketFobDiff marketFobDiff = marketMap.get(entry.getKey());
                marketFobDiff.setLastDiff(marketFobDiff.getDiff());
                marketFobDiff.setLastDate(marketFobDiff.getUpdatedDate());
                marketFobDiff.setDiff(entry.getValue());
                marketFobDiff.setLastDate(new Date());
                marketFobDiff.setUser(currentUser);
            }
        }
        marketFobDiffRepository.save(marketFobDiffs);
        return "redirect:/daily-basic.htm";
    }
    
    private Map<Integer, MarketFobDiff> getMarketMap(List<MarketFobDiff> marketFobDiffs) {
        Map<Integer, MarketFobDiff> map = new HashMap<>();
        for(MarketFobDiff marketFobDiff: marketFobDiffs) {
            map.put(marketFobDiff.getGradeMaster().getId(), marketFobDiff);
        }
        return map;
    }
    
    private Map<Integer, Float> getTradeMap(List<GradeConverter> gradeConverters, JSONArray tradeFobArray) throws JSONException {
        Map<Integer, Float> map = new HashMap<>();
        for(GradeConverter gc: gradeConverters) {
            for(int i = 0; i < tradeFobArray.length(); i++) {
                JSONObject obj = tradeFobArray.getJSONObject(i);
                if(gc.getTradeId() != null && obj.getInt("gradeMaster") == gc.getTradeId()) {
                    map.put(gc.getGradeMaster().getId(), (float) obj.getDouble("diff"));
                }
            }
        }
        return map;
    }
        
    @RequestMapping(value = "market-fob-diff.json", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getHistories() throws JSONException {
        JSONArray jSONArray = new JSONArray(marketFobDiffHistoryRepository.findTopByOrderByIdDesc().getValue());
        List<GradeMaster> grades = gradeRepository.findAll();
        List<GradeConverter> gradeConverters = gradeConverterRepository.findAll();
        JSONArray result = new JSONArray();
        for(GradeConverter gc: gradeConverters) {
            for(int i = 0; i < jSONArray.length(); i++) {
                JSONObject obj = jSONArray.getJSONObject(i);
                if(gc.getTradeId() != null && obj.getInt("gradeMaster") == gc.getTradeId()) {
                    GradeMaster gradeMaster = gc.getGradeMaster();
                    obj.put("gradeMaster__id", gradeMaster.getId());
                    obj.put("gradeMaster__name", gradeMaster.getName());
                    result.put(obj);
                    break;
                }
            }
        }
        
        JSONArray remaining = new JSONArray();
        for(int  i = 0; i < grades.size(); i++) {
            boolean notFound = true;
            for(int j = 0; j < result.length(); j++) {
                System.out.println("compare: " + result.getJSONObject(j).getInt("gradeMaster__id") + ": " + grades.get(i).getId());
                if(result.getJSONObject(j).getInt("gradeMaster__id") == grades.get(i).getId()) {
                    notFound = false;
                }
                continue;
            }
            if(notFound) {
                remaining.put(grades.get(i).getId());
            }
        }
        JSONObject object = new JSONObject();
        object.put("remaining", remaining);
        object.put("result", result);
        return new ResponseEntity<>(object.toString(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "daily-basic-histories.json", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getDailyBasisHistories() throws JSONException {
        JSONArray jSONArray = new JSONArray(clientDailyBasisHistoryRepository.findTopByOrderByIdDesc().getValue());
        return new ResponseEntity<>(jSONArray.toString(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "override_daily_basis_with_value_from_trade.htm", method = RequestMethod.POST)
    public String saveDailyBasisFromTrade(HttpServletRequest request) throws JSONException {
        JSONArray jSONArray = new JSONArray(clientDailyBasisHistoryRepository.findTopByOrderByIdDesc().getValue());
        for(int i = 0; i < jSONArray.length(); i++) {
            JSONObject trade = jSONArray.getJSONObject(i);
            DailyBasis dailyBasis = dailyBasisRepository.findFirstByTerminalMonth(trade.getString("terminalMonth"));
            if(dailyBasis != null) {
                dailyBasis.setPreviousBasis(dailyBasis.getCurrentBasis());
                dailyBasis.setPreviousDate(dailyBasis.getUpdatedDate());
                dailyBasis.setUser(getUser(request));
                dailyBasis.setCurrentBasis((float) trade.getDouble("currentBasis"));
                dailyBasis.setLiffeHigh((float) trade.getDouble("liffeHigh"));
                dailyBasis.setLiffeLow((float) trade.getDouble("liffeLow"));
                dailyBasis.setUpdatedDate(new Date());
            }
        }
        return "redirect:/daily-basic.htm";
    }
    
    private User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
}
