
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.repository.PortRepository;

@RequestMapping("/millclient/ports")
@Controller
public class PortMasterListController extends BaseListController<PortMaster, Integer> {

	@Autowired
    public PortMasterListController(PortRepository repo) {
        super(repo);
    }
}