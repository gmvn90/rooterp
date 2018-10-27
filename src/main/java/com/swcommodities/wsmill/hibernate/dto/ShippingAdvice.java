package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.swcommodities.wsmill.utils.Constants;

/**
 * Created by gmvn on 8/9/16.
 */
@Entity
@Table(name = "shipping_advice")
public class ShippingAdvice implements java.io.Serializable {

    private Integer id;
    private String refNumber;
    private Date date;
    private ShippingInstruction shippingInstruction;
    private User user;
    private String remark = "";
    private Byte status;
    private Set<ShippingAdviceSent> shippingAdviceSents = new HashSet<>(0);

    public ShippingAdvice() {
    }

    public ShippingAdvice(User user, String refNumber) {
        this.refNumber = refNumber;
        this.user = user;
        this.refNumber = refNumber;
        this.date = new Date();
        this.status = Constants.ACTIVE;
        this.user = user;
    }

    public ShippingAdvice(int id) {
        this.id = id;
    }

    public ShippingAdvice(String refNumber, Date date, ShippingInstruction shippingInstruction, User user, String remark, Byte status) {
        this.setRefNumber(refNumber);
        this.setDate(date);
        this.setRemark(remark);
        this.setStatus(status);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ref_number", nullable = false)
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_id")
    public ShippingInstruction getShippingInstruction() {
        return shippingInstruction;
    }

    public void setShippingInstruction(ShippingInstruction shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shippingAdvice")
    public Set<ShippingAdviceSent> getShippingAdviceSents() {
        return shippingAdviceSents;
    }

    public void setShippingAdviceSents(Set<ShippingAdviceSent> shippingAdviceSents) {
        this.shippingAdviceSents = shippingAdviceSents;
    }
}
