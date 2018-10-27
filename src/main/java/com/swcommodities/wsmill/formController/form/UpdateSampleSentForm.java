/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import java.util.Date;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author trung
 */
@Data
public class UpdateSampleSentForm {
    
    private Date etaDate;
    private Date sentDate;
    private String userRemark;
    private String trackingNo;
    private String refNumber;
    private String siRef;
    private Date createdDate;
    private Date updatedDate;
    private String user;
    private int courierId;
    private String remark;
    private String recipient;
    private String lotRef;
    private Set<FileSentDTO> documents;   
    
}
