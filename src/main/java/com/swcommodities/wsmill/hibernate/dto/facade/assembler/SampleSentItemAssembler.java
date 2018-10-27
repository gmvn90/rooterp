package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import org.modelmapper.ModelMapper;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.xpath;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.swcommodities.wsmill.domain.model.SampleSentItem;
import com.swcommodities.wsmill.formController.form.SampleSentItemForm;
import com.swcommodities.wsmill.formController.form.SampleSentItemNewForm;
import com.swcommodities.wsmill.formController.form.SampleSentItemUpdateForm;


public class SampleSentItemAssembler {
	ModelMapper modelMapper = new ModelMapper();
	
	public SampleSentItem fromNewDto(SampleSentItemNewForm form) {
		return modelMapper.map(form, SampleSentItem.class);
	}
	
	public SampleSentItem fromUpdateDto(SampleSentItemUpdateForm form, SampleSentItem item) {
		modelMapper.map(form, item);
		return item;
	}
	
	public SampleSentItemForm fromObject(SampleSentItem item) {
		return modelMapper.map(item, SampleSentItemForm.class);
	}
	
	public List<SampleSentItemForm> fromObject(List<SampleSentItem> items) {
		return items.stream().map(x -> fromObject(x)).collect(Collectors.toList());
	}
	
}
