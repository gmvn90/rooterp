package com.swcommodities.wsmill.service;

import static java.lang.Math.toIntExact;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceCalculationResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceQuantityResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.TransactionCardViewResult;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.repository.OtherTransactionRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.TransactionItemRepository;
import com.swcommodities.wsmill.repository.TransactionRepository;

/**
 * Created by macOS on 4/5/17.
 */
@Service
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    TransactionRepository transactionRepository;
    TransactionItemRepository transactionItemRepository;
    CompanyRepository companyRepository;
    ProcessingInstructionRepository processingInstructionRepository;
    OtherTransactionRepository otherTransactionRepository;
    PaymentRepository paymentRepository;
    
    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, TransactionRepository transactionRepository, 
        TransactionItemRepository transactionItemRepository, CompanyRepository companyRepository, 
        ProcessingInstructionRepository processingInstructionRepository, OtherTransactionRepository otherTransactionRepository,
                          PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.companyRepository = companyRepository;
        this.processingInstructionRepository = processingInstructionRepository;
        this.otherTransactionRepository = otherTransactionRepository;
        this.paymentRepository = paymentRepository;
    }

        
    private TransactionCardViewResult getFromDIOrSITransaction(Transaction transaction) {
        TransactionCardViewResult result = new TransactionCardViewResult();
        result.setId(transaction.getId());
        result.setRefNumber(transaction.getRefNumber());
        result.setDate(transaction.getCreated());
        try {
            result.setGrade(transaction.getGrade().getName());
        } catch (Exception e) {
        }
        try {
            result.setLocation(transaction.getLocation().getName());
        } catch (Exception e) {
        }
        try {
            result.setType(transaction.getPiType().getName());
        } catch (Exception e) {
        }
        double cost = 0;
        double tons = 0;
        TransactionItem item = new TransactionItem();
        try {
            item = transaction.getTransactionItems().iterator().next();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            cost = item.getCost();
            result.setCost(cost);
        } catch (Exception e) {
        }
        try {
            tons = item.getTons();
            result.setTons(tons);
        } catch (Exception e) {
        }
        result.setTotal(cost * tons);
        return result;
    }
    
    public TransactionCardViewResult getFromSITransaction(Transaction transaction) {
        TransactionCardViewResult result = getFromDIOrSITransaction(transaction);
        result.setInsId(transaction.getSi().getId());
        result.setInsRef(transaction.getSi().getRefNumber());
        return result;
    }
    
    public TransactionCardViewResult getFromDITransaction(Transaction transaction) {
        TransactionCardViewResult result = getFromDIOrSITransaction(transaction);
        result.setInsId(transaction.getDi().getId());
        result.setInsRef(transaction.getDi().getRefNumber());
        return result;
    }
    
    public TransactionCardViewResult getFromPITransaction(Transaction transaction) {
        TransactionCardViewResult result = new TransactionCardViewResult();
        result.setId(transaction.getId());
        result.setRefNumber(transaction.getRefNumber());
        result.setInsId(transaction.getPi().getId());
        result.setInsRef(transaction.getPi().getRefNumber());
        result.setDate(transaction.getCreated());
        try {
            result.setGrade(transaction.getGrade().getName());
        } catch (Exception e) {
        }
        try {
            result.setLocation(transaction.getLocation().getName());
        } catch (Exception e) {
        }
        try {
            result.setType(transaction.getPiType().getName());
        } catch (Exception e) {
        }
        double total = 0;
        Set<TransactionItem> items = transaction.getTransactionItems();
        for(TransactionItem item: items) {
            total += item.getCost() * item.getTons();
        }
        result.setTotal(total);
        
        double tons = processingInstructionRepository.getProcessingAllocated(transaction.getPi().getId());
        result.setTons(tons);
        try {
            result.setCost(total / tons);
        } catch (Exception e) {
        }
        
        return result;
    }
    
    public List<TransactionCardViewResult> getDITransactions(int clientId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByClientAndType(clientId, InstructionStatus.TransactionType.DI);
        for(Transaction transaction: list) {
            results.add(getFromDITransaction(transaction));
        }
        return results;
    }
    
    public List<TransactionCardViewResult> getSITransactions(int clientId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByClientAndType(clientId, InstructionStatus.TransactionType.SI);
        for(Transaction transaction: list) {
            results.add(getFromSITransaction(transaction));
        }
        return results;
    }
    
    public List<TransactionCardViewResult> getPITransactions(int clientId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByClientAndType(clientId, InstructionStatus.TransactionType.PI);
        for(Transaction transaction: list) {
            results.add(getFromPITransaction(transaction));
        }
        return results;
    }
    
    public List<TransactionCardViewResult> getDITransactionsByInvoice(int invoiceId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByInvoiceAndType(invoiceId, InstructionStatus.TransactionType.DI);
        for(Transaction transaction: list) {
            results.add(getFromDITransaction(transaction));
        }
        return results;
    }
    
    public List<TransactionCardViewResult> getSITransactionsByInvoice(int invoiceId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByInvoiceAndType(invoiceId, InstructionStatus.TransactionType.SI);
        for(Transaction transaction: list) {
            results.add(getFromSITransaction(transaction));
        }
        return results;
    }
    
    public List<TransactionCardViewResult> getPITransactionsByInvoice(int invoiceId) {
        List<TransactionCardViewResult> results = new ArrayList<>();
        List<Transaction> list = transactionRepository.getTransactionsByInvoiceAndType(invoiceId, InstructionStatus.TransactionType.PI);
        for(Transaction transaction: list) {
            results.add(getFromPITransaction(transaction));
        }
        return results;
    } 
    
    public List<OtherTransaction> getOtherTransactions(int invoiceId) {
        List<OtherTransaction> results = otherTransactionRepository.getTransactionsByInvoice(invoiceId);
        return results;
    }
    
    public List<Payment> getPayments(int invoiceId) {
        List<Payment> results = paymentRepository.getPaymentsByInvoice(invoiceId);
        return results;
    }
    
    public InvoiceQuantityResult getInvoiceQuantity(int invoiceId) {
        List<Payment> payments = getPayments(invoiceId);
        List<TransactionCardViewResult> dis = getDITransactionsByInvoice(invoiceId);
        List<TransactionCardViewResult> pis = getPITransactionsByInvoice(invoiceId);
        List<TransactionCardViewResult> sis = getSITransactionsByInvoice(invoiceId);
        List<OtherTransaction> others = getOtherTransactions(invoiceId);
        
        InvoiceCalculationResult result = new InvoiceCalculationResult(dis, pis, sis, others, payments);
        InvoiceQuantityResult quantityResult = new InvoiceQuantityResult();
        quantityResult.setAmount(result.getTotal());
        quantityResult.setPayment(result.getTotalPayment());
        quantityResult.setBalance(result.getTotal() - result.getTotalPayment());
        return quantityResult;
    }
    
    public Invoice getInvoiceWithCostAndDueInfo(int invoiceId) {
        Invoice invoice = invoiceRepository.findOne(invoiceId);
        InvoiceQuantityResult invoiceQuantityResult = getInvoiceQuantity(invoiceId);
        invoice.setAmount(invoiceQuantityResult.getAmount());
        invoice.setBalance(invoiceQuantityResult.getBalance());
        invoice.setPaidAmount(invoiceQuantityResult.getPayment());
        System.out.println("getInvoiceWithCostAndDueInfo: " + invoice.getBalance());
        invoice.setDaysOverDue(getDaysOverdue(invoice));
        return invoice;
    }
    
    public Invoice saveInvoiceWithCostInfo(int invoiceId) {
        Invoice invoice = getInvoiceWithCostAndDueInfo(invoiceId);
        invoiceRepository.save(invoice);
        return invoice;
    }
    
    public int getDaysOverdue(Invoice invoice) {
        List<Payment> payments = paymentRepository.getPaymentsByInvoice(invoice.getId());
        Date dateForCalculatingDateOverDue = new Date();
        if (!payments.isEmpty()) {
            try {
                Collections.sort(payments, new Comparator<Payment>() {
                    @Override
                    public int compare(Payment o1, Payment o2) {
                        if(o1.getPaidDate() == null) {
                            return 1;
                        }
                        if(o2.getPaidDate() == null) {
                            return -1;
                        }
                        return o2.getPaidDate().compareTo(o1.getPaidDate());
                    }
                });
                dateForCalculatingDateOverDue = payments.get(0).getPaidDate();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        LocalDate now = new java.sql.Date(dateForCalculatingDateOverDue.getTime()).toLocalDate();
        LocalDate due;
        try {
            due = new java.sql.Date(invoice.getDueDate().getTime()).toLocalDate();
        } catch (Exception e) {
            due = new java.sql.Date(new Date().getTime()).toLocalDate();
            e.printStackTrace();
        }   
        
        long daysBetween = ChronoUnit.DAYS.between(due, now);
        return toIntExact(daysBetween);
    }
    
}
