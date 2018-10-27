package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;

public class SampleSentPagingResult {
	private List<SampleSentCache> results;
	private String pagination;
	private int perPage = 20;
	private int page = 1;
	private int totalPages = 0;
	private int totalRecords = 0;
	
	public List<SampleSentCache> getResults() {
		return results;
	}
	public void setResults(List<SampleSentCache> results) {
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
	

	
}
