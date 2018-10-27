package com.swcommodities.wsmill.formController;

import static java.lang.Math.toIntExact;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swcommodities.wsmill.exels.InvoiceInExcel;
import com.swcommodities.wsmill.hibernate.dto.BankAccount;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceCalculationResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.TransactionCardViewResult;
import com.swcommodities.wsmill.repository.BankAccountRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.repository.OtherTransactionRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;
import com.swcommodities.wsmill.repository.TransactionRepository;
import com.swcommodities.wsmill.service.InvoiceService;
import com.swcommodities.wsmill.utils.Converter;


/**
 * @author kiendn
 */
@Transactional
@SessionAttributes("invoiceForm")
@Controller
public class InvoiceController extends PagingController {

    private static Logger logger = Logger.getLogger(InvoiceController.class);

    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired PaymentRepository paymentRepository;

    @Autowired OtherTransactionRepository otherTransactionRepository;
    
    @Autowired BankAccountRepository bankAccountRepository;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping(value = "invoice-create", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer clientId,
                      @RequestParam(required = false) String clientRef) throws Exception{
        if(clientId == null) {
            clientId = -1;
        }
        if(clientRef == null) {
            clientRef = "";
        }
        boolean isPreGenerating = (clientId == -1 ? true: false);
        if (!isPreGenerating) {
            List<TransactionCardViewResult> deposits = invoiceService.getDITransactions(clientId);
            Collections.sort(deposits, new Comparator<TransactionCardViewResult>() {
                @Override
                public int compare(TransactionCardViewResult o1, TransactionCardViewResult o2) {
                    return o1.getInsRef().compareTo(o2.getInsRef());
                }
            });
            model.put("deposits", deposits);
            List<TransactionCardViewResult> upgrades = invoiceService.getPITransactions(clientId);
            Collections.sort(upgrades, new Comparator<TransactionCardViewResult>() {
                @Override
                public int compare(TransactionCardViewResult o1, TransactionCardViewResult o2) {
                    return o1.getInsRef().compareTo(o2.getInsRef());
                }
            });
            model.put("upgrades", upgrades);
            List<TransactionCardViewResult> withdraws = invoiceService.getSITransactions(clientId);
            Collections.sort(withdraws, new Comparator<TransactionCardViewResult>() {
                @Override
                public int compare(TransactionCardViewResult o1, TransactionCardViewResult o2) {
                    return o1.getInsRef().compareTo(o2.getInsRef());
                }
            });
            model.put("withdraws", withdraws);
        }
        model.put("clientId", clientId);
        model.put("clients", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
        model.put("clientRef", clientRef);
        model.put("isPreGenerating", isPreGenerating);

        return "invoice-create";
    }

    @RequestMapping(value = "invoice", method = RequestMethod.POST)
    public String post(Map<String, Object> model, @RequestParam(required = false) Integer clientIdForInvoice,
                      @RequestParam(required = false) String clientRefForInvoice,
                       @RequestParam(required = false) int[] selectedTransIds) throws Exception{
        Invoice invoice = new Invoice();
        invoice.setRefNumber(invoiceRepository.getNewInvoiceRef());
        invoice.setClient(companyRepository.findOne(clientIdForInvoice));
        invoice.setClientRef(clientRefForInvoice);
        invoice.setApprovalStatus(0);//pending
        invoice.setApprovalUser(getCurrentUser());
        invoice.setApprovalDate(new Date());
        invoice.setCompletionStatus(0);//pending
        invoice.setCompletionUser(getCurrentUser());
        invoice.setCompletionDate(new Date());
        invoice.setRemark("");
        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, 7);
        Date dueDate = c.getTime();
        invoice.setDueDate(dueDate);
        invoice = invoiceRepository.save(invoice);
        if(selectedTransIds != null) {
            for(int i : selectedTransIds) {
                Transaction transaction = transactionRepository.findOne(i);
                transaction.setInvoice(invoice);
                transaction.setInvoicedStatus(1);//invoiced
                transaction.setInvoicedUser(getCurrentUser());
                transaction.setInvoicedDate(new Date());
                transactionRepository.save(transaction);
            }
        }

        return "redirect:/invoice.htm?id=" + invoice.getId();
    }

