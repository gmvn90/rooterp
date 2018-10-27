/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.swcommodities.wsmill.bo.MenuService;
import com.swcommodities.wsmill.hibernate.dto.AccessoryMaster;
import com.swcommodities.wsmill.hibernate.dto.CellType;
import com.swcommodities.wsmill.hibernate.dto.City;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CupTest;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingType;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdviceSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.Warehouse;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;

import net.sf.jtpl.Template;

/**
 * @author kiendn
 */
public class GenTemplate {

    private MenuService menu_service;

    public GenTemplate(ServletRequest request) {

        ApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
        menu_service = (MenuService) ctx.getBean("menuService");

    }

    public String generateMenus(File file, Menu menu, User user) {
        String result = "";
        Template tpl, tpl1, tpl2;
        try {
            tpl = new Template(file);
            tpl1 = new Template(file);
            tpl2 = new Template(file);
            Menu menulv1, menulv2;
            if (menu.getMenu() != null) {
                //could be at lv3 or lv2
                if (menu.getMenu().getMenu() != null) {
                    //this one is menu lv3
                    menulv2 = menu.getMenu();
                    menulv1 = menulv2.getMenu();

                    //apply template for menulv1
                    ArrayList<Menu> menuLv1List = new ArrayList<Menu>(menu_service.getSiblingMenuLv1(menulv1, user.getId()));
                    for (Menu m : menuLv1List) {
                        tpl.assign("id", m.getId().toString());

                        if (m.getId().equals(Byte.valueOf("1"))) {
                            tpl.assign("home", "class=\"mpcth-icon-home\"");
                            if (m.getId().equals(menulv1.getId())) {
                                tpl.assign("class", "class=\"chosen\"");
                                tpl.assign("name", "");
                            } else {
                                tpl.assign("class", "");

                                tpl.assign("name", "");
                            }
                        } else {
                            tpl.assign("home", "");
                            if (m.getId().equals(menulv1.getId())) {
                                tpl.assign("class", "class=\"chosen\"");
                                tpl.assign("name", m.getName());
                            } else {
                                tpl.assign("class", "");
                                tpl.assign("name", m.getName());
                            }
                        }
                        tpl.parse("menu.lv1");
                    }
                    //            Common.destroy();

                    //apply template for menulv2
                    ArrayList<Menu> menuLv2List = new ArrayList<Menu>(menu_service.getSiblingMenu(menulv2, user));
                    for (Menu m : menuLv2List) {
                        tpl.assign("id", m.getId().toString());
                        tpl.assign("name", m.getName());
                        if (m.getId().equals(menulv2.getId())) {
                            tpl.assign("class", "class=\"chosen\"");
                        } else {
                            tpl.assign("class", "");
                        }

                        //            Common.destroy();
                        ArrayList<Menu> menuLv3List = new ArrayList<Menu>(m.getMenus());
                        if (!menuLv3List.isEmpty()) {

                            tpl.assign("lv3", generateMenulv3(tpl1, Common.sortMenu(menuLv3List)));
                        } else {
                            tpl.assign("lv3", "");
                        }

                        tpl.parse("menu.lv2");
                    }

                    tpl.parse("menu");
                } else { //is menu lv2
                    menulv2 = menu;
                    menulv1 = menu.getMenu();

                    //apply template for menulv1
                    ArrayList<Menu> menuLv1List = new ArrayList<>(menu_service.getSiblingMenuLv1(menulv1, user.getId()));
                    for (Menu m : menuLv1List) {
                        tpl.assign("id", m.getId().toString());

                        if (m.getId().equals(Byte.valueOf("1"))) {
                            tpl.assign("home", "class=\"mpcth-icon-home\"");
                            if (m.getId().equals(menulv1.getId())) {
                                tpl.assign("class", "class=\"chosen\"");
                                tpl.assign("name", "");
                            } else {
                                tpl.assign("class", "");

                                tpl.assign("name", "");
                            }
                        } else {
                            tpl.assign("home", "");
                            if (m.getId().equals(menulv1.getId())) {
                                tpl.assign("class", "class=\"chosen\"");
                                tpl.assign("name", m.getName());
                            } else {
                                tpl.assign("class", "");
                                tpl.assign("name", m.getName());
                            }
                        }
                        tpl.parse("menu.lv1");
                    }

                    //            Common.destroy();
                    //apply template for menulv2
                    ArrayList<Menu> menuLv2List = new ArrayList<Menu>(menu_service.getSiblingMenu(menulv2, user));
                    for (Menu m : menuLv2List) {
                        tpl.assign("id", m.getId().toString());
                        tpl.assign("name", m.getName());
                        if (m.getId().equals(menulv2.getId())) {
                            tpl.assign("class", "class=\"chosen\"");
                        } else {
                            tpl.assign("class", "");
                        }

                        //apply template for menulv3
                        ArrayList<Menu> menuLv3List = new ArrayList<Menu>(m.getMenus());
                        if (!menuLv3List.isEmpty()) {
                            tpl.assign("lv3", generateMenulv3(tpl2, Common.sortMenu(menuLv3List)));
                        } else {
                            tpl.assign("lv3", "");
                        }

                        tpl.parse("menu.lv2");
                    }

                    tpl.parse("menu");
                }
            }
            result = tpl.out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String generateMenulv3(Template tpl, ArrayList<Menu> menus) {
        for (Menu menu : menus) {
            tpl.assign("id", menu.getId().toString());
            tpl.assign("name", menu.getName());
            tpl.parse("lv3.lv3list");
        }

        tpl.parse("lv3");
        return tpl.out();
    }

    public String generateDisabledMenus(File file) {
        String result = "";
        try {
            Template tpl = new Template(file);
//            ArrayList<Menu> menuLv1List = menu_service.getMenuLv1();
//
//            //apply template for menulv1
//            for (Menu m : menuLv1List) {
//                tpl.assign("id", m.getId().toString());
//
//                if (m.getId().equals(Byte.valueOf("1"))) {
//                    tpl.assign("home", "class=\"mpcth-icon-home\"");
//                    tpl.assign("name", "");
//                } else {
//                    tpl.assign("home", "");
//                    tpl.assign("name", m.getName());
//                }
//                tpl.parse("disabled_menu.lv1");
//            }
//
            tpl.parse("disabled_menu");
            result = tpl.out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String generateFullHeader(File file, String header_content, String breadcrum) {
        String result = "";
        try {
            Template tpl = new Template(file);
            tpl.assign("menu", header_content);
            tpl.assign("breadcrum", breadcrum);
            result = tpl.parse("main").out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String generateBreadcrumbs(File file, Menu menu, Page page, String username, ArrayList<Page> pagesNotInMenus, String profile) {
        String result = "";
        try {

            Template tpl = new Template(file);
            tpl.assign("profile", profile);
            Menu upperMenu = menu.getMenu();
            if (upperMenu != null) { //has the upper menu (inputted menu is level 2 or 3)
                Menu highestMenu = upperMenu.getMenu();
                if (highestMenu != null) { //has the highest menu (inputted menu is level 3)
                    tpl.assign("id", highestMenu.getId().toString());
                    tpl.assign("name", highestMenu.getName());
                    tpl.assign("class", "class=\"mpcth-icon-right breadcrum_icon\"");
                    tpl.parse("breadcrumbs.link");
                }
                tpl.assign("id", upperMenu.getId().toString());
                tpl.assign("name", upperMenu.getName());
                tpl.assign("class", "class=\"mpcth-icon-right breadcrum_icon\"");

                tpl.parse("breadcrumbs.link");
            }
            tpl.assign("id", menu.getId().toString());
            tpl.assign("name", menu.getName());

            if (menu.getName().equals(menu.getPage().getName())) {
                tpl.assign("class", "");
                tpl.parse("breadcrumbs.link");

            } else {
                tpl.assign("class", "class=\"mpcth-icon-right breadcrum_icon\"");
                tpl.parse("breadcrumbs.link");

                if (pagesNotInMenus.contains(page)) {
                    tpl.assign("name", page.getName());
                    tpl.parse("breadcrumbs.link_not_href");
                } else {
                    tpl.assign("id", "");
                    tpl.assign("name", menu.getPage().getName());
                    tpl.assign("class", "");
                    tpl.parse("breadcrumbs.link");
                }
            }
            tpl.assign("username", username);
            tpl.parse("breadcrumbs");
            result = tpl.out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String generateLoginDialog(Template tpl) {
        tpl.parse("login");
        return tpl.out();
    }

    public String generateCreateWarehouseDialog(Template tpl) {
        tpl.parse("main");
        return tpl.out();
    }

    public String generateListOnCellArea(Template tpl) {
        tpl.parse("main");
        return tpl.out();
    }

    public String generateAreaSelectionDialog(Template tpl) {
        tpl.parse("main");
        return tpl.out();
    }

    public String generateGrades(Template tpl, ArrayList<GradeMaster> grades, Integer selectedValue) {
        for (int i = 0; i < grades.size(); i++) {
            tpl.assign("VALUE", grades.get(i).getId().toString());
            tpl.assign("NAME", grades.get(i).getName());
            if (grades.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateCompanies(Template tpl, ArrayList<CompanyMaster> companies, Integer selectedValue) {
        for (int i = 0; i < companies.size(); i++) {
            tpl.assign("VALUE", companies.get(i).getId().toString());
            tpl.assign("NAME", companies.get(i).getName());
            if (companies.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateShippingLines(Template tpl, ArrayList<ShippingLineMaster> shippingLines, Integer selectedValue) {
        for (int i = 0; i < shippingLines.size(); i++) {
            tpl.assign("VALUE", shippingLines.get(i).getId().toString());
            tpl.assign("NAME", shippingLines.get(i).getName());
            if (shippingLines.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generatePorts(Template tpl, ArrayList<PortMaster> ports, Integer selectedValue) {
        for (int i = 0; i < ports.size(); i++) {
            tpl.assign("VALUE", ports.get(i).getId().toString());
            tpl.assign("NAME", ports.get(i).getName());
            if (ports.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateCities(Template tpl, ArrayList<City> cities, Integer selectedValue) {
        for (int i = 0; i < cities.size(); i++) {
            tpl.assign("VALUE", cities.get(i).getId().toString());
            tpl.assign("NAME", cities.get(i).getName());
            if (cities.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateAccessoryMaster(Template tpl, ArrayList<AccessoryMaster> ams) {
        for (AccessoryMaster am : ams) {
            tpl.assign("id", am.getId() + "");
            tpl.assign("name", am.getName());
            tpl.assign("unit", am.getUnit());
            tpl.parse("main");
        }
        return tpl.out();
    }

    public String generateDocumentMaster(Template tpl, ArrayList<DocumentMaster> dms) {
        for (DocumentMaster dm : dms) {
            tpl.assign("id", dm.getId() + "");
            tpl.assign("name", dm.getName());
            tpl.parse("document");
        }
        return tpl.out();
    }

    public String generateWarehouses(Template tpl, ArrayList<Warehouse> warehouses, Integer selectedValue) {
        for (int i = 0; i < warehouses.size(); i++) {
            tpl.assign("VALUE", warehouses.get(i).getId().toString());
            tpl.assign("NAME", warehouses.get(i).getName());
            if (warehouses.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateWarehouseMaps(Template tpl, ArrayList<WarehouseMap> warehouseMaps, Integer selectedValue) {
        tpl.assign("VALUE", "-1");
        tpl.assign("NAME", "All");
        if (selectedValue == -1) {
            tpl.assign("selected", "selected='selected'");
        } else {
            tpl.assign("selected", "");
        }
        tpl.parse("initialize");
        for (int i = 0; i < warehouseMaps.size(); i++) {
            tpl.assign("VALUE", warehouseMaps.get(i).getId().toString());
            tpl.assign("NAME", warehouseMaps.get(i).getName());
            if (warehouseMaps.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateWarehouseCells(Template tpl, ArrayList<WarehouseCell> warehouseCells, String[] total) {
        int rows = warehouseCells.get(0).getWarehouseMap().getHeight();
        int cols = warehouseCells.get(0).getWarehouseMap().getWidth();
        int ordinary_x = 1;
        int ordinary_y = 0;
        int ox = 1;
        int oy = 1;
        for (int y = 1; y <= rows; y++) {
            for (int x = 1; x <= cols; x++) {
                int count = 0;
                for (WarehouseCell a : warehouseCells) {
                    if (a.getOrdinateX() == x && a.getOrdinateY() == y) {
                        if (x == 1) {
                            if (y == 1 || y == rows) {
                                tpl.assign("id", "" + a.getId());
                                tpl.assign("ordinate", "");
                                tpl.assign("classname", a.getCellType().getCellTypeName());
                                tpl.assign("label", "");
                                tpl.assign("total", total[count]);
                                tpl.parse("main.sub");
                            } else {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", oy + "");
                                    tpl.assign("total", total[count]);
                                    oy++;
                                    tpl.parse("main.sub");
                                }
                            }
                        } else {
                            if (y == 1 && x < cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", Common.convertIntToAlphabet(ox));
                                    tpl.assign("total", total[count]);
                                    ox++;
                                    tpl.parse("main.sub");
                                }
                            } else if (y == 1 && x == cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                }

                            } else {
                                if (x == cols) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    if (a.getCellType().getCellTypeName().equals("wall") || a.getCellType().getCellTypeName().equals("wall_ver") || a.getCellType().getCellTypeName().equals("wall_hor")) {
                                        tpl.assign("id", "" + a.getId());
                                        tpl.assign("ordinate", "");
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        tpl.parse("main.sub");

                                    } else {
                                        tpl.assign("id", "" + a.getId());
                                        tpl.assign("ordinate", Common.convertIntToAlphabet(ordinary_x) + ordinary_y);
                                        ordinary_x++;
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        tpl.parse("main.sub");
                                    }
                                }

                            }
                        }
                    }
                    count++;
                }

            }
            ordinary_x = 1;
            ordinary_y++;
            tpl.parse("main");
        }
        return tpl.out();

    }

    public String generateWarehouseCellsArea(Template tpl, ArrayList<WarehouseCell> warehouseCells, String[] total) {
        int rows = warehouseCells.get(0).getWarehouseMap().getHeight();
        int cols = warehouseCells.get(0).getWarehouseMap().getWidth();
        int ordinary_x = 1;
        int ordinary_y = 0;
        int ox = 1;
        int oy = 1;
        for (int y = 1; y <= rows; y++) {
            for (int x = 1; x <= cols; x++) {
                int count = 0;
                for (WarehouseCell a : warehouseCells) {
                    if (a.getOrdinateX() == x && a.getOrdinateY() == y) {
                        if (x == 1) {
                            if (y == 1 || y == rows) {
                                tpl.assign("id", "" + a.getId());
                                tpl.assign("ordinate", "");
                                tpl.assign("classname", a.getCellType().getCellTypeName());
                                tpl.assign("label", "");
                                tpl.assign("total", total[count]);
                                tpl.parse("main.sub");
                            } else {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", oy + "");
                                    tpl.assign("total", total[count]);
                                    oy++;
                                    tpl.parse("main.sub");
                                }
                            }
                        } else {
                            if (y == 1 && x < cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", Common.convertIntToAlphabet(ox));
                                    tpl.assign("total", total[count]);
                                    ox++;
                                    tpl.parse("main.sub");
                                }
                            } else if (y == 1 && x == cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                }

                            } else {
                                if (x == cols) {
                                    tpl.assign("id", "" + a.getId());
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    tpl.parse("main.sub");
                                } else {
                                    if (a.getCellType().getCellTypeName().equals("wall") || a.getCellType().getCellTypeName().equals("wall_ver") || a.getCellType().getCellTypeName().equals("wall_hor")) {
                                        tpl.assign("id", "" + a.getId());
                                        tpl.assign("ordinate", "");
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        tpl.parse("main.sub");

                                    } else {
                                        tpl.assign("id", "" + a.getId());
                                        tpl.assign("ordinate", Common.convertIntToAlphabet(ordinary_x) + ordinary_y);
                                        ordinary_x++;
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        tpl.parse("main.sub");
                                    }
                                }

                            }
                        }
                    }
                    count++;
                }

            }
            ordinary_x = 1;
            ordinary_y++;
            tpl.parse("main");
        }
        return tpl.out();

    }

    public String generateWarehouseCellsForEdit(Template tpl, ArrayList<WarehouseCell> warehouseCells, String[] total) {
        int rows = warehouseCells.get(1).getWarehouseMap().getHeight();
        int cols = warehouseCells.get(1).getWarehouseMap().getWidth();
        int ordinary_x = 1;
        int ordinary_y = 0;
        int ox = 1;
        int oy = 1;
        for (int y = 1; y <= rows; y++) {
            for (int x = 1; x <= cols; x++) {
                int count = 0;
                for (WarehouseCell a : warehouseCells) {
                    if (a.getOrdinateX() == x && a.getOrdinateY() == y) {
                        if (x == 1) {
                            if (y == 1 || y == rows) {
                                tpl.assign("id", a.getId() + "");
                                tpl.assign("ordinate", "");
                                tpl.assign("classname", a.getCellType().getCellTypeName());
                                tpl.assign("label", "");
                                tpl.assign("total", total[count]);
                                if (total[count].equals("")) {
                                    tpl.assign("available", "ok");
                                } else {
                                    tpl.assign("available", "");
                                }
                                tpl.parse("main.row.col");
                            } else {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    tpl.parse("main.row.col");
                                } else {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", oy + "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    oy++;
                                    tpl.parse("main.row.col");
                                }
                            }
                        } else {
                            if (y == 1 && x < cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    tpl.parse("main.row.col");
                                } else {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", Common.convertIntToAlphabet(ox));
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    ox++;
                                    tpl.parse("main.row.col");
                                }
                            } else if (y == 1 && x == cols) {
                                if (a.getCellType().getCellTypeName().equals("wall")) {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("label", "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    tpl.parse("main.row.col");
                                } else {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    tpl.parse("main.row.col");
                                }

                            } else {
                                if (x == cols) {
                                    tpl.assign("id", a.getId() + "");
                                    tpl.assign("ordinate", "");
                                    tpl.assign("classname", a.getCellType().getCellTypeName());
                                    tpl.assign("lable", "");
                                    tpl.assign("total", total[count]);
                                    if (total[count].equals("")) {
                                        tpl.assign("available", "ok");
                                    } else {
                                        tpl.assign("available", "");
                                    }
                                    tpl.parse("main.row.col");
                                } else {
                                    if (a.getCellType().getCellTypeName().equals("wall") || a.getCellType().getCellTypeName().equals("wall_ver") || a.getCellType().getCellTypeName().equals("wall_hor")) {
                                        tpl.assign("id", a.getId() + "");
                                        tpl.assign("ordinate", "");
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        if (total[count].equals("")) {
                                            tpl.assign("available", "ok");
                                        } else {
                                            tpl.assign("available", "");
                                        }
                                        tpl.parse("main.row.col");

                                    } else {
                                        tpl.assign("id", a.getId() + "");
                                        tpl.assign("ordinate", Common.convertIntToAlphabet(ordinary_x) + ordinary_y);
                                        ordinary_x++;
                                        tpl.assign("classname", a.getCellType().getCellTypeName());
                                        tpl.assign("label", "");
                                        tpl.assign("total", total[count]);
                                        if (total[count].equals("")) {
                                            tpl.assign("available", "ok");
                                        } else {
                                            tpl.assign("available", "");
                                        }
                                        tpl.parse("main.row.col");
                                    }
                                }

                            }
                        }
                    }
                    count++;
                }

            }
            ordinary_x = 1;
            ordinary_y++;
            tpl.parse("main.row");
        }
        tpl.parse("main");
        return tpl.out();

    }

    public String generateListCellType(Template tpl, ArrayList<CellType> cellTypes, String current_cell_type) {
        for (int i = 0; i < cellTypes.size(); i++) {
            if (current_cell_type.equals("storage") || current_cell_type.equals("room_machine") || current_cell_type.equals("out_of_use")) {
                if (cellTypes.get(i).getCellTypeName().equals("storage") || cellTypes.get(i).getCellTypeName().equals("room_machine") || cellTypes.get(i).getCellTypeName().equals("out_of_use")) {
                    tpl.assign("cellType", cellTypes.get(i).getCellTypeName());
                    tpl.assign("displayname", cellTypes.get(i).getDisplayName());
                    tpl.parse("main.item");
                }
            }
            if (current_cell_type.equals("door_hor") || current_cell_type.equals("wall_hor")) {
                if (cellTypes.get(i).getCellTypeName().equals("door_hor") || cellTypes.get(i).getCellTypeName().equals("wall_hor")) {
                    tpl.assign("cellType", cellTypes.get(i).getCellTypeName());
                    tpl.assign("displayname", cellTypes.get(i).getDisplayName());
                    tpl.parse("main.item");
                }
            }
            if (current_cell_type.equals("door_ver") || current_cell_type.equals("wall_ver")) {
                if (cellTypes.get(i).getCellTypeName().equals("door_ver") || cellTypes.get(i).getCellTypeName().equals("wall_ver")) {
                    tpl.assign("cellType", cellTypes.get(i).getCellTypeName());
                    tpl.assign("displayname", cellTypes.get(i).getDisplayName());
                    tpl.parse("main.item");
                }
            }

        }
        tpl.parse("main");
        return tpl.out();
    }

    public String generatePackings(Template tpl, ArrayList<PackingMaster> packings, Integer selectedValue) {
        for (int i = 0; i < packings.size(); i++) {
            tpl.assign("VALUE", packings.get(i).getId().toString());
            tpl.assign("NAME", packings.get(i).getName());
            if (packings.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateProcessingTypes(Template tpl, ArrayList<ProcessingType> types, Integer selectedValue) {
        for (int i = 0; i < types.size(); i++) {
            tpl.assign("VALUE", types.get(i).getId().toString());
            tpl.assign("NAME", types.get(i).getName());
            if (types.get(i).getId().equals(selectedValue)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateProcessSelectList(Template tpl, ArrayList<ProcessingInstruction> processes, int selected_value) {
        for (ProcessingInstruction p : processes) {
            tpl.assign("VALUE", p.getId().toString());
            tpl.assign("NAME", p.getRefNumber());
            if (p.getId().equals(selected_value)) {
                tpl.assign("selected", "selected='selected'");
            } else {
                tpl.assign("selected", "");
            }
            tpl.parse("initialize");
        }
        return tpl.out();
    }

    public String generateProcessRefList(Template tpl, ArrayList<ProcessingInstruction> processes, int selected_value) {
        for (ProcessingInstruction processingInstruction : processes) {
            tpl.assign("class", processingInstruction.getId().equals(selected_value) ? "ref_child chosen" : "ref_child");
            tpl.assign("id", "po_" + processingInstruction.getId().toString());
            tpl.assign("value", processingInstruction.getRefNumber());
            tpl.parse("list");
        }

        return tpl.out();

    }

    public String generateRefList(Template tpl, ArrayList<?> list, String prefix) throws Exception {
        for (Object ir : list) {
            int id = (Integer) ir.getClass().getMethod("getId", (Class<?>[]) null).invoke(ir, (Object[]) null);
            String refNumber = (String) ir.getClass().getMethod("getRefNumber", (Class<?>[]) null).invoke(ir, (Object[]) null);
            tpl.assign("class", "ref_child");
            tpl.assign("id", prefix + id);
            tpl.assign("value", refNumber);
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateRefListWithSelected(Template tpl, ArrayList<?> list, int selected_value, String prefix) throws Exception {
        for (Object obj : list) {
            int id = (Integer) obj.getClass().getMethod("getId", (Class<?>[]) null).invoke(obj, (Object[]) null);
            String refNumber = (String) obj.getClass().getMethod("getRefNumber", (Class<?>[]) null).invoke(obj, (Object[]) null);
            tpl.assign("class", (id == selected_value) ? "ref_child chosen" : "ref_child");
            tpl.assign("id", prefix + id);
            tpl.assign("value", refNumber);
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateProcessDetailPage(Template tpl, ProcessingInstruction pi, Map m) {
        tpl.assign("ref_number", pi.getRefNumber());
        tpl.assign("client_ref", pi.getClientRef());
        tpl.assign("date_class", "");
        tpl.assign("created_date", Common.getDateFromDatabase(pi.getCreatedDate(), Common.date_format));
        tpl.assign("from_date", Common.getDateFromDatabase(pi.getFromDate(), Common.date_format_ddMMyyyy_dash));
        tpl.assign("to_date", Common.getDateFromDatabase(pi.getToDate(), Common.date_format_ddMMyyyy_dash));
        tpl.assign("tons", pi.getQuantity() + "");
        tpl.assign("remark", pi.getRemark());
        tpl.assign("status_value", pi.getStatus() + "");
        tpl.assign("checked", (pi.getStatus() == 1) ? "checked" : "");
        tpl.assign("username", pi.getUser().getUserName());
        tpl.assign("allocated", m.get("allocated") + " ");
        tpl.assign("in_process", m.get("in_process") + " ");
        tpl.assign("ex_process", m.get("ex_process") + " ");

        tpl.assign("weight_loss_stt", (pi.getStatus() == 1) ? "display: block" : "display: none");
        tpl.assign("weight_loss", (pi.getStatus() == 1) ? m.get("weight_loss") + " " : " ");

        tpl.assign("pending", (pi.getStatus() == 1) ? "0.000 " : m.get("pending") + " ");
        tpl.assign("grade_id", pi.getGradeMaster().getId() + "");
        tpl.assign("client_id", pi.getCompanyMasterByClientId().getId() + "");
        tpl.assign("packing_id", pi.getPackingMaster().getId() + "");
        tpl.assign("processing_type_id", pi.getProcessingType().getId() + "");
        tpl.parse("main");
        return tpl.out();
    }

    public String generateProcessDetailPage_v2(Template tpl, ProcessingInstruction pi) {
        tpl.assign("userByUpdateRequestUserId", pi.getUserByUpdateRequestUserId() != null ? pi.getUserByUpdateRequestUserId().getUserName() : "");
        tpl.assign("requestStatusDate", pi.getRequestStatusDate() != null ? Common.getDateFromDatabase(pi.getRequestStatusDate(), Common.date_format) : "");
        tpl.assign("userByUpdateCompletionUserId", pi.getUserByUpdateCompletionUserId() != null ? pi.getUserByUpdateCompletionUserId().getUserName() : "");
        tpl.assign("completionStatusDate", pi.getCompletionStatusDate() != null ? Common.getDateFromDatabase(pi.getCompletionStatusDate(), Common.date_format) : "");
        tpl.assign("requestRemark", pi.getRequestRemark() != null ? pi.getRequestRemark() : "");
        tpl.assign("refNumber", pi.getRefNumber());
        tpl.assign("createdDate", Common.getDateFromDatabase(pi.getCreatedDate(), Common.date_format));
        tpl.assign("clientRef", pi.getClientRef() != null ? pi.getClientRef() : "");
        tpl.assign("date_class", "");
        tpl.assign("quantities", pi.getQuantity() + "");
        tpl.assign("requestedCreditDate", pi.getCreditDate() != null ? Common.getDateFromDatabase(pi.getCreditDate(), Common.date_format_a) : "");
        tpl.assign("remark", pi.getRemark());

        tpl.assign("requestStatus", pi.getRequestStatus() != null ? pi.getRequestStatus() + "" : "0");
        tpl.assign("status", pi.getStatus() + "");
        tpl.assign("grade", pi.getGradeMaster().getId() + "");
        tpl.assign("client", pi.getCompanyMasterByClientId().getId() + "");
        tpl.assign("packing", pi.getPackingMaster().getId() + "");
        tpl.assign("processingType", pi.getProcessingType().getId() + "");
        tpl.parse("main");
        return tpl.out();
    }

    public String generateEmptyProcessPage_v2(Template tpl) {

        tpl.assign("userByUpdateRequestUserId", "");
        tpl.assign("requestStatusDate", "");
        tpl.assign("userByUpdateCompletionUserId", "");
        tpl.assign("completionStatusDate", "");
        tpl.assign("requestRemark", "");
        tpl.assign("refNumber", "");
        tpl.assign("createdDate", "");
        tpl.assign("date_class", "class='current_date_time'");
        tpl.assign("clientRef", "");
        tpl.assign("quantities", "0");
        tpl.assign("requestedCreditDate", "");
        tpl.assign("remark", "");

        tpl.assign("requestStatus", "0");
        tpl.assign("status", "0");
        tpl.assign("grade", "-1");
        tpl.assign("client", "-1");
        tpl.assign("packing", "-1");
        tpl.assign("processingType", "-1");

        tpl.parse("main");
        return tpl.out();
    }

    public String generateEmptyProcessPage(Template tpl) {
        tpl.assign("ref_number", "");
        tpl.assign("client_ref", "");
        tpl.assign("date_class", "class='current_date_time'");
        tpl.assign("created_date", "");
        tpl.assign("from_date", "");
        tpl.assign("to_date", "");
        tpl.assign("tons", "");
        tpl.assign("remark", "");
        tpl.assign("status_value", "0");
        tpl.assign("checked", "");
        tpl.assign("username", "");
        tpl.assign("allocated", " ");
        tpl.assign("in_process", " ");
        tpl.assign("ex_process", " ");
        tpl.assign("weight_loss_stt", "display: none");
        tpl.assign("pending", " ");
        tpl.assign("grade_id", "-1");
        tpl.assign("client_id", "-1");
        tpl.assign("packing_id", "-1");
        tpl.assign("processing_type_id", "-1");
        tpl.parse("main");
        return tpl.out();
    }

    public String generateEmptyShipPage(Template tpl) {
        tpl.assign("ref_number", "");
        tpl.assign("client_ref", "");
        tpl.assign("buyer_ref", "");
        tpl.assign("date_class", "class='current_date_time'");
        tpl.assign("created_date", "");
        tpl.assign("contract_quantity", "");
        tpl.assign("quantities", "");
        tpl.assign("loading_date", "");
        tpl.assign("from_date", "");
        tpl.assign("to_date", "");
        tpl.assign("service_contract_no", "");
        tpl.assign("feeder_vessel", "");
        tpl.assign("feeder_ets", "");
        tpl.assign("feeder_eta", "");
        tpl.assign("ocean_vessel", "");
        tpl.assign("ocean_ets", "");
        tpl.assign("ocean_eta", "");
        tpl.assign("marking", "");
        tpl.assign("freight", "");
        tpl.assign("lc_no", "");
        tpl.assign("lc_date", "");
        tpl.assign("invoice_no", "");
        tpl.assign("invoice_date", "");
        tpl.assign("bl_number", "");
        tpl.assign("bl_date", "");
        tpl.assign("remark", "");
        tpl.assign("status_value", "0");
        tpl.assign("checked", "");
        tpl.assign("username", "");

        tpl.assign("client_id", "-1");
        tpl.assign("supplier_id", "-1");
        tpl.assign("grade_id", "-1");
        tpl.assign("shipment_id", "-1");
        tpl.assign("shipper_id", "-1");
        tpl.assign("consignee_id", "-1");
        tpl.assign("notify_id", "-1");
        tpl.assign("shipping_line_id", "-1");
        tpl.assign("loading_port_id", "-1");
        tpl.assign("place_id", "-1");
        tpl.assign("discharge_port_id", "-1");
        tpl.assign("weight_cert_id", "-1");
        tpl.assign("quality_cert_id", "-1");

        tpl.assign("notifyJson", "");
        tpl.assign("accessoryJson", "");
        tpl.assign("documentJson", "");

        tpl.assign("stt", "new");

        tpl.parse("main");
        return tpl.out();
    }

    public String generateEmptyShipPage_v2(Template tpl) {
        tpl.assign("ref_number", "");
        tpl.assign("created_date", "");
        tpl.assign("client_ref", "");
        tpl.assign("supplier_ref", "");
        tpl.assign("shipper_ref", "");
        tpl.assign("buyer_ref", "");
        tpl.assign("date_class", "class='current_date_time'");
        tpl.assign("from_date", "");
        tpl.assign("to_date", "");
        tpl.assign("service_contract_no", "");
        tpl.assign("feeder_vessel", "");
        tpl.assign("feeder_ets", "");
        tpl.assign("feeder_eta", "");
        tpl.assign("ocean_vessel", "");
        tpl.assign("ocean_ets", "");
        tpl.assign("ocean_eta", "");
        tpl.assign("loading_date", "");
        tpl.assign("booking_ref", "");
        tpl.assign("closing_date", "");
        tpl.assign("closing_time", "");
        tpl.assign("full_cont_return", "");
        tpl.assign("bl_number", "");
        tpl.assign("bl_date", "");
        tpl.assign("remark", "");
        tpl.assign("ico_number", "");
        tpl.assign("remark", "");
        tpl.assign("remark", "");
        tpl.assign("quantities", "");

        tpl.assign("client_id", "-1");
        tpl.assign("supplier_id", "-1");
        tpl.assign("grade_id", "-1");
        tpl.assign("allocation_grade_id", "-1");
        tpl.assign("buyer_id", "-1");
        tpl.assign("shipper_id", "-1");
        tpl.assign("consignee_id", "-1");
        tpl.assign("notify_id", "-1");
        tpl.assign("shipment_id", "-1");
        tpl.assign("shipping_line_id", "-1");
        tpl.assign("loading_port_id", "-1");
        tpl.assign("discharge_port_id", "-1");
        tpl.assign("transit_port_id", "-1");

        tpl.assign("notifyJson", "");

        tpl.assign("stt", "new");

        tpl.parse("main");
        return tpl.out();
    }

    public String generateDeliveryInsRefList(Template tpl, ArrayList<DeliveryInstruction> deliveryInses) {
        String ref_child = "ref_child";
        for (int i = 0; i < deliveryInses.size(); i++) {
            if (i == deliveryInses.size() - 1) {
                ref_child = "ref_child hotpoint";
            }
            tpl.assign("id", deliveryInses.get(i).getId().toString());
            tpl.assign("value", deliveryInses.get(i).getRefNumber());
            tpl.assign("class", ref_child);
            tpl.parse("list");
            //ref_child = "ref_child";
        }
        tpl.assign("waypoint_class", "ref_child");
        tpl.parse("waypoint");
        return tpl.out();
    }

    public String generateDeliveryInsDetailPage(Template tpl, DeliveryInstruction di, Map m) {
        DecimalFormat decim = new DecimalFormat("0.000");
        tpl.assign("client_id", di.getCompanyMasterByClientId().getId() + "");
        tpl.assign("client_ref", di.getClientRef());
        tpl.assign("ref_number", di.getRefNumber());
        tpl.assign("date_class", "");
        tpl.assign("created_date", Common.getDateFromDatabase(di.getDate(), Common.date_format));
        tpl.assign("supplier_id", di.getCompanyMasterBySupplierId().getId() + "");
        tpl.assign("supplier_ref", di.getSupplierRef());
        tpl.assign("packing_id", di.getPackingMaster().getId() + "");
        tpl.assign("tons", di.getTons() + "");
        tpl.assign("kg_per_bag", di.getKgPerBag() + "");
        tpl.assign("no_of_bag", di.getNoOfBags() + "");
        tpl.assign("warehouse_id", di.getWarehouse().getId() + "");
        tpl.assign("delivery_date", Common.getDateFromDatabase(di.getDeliveryDate(), Common.date_format_ddMMyyyy_dash));
        tpl.assign("from_time", di.getFromTime());
        tpl.assign("to_time", di.getToTime());
        tpl.assign("marking_on_bags", di.getMarkingOnBags());
        tpl.assign("weight_con_id", di.getCompanyMasterByWeightControllerId().getId() + "");
        tpl.assign("quality_con_id", di.getCompanyMasterByQualityControllerId().getId() + "");
        tpl.assign("grade_id", di.getGradeMaster().getId() + "");
        tpl.assign("quality", di.getQualityId() + "");
        tpl.assign("delivered", decim.format(m.get("delivered")) + " ");
        tpl.assign("pending", decim.format(m.get("pending")) + " ");
        tpl.assign("canceled", decim.format(m.get("canceled")) + " ");
        tpl.assign("remark", di.getRemark());
        tpl.assign("status_value", di.getStatus() + "");
        tpl.assign("checked", (di.getStatus() == 1) ? "checked" : "");
        tpl.assign("username", di.getUser().getUserName());
        tpl.parse("main");
        return tpl.out();
    }

    public String generateEmptyDeliveryInsPage(Template tpl) {
        tpl.assign("client_id", "-1");
        tpl.assign("client_ref", "");
        tpl.assign("ref_number", "");
        tpl.assign("date_class", "class='current_date_time'");
        tpl.assign("created_date", "");
        tpl.assign("supplier_id", "-1");
        tpl.assign("supplier_ref", "");
        tpl.assign("packing_id", "-1");
        tpl.assign("tons", "");
        tpl.assign("kg_per_bag", "");
        tpl.assign("no_of_bag", "");
        tpl.assign("warehouse_id", "-1");
        tpl.assign("delivery_date", "");
        tpl.assign("from_time", "");
        tpl.assign("to_time", "");
        tpl.assign("marking_on_bags", "");
        tpl.assign("weight_con_id", "-1");
        tpl.assign("quality_con_id", "-1");
        tpl.assign("grade_id", "-1");
        tpl.assign("delivered", " ");
        tpl.assign("pending", " ");
        tpl.assign("canceled", " ");
        tpl.assign("remark", "");
        tpl.assign("status_value", "0");
        tpl.assign("checked", "");
        tpl.assign("username", "");
        tpl.parse("main");
        return tpl.out();
    }

    public String generateDIRefFilterList(Template tpl, ArrayList<DeliveryInstruction> list) {
        for (DeliveryInstruction deliveryInstruction : list) {
            tpl.assign("class", "");
            tpl.assign("id", "ins_" + deliveryInstruction.getId());
            tpl.assign("value", deliveryInstruction.getRefNumber());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateDeliveryRefList(Template tpl, ArrayList<DeliveryInstruction> inses, int selected_value) {
        for (DeliveryInstruction ins : inses) {
            tpl.assign("class", ins.getId().equals(selected_value) ? "ref_child chosen" : "ref_child");
            tpl.assign("id", "ins_" + ins.getId().toString());
            tpl.assign("value", ins.getRefNumber());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateRefFilterList(Template tpl, ArrayList<Object[]> list, String type) {
        for (Object[] objects : list) {
            tpl.assign("class", "");
            tpl.assign("id", type + "_" + objects[0]);
            tpl.assign("value", objects[1].toString());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generatePIRefFilterList(Template tpl, ArrayList<ProcessingInstruction> list) {
        for (ProcessingInstruction processingInstruction : list) {
            tpl.assign("class", "");
            tpl.assign("id", "ins_" + processingInstruction.getId());
            tpl.assign("value", processingInstruction.getRefNumber());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateGradeFilterList(Template tpl, ArrayList<GradeMaster> list) {
        for (GradeMaster gradeMaster : list) {
            tpl.assign("class", "");
            tpl.assign("id", "grade_" + gradeMaster.getId());
            tpl.assign("value", gradeMaster.getName());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateGradeFilterList(Template tpl, ArrayList<GradeMaster> list, int selected_value) {
        if (list != null && !list.isEmpty()) {
            for (GradeMaster gradeMaster : list) {
                if (gradeMaster != null) {
                    tpl.assign("class", gradeMaster.getId().equals(selected_value) ? "chosen" : "");
                    tpl.assign("id", "grade_" + gradeMaster.getId());
                    tpl.assign("value", gradeMaster.getName());
                    tpl.parse("list");
                }
            }

            return tpl.out();
        }
        return "";
    }

    public String generateCompanyFilterList(Template tpl, ArrayList<CompanyMaster> list, String type) {
        for (CompanyMaster companyMaster : list) {
            tpl.assign("class", "");
            tpl.assign("id", type + "_" + companyMaster.getId());
            tpl.assign("value", companyMaster.getName());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateWarehouseMapFilterList(Template tpl, ArrayList<WarehouseMap> list, String type) {
        for (WarehouseMap warehousemap : list) {
            tpl.assign("class", "");
            tpl.assign("id", type + "_" + warehousemap.getId());
            tpl.assign("value", warehousemap.getName());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateWNImport(Template tpl, WeightNote wn, DeliveryInstruction di, String wnr_content, String str_area, int cell_id) {
        tpl.assign("ref_number", wn.getRefNumber());
        tpl.assign("wn_dateTime", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
        tpl.assign("ins_ref_name", Common.getInsName(wn.getType()));
        tpl.assign("ins_ref_number", di.getRefNumber());
        tpl.assign("supplier_ref", di.getClientRef());
        tpl.assign("supplier", di.getCompanyMasterBySupplierId().getName());
        tpl.assign("truck_no", (wn.getTruckNo() != null) ? wn.getTruckNo() : "");
        tpl.assign("driver_name", (wn.getDriver() != null) ? wn.getDriver() : "");
        tpl.assign("quality_ref", wn.getQualityReport().getRefNumber());
        tpl.assign("grade_id", wn.getGradeMaster().getId().toString());
        tpl.assign("packing_id", wn.getPackingMaster().getId().toString());
        tpl.assign("area", str_area);
        tpl.assign("status", wn.getStatus().toString());
        tpl.assign("checked", (wn.getStatus() == 1) ? "checked" : "");
        tpl.assign("wnr_content", wnr_content);
        tpl.assign("inst_id", di.getId().toString());
        tpl.assign("cell_id", cell_id + "");
        if (wn.getWarehouseCell() == null) {
            tpl.assign("id_warehousemap", "");
            tpl.assign("id_warehousecell", "");
        } else {
            tpl.assign("id_warehousemap", wn.getWarehouseCell().getWarehouseMap().getId().toString());
            tpl.assign("id_warehousecell", wn.getWarehouseCell().getId().toString());
        }
        return tpl.parse("main").out();
    }

    public String generateWNExport(Template tpl, WeightNote wn, ShippingInstruction si, String wnr_content) {
        tpl.assign("ref_number", wn.getRefNumber());
        tpl.assign("wn_dateTime", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
        tpl.assign("ins_ref_name", Common.getInsName(wn.getType()));
        tpl.assign("ins_ref_number", si.getRefNumber());
        tpl.assign("supplier_ref", si.getClientRef());
        tpl.assign("supplier", si.getCompanyMasterBySupplierId().getName());
        tpl.assign("container_no", (wn.getContainerNo() != null) ? wn.getContainerNo() : "");
        tpl.assign("ico_no", (wn.getIcoNo() != null) ? wn.getIcoNo() : "");
        tpl.assign("quality_ref", wn.getQualityReport().getRefNumber());
        tpl.assign("grade_id", wn.getGradeMaster().getId().toString());
        tpl.assign("packing_id", wn.getPackingMaster().getId().toString());
        tpl.assign("seal_no", (wn.getSealNo() != null) ? wn.getSealNo() : "");
        tpl.assign("status", wn.getStatus().toString());
        tpl.assign("checked", (wn.getStatus() == 1) ? "checked" : "");
        tpl.assign("wnr_content", wnr_content);
        tpl.assign("inst_id", si.getId().toString());
        tpl.assign("id_warehousemap", "");
        return tpl.parse("main").out();
    }

    public String generateWNExprocess(Template tpl, WeightNote wn, ProcessingInstruction pi, String wnr_content, String str_area, int cell_id) {
        tpl.assign("ref_number", wn.getRefNumber());
        tpl.assign("wn_dateTime", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
        tpl.assign("ins_ref_name", Common.getInsName(wn.getType()));
        tpl.assign("ins_ref_number", pi.getRefNumber());
        tpl.assign("supplier_ref", pi.getClientRef());
        tpl.assign("supplier", pi.getCompanyMasterByClientId().getName());
        tpl.assign("quality_ref", wn.getQualityReport().getRefNumber());
        tpl.assign("grade_id", wn.getGradeMaster().getId().toString());
        tpl.assign("packing_id", wn.getPackingMaster().getId().toString());
        tpl.assign("area", str_area);
        tpl.assign("status", wn.getStatus().toString());
        tpl.assign("checked", (wn.getStatus() == 1) ? "checked" : "");
        tpl.assign("wnr_content", wnr_content);
        tpl.assign("inst_id", pi.getId().toString());
        tpl.assign("cell_id", cell_id + "");
        if (wn.getWarehouseCell() == null) {
            tpl.assign("id_warehousemap", "");
            Template id_warehousecell = tpl.assign("id_warehousecell", "");
        } else {
            tpl.assign("id_warehousemap", wn.getWarehouseCell().getWarehouseMap().getId().toString());
            tpl.assign("id_warehousecell", wn.getWarehouseCell().getId().toString());
        }
        return tpl.parse("main").out();
    }

    public String generateWNInprocess(Template tpl, WeightNote wn, ProcessingInstruction pi, String wnr_content) {
        tpl.assign("ref_number", wn.getRefNumber());
        tpl.assign("wn_dateTime", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
        tpl.assign("ins_ref_name", Common.getInsName(wn.getType()));
        tpl.assign("ins_ref_number", pi.getRefNumber());
        tpl.assign("supplier_ref", pi.getClientRef());
        tpl.assign("supplier", pi.getCompanyMasterByClientId().getName());
        tpl.assign("quality_ref", wn.getQualityReport().getRefNumber());
        tpl.assign("grade_id", wn.getGradeMaster().getId().toString());
        tpl.assign("packing_id", wn.getPackingMaster().getId().toString());
        tpl.assign("status", wn.getStatus().toString());
        tpl.assign("checked", (wn.getStatus() == 1) ? "checked" : "");
        tpl.assign("wnr_content", wnr_content);
        tpl.assign("inst_id", pi.getId().toString());
        if (wn.getWarehouseCell() == null) {
            tpl.assign("id_warehousemap", "");
            tpl.assign("id_warehousecell", "");
        } else {
            tpl.assign("id_warehousemap", wn.getWarehouseCell().getWarehouseMap().getId().toString());
            tpl.assign("id_warehousecell", wn.getWarehouseCell().getId().toString());
        }
        return tpl.parse("main").out();
    }

    public String printAlert(Template tpl, String message) {
        tpl.assign("message", message);
        tpl.parse("alert");
        return tpl.out();
    }

    public String generateWeightNoteReceiptsArea(Template tpl, ArrayList<Map> weightnotereceipts) {
        for (Map w : weightnotereceipts) {
            tpl.assign("WNRef", w.get("ref").toString());
            tpl.assign("netweight", w.get("net").toString());
            tpl.assign("grade", w.get("grade").toString());
            tpl.parse("main.item");
        }
        tpl.parse("main");
        return tpl.out();
    }

    public String wnr_generateSaveButton(ServletContext context) {
        try {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipts_ip.html"));
            tpl.parse("save");
            return tpl.out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

    public String wnr_generateDelButton(ServletContext context) {
        try {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipts_ip.html"));
            tpl.parse("del");
            return tpl.out();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenTemplate.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

    public String generateWNRList(ServletContext context, String type, ArrayList<WeightNoteReceipt> weightNoteReceipts, ArrayList<PackingMaster> packings, boolean del_right, boolean save_right) throws Exception {
        Template tpl;
        switch (type) {
            case "IP":
            case "EX":
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipts_ip.html"));
                break;
            case "IM":
            case "XP":
            default:
                tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipt.html"));
                break;
        }

        float total_tare = 0;
        float total_gross = 0;
        int total_bags = 0;
        float total_pallet = 0;
        float total_bags_weight = 0;
        int count = 1;

        String readonly = "";
        if (!save_right) {
            readonly = "readonly";
        }

        for (WeightNoteReceipt wnr : weightNoteReceipts) {
            tpl.assign("id", wnr.getId().toString());
            tpl.assign("no", count + "");
            tpl.assign("ref_number", wnr.getRefNumber());
            tpl.assign("datetime", Common.getDateFromDatabase(wnr.getDate(), Common.date_format));
            tpl.assign("packing_option",
                    this.generatePackings(new Template(new File(context.getRealPath("/") + "templates/option.html")),
                            packings, wnr.getPackingMaster().getId()));
            tpl.assign("numberOfBag", wnr.getNoOfBags().toString());
            tpl.assign("kgPerBag", wnr.getPackingMaster().getWeight().toString());
//            if (!type.equals("IP")) {
//                tpl.assign("pallet_ref", wnr.getPalletName());
//                tpl.assign("pallet_weight", wnr.getPalletWeight().toString());
//            }
            tpl.assign("gross", wnr.getGrossWeight().toString());
            tpl.assign("tare", wnr.getTareWeight().toString());
            tpl.assign("net", (wnr.getGrossWeight() - wnr.getTareWeight()) + "");
            tpl.assign("read_only", readonly);

            if (save_right) {
                tpl.assign("btnsave", wnr_generateSaveButton(context));
            } else {
                tpl.assign("btnsave", "");
            }
            if (del_right) {
                tpl.assign("btndel", wnr_generateDelButton(context));
            } else {
                tpl.assign("btndel", "");
            }

            total_tare += wnr.getTareWeight();
            total_gross += wnr.getGrossWeight();
            total_bags += wnr.getNoOfBags();
            total_bags_weight += wnr.getPackingMaster().getWeight();
            //total_pallet += wnr.getPalletWeight();

            count++;
            if (type.equals("IP") || type.equals("EX")) {
                tpl.assign("opts", (wnr.getOptions() != null) ? wnr.getOptions() : "");
            }
            tpl.parse("table.row");
        }

        tpl.assign("total_bags", total_bags + "");
        tpl.assign("total_bags_weight", total_bags_weight + "");
        //tpl.assign("total_pallet", total_pallet + "");
        tpl.assign("total_gross", total_gross + "");
        tpl.assign("total_tare", total_tare + "");
        tpl.assign("total_net", (total_gross - total_tare) + "");
        if (save_right) {
            tpl.parse("table.header");
            tpl.parse("table.footer");
        }
        if (del_right) {
            tpl.parse("table.header");
            tpl.parse("table.footer");
        }

        tpl.parse("table");
        return tpl.out();
    }

    public String generateWNRRow(ServletContext context, String type, WeightNoteReceipt wnr, ArrayList<PackingMaster> packings, int no, boolean del_right, boolean save_right) throws Exception {
        Template tpl;
        switch (type) {
            case "EX":
            case "IP":
                tpl = new Template(new File(context.getRealPath("/") + "templates/wnr_row_ip.html"));
                break;
            case "IM":
            case "XP":
            default:
                tpl = new Template(new File(context.getRealPath("/") + "templates/wnr_row.html"));
                break;
        }

        String readonly = "";
        if (!save_right) {
            readonly = "readonly";
        }

        tpl.assign("id", wnr.getId().toString());
        tpl.assign("no", no + "");
        tpl.assign("ref_number", wnr.getRefNumber());
        tpl.assign("datetime", Common.getDateFromDatabase(wnr.getDate(), Common.date_format));
        tpl.assign("packing_option",
                this.generatePackings(new Template(new File(context.getRealPath("/") + "templates/option.html")),
                        packings, wnr.getPackingMaster().getId()));
        tpl.assign("numberOfBag", wnr.getNoOfBags().toString());
        tpl.assign("kgPerBag", wnr.getPackingMaster().getWeight().toString());
//        if (!type.equals("IP")) {
//            tpl.assign("pallet_weight", wnr.getPalletWeight().toString());
//            tpl.assign("pallet_ref", wnr.getPalletName());
//            tpl.assign("pallet_weight", wnr.getPalletWeight().toString());
//        }
        tpl.assign("read_only", readonly);
        tpl.assign("gross", wnr.getGrossWeight().toString());
        tpl.assign("tare", wnr.getTareWeight().toString());
        tpl.assign("net", (wnr.getGrossWeight() - wnr.getTareWeight()) + "");

        if (save_right) {
            tpl.assign("btnsave", wnr_generateSaveButton(context));
        } else {
            tpl.assign("btnsave", "");
        }
        if (del_right) {
            tpl.assign("btndel", wnr_generateDelButton(context));
        } else {
            tpl.assign("btndel", "");
        }

        tpl.parse("row");

        return tpl.out();
    }

    /**
     * used for viewing in detail weight note in allocation page
     */
    public String generateWnInDetail(ServletContext context, ArrayList<WeightNoteReceipt> wnrs, byte type, int wn_id) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/wn_in_detail.html"));
        for (WeightNoteReceipt wnr : wnrs) {
            tpl.assign("ref_number", wnr.getRefNumber());
            tpl.assign("gross", wnr.getGrossWeight().toString());
            tpl.assign("tare", wnr.getTareWeight().toString());
            tpl.assign("net", (wnr.getGrossWeight() - wnr.getTareWeight()) + "");
            tpl.assign("id", wnr.getId().toString());
            tpl.parse("main.row");
        }
        tpl.assign("wn_id", wn_id + "");
        tpl.assign("function", (type == Constants.AVAILABLE) ? "allocate" : "deallocate");
        tpl.assign("value", (type == Constants.AVAILABLE) ? "Allocate" : "Deallocate");
        return tpl.parse("main").out();
    }

    public String generateQrRefList(Template tpl, ArrayList<QualityReport> qrs) {
        for (QualityReport qr : qrs) {
            tpl.assign("class", "ref_child");
            tpl.assign("id", "qr_" + qr.getId());
            tpl.assign("value", qr.getRefNumber());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateQualityReportContent(Template tpl, QualityReport qr, String ref_number, String grade_name, String company_name) {
        tpl.assign("qr_ref", qr.getRefNumber());
        tpl.assign("date", (qr.getDate() != null) ? Common.getDateFromDatabase(qr.getDate(), Common.date_format) : "");
        tpl.assign("supplier", company_name);
        tpl.assign("supplier_ref", "");
        tpl.assign("wn_ref", ref_number);
        tpl.assign("grade", grade_name);
        tpl.assign("black", (qr.getBlack() != null) ? qr.getBlack() + "" : "0");
        tpl.assign("brown", (qr.getBrown() != null) ? qr.getBrown() + "" : "0");
        tpl.assign("broken", (qr.getBroken() != null) ? qr.getBroken() + "" : "0");
        tpl.assign("black_broken", (qr.getBlackBroken() != null) ? qr.getBlackBroken() + "" : "0");
        tpl.assign("worm", (qr.getWorm() != null) ? qr.getWorm() + "" : "0");
        tpl.assign("fm", (qr.getForeignMatter() != null) ? qr.getForeignMatter() + "" : "0");
        tpl.assign("moisture", (qr.getMoisture() != null) ? qr.getMoisture() + "" : "0");
        tpl.assign("moldy", (qr.getMoldy() != null) ? qr.getMoldy() + "" : "0");
        tpl.assign("old_crop", (qr.getOldCrop() != null) ? qr.getOldCrop() + "" : "0");
        tpl.assign("other_bean", (qr.getOtherBean() != null) ? qr.getOtherBean() + "" : "0");
        //tpl.assign("cherry", (qr.getCherry() != null) ? qr.getCherry() + "" : "0");
        tpl.assign("defect", (qr.getDefect() != null) ? qr.getDefect() + "" : "0");
        //tpl.assign("above_sc20", (qr.getAboveSc20() != null) ? qr.getAboveSc20() + "" : "0");
        tpl.assign("sc20", (qr.getSc20() != null) ? qr.getSc20() + "" : "0");
        tpl.assign("sc19", (qr.getSc19() != null) ? qr.getSc19() + "" : "0");
        tpl.assign("sc18", (qr.getSc18() != null) ? qr.getSc18() + "" : "0");
        tpl.assign("sc17", (qr.getSc17() != null) ? qr.getSc17() + "" : "0");
        tpl.assign("sc16", (qr.getSc16() != null) ? qr.getSc16() + "" : "0");
        tpl.assign("sc15", (qr.getSc15() != null) ? qr.getSc15() + "" : "0");
        tpl.assign("sc14", (qr.getSc14() != null) ? qr.getSc14() + "" : "0");
        tpl.assign("sc13", (qr.getSc13() != null) ? qr.getSc13() + "" : "0");
        tpl.assign("sc12", (qr.getSc12() != null) ? qr.getSc12() + "" : "0");
        tpl.assign("below_sc12", (qr.getBelowSc12() != null) ? qr.getBelowSc12() + "" : "0");
        tpl.assign("remark", (qr.getRemark() != null) ? qr.getRemark() + "" : "");
        if (qr.getStatus() == null) {
            tpl.assign("status", "0");
            tpl.assign("checked", "");
        } else {
            tpl.assign("status", qr.getStatus().toString());
            tpl.assign("checked", (qr.getStatus() == 1) ? "checked" : "");
        }

        tpl.parse("main");
        return tpl.out();
    }

    public String generateCupTestRows(Template tpl, ArrayList<CupTest> cuptestes) {
        int row_count = 1;
        if (cuptestes.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                if (i == 4) {
                    tpl.assign("id", (i + 1) + "");
                    tpl.assign("note", "");
                    tpl.assign("lastitem",
                            "<td class='rejected'><input type='text' class='rejected_value' id='rejected' value='' style='width: 94%; text-align: right'/></td>"
                            + "<td class='rejected total' align='left'> /" + (i + 1)
                            + "<a class='mpcth-icon-plus-squared' href='javascript:void(0)' id='add_row' style='margin-left: 5px'></a>"
                            + "<a class='mpcth-icon-minus-squared' href='javascript:void(0)' id='remove_row' style='margin-left: 5px'></a>"
                            + "</td>");
                    tpl.parse("main.item");
                } else {
                    tpl.assign("id", (i + 1) + "");
                    tpl.assign("note", "");
                    tpl.assign("lastitem", "");
                    tpl.parse("main.item");
                }

            }
            tpl.assign("row_count", "1");
            tpl.assign("class", "row_last last_item on");
            tpl.parse("main");
        } else {
            for (int z = 0; z < cuptestes.size(); z++) {
                if (z == 0) {
                    tpl.assign("id", (z + 1) + "");
                    tpl.assign("note", cuptestes.get(z).getNote());
                    tpl.assign("lastitem", "");
                    tpl.parse("main.item");
                } else if (z > 0 && z < (cuptestes.size() - 1)) {
                    if (((z + 1) % 5) == 0) {
                        tpl.assign("id", (z + 1) + "");
                        tpl.assign("note", cuptestes.get(z).getNote());
                        tpl.assign("lastitem",
                                "<td class='rejected'><input type='text' class='rejected_value' id='rejected' value=" + cuptestes.get(z).getQualityReport().getRejectedCup() + " style='width: 94%; text-align: right'/></td>"
                                + "<td class='rejected total' align='left'> /" + (z + 1)
                                + "<a class='mpcth-icon-plus-squared' href='javascript:void(0)' id='add_row' style='margin-left: 5px'></a>"
                                + "<a class='mpcth-icon-minus-squared' href='javascript:void(0)' id='remove_row' style='margin-left: 5px'></a>"
                                + "</td>");
                        tpl.parse("main.item");

                        tpl.assign("row_count", row_count + "");
                        tpl.assign("class", "row_normal on");
                        row_count++;
                        tpl.parse("main");

                    } else {
                        tpl.assign("id", (z + 1) + "");
                        tpl.assign("note", cuptestes.get(z).getNote());
                        tpl.assign("lastitem", "");
                        tpl.parse("main.item");
                    }
                } else {
                    tpl.assign("id", (z + 1) + "");
                    tpl.assign("note", cuptestes.get(z).getNote());
                    tpl.assign("lastitem",
                            "<td class='rejected'><input type='text' class='rejected_value' id='rejected' value=" + cuptestes.get(z).getQualityReport().getRejectedCup() + " style='width: 94%; text-align: right'/></td>"
                            + "<td class='rejected total' align='left'> /" + (z + 1)
                            + "<a class='mpcth-icon-plus-squared' href='javascript:void(0)' id='add_row' style='margin-left: 5px'></a>"
                            + "<a class='mpcth-icon-minus-squared' href='javascript:void(0)' id='remove_row' style='margin-left: 5px'></a>"
                            + "</td>");
                    tpl.parse("main.item");
                    tpl.assign("class", "row_last last_item on");
                    tpl.assign("row_count", row_count + "");
                    row_count++;
                    tpl.parse("main");
                }
            }
        }

        return tpl.out();
    }

    public String generateWrInfo(ServletContext context, String[] arr) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_receipt.html"));
        tpl.assign("date", arr[0]);
        tpl.assign("grade", arr[1]);
        tpl.assign("company", arr[2]);
        tpl.assign("remark", arr[3]);
        return tpl.parse("wr_info").out();
    }

    public String generateWrRowScroll(ServletContext context, ArrayList<Object[]> wns, String prefix) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/whr_row.html"));
        for (Object[] wn : wns) {
            tpl.assign("wn_ref", wn[1].toString());
            tpl.assign("wr_txt_color", (((Byte) wn[2]).equals(Constants.COMPLETE)) ? "black" : "red");
            tpl.assign("wn_status", (((Byte) wn[2]).equals(Constants.COMPLETE)) ? "Complete" : "Pending");
            tpl.assign("qr_ref", wn[3].toString());
            tpl.assign("qr_txt_color", (((Byte) wn[4]).equals(Constants.COMPLETE)) ? "black" : "red");
            tpl.assign("qr_status", (((Byte) wn[4]).equals(Constants.COMPLETE)) ? "Complete" : "Pending");
            tpl.assign("grade", wn[5].toString());
            tpl.assign("bags", wn[8].toString());
            tpl.assign("net", wn[7].toString());
            tpl.assign("prefix", prefix);
            tpl.assign("id", wn[0].toString());
            tpl.assign("chkVal", "Select");
            tpl.assign("checked", (((Byte) wn[2]).equals(Constants.COMPLETE) && ((Byte) wn[4]).equals(Constants.COMPLETE)) ? "" : "disabled");
            tpl.parse("main.scroll");
        }
        tpl.parse("main");
        return tpl.out();
    }

    public String generateWrRow(ServletContext context, ArrayList<Object[]> wns, String prefix) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/whr_row.html"));
        for (Object[] wn : wns) {
            tpl.assign("wn_ref", wn[1].toString());
            tpl.assign("wr_txt_color", (((Byte) wn[2]).equals(Constants.COMPLETE)) ? "black" : "red");
            tpl.assign("wn_status", (((Byte) wn[2]).equals(Constants.COMPLETE)) ? "Complete" : "Pending");
            tpl.assign("qr_ref", wn[3].toString());
            tpl.assign("qr_txt_color", (((Byte) wn[4]).equals(Constants.COMPLETE)) ? "black" : "red");
            tpl.assign("qr_status", (((Byte) wn[4]).equals(Constants.COMPLETE)) ? "Complete" : "Pending");
            tpl.assign("grade", wn[5].toString());
            tpl.assign("bags", wn[8].toString());
            tpl.assign("net", wn[7].toString());
            tpl.assign("prefix", prefix);
            tpl.assign("id", wn[0].toString());
            tpl.assign("chkVal", "Select");
            tpl.assign("checked", (((Byte) wn[2]).equals(Constants.COMPLETE) && ((Byte) wn[4]).equals(Constants.COMPLETE)) ? "" : "disabled");
            tpl.parse("main.row");
        }
        tpl.parse("main");
        return tpl.out();
    }

    public String generatePendingDiRow(ServletContext context, ArrayList<HashMap> dis, String prefix) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/pending_delivery.html"));
        float t_quantity = 0, t_pending = 0, t_delivered = 0;
        for (HashMap di : dis) {
            tpl.assign("di_ref", di.get("ref_number") + "");
            tpl.assign("supplier", di.get("supplier") + "");
            tpl.assign("origin", di.get("origin") + "");
            tpl.assign("quality", di.get("quality") + "");
            tpl.assign("grade", di.get("grade") + "");
            tpl.assign("packing", di.get("packing") + "");

            float tons = (float) di.get("tons");
            t_quantity += tons;
            tpl.assign("quantity", tons + "");

            float pending = (float) di.get("pending");
            tpl.assign("pending", pending + "");
            t_pending += pending;

            float delivered = (float) di.get("delivered");
            tpl.assign("delivered", delivered + "");
            t_delivered += delivered;

            tpl.assign("date", di.get("created_date") + "");
            tpl.assign("from", di.get("from_time") + "");
            tpl.assign("to", di.get("to_time") + "");
            tpl.assign("prefix", prefix);
            tpl.assign("id", di.get("id") + "");
            tpl.parse("body.row");
        }
        tpl.parse("body");

        tpl.assign("t_quantity", t_quantity + "");
        tpl.assign("t_delivered", t_delivered + "");
        tpl.assign("t_pending", t_pending + "");

        tpl.parse("footer");
        return tpl.out();
    }

    public String generatePendingPiRow(ServletContext context, ArrayList<HashMap> pis, String prefix) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/pending_processing.html"));
        float t_allocated = 0, t_inprocess = 0, t_exprocess = 0, t_pending = 0;
        for (HashMap pi : pis) {
            tpl.assign("po_ref", pi.get("ref_number") + "");
            tpl.assign("origin", pi.get("origin") + "");
            tpl.assign("quality", pi.get("quality") + "");
            tpl.assign("grade", pi.get("grade") + "");
            tpl.assign("packing", pi.get("packing") + "");

            float allocated = (float) pi.get("allocated");
            tpl.assign("allocated", allocated + "");
            t_allocated += allocated;

            float in_process = (float) pi.get("in_process");
            tpl.assign("inpro", in_process + "");
            t_inprocess += in_process;

            float ex_process = (float) pi.get("ex_process");
            tpl.assign("expro", ex_process + "");
            t_exprocess += ex_process;

            float pending = (float) pi.get("pending");
            tpl.assign("pending", pending + "");
            t_pending += pending;

            tpl.assign("from", pi.get("from_date") + "");
            tpl.assign("to", pi.get("to_date") + "");
            tpl.assign("prefix", prefix);
            tpl.assign("id", pi.get("id") + "");
            tpl.parse("body.row");
        }
        tpl.parse("body");

        tpl.assign("t_allocated", t_allocated + "");
        tpl.assign("t_inprocess", t_inprocess + "");
        tpl.assign("t_exprocess", t_exprocess + "");
        tpl.assign("t_pending", t_pending + "");

        tpl.parse("footer");

        return tpl.out();
    }

    public String generatePendingSiRow(ServletContext context, ArrayList<HashMap> sis, String prefix) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/pending_shipping.html"));
        float t_total = 0, t_delivered = 0, t_pending = 0;

        for (HashMap si : sis) {
            tpl.assign("si_ref", si.get("ref_number") + "");
            tpl.assign("buyer", si.get("buyer") + "");
            tpl.assign("origin", si.get("origin") + "");
            tpl.assign("quality", si.get("quality") + "");
            tpl.assign("grade", si.get("grade") + "");
            tpl.assign("packing", si.get("packing") + "");

            float tons = (float) si.get("tons");
            t_total += tons;
            tpl.assign("total", tons + "");

            float pending = (float) si.get("pending");
            tpl.assign("pending", pending + "");
            t_pending += pending;

            float delivered = (float) si.get("delivered");
            tpl.assign("delivered", delivered + "");
            t_delivered += delivered;

            tpl.assign("from", si.get("from_date") + "");
            tpl.assign("to", si.get("to_date") + "");
            tpl.assign("prefix", prefix);
            tpl.assign("id", si.get("id") + "");
            tpl.parse("body.row");
        }
        tpl.parse("body");

        tpl.assign("t_total", t_total + "");
        tpl.assign("t_delivered", t_delivered + "");
        tpl.assign("t_pending", t_pending + "");

        tpl.parse("footer");

        return tpl.out();
    }

    public String generateInstList_weighing(ServletContext context, ArrayList<Object[]> wns) throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        tpl.assign("class", "chosen");
        tpl.assign("id", "instruction_-1");
        tpl.assign("value", "All");
        tpl.parse("list");

        for (Object[] wn : wns) {
            tpl.assign("class", "");
            tpl.assign("id", "instruction_" + wn[0]);
            tpl.assign("value", wn[1].toString());
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generateFilterListCommon(Template tpl, ArrayList<Map> list, int selected_value, String prefix) {
        for (Map map : list) {
            tpl.assign("class", (map.get("id").equals(selected_value)) ? "chosen" : "");
            tpl.assign("id", prefix + map.get("id"));
            tpl.assign("value", map.get("name") + "");
            tpl.parse("list");
        }
        return tpl.out();
    }

    public String generatelistUser(Template tpl, ArrayList<User> users) {
        for (User user : users) {
            tpl.assign("id", user.getId().toString());
            tpl.assign("userName", user.getUserName());
            tpl.assign("fullName", user.getFullName());
            tpl.assign("img", "");
            tpl.parse("user");
        }
        return tpl.out();
    }

    

    public String generateShippingAdviceSents(Template tpl, Set<ShippingAdviceSent> sass, String baseShippingAdviceFolder) {
        boolean isNoRowHere = true;
        for (ShippingAdviceSent sas : sass) {
            tpl.assign("sasRef", sas.getRefNumber());
            tpl.assign("fileLink", baseShippingAdviceFolder + sas.getFileName());
            tpl.assign("sasToEmail", sas.getEmail());
            tpl.assign("sasUser", sas.getUser().getUserName());
            tpl.assign("sasDate", Common.getDateFromDatabase(sas.getDate(), Common.date_format));
            tpl.parse("row.pdfrow");
            isNoRowHere = false;
        }
        tpl.parse("row");

        if (isNoRowHere == true) {
            return "";
        }
        return tpl.out();
    }

    public String generateWnListInShippingAdvice(Template tpl, JSONArray wn_list, String block) throws JSONException {
        DecimalFormat df = new DecimalFormat("0.000");
        for (int i = 0; i < wn_list.length(); i++) {
            tpl.assign("con", wn_list.getJSONObject(i).getJSONObject("elements").getString("con"));
            tpl.assign("seal", wn_list.getJSONObject(i).getJSONObject("elements").getString("seal"));
            double bag = wn_list.getJSONObject(i).getJSONObject("elements").getDouble("bag");
            double gross = wn_list.getJSONObject(i).getJSONObject("elements").getDouble("gross");
            double tare = wn_list.getJSONObject(i).getJSONObject("elements").getDouble("tare");
            double net = wn_list.getJSONObject(i).getJSONObject("elements").getDouble("net");
            tpl.assign("bag", bag + "");
            tpl.assign("gross", df.format(gross) + "");
            tpl.assign("tare", df.format(tare) + "");
            tpl.assign("net", df.format(net) + "");
            tpl.assign("i", i + "");
            tpl.parse("row." + block);
        }

        tpl.parse("row");
        return tpl.out();
    }

    

    public String generateWnListInShippingAdvicePdf(Template tpl, JSONArray wn_list, JSONArray wnRows) throws JSONException {
        double tbag = 0, tgross = 0, ttare = 0, tnet = 0;
        DecimalFormat df = new DecimalFormat("0.000");
        DecimalFormat df2 = new DecimalFormat("0.0");
        for (int i = 0; i < wn_list.length(); i++) {
            tpl.assign("con", wn_list.getJSONObject(i).getJSONObject("elements").getString("con"));
            tpl.assign("seal", wn_list.getJSONObject(i).getJSONObject("elements").getString("seal"));
            double bag = wnRows.getJSONObject(i).getDouble("bag");
            double gross = wnRows.getJSONObject(i).getDouble("gross");
            double tare = wnRows.getJSONObject(i).getDouble("tare");
            double net = wnRows.getJSONObject(i).getDouble("net");

            tbag += bag;
            tgross += gross;
            ttare += tare;
            tnet += net;

            tpl.assign("bag", df2.format(bag) + "");
            tpl.assign("gross", df.format(gross) + "");
            tpl.assign("tare", df.format(tare) + "");
            tpl.assign("net", df.format(net) + "");
            tpl.parse("row.wnrow");

        }
        tpl.assign("tbag", df2.format(tbag) + "");
        tpl.assign("tgross", df.format(tgross) + "");
        tpl.assign("ttare", df.format(ttare) + "");
        tpl.assign("tnet", df.format(tnet) + "");

        tpl.parse("row");
        return tpl.out();
    }

    public String generateSampleSent(Set<SampleSent> sampleSents, Template tpl) {

        for (SampleSent ss : sampleSents) {
            tpl.assign("refNumber", ss.getRefNumber());
            tpl.assign("courier", ss.getCourierMaster() != null ? ss.getCourierMaster().getName() : "");
            tpl.assign("awbNo", ss.getTrackingNo() != null ? ss.getTrackingNo() : "");
            tpl.assign("remark", ss.getRemark() != null ? ss.getRemark() : "");
            byte approvalStatus = ss.getApprovalStatus();
            String approval = "";
            switch (approvalStatus) {
                case 0:
                    approval = "pending";
                    break;
                case 1:
                    approval = "approved";
                    break;
                case 2:
                    approval = "rejected";
                    break;
            }
            tpl.assign("approvalStatus", approval);
            tpl.assign("user", ss.getUser().getUserName());
            tpl.assign("updatedDate", Common.getDateFromDatabase(ss.getUpdatedDate(), Common.date_format));

            tpl.parse("row.SSrow");
        }
        tpl.parse("row");
        return tpl.out();
    }

}
