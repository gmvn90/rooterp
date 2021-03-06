package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PalletMaster generated by hbm2java
 */
@Entity
@Table(name = "pallet_master"
)
public class PalletMaster implements java.io.Serializable {

    private Integer id;
    private String name;
    private Float value;
    private Date createdDate;
    private Date lastUpdate;
    private Byte status;
    private String log;

    public PalletMaster() {
    }

    public PalletMaster(String name, Float value, Date createdDate, Date lastUpdate, Byte status, String log) {
        this.name = name;
        this.value = value;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.log = log;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", precision = 12, scale = 0)
    public Float getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", length = 19)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column(name = "log", length = 65535)
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
