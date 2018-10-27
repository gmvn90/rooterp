package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by macOS on 4/4/17.
 */
@Entity
@Table(name = "transactionitem")
public class TransactionItem extends AbstractTimestampEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Transaction transaction;
    private String instRef;
    private Integer type;
    @ManyToOne(fetch = FetchType.LAZY)
    private LocationMaster location;
    @ManyToOne(fetch = FetchType.LAZY)
    private GradeMaster grade;
    private Double tons;
    private Double cost;

    public TransactionItem() {
    }

    public TransactionItem(GradeMaster grade, Integer type, Double tons) {
        this.grade = grade;
        this.type = type;
        this.tons = tons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        transaction.getTransactionItems().add(this);
    }

    public String getInstRef() {
        return instRef;
    }

    public void setInstRef(String instRef) {
        this.instRef = instRef;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocationMaster getLocation() {
        return location;
    }

    public void setLocation(LocationMaster location) {
        this.location = location;
    }

    public GradeMaster getGrade() {
        return grade;
    }

    public void setGrade(GradeMaster grade) {
        this.grade = grade;
    }

    public Double getTons() {
        return tons;
    }

    public void setTons(Double tons) {
        this.tons = tons;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        TransactionItem cat = (TransactionItem) o;
        return grade.getName().compareTo(cat.getGrade().getName());
    }
}
