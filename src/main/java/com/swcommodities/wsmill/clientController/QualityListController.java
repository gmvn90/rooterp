package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.QualityMaster;
import com.swcommodities.wsmill.repository.QualityRepository;

@RequestMapping("/millclient/qualities")
@Controller
public class QualityListController extends BaseListController<QualityMaster, Long> {
	@Autowired
    public QualityListController(QualityRepository repo) {
        super(repo);
    }
}