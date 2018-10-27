package com.swcommodities.wsmill.hibernate.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by macOS on 2/14/17.
 */
@Entity
@Table(name = "stock_report_history")
public class StockReportHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Double deposit;
    private Double withdraw;
    private Date firstDate;
    private Date loadingDate;
    private Integer gradeId;
    private String gradeName;
    private Integer clientId;
    private Date historyDate;
    private Double total;
    @Transient
    private Date showDate;

    public StockReportHistory() {
    }

    public StockReportHistory(Double deposit, Date firstDate, Integer gradeId, String gradeName) {
        this.deposit = deposit;
        this.firstDate = firstDate;
        this.gradeId = gradeId;
        this.gradeName = gradeName;
    }

    public StockReportHistory(Double withdraw, Date loadingDate, String gradeName, Integer gradeId) {
        this.withdraw = withdraw;
        this.loadingDate = loadingDate;
        this.gradeName = gradeName;
        this.gradeId = gradeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDeposit() {
        if (deposit == null) {
            return 0.0;
        }
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getWithdraw() {
        if (withdraw == null) {
            return 0.0;
        }
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Double getTotal() {
        if (total == null) {
            return 0.0;
        }
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDate() {
        if (firstDate == null) {
            return loadingDate;
        }
        return firstDate;
    }

}
