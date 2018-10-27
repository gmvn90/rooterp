package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SampleSentItemUpdateForm {
	
	private String sampleSentItemId;
	public ApprovalStatus approvalStatus;
	
}
