package com.swcommodities.wsmill.clientController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.contract.OwnerInstruction;
import com.swcommodities.wsmill.hibernate.dto.contract.OwnerJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.UserRepository;

/**
 * Created by dunguyen on 8/5/16.
 */
public abstract class BaseOwnerInstructionController<T extends OwnerInstruction, ID extends Serializable> {
		
    protected Logger logger = LoggerFactory.getLogger(BaseOwnerInstructionController.class);

    protected OwnerJpaRepository<T, ID> repo;
    
    @Autowired protected UserRepository userRepository;
    
    @Autowired CompanyRepository companyRepository;


    public BaseOwnerInstructionController(OwnerJpaRepository<T, ID> repo) {
        this.repo = repo;
    }
    
    public OwnerJpaRepository<T, ID> getRepo() {
		return repo;
	}

	public void setRepo(OwnerJpaRepository<T, ID> repo) {
		this.repo = repo;
	}
	
	protected Integer getCompanyId(HttpSession httpSession) {
		Integer company_id = (Integer) httpSession.getAttribute("company_id");
		return company_id;
	}
	
	protected Integer getUserId(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		return user.getId();
	}

    @RequestMapping(value = "/all.json", method=RequestMethod.GET)
    public ResponseEntity<List<T>> get(HttpSession httpSession) {
    	System.out.println("ResponseEntityGET");
    	List<T> all = new ArrayList<>();
    	Integer company_id = getCompanyId(httpSession);
    	if(company_id > 0) {
    		all = this.repo.findByCompanyMasterByClientId_Id(company_id);
    	}
        return new ResponseEntity<List<T>>(all, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/allRef.json", method=RequestMethod.GET)
    public ResponseEntity<List<RefList>> getAllRef(HttpSession httpSession) {
    	System.out.println("ResponseEntityGET");
    	System.out.println(httpSession.getAttribute("username"));
    	List<RefList> all = new ArrayList<>();
    	Integer company_id = getCompanyId(httpSession);
    	if(company_id > 0) {
    		all = this.repo.findOwnerRefList(company_id);
    	}
        return new ResponseEntity<List<RefList>>(all, HttpStatus.OK);
    }

   

    @RequestMapping(value="/{id}.json", method=RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Map<String, Object>> delete(@PathVariable ID id) {
    	System.out.println("ResponseEntitySingleDelete");
        //this.repo.delete(id);
        Map<String, Object> m = Maps.newHashMap();
        m.put("success", true);
        return new ResponseEntity<Map<String,Object>>(m, HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/test_user.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<User> getUserRequest(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    public String getIdJson(int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", id);
        return object.toString();
    }
    
}
