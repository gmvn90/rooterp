package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.repository.OriginRepository;

@RequestMapping("/millclient/origins")
@Controller
public class OriginListController extends BaseListController<OriginMaster, Integer> {
	@Autowired
    public OriginListController(OriginRepository repo) {
        super(repo);
    }
}