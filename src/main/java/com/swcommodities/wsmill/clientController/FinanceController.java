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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.FinanceContractDetailAssembler;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractDetailResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractPagingResult;
import com.swcommodities.wsmill.repository.FinanceContractRepository;
import com.swcommodities.wsmill.repository.InterestPaymentRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;
import com.swcommodities.wsmill.service.FinanceContractPagingService;

/**
 *
 * @author macOS
 */

@org.springframework.stereotype.Controller("clientController_financeController")
@RequestMapping("/millclient/finances")
@Transactional
public class FinanceController {
    
    @Autowired FinanceContractRepository financeContractRepository;
    @Autowired FinanceContractPagingService financeContractPagingService;
    @Autowired PaymentRepository paymentRepository;
    @Autowired InterestPaymentRepository interestPaymentRepository;
    
    protected int getCompanyId(HttpSession httpSession) {
		int company_id = (Integer) httpSession.getAttribute("company_id");
		return company_id;
	}
    
    @RequestMapping(value = "/searchlist.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<FinanceContractPagingResult> getSearchView(HttpSession httpSession, @RequestParam(required = false) Integer pageNo, 
        @RequestParam(required = false) Integer perPage, @RequestParam(required = false) Integer status, 
        @RequestParam(required = false) String txtSearch, @RequestParam(required = false, name = "startDate") Date startDate, 
        @RequestParam(required = false, name = "endDate") Date endDate)
			throws JsonProcessingException {
		int clientId = getCompanyId(httpSession);
        FinanceContractPagingResult result = financeContractPagingService.getAggregateInfo(clientId, startDate, endDate, txtSearch, pageNo, perPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<FinanceContractDetailResult> getCardView(HttpSession httpSession, @PathVariable int id)
			throws JsonProcessingException {
		int clientId = getCompanyId(httpSession);
        FinanceContractDetailAssembler assembler = new FinanceContractDetailAssembler();
        System.out.println("id is: " + id);
        FinanceContract financeContract = financeContractRepository.findOne(id);
        
        Assert.isTrue(financeContract != null);
        Assert.isTrue(financeContract.getClient() != null);
        Assert.isTrue(clientId == (int) financeContract.getClient().getId());
        List<Payment> payments = paymentRepository.getPaymentsByFinanceContract(id);
        List<InterestPayment> interestPayments = interestPaymentRepository.getInterestPaymentsByFinanceContract(id);
		return new ResponseEntity<>(assembler.toDto(financeContract, payments, interestPayments), HttpStatus.OK);
	}
}
