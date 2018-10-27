/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.bo.AuthorizationService;
import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.PageService;
import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.AuthorizationId;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.view.ClientUserView;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class AccountController {

    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "userdata";
    private static final String usernameHuong = "huong";
    // upload settings
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 4; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private Pattern pattern;

    private static final Logger logger = Logger.getLogger(AccountController.class);
    private File fileUploadPath;

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private PageService pageService;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;

    @RequestMapping("load_user_list.htm")
    public
    @ResponseBody
    void load_user_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
        ArrayList<Map<String, String>> map_list = new ArrayList<>();
        int count = 0;
        for (User user : users) {
            count++;
            Map<String, String> map = new HashMap<>();
            map.put("order", count + "");
            map.put("id", user.getId().toString());
            map.put("username", user.getUserName());
            map.put("fullname", user.getFullName());
            map_list.add(map);
        }

        JSONArray json_array = new JSONArray(map_list);
        Map<String, Object> mp = new HashMap<>();
        mp.put("users", json_array);
        JSONObject json = new JSONObject(mp);
        response.getWriter().print("[" + json.toString() + "]");
    }

    @RequestMapping("checkUsernameFromSession.htm")
    public
    @ResponseBody
    void getUsernameFromSession(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        String username = supporter.getStringRequest("username");
        User user = (User) request.getSession().getAttribute("user");
        if (user.getUserName().equals(username)) {
            response.getWriter().print("1");
        } else {
            response.getWriter().print("0");
        }

    }

    @RequestMapping("load_user_info.htm")
    public
    @ResponseBody
    void load_user_info(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int user_id = supporter.getIntValue("user_id");
        User user = userService.findUserById(user_id);
        JSONObject json = new JSONObject();
        json.put("id", user.getId().toString());
        json.put("userName", user.getUserName());
        json.put("fullName", user.getFullName());
        json.put("dob", Common.getDateFromDatabase(user.getDob(), Common.date_format_ddMMyyyy_dash));
        json.put("phone", user.getPhone());
        json.put("email", user.getEmail());
        json.put("password", user.getPassword());
        json.put("active", user.isActive());
        json.put("isOnlyForClientSite", user.isOnlyForClientSite());

        //check is client
        ClientUserView client = userService.getUserClient(user_id);
        if (client != null) {
            json.put("client", 1);
            json.put("company", client.getCompanyId());
        } else {
            json.put("client", 0);
            json.put("company", 0);
        }

        JSONArray json_array = new JSONArray(authorizationService.getPermission(user_id));
        json.put("perms", json_array);

        response.getWriter().print("[" + json.toString() + "]");
    }

    @RequestMapping("get_user_profile.htm")
    public
    @ResponseBody
    void get_user_profile(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        JSONObject json = new JSONObject();
        json.put("id", user.getId().toString());
        json.put("userName", user.getUserName());
        json.put("fullName", user.getFullName());
        json.put("dob", Common.getDateFromDatabase(user.getDob(), Common.date_format_ddMMyyyy_dash));
        json.put("phone", user.getPhone());
        json.put("email", user.getEmail());
        response.getWriter().print("[" + json.toString() + "]");
    }

    @RequestMapping("load_access_list.htm")
    public
    @ResponseBody
    void load_access_list(HttpServletResponse response) throws Exception {
        response.getWriter().print("[" + userService.getUserAccessList() + "]");
    }

    @RequestMapping(value = "upload.json", method = RequestMethod.POST)
    public
    @ResponseBody
    void uploadImage(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
        try {
            String path = "";
            //if (request instanceof MultipartHttpServletRequest){
            User user = (User) request.getSession().getAttribute("user");
//                MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest)request;
//                Iterator<String> filenames = multiPartRequest.getFileNames();
//                while(filenames.hasNext()){
//                    String filename = filenames.next();
//                    MultipartFile file = multiPartRequest.getFile(filename);
            path = this.saveMultipartToDisk(file, user);
            JSONObject json;
            if (user.getExtras() != null && !user.getExtras().equals("")) {
                json = new JSONObject(user.getExtras());
            } else {
                json = new JSONObject();
            }
            json.put("name", file.getOriginalFilename());
            json.put("size", MAX_FILE_SIZE);
            json.put("url", Common.getUrlPath(request) + UPLOAD_DIRECTORY + "/" + user.getId() + "/" + file.getOriginalFilename());
            json.put("thumbnailUrl", "thumbnailUsrPic.html?pic=" + UPLOAD_DIRECTORY + "/" + user.getId() + "/" + file.getOriginalFilename());
            json.put("deletelUrl", "deleteUsrPic.html?pic=" + UPLOAD_DIRECTORY + "/" + user.getId() + "/" + file.getOriginalFilename());
            json.put("deleteType", "DELETE");
            user.setExtras(json.toString());
            userService.updateUser(user);
//                }
            response.getWriter().print(json.toString());
            //}
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

    }

    @RequestMapping("update_permission.htm")
    public
    @ResponseBody
    void update_permission(HttpServletResponse response) throws IOException {
        try {
            ServletSupporter supporter = new ServletSupporter(request);
            String message = "New Account Has Been Created";
            String password = supporter.getStringRequest("password");
            int client = supporter.getIntValue("client");
            int company_id = supporter.getIntValue("company");
            User user;
            if (supporter.getIntValue("id") != 0) {   /* update existing user */
                user = userService.findUserById(supporter.getIntValue("id"));
                user.setId(supporter.getIntValue("id"));
                message = "Update Succeeded";
                if (!password.equals("")) {
                    user.setPassword(password);
                }
            } else {
                user = new User();
                user.setPassword(password);
            }
            user.setUserName(supporter.getStringRequest("userName"));
            user.setFullName(supporter.getStringRequest("fullName"));
            user.setDob(!(supporter.getStringRequest("dob").trim().equals("")) ? supporter.getDateValue("dob", Common.date_format_ddMMyyyy_dash) : null);
            user.setPhone(supporter.getStringRequest("phone"));
            user.setEmail(supporter.getStringRequest("email"));
            user.setActive(supporter.getBooleanValue("active"));
            user.setOnlyForClientSite(supporter.getBooleanValue("isOnlyForClientSite"));
            user = userService.updateUser(user);
            if (company_id > 0) {
                ClientUserView clientUser = userService.getUserClient(user.getId());
                if (client == 1) {
                    if (clientUser != null) {
                        clientUser.setCompanyId(company_id);
                        userService.updateClientUserView(clientUser);
                    } else {
                        clientUser = new ClientUserView();
                        clientUser.setUserId(user.getId());
                        clientUser.setCompanyId(company_id);
                        userService.updateClientUserView(clientUser);
                    }
                } else if (client == 0) {
                    if (clientUser != null) {
                        userService.removeClientUserView(clientUser);
                    }
                }
//                CompanyMaster company = companyService.getCompanyById(company_id);
////                ClientUser clientUser = new ClientUser(new ClientUserId(user.getId(), company.getId()), company, user);
//                userService.updateClientUser(clientUser);
            }

            if (!supporter.getStringRequest("perms").equals("")) {
                JSONArray json_array = new JSONArray(supporter.getStringRequest("perms"));
                for (int i = 0; i < json_array.length(); i++) {
                    JSONObject obj = json_array.getJSONObject(i);
                    Page page = pageService.getPageByPageCode(obj.getInt("page"));
                    Authorization auth = new Authorization(new AuthorizationId(user.getId(), page.getId()), user, page, Byte.valueOf(obj.getString("perm")));
                    authorizationService.updateAuthorization(auth);
                }
            }
            response.getWriter().print(message);
        } catch (ParseException | JSONException | NumberFormatException | IOException e) {
            response.getWriter().print("Update Failed");
        }
    }

    @RequestMapping("getUserName.htm")
    public
    @ResponseBody
    void getUserName(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        String user = userService.getUserNameById(supporter.getIntValue("id"));
        response.getWriter().print(user);
    }

    @RequestMapping("update_profile.htm")
    public
    @ResponseBody
    void update_profile(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        try {
            ServletSupporter supporter = new ServletSupporter(request);
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                user.setDob(supporter.getDateValue("dob", Common.date_format_ddMMyyyy_dash));
                user.setEmail(supporter.getStringRequest("email"));
                user.setFullName(supporter.getStringRequest("fullName"));
                user.setPhone(supporter.getStringRequest("phone"));
                user.setPassword(supporter.getStringRequest("password"));
                userService.updateUser(user);
                json.put("type", "success");
                json.put("msg", "Update succeeded");
            } else {
                json.put("type", "error");
                json.put("msg", "Session Timed Out");
            }
        } catch (ParseException | JSONException e) {
            e.printStackTrace();
            json.put("type", "error");
            json.put("msg", "Update Failed");
        } finally {
            response.getWriter().write(json.toString());
        }
    }

    //get directory base on user_id
    private String calculateDestinationDirectory(User user) {
        String result = this.context.getRealPath("");
        result += "/";
        result += UPLOAD_DIRECTORY;
        result += "/";
        result += user.getId();
        return result;
    }

    //get the file dir
    private String calculateDestinationPath(MultipartFile file, User user) {
        String result = this.calculateDestinationDirectory(user);
        result += "/";
        result += file.getOriginalFilename();
        return result;
    }

    private String saveMultipartToDisk(MultipartFile file, User user) throws Exception {
        File dir = new File(this.calculateDestinationDirectory(user));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path = this.calculateDestinationPath(file, user);
        File multipartFile = new File(path);
        file.transferTo(multipartFile);
        return path;
    }
}
