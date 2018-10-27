package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dunguyen on 10/11/16.
 */
@Entity
@Table(name = "origins")
public class OriginMaster extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private Country country;
    
    private String origin;

    public Integer getId() {
        return id;
    }

    public OriginMaster() {
    }

    public OriginMaster(Integer id) {
        this.id = id;
    }

    public OriginMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public OriginMaster setCountry(Country country) {
        this.country = country;
        return this;
    }

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
    
    
}
