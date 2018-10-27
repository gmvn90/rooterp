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

import com.swcommodities.wsmill.hibernate.dto.MarketFobDiff;
import com.swcommodities.wsmill.hibernate.dto.facade.MarketFobDiffDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.MarketFobDiffAssembler;
import com.swcommodities.wsmill.service.MarketFobService;

/**
 *
 * @author macOS
 */

@RequestMapping("/millclient/marketfobdiff")
@Controller
public class RealMarketFobDiffController {
    @Autowired MarketFobService repository;
    
    @RequestMapping(value = "/all.json", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<MarketFobDiffDTO>> create(HttpSession httpSession) {
        MarketFobDiffAssembler assembler = new MarketFobDiffAssembler();
        List<MarketFobDiff> marketFobDiffs = repository.getAllExceptUnused();
        List<MarketFobDiffDTO> dTOs = new ArrayList<>();
        for(MarketFobDiff marketFobDiff: marketFobDiffs) {
            dTOs.add(assembler.toDto(marketFobDiff));
        }
        return new ResponseEntity<>(dTOs, HttpStatus.OK);
    }
}
