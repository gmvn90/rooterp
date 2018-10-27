package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.formController.form.NewPIForm;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToint;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.PackingMasterToInt;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.PiTypeToInteger;
import fr.xebia.extras.selma.Field;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL, withCustom= {
		PackingMasterToInt.class, PiTypeToInteger.class, GradeToId.class, CompanyMasterToint.class
}, withCustomFields = {
    @Field({"ProcessingInstruction.companyMasterByClientId", "NewPIForm.clientId"}),
})
public interface SelmaNewPIMapper {
    @Maps(withCustomFields = {
        @Field({"ProcessingInstruction.user.userName", "NewPIForm.user"}),
    })
	NewPIForm fromModel(ProcessingInstruction processingInstruction);
    @Maps(withIgnoreFields = {
        "createdDate", "allocatedWeight", "inProcessWeight", "exProcessWeight", "pendingWeight", "updatedDate", "refNumber"
    }, withCustom = {
        
    })
	ProcessingInstruction fromForm(NewPIForm form, ProcessingInstruction updated);
}
