package com.swcommodities.wsmill.service;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;

@Service
public class MyAsyncService {

    @Autowired
    CacheService cacheService;
    
    @Autowired ShippingInstructionRepository shippingInstructionRepository;

    @Async
    public Future<String> findUser(String user) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(100L);
        //System.out.println("just return hihh");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheDI(int id) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save di start");
        cacheService.writeDICache(id);
        System.out.println("async save di finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheDI(DeliveryInstruction deliveryInstruction) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save di start");
        cacheService.writeDICache(deliveryInstruction);
        System.out.println("async save di finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCachePI(int id) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save pi start");
        cacheService.writePICache(id);
        System.out.println("async save pi finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCachePI(ProcessingInstruction processingInstruction) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save pi start");
        cacheService.writePICache(processingInstruction);
        System.out.println("async save pi finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheSI(int id) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save si start");
        cacheService.writeSICache(id);
        System.out.println("async save si finish");
        return new AsyncResult<>(results);
    }
    
    @Async
    public Future<String> saveCacheSI(String refNumber) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save si start");
        cacheService.writeSICache(refNumber);
        System.out.println("async save si finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheSI(ShippingInstruction shippingInstruction) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save si start");
        cacheService.writeSICache(shippingInstruction);
        System.out.println("async save si finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheSampleSent(int id) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save sample sent start");
        cacheService.writeSampleSentCache(id);
        System.out.println("async save sample sent finish");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<String> saveCacheSampleSent(SampleSent sampleSent) throws InterruptedException {
        String results = "okreturn";
        // Artificial delay of 1s for demonstration purposes
        System.out.println("async save sample sent start");
        cacheService.writeSampleSentCache(sampleSent);
        System.out.println("async save sample sent finish");
        return new AsyncResult<>(results);
    }
    
    @Async
    public Future<String> saveCacheSampleSentByShippingId(int id) throws InterruptedException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(id);
        shippingInstruction.getSampleSents().forEach(ss -> {
            cacheService.writeSampleSentCache(ss);
        });
        String results = "okreturn";
        return new AsyncResult<>(results);
    }
    
    @Async
    public Future<String> saveCacheSampleSentByShippingRefNumber(String refNumber) throws InterruptedException {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(refNumber);
        shippingInstruction.getSampleSents().forEach(ss -> {
            cacheService.writeSampleSentCache(ss);
        });
        String results = "okreturn";
        return new AsyncResult<>(results);
    }
    
    @Async
    public Future<String> saveCacheSampleSentByShippingObject(ShippingInstruction shippingInstruction) throws InterruptedException {
        shippingInstruction.getSampleSents().forEach(ss -> {
            cacheService.writeSampleSentCache(ss);
        });
        String results = "okreturn";
        return new AsyncResult<>(results);
    }

}
