/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.formController.form.ClaimForm;
import com.swcommodities.wsmill.formController.form.UpdateClaimForm;
import com.swcommodities.wsmill.hibernate.dto.Claim;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructClaim;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author macOS
 */

public class ClaimAssembler {
    
    public Claim fromDto(ClaimForm form) {
        return MapStructClaim.INSTANCE.fromDto(form);
            
    }

    public Claim fromDto(UpdateClaimForm form, Claim claim) {
        MapStructClaim.INSTANCE.fromDto(form, claim);
        return claim;
    }
    
    
    public UpdateClaimForm toUpdateForm(Claim claim) {
        return MapStructClaim.INSTANCE.fromObject(claim);
    }
    
    public List<UpdateClaimForm> toUpdateFormList(Collection<Claim> claims) {
        if(claims == null) {
            return new ArrayList<>();
        }
        return claims.stream().map(claim -> MapStructClaim.INSTANCE.fromObject(claim)).collect(Collectors.toList());
    }
    
}
