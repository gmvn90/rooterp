package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.application.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.formController.form.MenuPageAssign;
import com.swcommodities.wsmill.formController.form.NewMenu;
import com.swcommodities.wsmill.formController.form.NewPage;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.repository.MenuRepository;
import com.swcommodities.wsmill.repository.PageRepository;
import com.swcommodities.wsmill.service.InvoiceService;
import com.swcommodities.wsmill.application.scheduler.Scheduler;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 * Created by dunguyen on 7/18/16.
 */

@Controller
@Transactional
public class AdminFunctionsController {

    @Autowired
    private Scheduler scheduler;
    
    @Autowired InvoiceService invoiceService;
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired ShippingInstructionService shippingInstructionService;
    @Autowired MenuService menuService;
    @Autowired MenuRepository menuRepository;
    @Autowired PageRepository pageRepository;
    
    @RequestMapping(method= RequestMethod.POST, value = "delivery_saveCachePending.htm")
    public String delivery_saveCachePending() {
        scheduler.saveDeliveryInstruction();
        return "admin/admin-functions";
    }
    @RequestMapping(method= RequestMethod.POST, value = "delivery_saveCacheAll.htm")
    public String delivery_saveCacheAll(HttpServletRequest request) {
        int number = Integer.valueOf(request.getParameter("number"));
        scheduler.saveDeliveryInstructionAll_improve(number);
        return "admin/admin-functions";
    }

    @RequestMapping(method= RequestMethod.POST, value = "processing_saveCachePending.htm")
    public String processing_saveCachePending() {
        scheduler.saveProcesssingInstruction();
        return "redirect:/admin-functions.htm";
    }
    @RequestMapping(method= RequestMethod.POST, value = "processing_saveCacheAll.htm")
    public String processing_saveCacheAll(HttpServletRequest request) {
        int number = Integer.valueOf(request.getParameter("number"));
        scheduler.saveProcesssingInstructionAll_improve(number);
        return "redirect:/admin-functions.htm";
    }

    @RequestMapping(method= RequestMethod.POST, value = "shipping_saveCachePending.htm")
    public String shipping_saveCachePending() {
        scheduler.saveShippingInstruction();
        return "admin/admin-functions";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "shipping_saveCacheAll.htm")
    public String shipping_saveCacheAll(HttpServletRequest request) {
        int number = Integer.valueOf(request.getParameter("number"));
        scheduler.saveShippingInstructionAll_improve(number);
        return "admin/admin-functions";
    }

    @RequestMapping(method= RequestMethod.POST, value = "sampleSent_saveCachePending.htm")
    public String sampleSent_saveCachePending() {
        scheduler.saveSampleSent();
        return "admin/admin-functions";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "sampleSent_saveCacheAll.htm")
    public String sampleSent_saveCacheAll() {
        scheduler.saveSampleSentAll_improve();
        return "admin/admin-functions";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "invoice_saveCost.htm")
    public String saveAllInvoices() {
        for(Invoice invoice: invoiceRepository.findAll()) {
            invoiceService.saveInvoiceWithCostInfo(invoice.getId());
        }
        return "admin/admin-functions";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "initUserAndMenu.htm")
    public String initUserAndMenu() {
        menuService.updatePageAndMenuForUsers();
        return "admin/admin-functions";
    }
    
    @RequestMapping(method= RequestMethod.GET, value = "list-menus.htm")
    public String getMenus(Model model) {
        List<Menu> menus = menuService.getAllMenus();
        model.addAttribute("mymenus", menus);
        model.addAttribute("mypages", menuService.getRealPages());
        return "admin/menus";
    }
    
    @RequestMapping(method= RequestMethod.GET, value = "list-pages.htm")
    public String getPages(Model model) {
        model.addAttribute("mypages", menuService.getAllPages());
        return "admin/pages";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "new-menu.htm")
    @Transactional
    public String addMenu(Model model, NewMenu newMenu) {
        Menu menu = new Menu(newMenu.getName(), true);
        menu.setOrder(newMenu.getOrder());
        menu.setShowInMainMenu(newMenu.getShowInMainMenu());
        menu.setMenu(new Menu(newMenu.getParent()));
        menuRepository.save(menu);
        return "redirect:/list-menus.htm";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "update-menu.htm")
    @Transactional
    public String updateMenu(Model model, NewMenu newMenu) {
        Menu menu = menuRepository.findOne(newMenu.getId());
        menu.setName(newMenu.getName());
        menu.setOrder(newMenu.getOrder());
        menu.setShowInMainMenu(newMenu.getShowInMainMenu());
        menu.setDefault_(newMenu.getIsDefault());
        menuRepository.save(menu);
        return "redirect:/list-menus.htm";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "update-menu-assign-page.htm")
    @Transactional
    public String assignPage(Model model, MenuPageAssign assignment) {
        Menu menu = menuRepository.findOne(assignment.getId());
        menu.setPage(new Page(assignment.getPage()));
        menuRepository.save(menu);
        return "redirect:/list-menus.htm";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "new-page.htm")
    @Transactional
    public String addPage(Model model, NewPage newPage) {
        newPage.setId(null);
        Page page = new Page(newPage.getPage(), newPage.getName(), newPage.getUrl());
        //temporaty fix
        page.setId(pageRepository.findTopByOrderByIdDesc().getId() + 1);
        
        pageRepository.save(page);
        return "redirect:/list-pages.htm";
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "update-page.htm")
    @Transactional
    public String updatePage(Model model, NewPage newPage) {
        Page page = pageRepository.findOne(newPage.getId());
        page.setName(newPage.getName());
        page.setUrl(newPage.getUrl());
        pageRepository.save(page);
        return "redirect:/list-pages.htm";
    }
    
}
