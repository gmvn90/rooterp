/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kiendn
 */
@Entity
@Table(name = "client_user")
public class ClientUserView  implements java.io.Serializable {
    private Integer userId;
    private Integer companyId;

    public ClientUserView() {
    }

    public ClientUserView(Integer userId, Integer companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }
    @Id
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Column(name = "client_id", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    
}
