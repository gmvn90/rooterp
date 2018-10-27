package com.swcommodities.wsmill.application.scheduler;

import com.swcommodities.wsmill.application.service.InitService;
import com.swcommodities.wsmill.domain.event.si.SIUpdatedEvent;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.formController.SIController;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.specification.di.DIPendingCompletionStatus;
import com.swcommodities.wsmill.hibernate.specification.pi.PendingCompletionStatus;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.service.CacheService;
import com.swcommodities.wsmill.service.DailyBasicAppService;
import java.util.Date;
import java.util.stream.IntStream;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Scheduler {
    
    @Autowired TaskExecutor taskExecutor;
    
    @Autowired private ApplicationEventPublisher publisher;

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    @Autowired
    private DeliveryInstructionRepository deliveryInstructionRepository;
    @Autowired
    private ShippingInstructionRepository shippingInstructionRepository;
    @Autowired
    private ProcessingInstructionRepository processingInstructionRepository;
    @Autowired
    private SampleSentRepository sampleSentRepository;
    @Autowired
    CacheService cacheService;
    @Autowired
    private DailyBasicAppService dailyBasicAppService;
    
    @Autowired InitService initService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void saveDeliveryInstruction() {
        List<DeliveryInstruction> dis = deliveryInstructionRepository.findByStatus(Byte.valueOf("0"));
        System.out.println("di length is: *************** " + dis.size());
        for (DeliveryInstruction deliveryInstruction : dis) {
            cacheService.writeDICache(deliveryInstruction.getId());
        }
    }

    public void saveDeliveryInstructionAll() {
        List<DeliveryInstruction> dis = deliveryInstructionRepository.findAll();
        System.out.println("di length is: *************** " + dis.size());
        for (DeliveryInstruction deliveryInstruction : dis) {
            cacheService.writeDICache(deliveryInstruction.getId());
        }
    }

    public void saveDeliveryInstructionAll_improve(int start) {
        List<DeliveryInstruction> dis = deliveryInstructionRepository.getDiForUpdateCacheAll(start);
        System.out.println("di length is: *************** " + dis.size());
        for (DeliveryInstruction deliveryInstruction : dis) {
            cacheService.writeDICache(deliveryInstruction.getId());
        }
    }
    
    public void updateSI(String refNumber) {
        
    }

    public void saveShippingInstruction() {
        List<ShippingInstruction> sis = shippingInstructionRepository.findByStatus(Byte.valueOf("0"));
        for (ShippingInstruction shippingInstruction : sis) {
            System.out.println("Inside new");
            publisher.publishEvent(new SIUpdatedEvent(shippingInstruction.getRefNumber()));
        }
        //System.out.println("******si length is: *************** " + sis.size());

    }

    public void saveShippingInstructionAll() {
        List<ShippingInstruction> sis = shippingInstructionRepository.findAll();
//        System.out.println("si length is: *************** " + sis.size());
//        for (ShippingInstruction shippingInstruction : sis) {
//            //System.out.println("id is:" + shippingInstruction.getId());
//            cacheService.writeSICache(shippingInstruction.getId());
//
//        }
        for (ShippingInstruction shippingInstruction : sis) {
            System.out.println("Inside new");
            publisher.publishEvent(new SIUpdatedEvent(shippingInstruction.getRefNumber()));
        }
        //System.out.println("******si length is: *************** " + sis.size());
    }

    public void saveShippingInstructionAll_improve(int start) {
        List<ShippingInstruction> sis = shippingInstructionRepository.getSiForUpdateCacheAll(start);
//        System.out.println("si length is: *************** " + sis.size());
//        for (ShippingInstruction shippingInstruction : sis) {
//            //System.out.println("id is:" + shippingInstruction.getId());
//            cacheService.writeSICache(shippingInstruction.getId());
//
//        }
        for (ShippingInstruction shippingInstruction : sis) {
            System.out.println("Inside new " + shippingInstruction.getRefNumber());
            publisher.publishEvent(new SIUpdatedEvent(shippingInstruction.getRefNumber()));
        }
        
        //System.out.println("******si length is: *************** " + sis.size());
    }

    @Transactional
    public void saveProcesssingInstruction() {
        List<ProcessingInstruction> dis = processingInstructionRepository.findAll(
            Specifications.where(new PendingCompletionStatus(CompletionStatus.PENDING)));
        dis.forEach((processingInstruction) -> {
            //cacheService.writeDICache(deliveryInstruction.getId());
            taskExecutor.execute(PICacheWorker.builder()
                .cacheService(cacheService)
                .processingInstruction(processingInstruction)
                .build());
        });

    }

    public void saveProcesssingInstructionAll() {
        List<ProcessingInstruction> pis = processingInstructionRepository.findAll();
        for (ProcessingInstruction processingInstruction : pis) {
            taskExecutor.execute(PICacheWorker.builder()
                .cacheService(cacheService)
                .processingInstruction(processingInstruction)
                .build());
        }
    }

    public void saveProcesssingInstructionAll_improve(int start) {
        List<ProcessingInstruction> pis = processingInstructionRepository.getPiForUpdateCacheAll(start);
        System.out.println("pi length is: *************** " + pis.size());
        for (ProcessingInstruction processingInstruction : pis) {
            taskExecutor.execute(PICacheWorker.builder()
                .cacheService(cacheService)
                .processingInstruction(processingInstruction)
                .build());
        }

    }

    public void saveSampleSent() {
        List<SampleSent> sss = sampleSentRepository.findByApprovalStatus(Byte.valueOf("0"));
        for (SampleSent sampleSent : sss) {
            cacheService.writeSampleSentCache(sampleSent.getId());
        }

    }

    public void saveSampleSentAll_improve() {
        List<SampleSent> sss = sampleSentRepository.getSampleSentsForUpdateCacheAll();
        for (SampleSent sampleSent : sss) {
            cacheService.writeSampleSentCache(sampleSent.getId());
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 24 * 60) // 1day
    public void initDailyBasis() {
        dailyBasicAppService.createNextTerminalMonthsIfNotExisted();
    }
    
    @Scheduled(cron = "0 0/10 * * * *")
    @Transactional
    public void saveCacheWorker() {
        System.out.println("saveCacheWorker: " + new Date());
        List<DeliveryInstruction> dis = deliveryInstructionRepository.findAll(new DIPendingCompletionStatus());
        for(DeliveryInstruction di: dis) {
            taskExecutor.execute(DICacheWorker.builder()
                .deliveryInstruction(di)
                .cacheService(cacheService)
                .build());
        }
        System.out.println("saveCacheWorker end: " + new Date());
    }
    
    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void initRefNumbers() {
        System.out.println("initRefNumbers");
        initService.initOnNewYear();
        System.out.println("initRefNumbers end");
    }

}
