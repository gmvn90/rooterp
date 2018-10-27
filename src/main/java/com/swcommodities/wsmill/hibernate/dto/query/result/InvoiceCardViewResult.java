/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;

/**
 *
 * @author macOS
 */
public class InvoiceCardViewResult {
    private Invoice invoice;
    private InvoiceCalculationResult figure;
    private List<TransactionCardViewResult> dis;
    private List<TransactionCardViewResult> pis;
    private List<TransactionCardViewResult> sis;
    private List<OtherTransaction> others;
    private List<Payment> payments;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceCalculationResult getFigure() {
        return figure;
    }

    public void setFigure(InvoiceCalculationResult figure) {
        this.figure = figure;
    }

    public List<TransactionCardViewResult> getDis() {
        return dis;
    }

    public void setDis(List<TransactionCardViewResult> dis) {
        this.dis = dis;
    }

    public List<TransactionCardViewResult> getPis() {
        return pis;
    }

    public void setPis(List<TransactionCardViewResult> pis) {
        this.pis = pis;
    }

    public List<TransactionCardViewResult> getSis() {
        return sis;
    }

    public void setSis(List<TransactionCardViewResult> sis) {
        this.sis = sis;
    }

    public List<OtherTransaction> getOthers() {
        return others;
    }

    public void setOthers(List<OtherTransaction> others) {
        this.others = others;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

  
      
}
