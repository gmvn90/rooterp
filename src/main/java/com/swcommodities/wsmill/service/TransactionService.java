package com.swcommodities.wsmill.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.DeliveryInstructionRepository;
import com.swcommodities.wsmill.repository.LocationMasterRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.repository.TransactionItemRepository;
import com.swcommodities.wsmill.repository.TransactionRepository;

/**
 * Created by macOS on 4/5/17.
 */
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionItemRepository transactionItemRepository;

    @Autowired
    LocationMasterRepository locationMasterRepository;

    @Autowired
    ShippingInstructionRepository shippingInstructionRepository;

    @Autowired
    DeliveryInstructionRepository deliveryInstructionRepository;

    @Autowired
    ProcessingInstructionRepository processingInstructionRepository;

    @Autowired
    CostCalculator costCalculator;

     @Transactional
    public void createTransactionWhenCompletingDI(DeliveryInstruction di, User user) {
        Transaction transaction = new Transaction();
        transaction.setRefNumber(transactionRepository.getNewTransactionRef());
        transaction.setType(1); //delivery instruction type
        transaction.setApprovalStatus(1); //pending
        transaction.setApprovalDate(new Date());
        transaction.setInvoicedStatus(0); //pending
        transaction.setDi(di);
        transaction.setClient(di.getCompanyMasterByClientId());
        transaction.setClientRef(di.getClientRef());
        transaction.setCreatedUser(user);
        transaction.setApproveUser(user);
        transaction.setLocation(locationMasterRepository.findAll().get(0));
        transaction.setGrade(di.getGradeMaster());
        transaction.setStorageWeightLoss(Double.valueOf(0));
        transaction.setProcessingWeightLoss(Double.valueOf(0));
        transaction = transactionRepository.save(transaction);

        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setTransaction(transaction);
        transactionItem.setInstRef(di.getRefNumber());
        transactionItem.setType(1); //deposit type
        transactionItem.setLocation(locationMasterRepository.findAll().get(0));
        transactionItem.setGrade(di.getGradeMaster());
        transactionItem.setTons(di.getDeliverd());
        transactionItem.setCost(costCalculator.getInstoreCost(di.getCompanyMasterByClientId().getId()));

        transactionItem = transactionItemRepository.save(transactionItem);
    }

    @Transactional
    public void createTransactionWhenCompletingPI(ProcessingInstruction pi, User user) {
        Transaction transaction = new Transaction();
        transaction.setRefNumber(transactionRepository.getNewTransactionRef());
        transaction.setType(2); //processing instruction type
        transaction.setApprovalStatus(0); //pending
        transaction.setApprovalDate(new Date());
        transaction.setInvoicedStatus(0); //pending
        transaction.setPi(pi);
        transaction.setClient(pi.getCompanyMasterByClientId());
        transaction.setClientRef(pi.getClientRef());
        transaction.setCreatedUser(user);
        transaction.setApproveUser(user);
        transaction.setLocation(locationMasterRepository.findAll().get(0));
        transaction.setPiType(pi.getPiType());
        transaction.setGrade(pi.getGradeMaster());
        transaction.setStorageWeightLoss(processingInstructionRepository.getProcessingAllocated(pi.getId()) - processingInstructionRepository.getProcessingInprocess(pi.getId()));
        transaction.setProcessingWeightLoss(processingInstructionRepository.getProcessingInprocess(pi.getId()) - processingInstructionRepository.getProcessingExprocess(pi.getId()));
        transaction = transactionRepository.save(transaction);

        List<TransactionItem> transactionItems = processingInstructionRepository.getPiTransactionItemBasic(pi.getId());
        for (TransactionItem transactionItem: transactionItems) {
            transactionItem.setCost(Double.valueOf(0));
            transactionItem.setTransaction(transaction);
            transactionItem.setInstRef(pi.getRefNumber());
            transactionItem.setLocation(locationMasterRepository.findAll().get(0));
        }
        transactionItemRepository.save(transactionItems);

    }

    public void createTransactionWhenCompletingSI(ShippingInstruction si, User user) {
        Transaction transaction = new Transaction();
        transaction.setRefNumber(transactionRepository.getNewTransactionRef());
        transaction.setType(3); //shipping instruction type
        transaction.setApprovalStatus(1); //pending
        transaction.setApprovalDate(new Date());
        transaction.setInvoicedStatus(0); //pending
        transaction.setSi(si);
        transaction.setClient(si.getCompanyMasterByClientId());
        transaction.setClientRef(si.getClientRef());
        transaction.setCreatedUser(user);
        transaction.setApproveUser(user);
        try {
            transaction.setLocation(locationMasterRepository.findAll().get(0));
        } catch(Exception e) {
            
        }
        
        transaction.setGrade(si.getGradeMaster());
        transaction.setStorageWeightLoss(shippingInstructionRepository.getShippingAllocated(si.getId()) - shippingInstructionRepository.getShippingDeliverd(si.getId()));
        transaction.setProcessingWeightLoss(Double.valueOf(0));
        transaction = transactionRepository.save(transaction);

        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setTransaction(transaction);
        transactionItem.setInstRef(si.getRefNumber());
        transactionItem.setType(2); //withdraw type
        try {
            transaction.setLocation(locationMasterRepository.findAll().get(0));
        } catch(Exception e) {
            
        }
        transactionItem.setGrade(si.getGradeMaster());
        transactionItem.setTons(si.getDeliverdWeight());
        transactionItem.setCost((double) (si.getCostToFob() != null ? si.getCostToFob() : 0));
        transactionItem = transactionItemRepository.save(transactionItem);
    }
    
    @Transactional
    public void createTransactionWhenCompletingSI(int siId, int userId) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(siId);
        User user = new User(userId);
        createTransactionWhenCompletingSI(shippingInstruction, user);
        
    }
}
