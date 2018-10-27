/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.swcommodities.wsmill.application.exception.UserNotExist;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.UserRepository;

/**
 *
 * @author macOS
 */

@Service
public class ClientUserService {
    private UserRepository userRepository;
    
    public ClientUserService() {}
    
    @Autowired
    public ClientUserService(UserRepository userRepository) {
        Assert.notNull(userRepository);
        this.userRepository = userRepository;
    }
    
    public void changePassword(String username, String oldRawPassword, String newRawPassword) throws NoSuchAlgorithmException, UserNotExist {
        User user = getUserByRawPassword(username, oldRawPassword);
        user.setPassword(StringEncodingService.encodePassword(newRawPassword));
        userRepository.save(user);
    }
    
    public void saveRawUser(User user) throws NoSuchAlgorithmException {
        user.setPassword(StringEncodingService.encodePassword(user.getPassword()));
        userRepository.save(user);
    }
    
    public User getUserByRawPassword(String username, String oldRawPassword) throws NoSuchAlgorithmException, UserNotExist {
        User user = userRepository.findByUserNameAndPassword(username, StringEncodingService.encodePassword(oldRawPassword));
        if(user == null) {
            throw new UserNotExist("Username or password not correct");
        }
        return user;
    }
    
}
