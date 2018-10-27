
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.repository.PITypeRepository;

@RequestMapping("/millclient/pitypes")
@Controller
public class PITypesListController extends BaseListController<PIType, Integer> {

	@Autowired
    public PITypesListController(PITypeRepository repo) {
        super(repo);
    }
}