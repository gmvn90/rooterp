package com.swcommodities.wsmill.formController;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.TransactionItemRepository;
import com.swcommodities.wsmill.repository.TransactionRepository;


/**
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("transactionForm") // Specify attributes to be stored in the session
@Transactional
public class TransactionController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping(value = "transaction.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) throws Exception{
        TransactionForm form = new TransactionForm();
        Transaction transaction = transactionRepository.findOne(id);
        form.setItem(transaction);
        model.put("transactionForm", form);
        model.put("approvalStatuses", InstructionStatus.getApprovalStatuses());
        model.put("invoicedStatuses", InstructionStatus.getInvoicedStatus());
        model.put("transactionTypes", InstructionStatus.getTransactionTypes());

        return "transaction";
    }

    @RequestMapping(value = "transaction.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("transactionForm") TransactionForm transactionForm,
                       Map<String, Object> model, HttpServletRequest req) {
        Transaction transaction = transactionForm.getItem();
        if (transactionRepository.findOne(transaction.getId()).getInvoicedStatus().equals(1)) {
            return "redirect:/transaction.htm?id=" + transaction.getId();
        }
        transactionRepository.save(transaction);
        Set<TransactionItem> transactionItems = transactionForm.getItem().getTransactionItems();
        transactionItemRepository.save(transactionItems);

        return "redirect:/transaction.htm?id=" + transaction.getId();
    }

    @RequestMapping(value = "transaction_saveApprovalStatus.htm", method = RequestMethod.POST)
    public String updateApprovalStatus(@ModelAttribute("transactionForm") TransactionForm transactionForm, final RedirectAttributes redirectAttributes,
                               Map<String, Object> model) {
        Transaction transaction = transactionForm.getItem();
        if (transactionRepository.findOne(transaction.getId()).getInvoicedStatus().equals(1)) {
            return "redirect:/transaction.htm?id=" + transaction.getId();
        }

        transaction.setApproveUser(getCurrentUser());
        transaction.setApprovalDate(new Date());

        transactionRepository.save(transaction);

        return "redirect:/transaction.htm?id=" + transaction.getId();
    }

    @RequestMapping(value = "transaction_saveInvoicedStatus.htm", method = RequestMethod.POST)
    public String updateInvoicedStatus(@ModelAttribute("transactionForm") TransactionForm transactionForm, final RedirectAttributes redirectAttributes,
                                      Map<String, Object> model) {
        Transaction transaction = transactionForm.getItem();
        if (transactionRepository.findOne(transaction.getId()).getInvoicedStatus().equals(1)) {
            return "redirect:/transaction.htm?id=" + transaction.getId();
        }
        transaction.setInvoicedUser(getCurrentUser());
        transaction.setInvoicedDate(new Date());

        transactionRepository.save(transaction);

        return "redirect:/transaction.htm?id=" + transaction.getId();
    }

}