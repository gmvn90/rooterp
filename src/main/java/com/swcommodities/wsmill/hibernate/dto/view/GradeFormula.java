package com.swcommodities.wsmill.hibernate.dto.view;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="grade_formulas")
public class GradeFormula  implements Serializable{
	private Integer id;
	private Integer gradeId;
	private String formula;
	
	
	public GradeFormula() {
		super();
	}

	public GradeFormula(Integer id, Integer gradeId, String formula) {
		super();
		this.id = id;
		this.gradeId = gradeId;
		this.formula = formula;
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="grade_id")
	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name="formula")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	
}
