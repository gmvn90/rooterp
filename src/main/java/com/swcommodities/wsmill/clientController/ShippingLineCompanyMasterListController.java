
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.repository.ShippingLineCompanyMasterRepository;

@RequestMapping("/millclient/shippinglines")
@Controller
public class ShippingLineCompanyMasterListController extends BaseListController<ShippingLineCompanyMaster, Integer> {

	@Autowired
    public ShippingLineCompanyMasterListController(ShippingLineCompanyMasterRepository repo) {
        super(repo);
    }
}