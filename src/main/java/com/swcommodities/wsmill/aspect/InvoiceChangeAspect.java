package com.swcommodities.wsmill.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.swcommodities.wsmill.hibernate.dto.BelongToFinanceContract;
import com.swcommodities.wsmill.hibernate.dto.BelongToInvoice;
import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.service.FinanceContractService;
import com.swcommodities.wsmill.service.InvoiceService;
/**
 *
 * @author macOS
 */

@Aspect
public class InvoiceChangeAspect {
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired InvoiceService invoiceService;
    @Autowired FinanceContractService financeContractService;
    
    @After("execution(* com.swcommodities.wsmill.repository.OtherTransactionRepository.save(..)) || execution(* com.swcommodities.wsmill.repository.PaymentRepository.save(..)) || execution(* com.swcommodities.wsmill.repository.TransactionRepository.save(..))")
	public void saveTransactionOrPaymentEvent(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        Object signatureArg = signatureArgs[0];
        BelongToInvoice transaction;
        try {
            transaction = (BelongToInvoice) signatureArg;
        } catch (Exception e) {
            Iterable<BelongToInvoice> transactions = (Iterable<BelongToInvoice>) signatureArg;
            transaction = transactions.iterator().next();
        }
        Invoice invoice = transaction.getInvoice();
        
        if(invoice != null) {
            saveInvoiceCost(invoice.getId());
            System.out.println("InvoiceChangeAspect saveTransactionOrPaymentEvent other");
        } else {
            System.out.println("InvoiceChangeAspect saveTransactionOrPaymentEvent invoice null");
        }
	}
    
    @After("execution(* com.swcommodities.wsmill.repository.TransactionItemRepository.save(..))")
	public void saveTransactionItemEvent(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        Object signatureArg = signatureArgs[0];
        TransactionItem item;
        try {
            item = (TransactionItem) signatureArg;
        } catch (Exception e) {
            Iterable<TransactionItem> items = (Iterable<TransactionItem>) signatureArg;
            try {
                item = items.iterator().next();
            } catch (Exception e1) {
                System.out.println(e1.toString());
                item = null;
            }
            
        }
        if(item != null) {
            Transaction transaction = item.getTransaction();
            if(transaction != null) {
                if(transaction.getInvoice() != null) {
                    saveInvoiceCost(transaction.getInvoice().getId());
                } else {
                    System.out.println("InvoiceChangeAspect transaction.getInvoice() != null");
                }
            } else {
                System.out.println("InvoiceChangeAspect transaction != null");
            }
        }
	}
    
    private void saveInvoiceCost(int invoiceId) {
        System.out.println("saveInvoiceCostinvoiceService: " + invoiceService);
        invoiceService.saveInvoiceWithCostInfo(invoiceId);
    }

    @After("execution(* com.swcommodities.wsmill.repository.InterestPaymentRepository.save(..)) || execution(* com.swcommodities.wsmill.repository.PaymentRepository.save(..))")
    public void saveFinanceReportOnPaymentEvent(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        Object signatureArg = signatureArgs[0];
        BelongToFinanceContract belongToFinanceContract;
        try {
            belongToFinanceContract = (BelongToFinanceContract) signatureArg;
        } catch (Exception e) {
            Iterable<BelongToFinanceContract> belongToFinanceContracts = (Iterable<BelongToFinanceContract>) signatureArg;
            belongToFinanceContract = belongToFinanceContracts.iterator().next();
        }
        FinanceContract financeContract = belongToFinanceContract.getFinanceContract();
        if(financeContract != null) {
            saveFinanceContractDetailNumber(financeContract.getId());
            System.out.println("InvoiceChangeAspect saveTransactionOrPaymentEvent other");
        } else {
            System.out.println("InvoiceChangeAspect saveTransactionOrPaymentEvent invoice null");
        }
    }

    private void saveFinanceContractDetailNumber(int financeContractId) {
        System.out.println("saveInvoiceCostinvoiceService: " + invoiceService);
        financeContractService.saveFinanceContractDetailNumber(financeContractId);
    }
}
