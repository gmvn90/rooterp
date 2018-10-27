/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swcommodities.wsmill.clientRepository.ClientExchangeHistoryRepository;
import com.swcommodities.wsmill.hibernate.clientDto.ClientExchangeHistory;
import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.facade.ExchangeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.ExchangeAssembler;
import com.swcommodities.wsmill.repository.ExchangeRepository;

/**
 *
 * @author macOS
 */

@RequestMapping("/millclient/exchange")
@Controller("clientController_exchangeController")
public class ClientExchangeController {
    @Autowired ClientExchangeHistoryRepository repository;
    @Autowired ExchangeRepository exchangeRepository;
    
    @RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody ClientExchangeHistory json, HttpSession httpSession)
			throws JsonProcessingException {
        repository.save(json);
		return new ResponseEntity<String>("{\"id\": " + json.getId() + "}", HttpStatus.CREATED);
	}
    
    @RequestMapping(value = "/getCurrent.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ExchangeDTO> getCurrent(HttpSession httpSession)
			throws JsonProcessingException {
        Exchange exchange = exchangeRepository.getFirstObject();
        ExchangeAssembler exchangeAssembler = new ExchangeAssembler();
        ExchangeDTO dto = exchangeAssembler.toDto(exchange);
        return new ResponseEntity<>(dto, HttpStatus.OK);
	}
    
}
