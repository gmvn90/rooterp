package com.swcommodities.wsmill.formController;

import static java.lang.Math.toIntExact;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.domain.service.CostCalculator;
import com.swcommodities.wsmill.exels.ConsignmentContractExcel;
import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.BankAccountRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.DailyBasisRepository;
import com.swcommodities.wsmill.repository.FinanceContractRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.InterestPaymentRepository;
import com.swcommodities.wsmill.repository.LocationMasterRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import com.swcommodities.wsmill.service.FinanceContractService;
import com.swcommodities.wsmill.utils.Converter;


/**
 * @author kiendn
 */
@Transactional
@SessionAttributes("financeContractForm")
@Controller
public class FinanceContractController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;

    @Autowired
    private FinanceContractRepository financeContractRepository;
    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private OriginRepository originRepository;
    @Autowired
    private QualityRepository qualityRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private LocationMasterRepository locationMasterRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private InterestPaymentRepository interestPaymentRepository;
    @Autowired
    private CostCalculator costCalculator;
    @Autowired private FinanceContractService financeContractService;
    @Autowired private DailyBasisRepository dailyBasisRepository;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    private boolean isEdit(FinanceContract financeContract) {
        return financeContract.getId() != null;
    }

    @RequestMapping(value = "finance-contract.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @ModelAttribute("paymentForm") PaymentForm paymentForm, @ModelAttribute("interestPaymentForm") InterestPaymentForm interestPaymentForm,
                      @RequestParam(required = false) Integer id,
        @RequestParam(required = false) Integer tryBankId) {
        FinanceContractForm form = new FinanceContractForm();
        int currentId = -1;
        boolean is_form_edit = false;
        boolean tryBank = false;
        if(id != null) {
            currentId = id;
            is_form_edit = true;
            FinanceContract financeContract = financeContractRepository.findOne(id);
            form.setItem(financeContract);

            List<Payment> payments = paymentRepository.getPaymentsByFinanceContract(currentId);
            double tempPaymentRemaining = financeContract.getTotalFinanced();
            for (Payment payment: payments) {
                tempPaymentRemaining = tempPaymentRemaining - payment.getAmount();
                payment.setRemaining(tempPaymentRemaining);
            }
            model.put("payments", payments);

            List<InterestPayment> interestPayments = interestPaymentRepository.getInterestPaymentsByFinanceContract(currentId);
            double tempInterestPaymentRemaining = financeContract.getInterestIncome();
            for (InterestPayment interestPayment: interestPayments) {
                tempInterestPaymentRemaining = tempInterestPaymentRemaining - interestPayment.getAmount();
                interestPayment.setRemaining(tempInterestPaymentRemaining);
            }
            model.put("interestPayments", interestPayments);
            model.put("balancedUnpaid", tempPaymentRemaining + tempInterestPaymentRemaining);

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
            }
            LocalDate now = new java.sql.Date(dateForCalculatingDateOverDue.getTime()).toLocalDate();
            if (financeContract.getCompletionStatus() == 0) {
                now = new java.sql.Date((new Date()).getTime()).toLocalDate();
            }
            LocalDate due = new java.sql.Date(financeContract.getDueDate().getTime()).toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(due, now);
            financeContract.setDaysOverdue(toIntExact(daysBetween));
            model.put("daysOverDue", toIntExact(daysBetween));
        }

        if(tryBankId != null) {
            tryBank = true;
            form.getItem().setBankAccount(bankAccountRepository.findOne(tryBankId));
        }
        
        model.put("tryBank", tryBank);
        model.put("financeContractForm", form);
        model.put("clients", companyRepository.findByCompanyTypes_IdOrderByNameAsc(companyTypeMasterRepository.findByLocalName("Client").getId()));
        model.put("banks", bankAccountRepository.findAll());
        model.put("dailyBasises", dailyBasisRepository.findAll());
        model.put("origins", originRepository.findAll());
        model.put("qualities", qualityRepository.findAll());
        model.put("grades", gradeRepository.findAllByOrderByNameAsc());
        model.put("locations", locationMasterRepository.findAllByOrderByNameAsc());
        model.put("approvalStatuses", InstructionStatus.getApprovalStatusesInteger());
        model.put("completionStatuses", InstructionStatus.getInvoiceCompletionStatuses());
        model.put("is_form_edit", is_form_edit);
        model.put("currentId", currentId);
        return "finance-contract";
    }

    @RequestMapping(value = "finance-contract.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("financeContractForm") FinanceContractForm financeContractForm,
                       Map<String, Object> model) {
        FinanceContract financeContract = financeContractForm.getItem();
        if(! isEdit(financeContract)) {

            financeContract.setRefNumber(financeContractRepository.getNewFinanceContractRef());

            financeContract.setApprovalStatus(0);
            financeContract.setApprovalUser(getCurrentUser());
            financeContract.setApprovalDate(new Date());

            financeContract.setCompletionStatus(0);
            financeContract.setCompletionUser(getCurrentUser());
            financeContract.setCompletionDate(new Date());

            financeContract.setMonthlyStorage(costCalculator.getStorageInContainerSizeLotCost(financeContract.getClient().getId()));
            financeContractService.saveFinanceContractDetailNumber(financeContract.getId());

        } else {
            if (financeContractRepository.getOne(financeContract.getId()).getCompletionStatus().equals(1)) {
                return "redirect:/finance-contract.htm?id=" + financeContract.getId();
            }
        }
        financeContractRepository.save(financeContract);
        return "redirect:/finance-contract.htm?id=" + financeContract.getId();
    }

    @RequestMapping(value = "financeContract_saveApprovalStatus.htm", method = RequestMethod.POST)
    public String updateApprovalStatus(@ModelAttribute("financeContractForm") FinanceContractForm financeContractForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        FinanceContract financeContract = financeContractForm.getItem();
        if (financeContractRepository.getOne(financeContract.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + financeContract.getId();
        }
        financeContract.setApprovalUser(getCurrentUser());
        financeContract.setApprovalDate(new Date());

        financeContractRepository.save(financeContract);

        return "redirect:/finance-contract.htm?id=" + financeContract.getId();
    }

    @RequestMapping(value = "financeContract_saveCompletionStatus.htm", method = RequestMethod.POST)
    public String updateCompletionStatus(@ModelAttribute("financeContractForm") FinanceContractForm financeContractForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        FinanceContract financeContract = financeContractForm.getItem();
        if (financeContractRepository.getOne(financeContract.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + financeContract.getId();
        }
        financeContract.setCompletionUser(getCurrentUser());
        financeContract.setCompletionDate(new Date());

        financeContractRepository.save(financeContract);

        return "redirect:/finance.htm?id=" + financeContract.getId();
    }
    
    @RequestMapping(value = "finance/addNewPayment.htm", method = RequestMethod.POST)
    public String addNewPayment(@ModelAttribute("paymentForm") PaymentForm paymentForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        FinanceContract financeContract = paymentForm.getItem().getFinanceContract();
        if (financeContractRepository.getOne(financeContract.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + financeContract.getId();
        }
        Payment payment = paymentForm.getItem();
        payment.setRefNumber(paymentRepository.getNewPaymentRef());
        payment.setApprovalUser(getCurrentUser());
        payment.setPaidDate(new Date());
        paymentRepository.save(payment);

        return "redirect:/finance-contract.htm?id=" + payment.getFinanceContract().getId();
    }
    
    @RequestMapping(value = "finance/updatePayment.htm", method = RequestMethod.POST)
    public String updatePayment(@ModelAttribute("paymentForm") PaymentForm paymentForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        int id = Integer.valueOf(request.getParameter("id"));
        String bank = request.getParameter("bank");
        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.valueOf(request.getParameter("amount"));
        Date paidDate = new Date();
        try{
            paidDate = Converter.getDateValue(request.getParameter("paidDate"));
        } catch(Exception e) {
            System.out.println("ex: " + e);
        }
        
        Payment payment = paymentRepository.findOne(id);
        if (financeContractRepository.getOne(payment.getFinanceContract().getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + payment.getFinanceContract().getId();
        }
        payment.setBank(bank);
        payment.setAccountNumber(accountNumber);
        payment.setAmount(amount);
        payment.setApprovalUser(getCurrentUser());
        payment.setPaidDate(paidDate);
        paymentRepository.save(payment);

        return "redirect:/finance-contract.htm?id=" + payment.getFinanceContract().getId();
    }

    @RequestMapping(value = "finance/addNewInterestPayment.htm", method = RequestMethod.POST)
    public String addNewInterestPayment(@ModelAttribute("interestPaymentForm") InterestPaymentForm interestPaymentForm, final RedirectAttributes redirectAttributes,
                                Map<String, Object> model) {
        FinanceContract financeContract = interestPaymentForm.getItem().getFinanceContract();
        if (financeContractRepository.getOne(financeContract.getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + financeContract.getId();
        }
        InterestPayment interestPayment = interestPaymentForm.getItem();
        interestPayment.setRefNumber(interestPaymentRepository.getNewInterestPaymentRef());
        interestPayment.setApprovalUser(getCurrentUser());
        interestPaymentRepository.save(interestPayment);

        return "redirect:/finance-contract.htm?id=" + interestPayment.getFinanceContract().getId();
    }

    @RequestMapping(value = "finance/updateInterestPayment.htm", method = RequestMethod.POST)
    public String updateInterestPayment(@ModelAttribute("interestPaymentForm") InterestPaymentForm interestPaymentForm, final RedirectAttributes redirectAttributes,
                                Map<String, Object> model) {
        int id = Integer.valueOf(request.getParameter("id"));
        String bank = request.getParameter("bank");
        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.valueOf(request.getParameter("amount"));

        InterestPayment interestPayment = interestPaymentRepository.findOne(id);
        if (financeContractRepository.getOne(interestPayment.getFinanceContract().getId()).getCompletionStatus().equals(1)) {
            return "redirect:/finance-contract.htm?id=" + interestPayment.getFinanceContract().getId();
        }
        interestPayment.setBank(bank);
        interestPayment.setAccountNumber(accountNumber);
        interestPayment.setAmount(amount);
        interestPayment.setApprovalUser(getCurrentUser());
        interestPaymentRepository.save(interestPayment);

        return "redirect:/finance-contract.htm?id=" + interestPayment.getFinanceContract().getId();
    }

    @RequestMapping(value = "getConsignmentContract", method = RequestMethod.POST)
    @ResponseBody
    public String getConsignmentContract() {
        String webPath = context.getContextPath() + "/";
        Integer financeContractId = Integer.parseInt(request.getParameter("financeContractId"));
        FinanceContract financeContract = financeContractRepository.getOne(financeContractId);
        double inStoreCost = costCalculator.getInstoreCost(financeContract.getClient().getId());
        ConsignmentContractExcel consignmentContractExcel = new ConsignmentContractExcel(context.getRealPath(""), financeContract);
        return consignmentContractExcel.generateConsignmentContract(webPath, inStoreCost);
    }

}