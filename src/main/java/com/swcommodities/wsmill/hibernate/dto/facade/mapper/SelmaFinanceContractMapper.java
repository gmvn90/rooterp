/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyToNameMapper;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompletionStatusToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.DailyBasisToValue;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.LocationMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.OriginMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.QualityMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.UserToName;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */
@Mapper(
    withCustomFields = {
        @Field(value = "client", withCustom = CompanyToNameMapper.class),
        @Field(value = "dailyBasis", withCustom = DailyBasisToValue.class),
        @Field(value = "quality", withCustom = QualityMasterToName.class),
        @Field(value = "origin", withCustom = OriginMasterToName.class),
        @Field(value = "location", withCustom = LocationMasterToName.class),
        @Field(value = "grade", withCustom = GradeMasterToName.class),
        @Field(value = "approvalUser", withCustom = UserToName.class),
        @Field(value = "completionUser", withCustom = UserToName.class),
        @Field(value = "completionStatus", withCustom = CompletionStatusToName.class),
        @Field(value = "approvalStatus", withCustom = CompletionStatusToName.class),
    },
    withIgnoreFields = {"accountNumber", "bankAccount", "transferIns", "user"}
)
public interface SelmaFinanceContractMapper {
    FinanceContractDTO asContractDTO(FinanceContract financeContract);
}
