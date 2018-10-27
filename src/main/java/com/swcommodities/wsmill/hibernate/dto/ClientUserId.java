/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author duhc
 */
@Embeddable
public class ClientUserId implements java.io.Serializable {

    private int userId;
    private int clientId;

    public ClientUserId() {
    }

    public ClientUserId(int userId, int clientId) {
        this.userId = userId;
        this.clientId = clientId;
    }

    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof ClientUserId)) {
            return false;
        }
        ClientUserId castOther = (ClientUserId) other;

        return (this.getUserId() == castOther.getUserId())
            && (this.getClientId() == castOther.getClientId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getUserId();
        result = 37 * result + this.getClientId();
        return result;
    }
}
