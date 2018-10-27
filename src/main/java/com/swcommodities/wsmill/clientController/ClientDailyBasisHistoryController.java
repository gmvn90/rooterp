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
import com.swcommodities.wsmill.clientRepository.ClientDailyBasisHistoryRepository;
import com.swcommodities.wsmill.hibernate.clientDto.ClientDailyBasisHistory;

/**
 *
 * @author macOS
 */

@RequestMapping("/millclient/dailybasis")
@Controller
public class ClientDailyBasisHistoryController {
    @Autowired ClientDailyBasisHistoryRepository repository;
    
    @RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody ClientDailyBasisHistory json, HttpSession httpSession)
			throws JsonProcessingException {
        repository.save(json);
		return new ResponseEntity<String>("{\"id\": " + json.getId() + "}", HttpStatus.CREATED);
	}
}
