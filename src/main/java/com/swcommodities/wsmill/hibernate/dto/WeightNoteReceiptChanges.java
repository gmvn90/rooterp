/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author macOS
 */
@Entity
@Table(name = "wnr_changes") 
@Data
public class WeightNoteReceiptChanges {
    
    @Id @GeneratedValue private long id;
    
    @Column(name = "ref_number")
    private String refNumber;
    
}
