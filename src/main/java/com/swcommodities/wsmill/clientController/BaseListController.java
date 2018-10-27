package com.swcommodities.wsmill.clientController;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

/**
 * Created by dunguyen on 8/5/16.
 */
public abstract class BaseListController<T, ID extends Serializable> {
		
    protected Logger logger = LoggerFactory.getLogger(BaseListController.class);

    protected ClientRefJpaRepository<T, ID> repo;
    
    public BaseListController(ClientRefJpaRepository<T, ID> repo) {
        this.repo = repo;
    }
    
    public ClientRefJpaRepository<T, ID> getRepo() {
		return repo;
	}

	public void setRepo(ClientRefJpaRepository<T, ID> repo) {
		this.repo = repo;
	}
	

    @RequestMapping(value = "/all.json", method=RequestMethod.GET)
    public ResponseEntity<List<T>> getAll() {
    	System.out.println("ResponseEntityGETall");
    	List<T> all = this.repo.findAll();
        return new ResponseEntity<List<T>>(all, HttpStatus.OK);
    }

    @RequestMapping(value="/allRef.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef() {
    	System.out.println("ResponseEntityGETallRef");
    	List<RefList> list = this.repo.findRefList();
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }

    
    
}
