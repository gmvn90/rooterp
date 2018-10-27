package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.formController.form.NewPIForm;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaNewPIMapper;
import fr.xebia.extras.selma.Selma;

public class PIAssembler {
	
	public NewPIForm fromModel(ProcessingInstruction processingInstruction) {
		SelmaNewPIMapper mapper = Selma.builder(SelmaNewPIMapper.class).build();
		return mapper.fromModel(processingInstruction);
	}
	
	public ProcessingInstruction fromForm(NewPIForm form, ProcessingInstruction origin) {
		SelmaNewPIMapper mapper = Selma.builder(SelmaNewPIMapper.class).build();
		return mapper.fromForm(form, origin);
	}
	
}
