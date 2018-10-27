package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "options")
public class OperationalCost extends AbstractTimestampEntity implements Comparable {

    public OperationalCost() {

    }

    public OperationalCost(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;
    }

    public OperationalCost(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The user's email
    @NotNull
    private String name;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    @JsonIgnore
    private Category category;

    @JsonProperty("is_usd")
    @Column(name = "is_usd")
    private boolean isUsd;

    @JsonProperty("option_type")
    @Column(name = "option_type")
    private int optionType;

    @JsonProperty("value_in_vnd")
    @Column(name = "value_in_vnd")
    private double valueInVnd;

    @JsonProperty("value_in_usd")
    @Column(name = "value_in_usd")
    private double valueInUsd;

    private String type;

    @JsonProperty("option_name")
    @Column(unique = true, name = "option_name")
    private String optionName;

    @JsonProperty("option_unit")
    @Column(name = "option_unit")
    private String optionUnit;

    @Column(name = "local_id")
    @JsonProperty("local_id")
    private int localId;

    @Transient
    private Cost cost;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public boolean isUsd() {
        return isUsd;
    }

    public boolean getIsUsd() {
        return isUsd;
    }

    public void setUsd(boolean usd) {
        isUsd = usd;
    }

    public void setIsUsd(boolean usd) {
        isUsd = usd;
    }

    public double getValueInVnd() {
        return valueInVnd;
    }

    public void setValueInVnd(double valueInVnd) {
        this.valueInVnd = valueInVnd;
    }

    public double getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(double valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    public void touch() {
        setUpdated(new Date());
    }

    public static String relationships = "{\"5\": [29, 32], \"7\": [10], \"14\": [21], \"15\": [24], \"16\": [27], \"17\": [30], \"18\": [9]}";

    public double getValue() {
        if (getOptionType() == OptionType.USD) {
            return getValueInUsd();
        } else if (getOptionType() == OptionType.VND) {
            return getValueInVnd();
        }
        return -1;
    }

    public void setValue(double value, int ratio) {
        if (getOptionType() == OptionType.USD) {
            setValueInUsd(value);
            setValueInVnd(value * ratio);
        } else if (getOptionType() == OptionType.VND) {
            setValueInVnd(value);
            setValueInUsd(value / ratio);
        }
    }

    public void setValueByForcing(double value, int ratio, int optionType) {
        if (optionType == OptionType.USD) {
            setValueInUsd(value);
            setValueInVnd(value * ratio);
        } else if (optionType == OptionType.VND) {
            setValueInVnd(value);
            setValueInUsd(value / ratio);
        }
    }

    public void setValueWithoutKnowingUSDOrVND(double valueInUsd, double valueInVnd, int ratio) {
        if (getOptionType() == OptionType.USD) {
            setValue(valueInUsd, ratio);
        } else if (getOptionType() == OptionType.VND) {
            setValue(valueInVnd, ratio);
        } else {
            setValue(0, ratio);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionUnit() {
        return optionUnit;
    }

    public void setOptionUnit(String optionUnit) {
        this.optionUnit = optionUnit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        SortedSet<OperationalCost> options = category.getOptions();
        if (options == null) {
            options = new TreeSet<>();
        }
        options.add(this);
        category.setOptions(options);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        OperationalCost opt = (OperationalCost) o;
        if (opt.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }

    public Cost getCost() {
        return cost;
    }

    public OperationalCost setCost(Cost cost) {
        this.cost = cost;
        return this;
    }
}
