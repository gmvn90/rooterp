package com.swcommodities.wsmill.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;

@Service
public class PIDomainService {
	
	@Autowired ProcessingInstructionRepository processingInstructionRepository;
	@Autowired PIRefNumberProviderService piRefNumberProviderService;
	
	public void newPI(ProcessingInstruction processingInstruction, User user) {
		processingInstruction.initNewSelf(user);
		processingInstructionRepository.save(processingInstruction);
	}
}
