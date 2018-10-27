/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.threadExecutor;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.swcommodities.wsmill.bo.StockHistoricalService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.StockHistories;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
public class Threads {
    private WeightNoteService weightNoteService;
    private StockHistoricalService  stockHistoricalService;
    
    public void setWeightNoteService(WeightNoteService weightNoteService) {
        this.weightNoteService = weightNoteService;
    }

    public void setStockHistoricalService(StockHistoricalService stockHistoricalService) {
        this.stockHistoricalService = stockHistoricalService;
    }

//	@Scheduled(cron = "0 55 23 ? * *")
//    public void saveStockHistoricalDaily(){
//        System.out.println("Begin save stock historical");
//        stockHistoricalService.saveStockHistoricalReport();
//    }
    
    @Scheduled(cron = "0 55 23 ? * *")
    public void saveDailyStockHistory(){
        System.out.println("Begin save stock historical");
        JSONObject json = new JSONObject(stockHistoricalService.getStockReport(-1, -1, -1, -1,false));
        String content = json.toString();
        long dateCode = Common.getDateCode(new Date());
        StockHistories stock = new StockHistories();
        stock.setContent(content);
        stock.setDateCode(dateCode);
        stockHistoricalService.updateHistory(stock);
    }
    
//    @Scheduled(cron = "0 55 23 ? * *")
//    public void saveDailyStockInvoice(){
//        System.out.println("Begin save stock invoice");
//        invoiceService.getStockInvoice();
//    }
//
//    @Scheduled(cron = "0 55 23 ? * *")
//    public void saveDailyShippedInvoice(){
//        System.out.println("Begin save shipped invoice");
//        invoiceService.getShippedInvoice();
//    }
//
//    @Scheduled(cron = "0 55 23 ? * *")
//    public void saveDailyProcessingInvoice(){
//        System.out.println("Begin save processing invoice");
//        invoiceService.saveProcessingInvoice();
//    }
}
