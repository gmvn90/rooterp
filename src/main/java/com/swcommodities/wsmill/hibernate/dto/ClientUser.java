/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author duhc
 */
@Entity
@Table(name = "client_user")
public class ClientUser implements java.io.Serializable {

    private ClientUserId id;
    private CompanyMaster companyMaster;
    private User user;

    public ClientUser() {
    }

    public ClientUser(ClientUserId id, CompanyMaster companyMaster, User user) {
        this.id = id;
        this.companyMaster = companyMaster;
        this.user = user;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column
            = @Column(name = "user_id", nullable = false))
        ,
        @AttributeOverride(name = "clientId", column
            = @Column(name = "client_id", nullable = false))})
    public ClientUserId getId() {
        return this.id;
    }

    public void setId(ClientUserId id) {
        this.id = id;
    }

//    @ElementCollection
//    @AttributeOverrides({
//        @AttributeOverride(name="key.id",column=@Column(name="page_id")),
//        @AttributeOverride(name="value.permission",column=@Column(name="permission"))
//    })
//    public Map<Integer,Byte> getMap(){
//        return this.map;
//    }
//    
//    public void setMap(Map map){
//        this.map = map;
//    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, insertable = false, updatable = false)
    public CompanyMaster getCompanyMaster() {
        return this.companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
    }
}
