package com.swcommodities.wsmill.hibernate.dto.contract;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;

public interface OwnerInstruction {
	CompanyMaster getCompanyMasterByClientId();
	void setCompanyMasterByClientId(CompanyMaster companyMaster);
}
