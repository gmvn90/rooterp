
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.LocationMaster;
import com.swcommodities.wsmill.repository.LocationMasterRepository;

@RequestMapping("/millclient/locations")
@Controller
public class LocationListController extends BaseListController<LocationMaster, Integer> {
	
	@Autowired
    public LocationListController(LocationMasterRepository repo) {
        super(repo);
    }
}