/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceCalculationResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceCardViewResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoicePagingResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.TransactionCardViewResult;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;
import com.swcommodities.wsmill.service.InvoicePagingService;
import com.swcommodities.wsmill.service.InvoiceService;

/**
 *
 * @author macOS
 */

@org.springframework.stereotype.Controller("clientController_invoiceController")
@RequestMapping("/millclient/invoices")
@Transactional
public class InvoiceController {
    
    @Autowired InvoicePagingService invoicePagingService;
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired InvoiceService invoiceService;
    @Autowired PaymentRepository paymentRepository;
    
    protected Integer getCompanyId(HttpSession httpSession) {
		Integer company_id = (Integer) httpSession.getAttribute("company_id");
		return company_id;
	}
    
    @RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<InvoicePagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo, 
        @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer status, 
        @RequestParam(required = false) String txtSearch, @RequestParam(required = false, name = "fromDate") Date fromDate, 
        @RequestParam(required = false, name = "endDate") Date endDate)
			throws JsonProcessingException {
		int clientId = getCompanyId(httpSession);
        InvoicePagingResult result = invoicePagingService.getAggregateInfoForClientSite(clientId, txtSearch, status, fromDate, endDate, pageNo, perPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<InvoiceCardViewResult> getCardView(HttpSession httpSession, @PathVariable Integer id)
			throws JsonProcessingException {
		int clientId = getCompanyId(httpSession);
        Invoice invoice = invoiceRepository.findOne(id);
        InvoiceCardViewResult result = new InvoiceCardViewResult();
        if (invoice.getClient().getId() != clientId) {
			System.out.println("dangerous: " + clientId + " # " + getCompanyId(httpSession));
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
        
        List<TransactionCardViewResult> dis = invoiceService.getDITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> pis = invoiceService.getPITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> sis = invoiceService.getSITransactionsByInvoice(invoice.getId());
        List<OtherTransaction> others = invoiceService.getOtherTransactions(invoice.getId());
        List<Payment> payments = paymentRepository.getPaymentsByInvoice(invoice.getId());
        InvoiceCalculationResult calculationResult = new InvoiceCalculationResult(dis, pis, sis, others, payments);
        
        result.setDis(dis);
        result.setPis(pis);
        result.setSis(sis);
        result.setOthers(others);
        result.setPayments(payments);
        result.setFigure(calculationResult);
        result.setInvoice(invoice);
        
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
