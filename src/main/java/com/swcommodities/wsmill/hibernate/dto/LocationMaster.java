package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by dunguyen on 10/11/16.
 */
@Entity
@Table(name = "location_master")
public class LocationMaster extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    private Byte status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public Integer getId() {
        return id;
    }

    public LocationMaster() {
    }

    public LocationMaster(Integer id) {
        this.id = id;
    }

    public LocationMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocationMaster setName(String name) {
        this.name = name;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public LocationMaster setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public LocationMaster setCountry(Country country) {
        this.country = country;
        return this;
    }
}
