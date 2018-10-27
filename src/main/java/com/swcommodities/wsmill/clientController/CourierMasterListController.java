
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.repository.CourierMasterRepository;

@RequestMapping("/millclient/couriers")
@Controller
public class CourierMasterListController extends BaseListController<CourierMaster, Integer> {

	@Autowired
    public CourierMasterListController(CourierMasterRepository repo) {
        super(repo);
    }
}