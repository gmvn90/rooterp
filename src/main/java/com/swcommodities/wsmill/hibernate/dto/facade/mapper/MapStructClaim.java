/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.formController.form.ClaimForm;
import com.swcommodities.wsmill.formController.form.UpdateClaimForm;
import com.swcommodities.wsmill.hibernate.dto.Claim;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ShippingInstructionToint;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.UserToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.WarehouseMasterToint;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author macOS
 */

@Mapper(uses = {
    ShippingInstructionToint.class,
    WarehouseMasterToint.class,
    UserToName.class
})
public interface MapStructClaim {
    
    MapStructClaim INSTANCE = Mappers.getMapper(MapStructClaim.class);
    
    @Mappings({
        @Mapping(source = "siId", target = "shippingInstructionBySiId"),
        @Mapping(target = "created", ignore = true),
        @Mapping(target = "updated", ignore = true),
        @Mapping(target = "createdUser", ignore = true),
        @Mapping(target = "id", ignore = true),
    })
    Claim fromDto(ClaimForm form);
    
    @Mappings({
        @Mapping(target = "created", ignore = true),
        @Mapping(target = "updated", ignore = true),
        @Mapping(target = "createdUser", ignore = true),
        @Mapping(target = "updatedArrivalWeightNoteUser", ignore = true),
        @Mapping(target = "updateArrivalWeightNoteDate", ignore = true),
        @Mapping(target = "claimStatus", ignore = true),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "swornWeigher", source = "swornWeigherId"),
        @Mapping(target = "warehouse", source = "warehouseId"),
        @Mapping(target = "documents", ignore = true)
    })
    void fromDto(UpdateClaimForm form, @MappingTarget Claim claim);
    
    @Mappings({
        @Mapping(source = "shippingInstructionBySiId", target = "siId"),
        @Mapping(target = "createdUser", source = "createdUser.userName"),
        @Mapping(target = "swornWeigherId", source = "swornWeigher"),
        @Mapping(target = "warehouseId", source = "warehouse"),
        @Mapping(target = "siRef", source = "shippingInstructionBySiId.refNumber")
    })
    UpdateClaimForm fromObject(Claim claim);
    
    default Set<FileSentDTO> map(Set<FileSent> fss) {
        return fss.stream().map(fs -> MapStructFileSent.INSTANCE.fromObject(fs)).collect(Collectors.toSet());
    }
    
}
