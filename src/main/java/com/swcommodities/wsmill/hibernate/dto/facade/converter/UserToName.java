/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author macOS
 */
public class UserToName {
    public String mapUser(User user) {
        if(user == null) {
            return "";
        }
        return user.getUserName();
    }
}
