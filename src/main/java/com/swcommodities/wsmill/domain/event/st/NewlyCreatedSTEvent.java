/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.st;

import com.swcommodities.wsmill.hibernate.dto.SampleType;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author macOS
 */

@Data
public class NewlyCreatedSTEvent {
    
    @NonNull private SampleType sampleType;
    
}
