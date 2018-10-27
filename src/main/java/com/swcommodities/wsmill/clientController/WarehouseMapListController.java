
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;
import com.swcommodities.wsmill.repository.WarehouseMapRepository;

@RequestMapping("/millclient/warehouseMaps")
@Controller
public class WarehouseMapListController extends BaseListController<WarehouseMap, Integer> {

	@Autowired
    public WarehouseMapListController(WarehouseMapRepository repo) {
        super(repo);
    }
}