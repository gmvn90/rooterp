/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.scheduler;

import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.service.CacheService;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;

/**
 *
 * @author macOS
 */

@Data @Builder
public class DICacheWorker implements Runnable {
    
    private CacheService cacheService;
    private DeliveryInstruction deliveryInstruction;
    private Integer id;
    
    @Override
    public void run() {
        Assert.notNull(cacheService);
        Assert.isTrue(deliveryInstruction != null || id != null);
        if(deliveryInstruction != null) {
            cacheService.writeDICache(deliveryInstruction);
        } else {
            cacheService.writeDICache(id);
        }
    }
    
}
