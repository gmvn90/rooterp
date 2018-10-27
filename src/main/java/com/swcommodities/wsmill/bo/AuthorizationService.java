/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.AuthorizationDao;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class AuthorizationService {

    private AuthorizationDao authorizationDao;

    public void setAuthorizationDao(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    public Authorization getAuthorizationOfUserInPage(User user, Page page) {
        Authorization authorization = authorizationDao.getAuthorizationOfUserInPage(user, page);
        return (authorization != null) ? authorization : null;
    }

    public Authorization getAuthorizationOfUserInPageSimple(int user_id, int page_id) {
        Authorization authorization = authorizationDao.getAuthorizationOfUserInPageSimple(user_id, page_id);
        return (authorization != null) ? authorization : null;
    }

    @Transactional(readOnly = true)
    public Byte getPagePermission(User user, Page page) {
        if (user != null) {
            Authorization authorization = getAuthorizationOfUserInPage(user, page);
            return (authorization != null) ? authorization.getPermission() : (byte) 0;
        }
        return 0;
    }

    @Transactional(readOnly = true)
    public ArrayList<Authorization> getAuthorizationOfUser(User user) {
        return authorizationDao.getAuthorizationOfUser(user);
    }

    @Transactional(readOnly = true)
    public Page getPageFromAuthorization(Authorization authorization) {
        return authorizationDao.getPageFromAuthorization(authorization);
    }

    public Authorization updateAuthorization(Authorization authorization) {
        return authorizationDao.updateAuthorization(authorization);
    }

    public ArrayList<Map> getPermission(int user_id) {
        return authorizationDao.getPermission(user_id);
    }
}
