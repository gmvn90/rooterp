/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Throwables;
import com.swcommodities.wsmill.application.exception.UserNotExist;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.UserPasswordForm;
import com.swcommodities.wsmill.hibernate.dto.serializer.UserSerializer;
import com.swcommodities.wsmill.repository.UserRepository;
import com.swcommodities.wsmill.service.ClientUserService;

/**
 *
 * @author macOS
 */

@org.springframework.stereotype.Controller("clientController_clientUserController")
@RequestMapping("/millclient/user")
public class ClientUserController {
    
    @Autowired UserRepository userRepository;
    @Autowired ClientUserService clientUserService;
    
    protected Integer getUserId(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		return user.getId();
	}
    
    protected User getUser(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		return user;
	}
    
    @RequestMapping(value="/detail.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getUserInfo(HttpSession httpSession) throws JsonProcessingException {
        User user = getUser(httpSession);
        
        ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(User.class, new UserSerializer());
		mapper.registerModule(module);
        user = userRepository.findOne(user.getId());
        return new ResponseEntity<>(mapper.writeValueAsString(user), HttpStatus.OK);
    }
    
    @RequestMapping(value="/password.json", method=RequestMethod.PUT)
    public @ResponseBody ResponseEntity<String> updateUser(HttpSession httpSession, @RequestBody UserPasswordForm userPasswordForm) throws NoSuchAlgorithmException {
        User user = getUser(httpSession);
        try {
            clientUserService.changePassword(user.getUserName(), userPasswordForm.getOldRawPassword(), userPasswordForm.getNewRawPassword());
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (UserNotExist ex) {
            return new ResponseEntity<>("{\"error\":\"UserNotExist\"}", HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value="/", method=RequestMethod.PUT)
    public @ResponseBody ResponseEntity<User> updateUser(HttpSession httpSession, @RequestBody User json) {
        Integer id = getUserId(httpSession);
        User entity = userRepository.findOne(id);
		try {
			BeanUtils.copyProperties(json, entity);
		} catch (Exception e) {
			System.out.println("while copying properties" + e);
			throw Throwables.propagate(e);
		}
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
