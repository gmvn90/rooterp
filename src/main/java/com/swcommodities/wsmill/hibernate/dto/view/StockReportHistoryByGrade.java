package com.swcommodities.wsmill.hibernate.dto.view;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.StockReportHistory;

/**
 * Created by macOS on 2/16/17.
 */
public class StockReportHistoryByGrade {
    private int gradeId;
    private String gradeName;
    private List<StockReportHistory> listDates;

    public StockReportHistoryByGrade() {
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<StockReportHistory> getListDates() {
        return listDates;
    }

    public void setListDates(List<StockReportHistory> listDates) {
        this.listDates = listDates;
    }
}
