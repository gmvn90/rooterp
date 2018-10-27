package com.swcommodities.wsmill.hibernate.dto;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * Created by dunguyen on 10/21/16.
 */
@Entity
@Table(name = "pi_types")
public class PIType extends AbstractTimestampEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @Sort(type = SortType.NATURAL)
    private Set<PITypeItem> piTypeItems;

    private String name;

    @Transient
    private double totalProcessingWeighLoss;

    @Transient
    private double totalRejectGrade3;

    @Transient
    private double totalPrice;

    public PIType() {
        // TODO Auto-generated constructor stub
    }

    public PIType(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        PIType opt = (PIType) o;
        if (opt.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getId() {
        return id;
    }

    public PIType setId(int id) {
        this.id = id;
        return this;
    }

    public Set<PITypeItem> getPiTypeItems() {
        return piTypeItems;
    }

    public PIType setPiTypeItems(Set<PITypeItem> piTypeItems) {
        this.piTypeItems = piTypeItems;
        return this;
    }

    public PIType addPiTypeItem(PITypeItem piTypeItem) {
        if (piTypeItems == null) {
            piTypeItems = new TreeSet<>();
        }
        piTypeItems.add(piTypeItem);
        return this;
    }

    public String getName() {
        return name;
    }

    public PIType setName(String name) {
        this.name = name;
        return this;
    }

    public double getTotalProcessingWeighLoss() {
        return totalProcessingWeighLoss;
    }

    public PIType setTotalProcessingWeighLoss(double totalProcessingWeighLoss) {
        this.totalProcessingWeighLoss = totalProcessingWeighLoss;
        return this;
    }

    public double getTotalRejectGrade3() {
        return totalRejectGrade3;
    }

    public PIType setTotalRejectGrade3(double totalRejectGrade3) {
        this.totalRejectGrade3 = totalRejectGrade3;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public PIType setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
