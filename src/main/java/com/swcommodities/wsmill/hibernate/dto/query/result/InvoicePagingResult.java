/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.Invoice;

/**
 *
 * @author macOS
 */
public class InvoicePagingResult {
    private List<Invoice> results;
	private String pagination;
	private int perPage = 20;
	private int page = 1;
	private int totalPages = 0;
	private int totalRecords = 0;
    private InvoiceAggregateInfo invoiceAggregateInfo;

    public List<Invoice> getResults() {
        return results;
    }

    public void setResults(List<Invoice> results) {
        this.results = results;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public InvoiceAggregateInfo getInvoiceAggregateInfo() {
        return invoiceAggregateInfo;
    }

    public void setInvoiceAggregateInfo(InvoiceAggregateInfo invoiceAggregateInfo) {
        this.invoiceAggregateInfo = invoiceAggregateInfo;
    }
    
    
    
}
