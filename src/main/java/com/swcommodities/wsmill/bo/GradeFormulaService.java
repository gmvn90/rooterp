package com.swcommodities.wsmill.bo;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.GradeFormulaDao;
import com.swcommodities.wsmill.hibernate.dto.view.GradeFormula;

@Transactional(propagation = Propagation.REQUIRED)
public class GradeFormulaService {
	private GradeFormulaDao gradeFormulaDao;

	public void setGradeFormulaDao(GradeFormulaDao gradeFormulaDao) {
		this.gradeFormulaDao = gradeFormulaDao;
	}

	public String getFormula(int id) {
		String formula = gradeFormulaDao.getFormula(id);
		return (formula != null) ? formula : "";
	}

	public Integer findId(int id) {
		return gradeFormulaDao.findId(id);
	}

	public void save(GradeFormula formula) {
		gradeFormulaDao.save(formula);
	}

	public void update(GradeFormula formula) {
		gradeFormulaDao.update(formula);
	}
}
