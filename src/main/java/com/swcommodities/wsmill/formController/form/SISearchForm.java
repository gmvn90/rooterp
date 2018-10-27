/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author macOS
 */
public class SISearchForm {
    private static final int DEFAULT_EMPTY = -1;
    
    private Integer pageNo = 0;
    private Integer perPage = 20;
    private Integer clientId = DEFAULT_EMPTY;
    private Integer buyerId = DEFAULT_EMPTY;
    private Integer gradeId = DEFAULT_EMPTY;
    private CompletionStatus completionStatus = CompletionStatus.PENDING;
    private CompletionStatus shipmentStatus = CompletionStatus.NO_STATUS;
    private String txtSearch = "";
    private Date startDate;
    private Date endDate;
    
    private Map<CompletionStatus, String> completionStatuses = CompletionStatus.getAll();

    public static class Builder {

        private Integer pageNo;
        private Integer perPage;
        private Integer clientId;
        private Integer buyerId;
        private Integer gradeId;
        private CompletionStatus completionStatus;
        private CompletionStatus shipmentStatus;
        private String txtSearch;
        private Date startDate;
        private Date endDate;

        private Builder() {
        }

        public Builder pageNo(final Integer value) {
            this.pageNo = value;
            return this;
        }

        public Builder perPage(final Integer value) {
            this.perPage = value;
            return this;
        }

        public Builder clientId(final Integer value) {
            this.clientId = value;
            return this;
        }

        public Builder buyerId(final Integer value) {
            this.buyerId = value;
            return this;
        }

        public Builder gradeId(final Integer value) {
            this.gradeId = value;
            return this;
        }

        public Builder completionStatus(final CompletionStatus value) {
            this.completionStatus = value;
            return this;
        }

        public Builder shipmentStatus(final CompletionStatus value) {
            this.shipmentStatus = value;
            return this;
        }

        public Builder txtSearch(final String value) {
            this.txtSearch = value;
            return this;
        }

        public Builder startDate(final Date value) {
            this.startDate = value;
            return this;
        }

        public Builder endDate(final Date value) {
            this.endDate = value;
            return this;
        }

        public SISearchForm build() {
            return new com.swcommodities.wsmill.formController.form.SISearchForm(pageNo, perPage, clientId, buyerId, gradeId, completionStatus, shipmentStatus, txtSearch, startDate, endDate);
        }
    }

    public static SISearchForm.Builder builder() {
        return new SISearchForm.Builder();
    }

    private SISearchForm(final Integer pageNo, final Integer perPage, final Integer clientId, final Integer buyerId, final Integer gradeId, final CompletionStatus completionStatus, final CompletionStatus shipmentStatus, final String txtSearch, final Date startDate, final Date endDate) {
        this.pageNo = pageNo != null ? pageNo : 0;
        this.perPage = perPage != null ? perPage: 20;
        this.clientId = clientId != null ? clientId: DEFAULT_EMPTY;
        this.buyerId = buyerId != null ? buyerId: DEFAULT_EMPTY;
        this.gradeId = gradeId != null ? gradeId: DEFAULT_EMPTY;
        this.completionStatus = completionStatus != null ? completionStatus: CompletionStatus.PENDING;
        this.shipmentStatus = shipmentStatus;
        this.txtSearch = txtSearch != null ? txtSearch : "";
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public SISearchForm() {}

    public static int getDEFAULT_EMPTY() {
        return DEFAULT_EMPTY;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public CompletionStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public String getTxtSearch() {
        return txtSearch;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public void setShipmentStatus(CompletionStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public void setTxtSearch(String txtSearch) {
        this.txtSearch = txtSearch;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<CompletionStatus, String> getCompletionStatuses() {
        return completionStatuses;
    }
    
    
    
}
