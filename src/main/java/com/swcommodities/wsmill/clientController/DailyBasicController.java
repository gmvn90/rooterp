/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swcommodities.wsmill.hibernate.dto.DailyBasis;
import com.swcommodities.wsmill.hibernate.dto.facade.DailyBasisDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.DailyBasicAssembler;
import com.swcommodities.wsmill.repository.DailyBasisRepository;

/**
 *
 * @author macOS
 */
@RequestMapping("/millclient/rawdailybasis")
@Controller
public class DailyBasicController {
    @Autowired private DailyBasisRepository repository;
    
    @RequestMapping(value = "/all.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<DailyBasisDTO>> create(HttpSession httpSession)
			throws JsonProcessingException {
        List<DailyBasis> all = repository.findAll();
        List<DailyBasisDTO> result = new ArrayList<>();
        DailyBasicAssembler assembler = new DailyBasicAssembler();
        for(DailyBasis dailyBasis: all) {
            result.add(assembler.toDto(dailyBasis));
        }
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
