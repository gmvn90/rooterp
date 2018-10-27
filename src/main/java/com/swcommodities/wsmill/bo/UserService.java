/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.UserDao;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.ClientUser;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.view.ClientUserView;
import com.swcommodities.wsmill.utils.Constants;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

    private UserDao userDao;
    private PageService pageService;
    private AuthorizationService authorizationService;

    public PageService getPageService() {
        return pageService;
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return this.userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public User getUser(String username, String password) {
        return this.userDao.getUser(username, password);
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public String generateUserPermissionList(Template tpl, User user) {
        ArrayList<Page> pages = pageService.getPages();

        for (Page page : pages) {
            assignPagePermission(tpl, user, page, Boolean.FALSE);
        }
        tpl.parse("permission_container");
        return tpl.out();
    }

    public String getUserAccessList() {
        ArrayList<Page> pages = pageService.getPages();
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        
        for (Page page : pages) {   /* parent: level 1 */
            Boolean hasChild = Boolean.FALSE;
            Map<String, Object> m = new HashMap<>();

            m.put("id", page.getId() + "");
            m.put("name", page.getName());

            ArrayList<Page> childlist = new ArrayList<>(page.getPages());
            ArrayList<Map<String, Object>> child_map = new ArrayList<>();

            if (!childlist.isEmpty()) { /* child: level 2 */
                int length = childlist.size();
                hasChild = Boolean.TRUE;
                int count = 1;
                for (Page child : childlist) {
                    Map<String, Object> cm = new HashMap<>();
                    cm.put("order", count + "");
                    cm.put("length", length + "");
                    cm.put("id", child.getId() + "");
                    cm.put("name", child.getName());
                    cm.put("style", child.getAccessStyle().toString());
                    Boolean _hasChild = Boolean.FALSE;

                    ArrayList<Page> final_childlist = new ArrayList<>(child.getPages());
                    ArrayList<Map<String, Object>> final_child_map = new ArrayList<>();

                    if (!final_childlist.isEmpty()) {       /* child: level 3 */
                        _hasChild = Boolean.TRUE;
                        int length1 = final_childlist.size();
                        int count1 = 1;
                        for (Page final_child : final_childlist) {
                            Map<String, Object> fm = new HashMap<>();
                            fm.put("order", count1 + "");
                            fm.put("length", length1 + "");
                            fm.put("id", final_child.getId() + "");
                            fm.put("name", final_child.getName());
                            fm.put("style", final_child.getAccessStyle().toString());
                            final_child_map.add(fm);
                            count1++;
                        }
                    }
                    cm.put("hasChild", _hasChild.toString());
                    cm.put("child", final_child_map);
                    count++;
                    child_map.add(cm);
                }
            }
            m.put("hasChild", hasChild.toString());
            m.put("style", page.getAccessStyle().toString());
            m.put("child", child_map);
            users.add(m);
        }
        JSONArray json_array = new JSONArray(users);
        Map<String, Object> mp = new HashMap<>();
        mp.put("access_list", json_array);
        JSONObject json = new JSONObject(mp);
        System.out.println("OUTPUT JSON : " + json.toString());
        return json.toString();
    }

    public void assignPagePermission(Template tpl, User user, Page page, Boolean isLevel02) {
        Authorization au = (user != null) ? authorizationService.getAuthorizationOfUserInPage(user, page) : null;
        tpl.assign("page_section", isLevel02 ? "section" : "page");
        String permissionName = isLevel02 ? (page.getPage().getId() + "-" + page.getId()) : (page.getId()) + "";
        tpl.assign("page_id", permissionName);
        tpl.assign("indent", (isLevel02) ? "indent_lvl_2" : "indent_lvl_1");
        tpl.assign("bold", (isLevel02) ? "" : "bold");
        tpl.assign("page", page.getName());
        tpl.assign("link", (isLevel02 && !page.getPages().isEmpty()) ? "link" : "");
        if (page.getAccessStyle().equals(Constants.p_full)/* || (isLevel02 && page.getAccessStyle().equals(Constants.p_yes_no))*/) {
//            if (Arrays.asList(Constants.strPS).contains(page.getId())) {
//                assignUserPermission(tpl, Constants.permissionPS, (au != null) ? au.getPermission() : (byte) 0);
//            } else {
            assignUserPermission(tpl, Constants.PERMISSION.get(Constants.p_full), (au != null) ? au.getPermission() : (byte) 0);
            //}
        } else {
            assignUserPermission(tpl, Constants.PERMISSION.get(Constants.p_yes_no), (au != null) ? au.getPermission() : (byte) 0);
        }
        tpl.parse("permission_container.access_right");
        if (!isLevel02) {
            ArrayList<Page> pages = pageService.getPageSections(page);
            for (Page pageSection : pages) {
                assignPagePermission(tpl, user, pageSection, !isLevel02);
            }

        }
    }

    public void assignUserPermission(Template tpl, Map<Byte, String> permission, Byte selectedValue) {
        for (Byte key : permission.keySet()) {
            if (key == selectedValue) {
                tpl.assign("checked", "checked");
            } else {
                tpl.assign("checked", "");
            }
            tpl.assign("p_value", key + "");
            tpl.assign("p_text", permission.get(key));
            tpl.parse("permission_container.access_right.permission");
        }
    }

    public String generatePermissionDialog(Template tpl, String body) {
        tpl.assign("body", body);
        tpl.parse("permission_dialog");
        return tpl.out();
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }
    
    public ClientUser checkUserClient(int user_id) {
        return userDao.checkUserClient(user_id);
    }
    
    public String getUserNameById(int id){
        String result = userDao.getUserNameById(id);
        if (result != null){
            return result;
        }
        return "";
    }
    
    public ClientUserView getUserClient(int user_id) {
        return userDao.getUserClient(user_id);
    }
    
    public ClientUser updateClientUser(ClientUser client){
        return userDao.updateClientUser(client);
    }
    public void removeClientUserView(ClientUserView user){
        userDao.removeClientUserView(user);
    }
    public ClientUserView updateClientUserView(ClientUserView client){
        return userDao.updateClientUserView(client);
    }
}
