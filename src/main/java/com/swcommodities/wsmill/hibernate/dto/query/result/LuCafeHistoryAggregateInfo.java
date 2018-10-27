/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

/**
 *
 * @author macOS
 */
public class LuCafeHistoryAggregateInfo {
    private long beginExpense;
    private long income;
    private long indayExpense;
    private long total;

    public LuCafeHistoryAggregateInfo() {}

    public LuCafeHistoryAggregateInfo(long beginExpense, long income, long indayExpense, long total) {
        this.setBeginExpense(beginExpense);
        this.setIncome(income);
        this.setIndayExpense(indayExpense);
        this.setTotal(total);
    }


    public long getBeginExpense() {
        return beginExpense;
    }

    public void setBeginExpense(long beginExpense) {
        this.beginExpense = beginExpense;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getIndayExpense() {
        return indayExpense;
    }

    public void setIndayExpense(long indayExpense) {
        this.indayExpense = indayExpense;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
