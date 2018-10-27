package com.swcommodities.wsmill.formController.form;

import java.util.Date;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SampleSentItemForm {
	private String sampleSentItemId;
	private String sampleSentItemRefNumber;
    private String lotNumberRefNumber;
    private ApprovalStatus status;
    private String updaterUserName;
    private Date updated;
}
