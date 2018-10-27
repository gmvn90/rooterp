package com.swcommodities.wsmill.clientController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.repository.CompanyRepository;

@RequestMapping("/millclient/companies")
@org.springframework.stereotype.Controller
public class CompanyListController extends BaseListController<CompanyMaster, Integer> {

	@Autowired
    public CompanyListController(CompanyRepository repo) {
        super(repo);
    }
	
	@RequestMapping(value="/allRef_Bank.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Bank() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Bank");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Broker.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Broker() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Broker");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Buyer.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Buyer() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Buyer");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Client.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Client() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Client");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Consignee.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Consignee() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Consignee");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Controller.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Controller() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Controller");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Notify_Party.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Notify_Party() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Notify Party");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Service.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Service() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Service");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Shipper.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Shipper() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Shipper");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/allRef_Shipping_Line.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Shipping_Line() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Shipping Line");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/allRef_Supplier.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Supplier() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Supplier");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
	@RequestMapping(value="/allRef_Warehousing.json", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<RefList>> getAllRef_Warehousing() {
    	List<RefList> list = ((CompanyRepository) this.repo).findByLocalName("Warehousing");
        return new ResponseEntity<List<RefList>>(list, HttpStatus.OK);
    }
	
}
