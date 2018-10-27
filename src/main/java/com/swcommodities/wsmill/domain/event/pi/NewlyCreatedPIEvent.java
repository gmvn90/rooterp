package com.swcommodities.wsmill.domain.event.pi;

import com.swcommodities.wsmill.domain.event.BaseObjectEvent;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;

public class NewlyCreatedPIEvent extends BaseObjectEvent<ProcessingInstruction>  {
	
	public NewlyCreatedPIEvent(ProcessingInstruction item) {
		super(item);
	}
	
}
