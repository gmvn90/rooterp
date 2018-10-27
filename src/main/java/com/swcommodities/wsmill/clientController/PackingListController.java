
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.repository.PackingRepository;

@RequestMapping("/millclient/packings")
@Controller
public class PackingListController extends BaseListController<PackingMaster, Integer> {
	
	@Autowired
    public PackingListController(PackingRepository repo) {
        super(repo);
    }
}