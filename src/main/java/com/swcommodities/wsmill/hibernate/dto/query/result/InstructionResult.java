package com.swcommodities.wsmill.hibernate.dto.query.result;

/**
 * Created by dunguyen on 10/19/16.
 */
public class InstructionResult {
    Double tons;
    Double deliverd;
    Double pending;
    Double allocated;
    Double inprocess;
    Double exprocess;

    public InstructionResult() {
    }

    public Double getTons() {
        return tons;
    }

    public InstructionResult setTons(Double tons) {
        this.tons = tons;
        return this;
    }

    public Double getDeliverd() {
        return deliverd;
    }

    public InstructionResult setDeliverd(Double deliverd) {
        this.deliverd = deliverd;
        return this;
    }

    public Double getPending() {
        return pending;
    }

    public InstructionResult setPending(Double pending) {
        this.pending = pending;
        return this;
    }

    public Double getAllocated() {
        return allocated;
    }

    public InstructionResult setAllocated(Double allocated) {
        this.allocated = allocated;
        return this;
    }

    public Double getInprocess() {
        return inprocess;
    }

    public InstructionResult setInprocess(Double inprocess) {
        this.inprocess = inprocess;
        return this;
    }

    public Double getExprocess() {
        return exprocess;
    }

    public InstructionResult setExprocess(Double exprocess) {
        this.exprocess = exprocess;
        return this;
    }

    public InstructionResult(Double tons, Double deliverd, Double pending) {
        this.tons = tons;
        this.deliverd = deliverd;
        this.pending = pending;
    }

    public InstructionResult(Double tons, Double allocated, Double inprocess, Double exprocess, Double pending) {
        this.tons = tons;
        this.allocated = allocated;
        this.inprocess = inprocess;
        this.exprocess = exprocess;
        this.pending = pending;
    }

    public InstructionResult(Double tons) {
        this.tons = tons;
    }


}
