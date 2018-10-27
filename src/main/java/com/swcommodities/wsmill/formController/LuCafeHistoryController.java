package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.controller.ForwardController;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.LuCafeHistory;
import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.LuCafeHistoryRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@SessionAttributes("luForm") // Specify attributes to be stored in the session
@Transactional
public class LuCafeHistoryController extends PagingController {

    private static Logger logger = Logger.getLogger(ForwardController.class);

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private LuCafeHistoryRepository luCafeHistoryRepository;

    @Autowired
    private CountryRepository countryRepository;

    private boolean isEdit(LuCafeHistory luCafeHistory) {
        return luCafeHistory.getId() != null;
    }

    private User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping(value = "lu-daily.htm", method = RequestMethod.GET)
    public String get(Map<String, Object> model, @RequestParam(required = false) Integer id) {
        LuCafeHistoryForm form = new LuCafeHistoryForm();
        int currentId = -1;
        boolean is_form_edit = false;
        if (id != null) {
            currentId = id;
            is_form_edit = true;
            LuCafeHistory luCafeHistory = luCafeHistoryRepository.findOne(id);
            form.setItem(luCafeHistory);
        }
        model.put("luForm", form);
        model.put("is_form_edit", is_form_edit);
        model.put("approvalStatuses", InstructionStatus.getApprovalStatusesInteger());
        model.put("currentId", currentId);
        return "lu/lu-daily";
    }

    @RequestMapping(value = "lu-daily.htm", method = RequestMethod.POST)
    public String post(@ModelAttribute("luForm") LuCafeHistoryForm luCafeHistoryForm,
                       Map<String, Object> model) {
        LuCafeHistory luCafeHistory = luCafeHistoryForm.getItem();
        if (luCafeHistoryRepository.findOne(luCafeHistory.getId()).getCompleted()) {
            return "redirect:/lu-daily.htm?id=" + luCafeHistory.getId();
        }
        luCafeHistory.setTotal(luCafeHistory.getIncome() - luCafeHistory.getBeginExpense() - luCafeHistory.getIndayExpense());
        luCafeHistoryRepository.save(luCafeHistory);
        return "redirect:/lu-daily.htm?id=" + luCafeHistory.getId();
    }

    @RequestMapping(value = "lu_saveApprovalStatus.htm", method = RequestMethod.POST)
    public String lu_saveApprovalStatus(@ModelAttribute("luForm") LuCafeHistoryForm luCafeHistoryForm, final RedirectAttributes redirectAttributes,
                                       Map<String, Object> model) {
        LuCafeHistory luCafeHistory = luCafeHistoryForm.getItem();
        if (luCafeHistoryRepository.findOne(luCafeHistory.getId()).getCompleted()) {
            return "redirect:/lu-daily.htm?id=" + luCafeHistory.getId();
        }

        luCafeHistory.setApprovalUser(getCurrentUser());
        luCafeHistory.setApprovalDate(new Date());

        luCafeHistoryRepository.save(luCafeHistory);

        return "redirect:/lu-daily.htm?id=" + luCafeHistory.getId();
    }


}