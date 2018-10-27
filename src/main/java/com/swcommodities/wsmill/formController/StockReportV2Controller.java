package com.swcommodities.wsmill.formController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swcommodities.wsmill.hibernate.dto.StockReportHistory;
import com.swcommodities.wsmill.hibernate.dto.view.StockReportHistoryByGrade;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.WeightNoteReceiptRepository;
import com.swcommodities.wsmill.service.StockReportHistoryService;

/**
 * Created by dunguyen on 7/18/16.
 */

@Controller
@Transactional
public class StockReportV2Controller {
    @Autowired private StockReportHistoryService stockReportHistoryService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyTypeMasterRepository companyTypeMasterRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    WeightNoteReceiptRepository weightNoteReceiptRepository;
    private final String balanceString = "Balance";

    @RequestMapping(method= RequestMethod.GET, value = "stock-report-v2.htm")
    public String getStockReportV2(Map<String, Object> model, @RequestParam(required = false, name = "historyDate") Date historyDate,
                                   @RequestParam(required = false, name = "clientId") Integer clientId,
                                   @RequestParam(required = false, name = "viewTypeId") Integer viewTypeId) throws ParseException {

        if(clientId == null) {
            clientId = -1;
        }

        if(viewTypeId == null) {
            viewTypeId = 1;
        }

        List<StockReportHistoryByGrade> grades = new ArrayList<>();
        if (viewTypeId == 0) {
            grades = stockReportHistoryService.getStockReportHistoryDayView(clientId);
        } else {
            grades = stockReportHistoryService.getStockReportHistoryMonthView(clientId);
        }
        
        Iterator<StockReportHistoryByGrade> it = grades.iterator();
        while(it.hasNext()) {
            StockReportHistoryByGrade grade = it.next();
            List<StockReportHistory> listDates = grade.getListDates();
            boolean hasItem = false;
            for(StockReportHistory his: listDates) {
                if(his.getWithdraw() != 0 || his.getDeposit() != 0) {
                    hasItem = true;
                    break;
                }
            }
            if(! hasItem) {
                it.remove();
            }
        }
        
        model.put("grades", grades);

        List<Date> listDates = new ArrayList<>();
        Date uptoDate = new Date();
        int width = 1700;
        String title = "Stock report monthly";
        if (viewTypeId == 0) {
            title = "Stock report daily";
            width = 2200;
            for (int i = 1; i <= 31; i++) {
                listDates.add(uptoDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(uptoDate);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                uptoDate = cal.getTime();
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                listDates.add(uptoDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(uptoDate);
                cal.add(Calendar.MONTH, 1);
                uptoDate = cal.getTime();
            }
        }
        
        int widthGrade = 300;
        int widthStock = 150;
        
        int totalColumns = listDates.size() + 4; // dates + grade(2) + stock + total
        int table_width = width + 30 + 7 * (totalColumns - 1) + 30; // 30: margin/ padding 7: td margin + border + notknowfornow
        ///
        
        
        model.put("listDates", listDates);
        model.put("viewTypeId", viewTypeId);
        model.put("totalColumns", totalColumns);
        model.put("width", table_width);
        model.put("eachRowWidth", (width - widthGrade - widthStock) / (totalColumns - 2));
        model.put("widthGrade", widthGrade);
        model.put("widthStock", widthStock);
        model.put("title", title);

        return "stock-report-v2";
    }

}
