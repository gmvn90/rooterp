package com.swcommodities.wsmill.hibernate.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.swcommodities.wsmill.hibernate.dto.view.GradeFormula;

public class GradeFormulaDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public GradeFormulaDao setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		return this;
	}

	public String getFormula(int id){
		String sql = "select formula from GradeFormula where gradeId =:gradeId";
		Query query = sessionFactory.getCurrentSession().createQuery(sql).setParameter("gradeId", id);
		return (String) query.uniqueResult();
	}
	
	public Integer findId(int id){
		String sql = "select id from GradeFormula where gradeId =:gradeId";
		Query query = sessionFactory.getCurrentSession().createQuery(sql).setParameter("gradeId", id);
		return (query.uniqueResult() != null) ? (Integer)query.uniqueResult() : -1;
	}
	
	public void save(GradeFormula formula){
		sessionFactory.getCurrentSession().save(formula);
	}
	
	public void update(GradeFormula formula){
		sessionFactory.getCurrentSession().update(formula);
	}
	
}
