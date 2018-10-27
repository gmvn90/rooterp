package com.swcommodities.wsmill.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.cache.DICache;
import com.swcommodities.wsmill.hibernate.dto.cache.PICache;
import com.swcommodities.wsmill.hibernate.dto.cache.SICache;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.SampleTypeAssembler;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructSampleTypeCacheMapper;
import com.swcommodities.wsmill.repository.DICacheRepository;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.repository.PICacheRepository;
import com.swcommodities.wsmill.repository.PackingCategoryRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.SICacheRepository;
import com.swcommodities.wsmill.repository.SSCacheRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.repository.SampleTypeRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;

@Service
@Transactional
public class CacheService {

    @Autowired
    private DeliveryInstructionRepository deliveryInstructionRepository;
    @Autowired
    private ShippingInstructionRepository shippingInstructionRepository;
    @Autowired
    private DICacheRepository diCacheRepository;
    @Autowired
    private SICacheRepository siCacheRepository;
    @Autowired
    private ProcessingInstructionRepository processingInstructionRepository;
    @Autowired
    private PICacheRepository piCacheRepository;
    @Autowired
    private SSCacheRepository ssCacheRepository;
    @Autowired
    private SampleSentRepository sampleSentRepository;
    @Autowired PackingCategoryRepository packingCategoryRepository;
    
    @Autowired SampleTypeRepository sampleTypeRepository;

    public void writeDICache(Integer id) {
        DeliveryInstruction deliveryInstruction = deliveryInstructionRepository.findOne(id);
        writeDICache(deliveryInstruction);
    }

    public void writeDICache(DeliveryInstruction deliveryInstruction) {
        DICache cache = diCacheRepository.findByDeliveryInstruction(deliveryInstruction);
        if (cache == null) {
            cache = new DICache();
            cache.setDeliveryInstruction(deliveryInstruction);
        }
        cache.setDiRef(deliveryInstruction.getRefNumber());
        try {
            CompanyMaster companyMaster = deliveryInstruction.getCompanyMasterByClientId();
            cache.setClient(companyMaster.getName());
            cache.setClientInt(companyMaster.getId());
        } catch (Exception e) {
        }
        cache.setClientRef(deliveryInstruction.getClientRef());
        try {
            CompanyMaster supplier = deliveryInstruction.getCompanyMasterBySupplierId();
            cache.setSupplier(supplier.getName());
            cache.setSupplierInt(supplier.getId());
        } catch (Exception e) {
        }

        cache.setSupplierRef(deliveryInstruction.getSupplierRef());

        try {
            cache.setOrigin(deliveryInstruction.getOriginMaster().getOrigin());
        } catch (Exception e) {
        }

        try {
            cache.setQuality(deliveryInstruction.getQualityMaster().getName());
        } catch (Exception e) {
        }

        try {
            cache.setGrade(deliveryInstruction.getGradeMaster().getName());
        } catch (Exception e) {
        }

        try {
            cache.setGradeInt(deliveryInstruction.getGradeMaster().getId());
        } catch (Exception e) {
        }

        try {
            cache.setPacking(deliveryInstruction.getPackingMaster().getName());
        } catch (Exception e) {
        }

        //System.out.println("mock1");
        cache.setTotalTons(deliveryInstruction.getTons() != null ? deliveryInstruction.getTons() : 0);
        //System.out.println("mock2");
        Double deliverd = deliveryInstructionRepository.getDeliveryDeliverd(deliveryInstruction.getId());
        if (deliverd == null) {
            deliverd = 0.0;
        }
        cache.setDeliverd(deliverd);
        cache.setBalance(cache.getTotalTons() - cache.getDeliverd());
        //System.out.println("mock3");
        try {
            cache.setStatus(InstructionStatus.getCompletionStatuses().get(deliveryInstruction.getStatus()));
        } catch (Exception e) {
        }
        cache.setStatusInt(deliveryInstruction.getStatus());
        diCacheRepository.save(cache);
    }

    public void writeSICache(Integer id) {
        //ShippingInstruction shippingInstruction = shippingInstructionRepository;
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(id);
        writeSICache(shippingInstruction);
    }
    
