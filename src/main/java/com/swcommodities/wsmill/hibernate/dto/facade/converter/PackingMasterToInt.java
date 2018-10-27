package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.PackingMaster;

public class PackingMasterToInt {
	public int fromObject(PackingMaster packingMaster) {
		return packingMaster.getId();
	}
    
    public Integer fromObjectInteger(PackingMaster packingMaster) {
        if(packingMaster == null) {
            return null;
        }
		return packingMaster.getId();
	}

	public PackingMaster fromId(int id) {
		return new PackingMaster(id);
	}
    
    public PackingMaster fromIdInteger(Integer id) {
		return new PackingMaster(id);
	}
    
}
