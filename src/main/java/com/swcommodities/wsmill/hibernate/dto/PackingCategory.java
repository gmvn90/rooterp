/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author macOS
 */

@Entity
@Table(name = "packing_category")
public class PackingCategory extends Category {
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="packing_id")
    @JsonIgnore
    private PackingMaster packingMaster;

    public PackingMaster getPackingMaster() {
        return packingMaster;
    }

    public void setPackingMaster(PackingMaster packingMaster) {
        this.packingMaster = packingMaster;
    }
    
}
