package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by dunguyen on 8/8/16.
 */
@Entity
@Table(name = "costs")
public class Cost extends AbstractTimestampEntity implements Comparable, Costable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "option_id")
    private OperationalCost option;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id")
    private CompanyMaster company;

    @Transient
    private double costPerTon;

    private String username;

    @JsonProperty("value")
    private double value;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OperationalCost getOption() {
        return option;
    }

    public void setOption(OperationalCost option) {
        this.option = option;
    }

    public CompanyMaster getCompany() {
        return company;
    }

    public void setCompany(CompanyMaster company) {
        this.company = company;
    }

    @Override
    public double getCostValue() {
        return value;
    }

    @Transient
    @JsonProperty("value_per_metric_ton")
    private double valuePerMetricTon;

    public double getValuePerMetricTon() {
        return valuePerMetricTon;
    }

    public Cost setValuePerMetricTon(double valuePerMetricTon) {
        this.valuePerMetricTon = valuePerMetricTon;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        Cost opt = (Cost) o;
        if (opt.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }

    public double getCostPerTon() {
        return costPerTon;
    }

    public Cost setCostPerTon(double costPerTon) {
        this.costPerTon = costPerTon;
        return this;
    }

    @Override
    public String getOptionUnit() {
        return getOption().getOptionUnit();
    }

    @Override
    public String getOptionName() {
        return getOption().getOptionName();
    }

}
