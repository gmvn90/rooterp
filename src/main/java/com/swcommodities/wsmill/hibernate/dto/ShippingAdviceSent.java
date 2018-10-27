package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by gmvn on 8/12/16.
 */
@Entity
@Table(name = "shipping_advice_sent")
public class ShippingAdviceSent implements java.io.Serializable {

    private Integer id;
    private String refNumber;
    private Date date;
    private ShippingAdvice shippingAdvice;
    private User user;
    private String fileName;
    private String email;
    private String emailCc;
    private Byte status;

    public ShippingAdviceSent() {
    }

    public ShippingAdviceSent(Integer id, String refNumber, Date date, ShippingAdvice shippingAdvice, User user, String fileName, String email, Byte status, String emailCc) {
        this.id = id;
        this.refNumber = refNumber;
        this.date = date;
        this.shippingAdvice = shippingAdvice;
        this.user = user;
        this.fileName = fileName;
        this.email = email;
        this.status = status;
        this.emailCc = emailCc;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sa_id")
    public ShippingAdvice getShippingAdvice() {
        return shippingAdvice;
    }

    public void setShippingAdvice(ShippingAdvice shippingAdvice) {
        this.shippingAdvice = shippingAdvice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "email_cc")
    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
