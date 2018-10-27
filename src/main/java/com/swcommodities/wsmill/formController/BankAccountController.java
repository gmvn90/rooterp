/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swcommodities.wsmill.hibernate.dto.BankAccount;
import com.swcommodities.wsmill.hibernate.dto.CurrencyType;
import com.swcommodities.wsmill.repository.BankAccountRepository;

/**
 *
 * @author macOS
 */
@Controller
@Transactional
@RequestMapping("/banks")
public class BankAccountController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public ResponseEntity<String> post(@RequestBody BankAccount acc, HttpSession httpSession) throws Exception {
        Object beneficiaryObject = httpSession.getAttribute("beneficiary");
//        if(beneficiaryObject == null || (Integer) beneficiaryObject != acc.getBeneficiary().getId()) {
//            throw new Exception();
//        }
        Map<Integer, String> currs = CurrencyType.getAllReverse();
        bankAccountRepository.save(acc);
        JSONObject object = new JSONObject();
        object.put("bankName", acc.getBank().getName());
        object.put("currencyString", currs.get(acc.getCurrency()));
        object.put("accountNumber", acc.getAccountNumber());
        object.put("transferInfo", acc.getTransferInfo());
        
        return new ResponseEntity<>(object.toString(), HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable(value="id") int id, HttpSession httpSession) throws Exception {
        BankAccount acc = bankAccountRepository.findOne(id);
        JSONObject object = new JSONObject();
        object.put("bankName", acc.getBank().getName());
        object.put("accountNumber", acc.getAccountNumber());
        object.put("transferInfo", acc.getTransferInfo());
        return new ResponseEntity<>(object.toString(), HttpStatus.OK);
    }
}
