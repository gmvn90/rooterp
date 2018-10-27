package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by dunguyen on 10/11/16.
 */
@Entity
@Table(name = "qualities")
public class QualityMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public QualityMaster() {

    }

    public QualityMaster(Integer id) {
        this.id = Long.valueOf(id);
    }

    public long getId() {
        return id;
    }

    public QualityMaster setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public QualityMaster setName(String name) {
        this.name = name;
        return this;
    }
}