    @RequestMapping(value = "invoice.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @ModelAttribute("paymentForm") PaymentForm paymentForm,
                      @ModelAttribute("otherTransactionForm") OtherTransactionForm otherTransactionForm, @RequestParam(required = false) Integer id) {
        InvoiceForm form = new InvoiceForm();
        Invoice invoice = invoiceRepository.findOne(id);
        form.setItem(invoice);
        model.put("invoiceForm", form);
        model.put("approvalStatuses", InstructionStatus.getApprovalStatuses());
        model.put("completionStatuses", InstructionStatus.getInvoiceCompletionStatuses());
        List<TransactionCardViewResult> dis = invoiceService.getDITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> pis = invoiceService.getPITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> sis = invoiceService.getSITransactionsByInvoice(invoice.getId());
        List<OtherTransaction> others = invoiceService.getOtherTransactions(invoice.getId());
        List<Payment> payments = paymentRepository.getPaymentsByInvoice(invoice.getId());
        model.put("banks", bankAccountRepository.findAll());
        Date dateForCalculatingDateOverDue = new Date();
        if (!payments.isEmpty()) {
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
            invoice.setLastPaymentDate(dateForCalculatingDateOverDue);
        }
        LocalDate now = new java.sql.Date(dateForCalculatingDateOverDue.getTime()).toLocalDate();
        LocalDate due = new java.sql.Date(invoice.getDueDate().getTime()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(due, now);
        invoice.setDaysOverDue(toIntExact(daysBetween));
        model.put("daysOverDue", toIntExact(daysBetween));
        model.put("deposits", dis);
        model.put("upgrades", pis);
        model.put("withdraws", sis);
        InvoiceCalculationResult result = new InvoiceCalculationResult(dis, pis, sis, others, payments);
        model.put("result", result);

        return "invoice";
    }

    @RequestMapping(value = "invoice_saveApprovalStatus.htm", method = RequestMethod.POST)
    public String updateApprovalStatus(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        Invoice invoice = invoiceForm.getItem();
        if (invoiceRepository.getOne(invoice.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + invoice.getId();
        }
        invoice.setApprovalUser(getCurrentUser());
        invoice.setApprovalDate(new Date());

        invoiceRepository.save(invoice);

        return "redirect:/invoice.htm?id=" + invoice.getId();
    }

    @RequestMapping(value = "invoice_saveCompletionStatus.htm", method = RequestMethod.POST)
    public String updateCompletionStatus(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        Invoice invoice = invoiceForm.getItem();
        if (invoiceRepository.getOne(invoice.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + invoice.getId();
        }
        invoice.setCompletionUser(getCurrentUser());
        invoice.setCompletionDate(new Date());

        invoiceRepository.save(invoice);

        return "redirect:/invoice.htm?id=" + invoice.getId();
    }

    @RequestMapping(value = "invoice_saveCommonInfo.htm", method = RequestMethod.POST)
    public String invoice_saveCommonInfo(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm, final RedirectAttributes redirectAttributes,
                                         Map<String, Object> model) {
        Invoice invoice = invoiceForm.getItem();
        if (invoiceRepository.getOne(invoice.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + invoice.getId();
        }
        invoiceRepository.save(invoice);

        return "redirect:/invoice.htm?id=" + invoice.getId();
    }
    
    @RequestMapping(value = "invoice/addNewPayment.htm", method = RequestMethod.POST)
    public String addNewPayment(@ModelAttribute("paymentForm") PaymentForm paymentForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        Invoice invoice = paymentForm.getItem().getInvoice();
        if (invoiceRepository.getOne(invoice.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + invoice.getId();
        }
        Payment payment = paymentForm.getItem();
        payment.setRefNumber(paymentRepository.getNewPaymentRef());
        payment.setApprovalUser(getCurrentUser());
        paymentRepository.save(payment);

        return "redirect:/invoice.htm?id=" + payment.getInvoice().getId();
    }
    
    @RequestMapping(value = "invoice/updatePayment.htm", method = RequestMethod.POST)
    public String updatePayment(@ModelAttribute("paymentForm") PaymentForm paymentForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        int id = Integer.valueOf(request.getParameter("id"));
        String bank = request.getParameter("bank");
        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.valueOf(request.getParameter("amount"));
        System.out.println("request.getParameter(\"paidDate\") " + request.getParameter("paidDate"));
        Date paidDate = new Date();
        try{
            paidDate = Converter.getDateValue(request.getParameter("paidDate"));
        } catch(Exception e) {
            System.out.println("ex: " + e);
        }
     
        Payment payment = paymentRepository.findOne(id);
        if (invoiceRepository.getOne(payment.getInvoice().getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + payment.getInvoice().getId();
        }
        payment.setBank(bank);
        payment.setAccountNumber(accountNumber);
        payment.setAmount(amount);
        payment.setApprovalUser(getCurrentUser());
        payment.setPaidDate(paidDate);
        payment.setCreated(new Date());
        
        
        paymentRepository.save(payment);

        return "redirect:/invoice.htm?id=" + payment.getInvoice().getId();
    }

    @RequestMapping(value = "addNewOtherTransaction.htm", method = RequestMethod.POST)
    public String addNewOtherTransaction(@ModelAttribute("otherTransactionForm") OtherTransactionForm otherTransactionForm, final RedirectAttributes redirectAttributes,
                                Map<String, Object> model) {
        Invoice invoice = otherTransactionForm.getItem().getInvoice();
        if (invoiceRepository.getOne(invoice.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + invoice.getId();
        }
        OtherTransaction otherTransaction = otherTransactionForm.getItem();
        otherTransaction.setTotal(otherTransaction.getTons()*otherTransaction.getCost());
        otherTransactionRepository.save(otherTransaction);
        return "redirect:/invoice.htm?id=" + otherTransaction.getInvoice().getId();
    }

    @RequestMapping(value = "updateOtherTransaction.htm", method = RequestMethod.POST)
    public String updateOtherTransaction(@ModelAttribute("otherTransactionForm") OtherTransactionForm otherTransactionForm, final RedirectAttributes redirectAttributes,
                                Map<String, Object> model) {
        int id = Integer.valueOf(request.getParameter("id"));
        String refNumber = request.getParameter("refNumber");
        String description = request.getParameter("description");
        double tons = Double.valueOf(request.getParameter("tons"));
        double cost = Double.valueOf(request.getParameter("cost"));

        OtherTransaction otherTransaction = otherTransactionRepository.findOne(id);
        if (invoiceRepository.getOne(otherTransaction.getInvoice().getId()).getCompletionStatus().equals(1)) {
            return "redirect:/invoice.htm?id=" + otherTransaction.getInvoice().getId();
        }
        otherTransaction.setRefNumber(refNumber);
        otherTransaction.setDescription(description);
        otherTransaction.setTons(tons);
        otherTransaction.setCost(cost);
        otherTransaction.setTotal(tons*cost);
        otherTransactionRepository.save(otherTransaction);

        return "redirect:/invoice.htm?id=" + otherTransaction.getInvoice().getId();
    }

    @RequestMapping(value = "getInvoiceExcel", method = RequestMethod.POST)
    @ResponseBody
    public String getInvoiceExcel() {
        String webPath = context.getContextPath() + "/";
        Integer invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
        Invoice invoice = invoiceRepository.getOne(invoiceId);
        List<TransactionCardViewResult> dis = invoiceService.getDITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> pis = invoiceService.getPITransactionsByInvoice(invoice.getId());
        List<TransactionCardViewResult> sis = invoiceService.getSITransactionsByInvoice(invoice.getId());
        List<OtherTransaction> others = invoiceService.getOtherTransactions(invoice.getId());
        BankAccount bankAccount = invoice.getBankAccount();
        InvoiceInExcel invoiceInExcel = new InvoiceInExcel(context.getRealPath(""), invoice, dis, pis, sis, others, bankAccount);
        return invoiceInExcel.generateInvoice(webPath);
    }

}