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
import com.swcommodities.wsmill.clientRepository.MarketFobDiffHistoryRepository;
import com.swcommodities.wsmill.hibernate.clientDto.MarketFobDiffHistory;

/**
 *
 * @author macOS
 */

@RequestMapping("/millclient/maket_to_fob")
@Controller
public class MarketFobDiffController {
    @Autowired MarketFobDiffHistoryRepository marketFobDiffHistoryRepository;
    
    @RequestMapping(value = "/all.json", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<String> create(@RequestBody MarketFobDiffHistory json, HttpSession httpSession)
			throws JsonProcessingException {
        
        marketFobDiffHistoryRepository.save(json);
		return new ResponseEntity<String>("{\"id\": " + json.getId() + "}", HttpStatus.CREATED);
	}
    
}
