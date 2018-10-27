package com.swcommodities.wsmill.hibernate.dto;

/**
 * Created by dunguyen on 9/16/16.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "exchanges")
public class Exchange extends AbstractTimestampEntity {

    public Exchange() {

    }

    public Exchange(Long id) {
        this.id = id;
    }

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

    @Transient
    private ExchangeHistory exchangeHistory;

    public ExchangeHistory getExchangeHistory() {
        return exchangeHistory;
    }

    public void setExchangeHistory(ExchangeHistory exchangeHistory) {
        this.exchangeHistory = exchangeHistory;
    }
}
