package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dunguyen on 8/8/16.
 */
@Entity
@Table(name = "exchange_histories")
public class ExchangeHistory extends AbstractTimestampEntity {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int ratio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    @Column(name = "exchangeCreated", nullable = false)
    private Date exchangeCreated;

    public Date getExchangeCreated() {
        return exchangeCreated;
    }

    public void setExchangeCreated(Date exchangeCreated) {
        this.exchangeCreated = exchangeCreated;
    }

}