    public void writeSICache(String refNumber) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(refNumber);
        writeSICache(shippingInstruction);
    }

    public void writeSICache(ShippingInstruction shippingInstruction) {

        //ShippingInstruction shippingInstruction = shippingInstructionRepository;
        SICache siCache = siCacheRepository.findByShippingInstruction(shippingInstruction);
        if (siCache == null) {
            siCache = new SICache();
            siCache.setShippingInstruction(shippingInstruction);
        }
        siCache.setSiRef(shippingInstruction.getRefNumber());
        try {
            siCache.setClient(shippingInstruction.getCompanyMasterByClientId().getName());
            siCache.setClientInt(shippingInstruction.getCompanyMasterByClientId().getId());
        } catch (Exception e) {
        }
        siCache.setClientRef(shippingInstruction.getClientRef());
        try {
            siCache.setBuyer(shippingInstruction.getCompanyMasterByBuyerId().getName());
            siCache.setBuyerInt(shippingInstruction.getCompanyMasterByBuyerId().getId());
        } catch (Exception e) {
        }
        siCache.setBuyerRef(shippingInstruction.getBuyerRef());
        siCache.setFirstDate(shippingInstruction.getFromDate());
        siCache.setLoadingDate(shippingInstruction.getLoadDate());
        try {
            siCache.setGrade(shippingInstruction.getGradeMaster().getName());
            siCache.setGradeInt(shippingInstruction.getGradeMaster().getId());
        } catch (Exception e) {
        }
        try {
            PackingMaster packingMaster = packingCategoryRepository.findOne(
                Long.valueOf(String.valueOf(shippingInstruction.getShippingCost().getPackingCostCategory()))).getPackingMaster();
            siCache.setPacking(packingMaster.getName());
        } catch (Exception e) {
            siCache.setPacking("");
        }

        try {
            siCache.setDest(shippingInstruction.getPortMasterByDischargePortId().getName());
        } catch (Exception e) {
            siCache.setDest("");
        }
        try {
            siCache.setTotal((double) (shippingInstruction.getQuantity() != null ? shippingInstruction.getQuantity() : 0));
        } catch (Exception e) {
        }
        Double exported = shippingInstructionRepository.getShippingDeliverd(shippingInstruction.getId());
        if (exported == null) {
            exported = 0.0;
        }
        siCache.setExported(siCache.getTotal() - exported);
        siCache.setClosingTime(shippingInstruction.getClosingTime());
        siCache.setwQCert(shippingInstruction.getWeightQualityCertificateCompanyName());
        try {
            siCache.setShippingLine(shippingInstruction.getShippingLineCompanyMaster().getName());
        } catch (Exception e) {
            siCache.setShippingLine("");
        }
        try {
            siCache.setSupplierInt(shippingInstruction.getCompanyMasterBySupplierId().getId());
        } catch (Exception e) {
        }
        siCache.setBookingNo(shippingInstruction.getBookingRef());
        siCache.setClosingDate(shippingInstruction.getClosingDate());
        siCache.setSampleStatus(shippingInstruction.getSampleStatus());
        try {
            siCache.setStatusInt((byte) shippingInstruction.getCompletionStatus().getOrder());
        } catch (Exception e) {
            System.out.println("setStatusInt " + e.getMessage());
        }
        
        try {
            siCache.setRequestStatus(RequestStatus.getRepresentative(shippingInstruction.getRequestStatusEnum()));
        } catch (Exception e) {
            System.out.println("setRequestStatus " + e.getMessage());
        }
        try {
            siCache.setShipmentStatusInt(shippingInstruction.getShipmentStatusEnum().getOrder());
        } catch(Exception e) {
            System.out.println("setShipmentStatusInt " + e.getMessage());
        }
        
        try {
            siCache.setShipmentStatus(CompletionStatus.getRepresentative(shippingInstruction.getShipmentStatusEnum()));
        } catch (Exception e) {
        }
        siCacheRepository.save(siCache);
    }
    
    
    
    public void writeChildSampleSentCaches(String refNumber) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(refNumber);
        shippingInstruction.getSampleSents().forEach(ss -> {
            writeSampleSentCache(ss);
        });
    }

    public SampleSentCache writeSampleSentCache(Integer id) {
        SampleSent sampleSent = sampleSentRepository.findOne(id);
        return writeSampleSentCache(sampleSent);
    }
    
    public SampleSentCache writeSampleTypeCache(String id) {
        SampleTypeAssembler assembler = new SampleTypeAssembler();
        SampleType st = sampleTypeRepository.findOne(id);
        SampleSentCache ssc = ssCacheRepository.findBySampleRef(st.getRefNumber());
        if(ssc == null) {
            return ssCacheRepository.save(assembler.toCache(st));
        }
        ssc = assembler.toCache(st, ssc);
        ssCacheRepository.save(ssc);
        return ssc;      
    }

    public SampleSentCache writeSampleSentCache(SampleSent sampleSent) {

        SampleSentCache sampleSentCache = ssCacheRepository.findBySampleSent(sampleSent);

        if (sampleSentCache == null) {
            sampleSentCache = new SampleSentCache();
            sampleSentCache.setSampleSent(sampleSent);
        }

        sampleSentCache.setSampleRef(sampleSent.getRefNumber());
        if (InstructionStatus.SampleSentType.TYPE.equals(sampleSent.getType())) {
            
        } else {
            sampleSentCache.setType(InstructionStatus.getSampleSentTypes().get(sampleSent.getType()));
            sampleSentCache.setTypeInt(sampleSent.getType());
            try {
                sampleSentCache.setSiRef(sampleSent.getShippingInstructionBySiId().getRefNumber());
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setClient(sampleSent.getShippingInstructionBySiId().getCompanyMasterByClientId().getName());
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setClientInt(sampleSent.getShippingInstructionBySiId().getCompanyMasterByClientId().getId());
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getClientRef() != null) {
                    sampleSentCache.setClientRef(sampleSent.getShippingInstructionBySiId().getClientRef());
                } else {
                    sampleSentCache.setClientRef("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getCompanyMasterByBuyerId() != null) {
                    sampleSentCache.setBuyer(sampleSent.getShippingInstructionBySiId().getCompanyMasterByBuyerId().getName());
                    sampleSentCache.setBuyerInt(sampleSent.getShippingInstructionBySiId().getCompanyMasterByBuyerId().getId());
                } else {
                    sampleSentCache.setBuyer("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getBuyerRef() != null) {
                    sampleSentCache.setBuyerRef(sampleSent.getShippingInstructionBySiId().getBuyerRef());
                } else {
                    sampleSentCache.setBuyerRef("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getFromDate() != null) {
                    sampleSentCache.setFirstDate(sampleSent.getShippingInstructionBySiId().getFromDate());
                } else {
                    sampleSentCache.setFirstDate(null);
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getOriginMaster() != null) {
                    sampleSentCache.setOrigin(sampleSent.getShippingInstructionBySiId().getOriginMaster().getCountry().getShortName());
                } else {
                    sampleSentCache.setOrigin("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getQualityMaster() != null) {
                    sampleSentCache.setQuality(sampleSent.getShippingInstructionBySiId().getQualityMaster().getName());
                } else {
                    sampleSentCache.setQuality("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getShippingInstructionBySiId().getGradeMaster() != null) {
                    sampleSentCache.setGrade(sampleSent.getShippingInstructionBySiId().getGradeMaster().getName());
                } else {
                    sampleSentCache.setGrade("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getCourierMaster() != null) {
                    sampleSentCache.setCourier(sampleSent.getCourierMaster().getName());
                } else {
                    sampleSentCache.setCourier("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getEtaDate() != null) {
                    sampleSentCache.setEtaDate(sampleSent.getEtaDate());
                } else {
                    sampleSentCache.setEtaDate(null);
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getTrackingNo() != null) {
                    sampleSentCache.setaWBNo(sampleSent.getTrackingNo());
                } else {
                    sampleSentCache.setaWBNo("");
                }
            } catch (Exception e) {
            }
            try {
                if (sampleSent.getSentDate() != null) {
                    sampleSentCache.setSentDate(sampleSent.getSentDate());
                } else {
                    sampleSentCache.setSentDate(null);
                }
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setSentStatus(SendingStatus.getRepresentative(sampleSent.getSendingStatusEnum()));
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setSentStatusInt((byte) sampleSent.getSendingStatusEnum().getOrder());
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setApprovalStatus(ApprovalStatus.getRepresentative(sampleSent.getApprovalStatusEnum()));
            } catch (Exception e) {
            }
            try {
                sampleSentCache.setApprovalStatusInt((byte) sampleSent.getApprovalStatusEnum().getOrder());
            } catch (Exception e) {
            }
        }
        ssCacheRepository.save(sampleSentCache);
        return sampleSentCache;
    }
    
    public SampleSentCache writeSampleSentCache(String refNumber) {
        SampleSent ss = sampleSentRepository.findFirstByRefNumber(refNumber);
        SampleSentCache cache = writeSampleSentCache(ss);
        return cache;
    }
    
    public SampleSentCache writeSampleSentCache(SampleSent ss, ApprovalStatus status) {
        SampleSentCache sampleSentCache = writeSampleSentCache(ss);
        sampleSentCache.setApprovalStatus(ApprovalStatus.getAll().get(status));
        sampleSentCache.setApprovalStatusInt((byte) status.getOrder());
        ssCacheRepository.save(sampleSentCache);
        return sampleSentCache;
    }
    
    public SampleSentCache writeSampleSentCache(String refNumber, ApprovalStatus status) {
        SampleSentCache sampleSentCache = writeSampleSentCache(refNumber);
        sampleSentCache.setApprovalStatus(ApprovalStatus.getAll().get(status));
        sampleSentCache.setApprovalStatusInt(Byte.valueOf(String.valueOf(status.ordinal())));
        ssCacheRepository.save(sampleSentCache);
        return sampleSentCache;
    }
    
    public SampleSentCache writeSampleSentCache(SampleSent ss, SendingStatus status) {
        SampleSentCache sampleSentCache = writeSampleSentCache(ss);
        sampleSentCache.setSentStatus(SendingStatus.getAll().get(status));
        sampleSentCache.setSentStatusInt((byte) status.getOrder());
        ssCacheRepository.save(sampleSentCache);
        return sampleSentCache;
    }
    
    public SampleSentCache writeSampleSentCache(String refNumber, SendingStatus status) {
        SampleSentCache sampleSentCache = writeSampleSentCache(refNumber);
        sampleSentCache.setSentStatus(SendingStatus.getAll().get(status));
        sampleSentCache.setSentStatusInt((byte) status.getOrder());
        ssCacheRepository.save(sampleSentCache);
        return sampleSentCache;
    }

    public void writePICache(Integer id) {
        ProcessingInstruction processingInstruction = processingInstructionRepository.findOne(id);
        writePICache(processingInstruction);
    }
    
    public void writePICache(String refNumber) {
        ProcessingInstruction processingInstruction = processingInstructionRepository.findFirstByRefNumber(refNumber);
        writePICache(processingInstruction);
        
    }

    public void writePICache(ProcessingInstruction processingInstruction) {

        PICache piCache = piCacheRepository.findByProcessingInstruction(processingInstruction);
        if (piCache == null) {
            piCache = new PICache();
            piCache.setProcessingInstruction(processingInstruction);
        }
        piCache.setPiRef(processingInstruction.getRefNumber());
        try {
            piCache.setClient(processingInstruction.getCompanyMasterByClientId().getName());
            piCache.setClientInt(processingInstruction.getCompanyMasterByClientId().getId());
        } catch (Exception e) {
        }

        piCache.setClientRef(processingInstruction.getClientRef());
        try {
            piCache.setType(processingInstruction.getPiType().getName());
        } catch (Exception ex) {
        }
        try {
            piCache.setDebitGrade(processingInstruction.getGradeMaster().getName());
        } catch (Exception ex) {
        }
        try {
            piCache.setPacking(processingInstruction.getPackingMaster().getName());
        } catch (Exception ex) {
        }

        piCache.setReqDate(processingInstruction.getCreatedDate());
        Double allocated = processingInstructionRepository.getProcessingAllocated(processingInstruction.getId());
        Double inprocess = processingInstructionRepository.getProcessingInprocess(processingInstruction.getId());
        Double exprocess = processingInstructionRepository.getProcessingExprocess(processingInstruction.getId());

        if (allocated == null) {
            allocated = 0.0;
        }
        if (inprocess == null) {
            inprocess = 0.0;
        }
        if (exprocess == null) {
            exprocess = 0.0;
        }
        piCache.setTotalTons(allocated);
        piCache.setDebitTons(inprocess);
        piCache.setCreditTons(exprocess);
        piCache.setBalance(inprocess - exprocess);
        try {
            piCache.setRequestStatus(RequestStatus.getRepresentative(processingInstruction.getRequestStatusEnum()));
        } catch (Exception e) {
        }

        try {
            piCache.setStatus(CompletionStatus.getRepresentative(processingInstruction.getCompletionStatus()));
        } catch (Exception e) {
        }
        try {
            piCache.setStatusInt((byte) processingInstruction.getCompletionStatus().getOrder());
        } catch(Exception e) {
        
        }
        
        piCacheRepository.save(piCache);

    }

}
