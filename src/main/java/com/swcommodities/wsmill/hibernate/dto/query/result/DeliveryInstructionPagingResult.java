package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.cache.DICache;

public class DeliveryInstructionPagingResult {
	private List<DICache> results;
	private String pagination;
	private InstructionResult instructionResult;
	private int perPage = 20;
	private int page = 1;
	private int totalPages = 0;
	private int totalRecords = 0;
	
	public List<DICache> getResults() {
		return results;
	}
	public void setResults(List<DICache> results) {
		this.results = results;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	public InstructionResult getInstructionResult() {
		return instructionResult;
	}
	public void setInstructionResult(InstructionResult instructionResult) {
		this.instructionResult = instructionResult;
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
