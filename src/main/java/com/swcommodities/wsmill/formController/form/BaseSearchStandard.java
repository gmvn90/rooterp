/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.Date;

/**
 *
 * @author macOS
 */
public class BaseSearchStandard {

    private static final int DEFAULT_EMPTY = -1;

    private Integer clientId = DEFAULT_EMPTY;
    private Date startDate;
    private Date endDate; 
    private String searchText; 
    private Integer page;
    private Integer perPage;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getPage() {
        return page == null ? 0: page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage == null ? 20: perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
    
    
    
}
