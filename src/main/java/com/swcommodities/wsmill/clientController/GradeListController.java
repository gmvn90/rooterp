
package com.swcommodities.wsmill.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.repository.GradeRepository;

@RequestMapping("/millclient/grades")
@Controller
public class GradeListController extends BaseListController<GradeMaster, Integer> {
	
	@Autowired
    public GradeListController(GradeRepository repo) {
        super(repo);
    }
}