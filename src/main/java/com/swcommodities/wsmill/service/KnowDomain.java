/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import com.swcommodities.wsmill.application.service.ShippingInstructionService;
import com.swcommodities.wsmill.formController.form.ShippingInstructionForm;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.InvoiceRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.SampleSentRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.repository.UserRepository;
import com.swcommodities.wsmill.repository.WeightNoteReceiptRepository;
import com.swcommodities.wsmill.repository.WeightNoteRepository;

import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author macOS
 */

@Service
public class KnowDomain {
    
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired ShippingInstructionRepository shippingInstructionRepository;
    @Autowired WeightNoteRepository weightNoteRepository;
    @Autowired UserRepository userRepository;
    @Autowired SampleSentRepository sampleSentRepository;
    @Autowired CourierMasterRepository courierMasterRepository;
    @Autowired ShippingInstructionService shippingInstructionService;
    @Autowired PITypeRepository piTypeRepository;
    @Autowired PackingRepository packingRepository;
    @Autowired GradeRepository gradeRepository;
    @Autowired CompanyRepository companyRepository;
    @Autowired ProcessingInstructionRepository processingInstructionRepository;
    @Autowired WeightNoteReceiptRepository weightNoteReceiptRepository;
    
    public String getRandomString() {
        return  UUID.randomUUID().toString();
    }
    
    public String getRandomName() {
    		return getRandomString().substring(0, 5);
    }
    
    public Invoice createInvoice(String refNumber) {
        Invoice invoice = new Invoice();
        invoice.setRefNumber(refNumber);
        invoiceRepository.save(invoice);
        return invoice;
    }
    
    public ShippingInstruction createShippingInstruction(User user) throws Exception {
        ShippingInstructionForm siForm = new ShippingInstructionForm();
		siForm.setTotalTons(100);
		siForm.setNumberOfContainer(5);
		siForm.setTonPerContainer(20);
		String siRef = shippingInstructionService.updateOrSaveShippingInstruction(siForm, user);
        return shippingInstructionService.getByRef(siRef);
    }
    
    public ShippingInstruction createShippingInstruction() throws Exception {
        User user = new User("aa", "aa", true);
        saveUser(user);
        ShippingInstructionForm siForm = new ShippingInstructionForm();
		siForm.setTotalTons(100);
		siForm.setNumberOfContainer(5);
		siForm.setTonPerContainer(20);
		String siRef = shippingInstructionService.updateOrSaveShippingInstruction(siForm, user);
        return shippingInstructionService.getByRef(siRef);
    }
    
    public User createOneUser(String username, String md5Password) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(md5Password);
        user.setEmail(getRandomString() + ".gmail.com");
        user.setActive(true);
        userRepository.save(user);        
        return user;
    }
    
    public User createOneRandomUser() {
        User user = new User();
        user.setUserName(getRandomName());
        user.setPassword(getRandomString());
        user.setEmail(getRandomString() + ".gmail.com");
        user.setActive(true);
        userRepository.save(user);        
        return user;
    }
    
    public ShippingInstruction reload(String refNumber) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(refNumber);
        return shippingInstruction;
    }
    
    public SampleSent createSampleSent(String refNumber, ShippingInstruction shippingInstruction, User user) {
        SampleSent ss = new SampleSent();
        ss.setInitialInfo(refNumber, user);
        shippingInstruction.addSampleSent(ss);
        return ss;
    }
    
    public WeightNote addWeightNote(ShippingInstruction shippingInstruction, float grossWeight, float tareWeight, String wnRef) {
        WeightNote weightNote = new WeightNote(grossWeight, tareWeight);
        weightNote.setShippingInstruction(shippingInstruction);
        weightNote.setRefNumber(getRandomString());
        weightNoteRepository.save(weightNote);
        WeightNoteReceipt wnr = new WeightNoteReceipt(grossWeight, tareWeight);
        wnr.setWeightNote(weightNote);
        weightNoteReceiptRepository.save(wnr);
        shippingInstruction.getWeightNotes().add(weightNote);
        shippingInstructionRepository.save(shippingInstruction);
        return weightNote;
    }
    
    public WeightNoteReceipt addWeightNoteReceipt(WeightNote wn) {
        WeightNoteReceipt wnr = new WeightNoteReceipt(100, 10);
        wnr.setWeightNote(wn);
        weightNoteReceiptRepository.save(wnr);
        return wnr;
    }
    
    public void saveShippingInstruction(ShippingInstruction shippingInstruction) {
        shippingInstructionRepository.save(shippingInstruction);
    }
    
    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    public SampleSentCache getBySS(int id) {
        return sampleSentRepository.getBySS(id);
    }
    
    public CourierMaster createOneCourierMaster() {
    	CourierMaster courierMaster = new CourierMaster("DHL");
    	courierMaster.setFullName("DHL");
    	courierMasterRepository.save(courierMaster);
    	return courierMaster;
    }
    
    public SampleSent getOneSampleSent(int id) {
    	return sampleSentRepository.findOne(id);
    }
    
    public ShippingInstruction getOneSI(int id) {
    	return shippingInstructionRepository.findOne(id);
    }
    public ShippingInstruction getOneSIByRef(String id) {
    	return shippingInstructionRepository.findFirstByRefNumber(id);
    }
    
    public void saveWeightNote(WeightNote wn) {
        weightNoteRepository.save(wn);
    }
    
    public void completeAllWn(ShippingInstruction shippingInstruction) {
        Iterator<WeightNote> iterator = shippingInstruction.getWeightNotes().iterator();
        while(iterator.hasNext()) {
            WeightNote  wn = iterator.next();
            wn.complete();
            saveWeightNote(wn);
        }
    }
    
    public PIType createPiType() {
    		PIType piType = new PIType();
    		piType.setName("pitype");
    		piTypeRepository.save(piType);
    		return piType;
    }
    
    public PackingMaster createPackingMaster() {
    		PackingMaster packingMaster = new PackingMaster();
    		packingMaster.setName("packingmaster");
    		packingRepository.save(packingMaster);
    		return packingMaster;
    }
    
    public GradeMaster createGradeMaster() {
    		GradeMaster gradeMaster = new GradeMaster();
    		gradeMaster.setName("grademaster");
    		gradeRepository.save(gradeMaster);
    		return gradeMaster;
    }
    
    public CompanyMaster createCompanyMaster() {
    		CompanyMaster companyMaster = new CompanyMaster();
    		companyMaster.setName(getRandomName());
    		companyRepository.save(companyMaster);
    		return companyMaster;
    }
    
    public ProcessingInstruction findOne(String refNumber) {
    		return processingInstructionRepository.findFirstByRefNumber(refNumber);
    }
    
    
}
