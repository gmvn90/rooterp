package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * Created by dunguyen on 10/21/16.
 */
@Entity
@Table(name = "pi_types_items")
public class PITypeItem extends AbstractTimestampEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "weight_loss")
    private double weightLoss;

    @Column(name = "reject_grade_3")
    private double rejectGrade3;

    @Transient
    private Cost cost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updater;

    @OneToOne(fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @Sort(type = SortType.NATURAL)
    private OperationalCost option;

    @Override
    public int compareTo(Object o) {
        PITypeItem opt = (PITypeItem) o;
        if (opt.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getId() {
        return id;
    }

    public PITypeItem setId(int id) {
        this.id = id;
        return this;
    }

    public double getWeightLoss() {
        return weightLoss;
    }

    public PITypeItem setWeightLoss(double weightLoss) {
        this.weightLoss = weightLoss;
        return this;
    }

    public double getRejectGrade3() {
        return rejectGrade3;
    }

    public PITypeItem setRejectGrade3(double rejectGrade3) {
        this.rejectGrade3 = rejectGrade3;
        return this;
    }

    public User getUpdater() {
        return updater;
    }

    public PITypeItem setUpdater(User updater) {
        this.updater = updater;
        return this;
    }

    public OperationalCost getOption() {
        return option;
    }

    public PITypeItem setOption(OperationalCost option) {
        this.option = option;
        return this;
    }

    public Cost getCost() {
        return cost;
    }

    public PITypeItem setCost(Cost cost) {
        this.cost = cost;
        return this;
    }

}
