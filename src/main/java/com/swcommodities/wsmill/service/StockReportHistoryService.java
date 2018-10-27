package com.swcommodities.wsmill.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.StockReportHistory;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.dto.view.StockReportHistoryByGrade;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.StockReportHistoryRepository;
import com.swcommodities.wsmill.repository.WeightNoteReceiptRepository;

/**
 * Created by macOS on 2/14/17.
 */
@Service
public class StockReportHistoryService {
    @Autowired
    StockReportHistoryRepository stockReportHistoryRepository;
    @Autowired
    WeightNoteReceiptRepository weightNoteReceiptRepository;
    @Autowired
    GradeRepository gradeRepository;

    public List<StockReportHistoryByGrade> getStockReportHistoryDayView(int clientId) throws ParseException {

        List<StockReportHistory> rawDeliveries = stockReportHistoryRepository.getDeliveryStockReport(clientId);
        List<StockReportHistory> rawShippings = stockReportHistoryRepository.getShippingStockReport(clientId);
        List<RefList> grades = gradeRepository.findRefListWithoutNonactive();

        //Merge delivery and shipping item & add merged item and delivery item into raw result
        List<StockReportHistory> rawResults = new ArrayList<>();
        for (StockReportHistory delivery : rawDeliveries) {
            boolean flag = true;
            for (StockReportHistory shipping : rawShippings) {

                if (delivery.getGradeId().equals(shipping.getGradeId())) {
                    if (delivery.getFirstDate().compareTo(shipping.getLoadingDate()) == 0) {
                        flag = false;
                        StockReportHistory rawResult = new StockReportHistory();
                        rawResult.setDeposit(delivery.getDeposit());
                        rawResult.setWithdraw(shipping.getWithdraw());
                        rawResult.setShowDate(delivery.getFirstDate());
                        rawResult.setGradeId(delivery.getGradeId());
                        rawResults.add(rawResult);
                        break;
                    }
                }

            }
            if (flag) {
                StockReportHistory rawResult = new StockReportHistory();
                rawResult.setDeposit(delivery.getDeposit());
                rawResult.setWithdraw((double) 0);
                rawResult.setShowDate(delivery.getFirstDate());
                rawResult.setGradeId(delivery.getGradeId());
                rawResults.add(rawResult);
            }
        }

        //Add shipping item (exclude merged shipping item) into raw result
        for (StockReportHistory shipping : rawShippings) {
            StockReportHistory rawResult = new StockReportHistory();
            boolean flag = true;
            for (StockReportHistory delivery : rawDeliveries) {
                if (shipping.getGradeId().equals(delivery.getGradeId())) {
                    if (shipping.getLoadingDate().compareTo(delivery.getFirstDate()) == 0 ) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                rawResult.setDeposit((double) 0);
                rawResult.setWithdraw(shipping.getWithdraw());
                rawResult.setShowDate(shipping.getLoadingDate());
                rawResult.setGradeId(shipping.getGradeId());
                rawResults.add(rawResult);
            }
        }

        //Group raw result by grade and add to final result
        List<StockReportHistoryByGrade> finalResultFull = new ArrayList<>();
        for (RefList grade : grades) {
            List<StockReportHistory> listResultForEachGrade = new ArrayList<>();
            for (StockReportHistory srh : rawResults) {
                if (grade.getId().equals(srh.getGradeId())) {
                    listResultForEachGrade.add(srh);
                }
            }
            StockReportHistoryByGrade stockReportHistoryByGrade = new StockReportHistoryByGrade();
            stockReportHistoryByGrade.setGradeId(grade.getId());
            stockReportHistoryByGrade.setGradeName(grade.getValue());
            stockReportHistoryByGrade.setListDates(listResultForEachGrade);
            finalResultFull.add(stockReportHistoryByGrade);
        }

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateStr = sdf.format(nowDate);

        List<StockReportHistoryByGrade> finalResultFullGrouped = new ArrayList<>();
        for (StockReportHistoryByGrade srhbg: finalResultFull) {
            List<StockReportHistory> srhListNew = new ArrayList<>();
            StockReportHistory srhFirst = new StockReportHistory();
            double firstDeposit = 0, firstWithdraw = 0;
            for(StockReportHistory srh : srhbg.getListDates()) {
                String srhShowDateStr = sdf.format(srh.getShowDate());
                if (sdf.parse(srhShowDateStr).compareTo(sdf.parse(nowDateStr)) <= 0) {
                    firstDeposit += (srh.getDeposit() != null ? srh.getDeposit() : 0);
                    firstWithdraw += (srh.getWithdraw() != null ? srh.getWithdraw() : 0);
                }
            }
            srhFirst.setShowDate(new Date());
            srhFirst.setDeposit(firstDeposit);
            srhFirst.setWithdraw(firstWithdraw);
            srhListNew.add(srhFirst);
            for(StockReportHistory srh : srhbg.getListDates()) {
                String srhShowDateStr = sdf.format(srh.getShowDate());
                if (sdf.parse(srhShowDateStr).compareTo(sdf.parse(nowDateStr)) > 0) {
                    srhListNew.add(srh);
                }
            }
            StockReportHistoryByGrade srhbgNew = new StockReportHistoryByGrade();
            srhbgNew.setGradeName(srhbg.getGradeName());
            srhbgNew.setGradeId(srhbg.getGradeId());
            srhbgNew.setListDates(srhListNew);
            finalResultFullGrouped.add(srhbgNew);
        }

        List<StockReportHistoryByGrade> srhbgListNew = new ArrayList<>();

        //improve
        Map<String, Double> listCurrentTons = weightNoteReceiptRepository.getCurrentTonByClientAndGrade(clientId);
        for (StockReportHistoryByGrade srhbg : finalResultFullGrouped) {
            StockReportHistoryByGrade srhbgNew = new StockReportHistoryByGrade();
            List<StockReportHistory> srhListNew = new ArrayList<>();
            Date uptoDate = new Date();
            double total = (listCurrentTons.get(srhbg.getGradeName()) != null ? listCurrentTons.get(srhbg.getGradeName()) : 0 );
            double grandDeposit = total;
            double grandWithdraw = 0;

            for (int i = 0; i <= 32; i++) {
                if (i == 0) {
                    StockReportHistory srhNew = new StockReportHistory();
                    srhNew.setWithdraw((double) 0);
                    srhNew.setDeposit(total);
                    srhNew.setTotal(total);
                    srhListNew.add(srhNew);
                }
                if (0 < i && i < 32){
                    String uptoDateStr = sdf.format(uptoDate);
                    StockReportHistory srhNew = new StockReportHistory();
                    for(StockReportHistory srh : srhbg.getListDates()) {
                        srhNew.setShowDate(uptoDate);
                        srhNew.setWithdraw((double) 0);
                        srhNew.setDeposit((double) 0);
                        String srhShowDateStr = sdf.format(srh.getShowDate());
                        if (sdf.parse(srhShowDateStr).compareTo(sdf.parse(uptoDateStr)) == 0) {
                            srhNew.setWithdraw(srh.getWithdraw());
                            srhNew.setDeposit(srh.getDeposit());
                            break;
                        }
                    }
                    total += (srhNew.getDeposit() - srhNew.getWithdraw());
                    grandDeposit += srhNew.getDeposit();
                    grandWithdraw += srhNew.getWithdraw();
                    srhNew.setTotal(srhNew.getDeposit() - srhNew.getWithdraw());

                    srhListNew.add(srhNew);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(uptoDate);
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    uptoDate = cal.getTime();
                }
                if (i == 32) {
                    StockReportHistory srhNew = new StockReportHistory();
                    srhNew.setDeposit(grandDeposit);
                    srhNew.setWithdraw(grandWithdraw);
                    srhNew.setTotal(srhNew.getDeposit() - srhNew.getWithdraw());
                    srhListNew.add(srhNew);
                }

            }
            srhbgNew.setListDates(srhListNew);
            srhbgNew.setGradeName(srhbg.getGradeName());
            srhbgNew.setGradeId(srhbg.getGradeId());
            srhbgListNew.add(srhbgNew);
        }

        StockReportHistoryByGrade balanceGrade = new StockReportHistoryByGrade();

        List<StockReportHistory> balanceSrhList = new ArrayList<>();
        double balanceTotal = 0;
        for (int i = 0; i <= 32; i++) {
            StockReportHistory balanceSrh = new StockReportHistory();
            double balanceDeposit = 0;
            double balanceWithdraw = 0;
            if (i < 32) {
                for (StockReportHistoryByGrade srhbg: srhbgListNew) {
                    StockReportHistory srh = srhbg.getListDates().get(i);
                    balanceDeposit += srh.getDeposit();
                    balanceWithdraw += srh.getWithdraw();
                    balanceTotal += srh.getTotal();
                }
                balanceSrh.setDeposit(balanceDeposit);
                balanceSrh.setWithdraw(balanceWithdraw);
                balanceSrh.setTotal(balanceTotal);
                balanceSrhList.add(balanceSrh);
            } else {
                for (StockReportHistoryByGrade srhbg: srhbgListNew) {
                    StockReportHistory srh = srhbg.getListDates().get(i);
                    balanceDeposit += srh.getDeposit();
                    balanceWithdraw += srh.getWithdraw();
                }
                balanceSrh.setDeposit(balanceDeposit);
                balanceSrh.setWithdraw(balanceWithdraw);
                balanceSrh.setTotal(balanceDeposit - balanceWithdraw);
                balanceSrhList.add(balanceSrh);
            }
        }
        balanceGrade.setListDates(balanceSrhList);
        balanceGrade.setGradeName("Balance");

        srhbgListNew.add(balanceGrade);
        return srhbgListNew;
    }

    public List<StockReportHistoryByGrade> getStockReportHistoryMonthView(int clientId) throws ParseException {

        List<StockReportHistory> rawDeliveries = stockReportHistoryRepository.getDeliveryStockReportInMonth(clientId);
        List<StockReportHistory> rawShippings = stockReportHistoryRepository.getShippingStockReportInMonth(clientId);
        List<RefList> grades = gradeRepository.findRefListWithoutNonactive();

        //Merge delivery and shipping item & add merged item and delivery item into raw result
        List<StockReportHistory> rawResults = new ArrayList<>();
        for (StockReportHistory delivery : rawDeliveries) {
            boolean flag = true;
            for (StockReportHistory shipping : rawShippings) {

                if (delivery.getGradeId().equals(shipping.getGradeId())) {
                    Calendar deliveryDate = Calendar.getInstance();
                    deliveryDate.setTime(delivery.getFirstDate());
                    Calendar shippingDate = Calendar.getInstance();
                    shippingDate.setTime(shipping.getLoadingDate());
                    if (deliveryDate.get(Calendar.MONTH) == shippingDate.get(Calendar.MONTH)) {
                        flag = false;
                        StockReportHistory rawResult = new StockReportHistory();
                        rawResult.setDeposit(delivery.getDeposit());
                        rawResult.setWithdraw(shipping.getWithdraw());
                        rawResult.setShowDate(delivery.getFirstDate());
                        rawResult.setGradeId(delivery.getGradeId());
                        rawResults.add(rawResult);
                        break;
                    }
                }

            }
            if (flag) {
                StockReportHistory rawResult = new StockReportHistory();
                rawResult.setDeposit(delivery.getDeposit());
                rawResult.setWithdraw((double) 0);
                rawResult.setShowDate(delivery.getFirstDate());
                rawResult.setGradeId(delivery.getGradeId());
                rawResults.add(rawResult);
            }
        }

        //Add shipping item (exclude merged shipping item) into raw result
        for (StockReportHistory shipping : rawShippings) {
            StockReportHistory rawResult = new StockReportHistory();
            boolean flag = true;
            for (StockReportHistory delivery : rawDeliveries) {

                if (shipping.getGradeId().equals(delivery.getGradeId())) {
                    Calendar shippingDate = Calendar.getInstance();
                    shippingDate.setTime(shipping.getLoadingDate());
                    Calendar deliveryDate = Calendar.getInstance();
                    deliveryDate.setTime(delivery.getFirstDate());
                    if (shippingDate.get(Calendar.MONTH) == deliveryDate.get(Calendar.MONTH) ) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                rawResult.setDeposit((double) 0);
                rawResult.setWithdraw(shipping.getWithdraw());
                rawResult.setShowDate(shipping.getLoadingDate());
                rawResult.setGradeId(shipping.getGradeId());
                rawResults.add(rawResult);
            }
        }

        //Group raw result by grade and add to final result
        List<StockReportHistoryByGrade> finalResultFull = new ArrayList<>();
        for (RefList grade : grades) {
            List<StockReportHistory> listResultForEachGrade = new ArrayList<>();
            for (StockReportHistory srh : rawResults) {
                if (grade.getId().equals(srh.getGradeId())) {
                    listResultForEachGrade.add(srh);
                }
            }
            StockReportHistoryByGrade stockReportHistoryByGrade = new StockReportHistoryByGrade();
            stockReportHistoryByGrade.setGradeId(grade.getId());
            stockReportHistoryByGrade.setGradeName(grade.getValue());
            stockReportHistoryByGrade.setListDates(listResultForEachGrade);
            finalResultFull.add(stockReportHistoryByGrade);
        }


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int nowMonth = cal.get(Calendar.MONTH);
        int nowYear = cal.get(Calendar.YEAR);

        List<StockReportHistoryByGrade> finalResultFullGrouped = new ArrayList<>();
        for (StockReportHistoryByGrade srhbg: finalResultFull) {
            List<StockReportHistory> srhListNew = new ArrayList<>();
            StockReportHistory srhFirst = new StockReportHistory();
            double firstDeposit = 0, firstWithdraw = 0;
            for(StockReportHistory srh : srhbg.getListDates()) {
                Calendar showCal = Calendar.getInstance();
                showCal.setTime(srh.getShowDate());
                int srhMonth = showCal.get(Calendar.MONTH);
                int srhYear = showCal.get(Calendar.YEAR);
                if (srhYear < nowYear) {
                    firstDeposit += (srh.getDeposit() != null ? srh.getDeposit() : 0);
                    firstWithdraw += (srh.getWithdraw() != null ? srh.getWithdraw() : 0);
                } else if (srhYear == nowYear) {
                    if (srhMonth <= nowMonth) {
                        firstDeposit += (srh.getDeposit() != null ? srh.getDeposit() : 0);
                        firstWithdraw += (srh.getWithdraw() != null ? srh.getWithdraw() : 0);
                    }
                }
            }
            srhFirst.setShowDate(new Date());
            srhFirst.setDeposit(firstDeposit);
            srhFirst.setWithdraw(firstWithdraw);
            srhListNew.add(srhFirst);
            for(StockReportHistory srh : srhbg.getListDates()) {
                Calendar showCal = Calendar.getInstance();
                showCal.setTime(srh.getShowDate());
                int srhMonth = showCal.get(Calendar.MONTH);
                int srhYear = showCal.get(Calendar.YEAR);
                if (srhYear > nowYear) {
                    srhListNew.add(srh);
                } else if (srhYear == nowYear) {
                    if (srhMonth > nowMonth) {
                        srhListNew.add(srh);
                    }
                }
            }
            StockReportHistoryByGrade srhbgNew = new StockReportHistoryByGrade();
            srhbgNew.setGradeName(srhbg.getGradeName());
            srhbgNew.setGradeId(srhbg.getGradeId());
            srhbgNew.setListDates(srhListNew);
            finalResultFullGrouped.add(srhbgNew);
        }

        List<StockReportHistoryByGrade> srhbgListNew = new ArrayList<>();

        Map<String, Double> listCurrentTons = weightNoteReceiptRepository.getCurrentTonByClientAndGrade(clientId);
        for (StockReportHistoryByGrade srhbg : finalResultFullGrouped) {
            StockReportHistoryByGrade srhbgNew = new StockReportHistoryByGrade();
            List<StockReportHistory> srhListNew = new ArrayList<>();
            Date uptoDate = new Date();


            double total = (listCurrentTons.get(srhbg.getGradeName()) != null ? listCurrentTons.get(srhbg.getGradeName()) : 0 );
            double grandDeposit = total;
            double grandWithdraw = 0;

            for (int i = 0; i <= 13; i++) {
                Calendar calTemp = Calendar.getInstance();
                calTemp.setTime(uptoDate);
                int uptoMonth = calTemp.get(Calendar.MONTH);
                if (i == 0) {
                    StockReportHistory srhNew = new StockReportHistory();
                    srhNew.setWithdraw((double) 0);
                    srhNew.setDeposit(total);
                    srhNew.setTotal(total);
                    srhListNew.add(srhNew);
                }
                if (0 < i && i < 13){
                    StockReportHistory srhNew = new StockReportHistory();
                    for(StockReportHistory srh : srhbg.getListDates()) {
                        srhNew.setShowDate(uptoDate);
                        srhNew.setWithdraw((double) 0);
                        srhNew.setDeposit((double) 0);
                        Calendar srhCal = Calendar.getInstance();
                        srhCal.setTime(srh.getShowDate());
                        int srhMonth = srhCal.get(Calendar.MONTH);
                        if (srhMonth == uptoMonth) {
                            srhNew.setWithdraw(srh.getWithdraw());
                            srhNew.setDeposit(srh.getDeposit());
                            break;
                        }
                    }
                    total += (srhNew.getDeposit() - srhNew.getWithdraw());
                    grandDeposit += srhNew.getDeposit();
                    grandWithdraw += srhNew.getWithdraw();
                    srhNew.setTotal(srhNew.getDeposit() - srhNew.getWithdraw());

                    srhListNew.add(srhNew);
                    Calendar plusCal = Calendar.getInstance();
                    plusCal.setTime(uptoDate);
                    plusCal.add(Calendar.MONTH, 1);
                    uptoDate = plusCal.getTime();
                }
                if (i == 13) {
                    StockReportHistory srhNew = new StockReportHistory();
                    srhNew.setDeposit(grandDeposit);
                    srhNew.setWithdraw(grandWithdraw);
                    srhNew.setTotal(srhNew.getDeposit() - srhNew.getWithdraw());
                    srhListNew.add(srhNew);
                }

            }
            srhbgNew.setListDates(srhListNew);
            srhbgNew.setGradeName(srhbg.getGradeName());
            srhbgNew.setGradeId(srhbg.getGradeId());
            srhbgListNew.add(srhbgNew);
        }

        StockReportHistoryByGrade balanceGrade = new StockReportHistoryByGrade();

        List<StockReportHistory> balanceSrhList = new ArrayList<>();
        double balanceTotal = 0;
        for (int i = 0; i <= 13; i++) {
            StockReportHistory balanceSrh = new StockReportHistory();
            double balanceDeposit = 0;
            double balanceWithdraw = 0;
            if (i < 13) {
                for (StockReportHistoryByGrade srhbg: srhbgListNew) {
                    StockReportHistory srh = srhbg.getListDates().get(i);
                    balanceDeposit += srh.getDeposit();
                    balanceWithdraw += srh.getWithdraw();
                    balanceTotal += srh.getTotal();
                }
                balanceSrh.setDeposit(balanceDeposit);
                balanceSrh.setWithdraw(balanceWithdraw);
                balanceSrh.setTotal(balanceTotal);
                balanceSrhList.add(balanceSrh);
            } else {
                for (StockReportHistoryByGrade srhbg: srhbgListNew) {
                    StockReportHistory srh = srhbg.getListDates().get(i);
                    balanceDeposit += srh.getDeposit();
                    balanceWithdraw += srh.getWithdraw();
                }
                balanceSrh.setDeposit(balanceDeposit);
                balanceSrh.setWithdraw(balanceWithdraw);
                balanceSrh.setTotal(balanceDeposit - balanceWithdraw);
                balanceSrhList.add(balanceSrh);
            }

        }
        balanceGrade.setListDates(balanceSrhList);
        balanceGrade.setGradeName("Balance");

        srhbgListNew.add(balanceGrade);
        return srhbgListNew;
    }

    public HashMap<String, HashMap<Date, StockReportHistory>> getStockReportHistoryDayView_Map(int clientId, Date beginning, Map<String, Double> currentStock) throws ParseException {

        List<StockReportHistory> rawDeliveries = stockReportHistoryRepository.getDeliveryStockReport(clientId);
        List<StockReportHistory> rawShippings = stockReportHistoryRepository.getShippingStockReport(clientId);

        HashMap<String, HashMap<Date, StockReportHistory>> finalResult = new HashMap<String, HashMap<Date, StockReportHistory>>();
        for (StockReportHistory srh : rawDeliveries) {
            if(beginning.compareTo(srh.getFirstDate()) >= 0) {
                // so its data doesnt count
                if(finalResult.containsKey(srh.getGradeName())) {
                    HashMap<Date, StockReportHistory> item = (HashMap)finalResult.get(srh.getGradeName());
                    if(item.containsKey(beginning)) {
                        StockReportHistory stockReportHistory = item.get(beginning);
                        stockReportHistory.setDeposit(stockReportHistory.getDeposit() + srh.getDeposit());
                    }
                } else {
                    HashMap<Date, StockReportHistory> res = new HashMap<>();
                    res.put(beginning, srh);
                    finalResult.put(srh.getGradeName(), res);
                }
            } else {
                if(finalResult.containsKey(srh.getGradeName())) {
                    HashMap<Date, StockReportHistory> item = (HashMap)finalResult.get(srh.getGradeName());
                    item.put(srh.getFirstDate(), srh);
                } else {
                    HashMap<Date, StockReportHistory> res = new HashMap<>();
                    res.put(srh.getFirstDate(), srh);
                    finalResult.put(srh.getGradeName(), res);
                }
            }

        }

        for (StockReportHistory srh : rawShippings) {
            if(beginning.compareTo(srh.getLoadingDate()) >= 0) {
                // so its data doesnt count
                if(finalResult.containsKey(srh.getGradeName())) {
                    HashMap<Date, StockReportHistory> item = (HashMap)finalResult.get(srh.getGradeName());
                    if(item.containsKey(beginning)) {
                        StockReportHistory stockReportHistory = item.get(beginning);
                        stockReportHistory.setWithdraw(stockReportHistory.getWithdraw() + srh.getWithdraw());
                        stockReportHistory.setTotal((currentStock.get(srh.getGradeName()) != null ? currentStock.get(srh.getGradeName()) : 0)  + (srh.getDeposit() != null ? srh.getDeposit() : 0) - (srh.getWithdraw() != null ? srh.getWithdraw() : 0));
                    }
                } else {
                    HashMap<Date, StockReportHistory> res = new HashMap<>();
                    srh.setTotal((currentStock.get(srh.getGradeName()) != null ? currentStock.get(srh.getGradeName()) : 0) + (srh.getDeposit() != null ? srh.getDeposit() : 0) - (srh.getWithdraw() != null ? srh.getWithdraw() : 0));
                    res.put(beginning, srh);
                    finalResult.put(srh.getGradeName(), res);
                }
            } else {
                if(finalResult.containsKey(srh.getGradeName())) {

                    HashMap<Date, StockReportHistory> items = (HashMap)finalResult.get(srh.getGradeName());
                    Date maxDate = Collections.max(items.keySet());
                    StockReportHistory recentStock = items.get(maxDate);
                    srh.setTotal((recentStock.getTotal() != null ? recentStock.getTotal() : 0) + (srh.getDeposit() != null ? srh.getDeposit() : 0) - (srh.getWithdraw() != null ? srh.getWithdraw() : 0));
                    items.put(srh.getLoadingDate(), srh);
                } else {
                    srh.setTotal((currentStock.get(srh.getGradeName()) != null ? currentStock.get(srh.getGradeName()) : 0) + (srh.getTotal() != null ? srh.getTotal() : 0) - (srh.getWithdraw() != null ? srh.getWithdraw() : 0));
                    HashMap<Date, StockReportHistory> res = new HashMap<>();
                    res.put(srh.getLoadingDate(), srh);
                    finalResult.put(srh.getGradeName(), res);
                }
            }

        }

        return finalResult;
    }

}
