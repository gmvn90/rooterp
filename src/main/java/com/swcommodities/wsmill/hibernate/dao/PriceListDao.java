package com.swcommodities.wsmill.hibernate.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.view.PriceListView;
import com.swcommodities.wsmill.utils.Common;

public class PriceListDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public PriceListDao setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		return this;
	}

	private long totalAfterFilter;

	public long getTotalAfterFilter() {
		return totalAfterFilter;
	}

	public void setTotalAfterFilter(long totalAfterFilter) {
		this.totalAfterFilter = totalAfterFilter;
	}

	public PriceListView updatePriceList(PriceListView obj) {
		sessionFactory.getCurrentSession().update(obj);
		return obj;
	}

	public PriceListView newPriceList(PriceListView obj) {
		sessionFactory.getCurrentSession().save(obj);
		return obj;
	}

	public String getLog(int id) {
		try {
			//DetachedCriteria crit = DetachedCriteria.forClass(PriceListView.class);
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PriceListView.class);
			crit.add(Restrictions.eq("id", id));
			crit.setProjection(Projections.property("log"));
			return crit.uniqueResult().toString();
		} catch (Exception e) {
			return "";
		}
	}

	public Integer checkClient(int client_id) {
		//DetachedCriteria crit = DetachedCriteria.forClass(PriceListView.class);
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(PriceListView.class);
		crit.add(Restrictions.eq("client", client_id));
		crit.setProjection(Projections.property("id"));
		@SuppressWarnings("unchecked")
		ArrayList<Integer> result = new ArrayList<Integer>(crit.list());
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<HashMap> searchGlobe(String searchTerm, String order, int start, int amount,
			String colName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select pl.id as id, cm.`name` as client,")
				.append("pl.clean_grade as clean_grade,")
				.append("pl.color_sort as color_sort, pl.`import` as imp,")
				.append("pl.polishing as polishing, pl.re_bag as re_bag,")
				.append("pl.re_weighing as re_weighing, ")
				.append("pl.`storage` as st, pl.jute_bag as jute_bag,")
				.append("pl.big_bag as big_bag, pl.bulk as bulk,")
				.append("pl.pp as pp, pl.jumbo_bag as jumbo_bag, pl.dry as dry ").append("from price_list pl ")
				.append("left join company_master cm on pl.client_id = cm.id");
		if (!searchTerm.equals("")) {
			sql.append(" having ").append("(client like :searchTerm ")
					.append("or clean_grade like :searchTerm ")
					.append("or color_sort like :searchTerm ")
					.append("or `import` like :searchTerm ").append("or re_bag like :searchTerm ")
					.append("or polishing like :searchTerm ")
					.append("or `storage` like :searchTerm ")
					.append("or big_bag like :searchTerm ")
					.append("or re_weighing like :searchTerm ")
					.append("or jute_bag like :searchTerm ").append("or bulk like :searchTerm ")
					.append("or dry like :searchTerm ").append("or pp like :searchTerm ")
					.append("or jumbo_bag like :searchTerm) ");
		}
		if (colName.equals("0")) {
			colName = "pl.id";
		}
		if (colName != null && !colName.equals("-1")) {
			sql.append(" ORDER BY ").append(colName).append(" ")
					.append((order.equals("desc") ? "desc" : "asc"));
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
				.addScalar("id", IntegerType.INSTANCE).addScalar("client", StringType.INSTANCE)
				.addScalar("clean_grade", FloatType.INSTANCE).addScalar("color_sort", FloatType.INSTANCE)
				.addScalar("imp", FloatType.INSTANCE).addScalar("polishing", FloatType.INSTANCE)
				.addScalar("re_bag", FloatType.INSTANCE).addScalar("re_weighing", FloatType.INSTANCE)
				.addScalar("st", FloatType.INSTANCE).addScalar("jute_bag", FloatType.INSTANCE)
				.addScalar("big_bag", FloatType.INSTANCE).addScalar("bulk", FloatType.INSTANCE)
				.addScalar("pp", FloatType.INSTANCE).addScalar("jumbo_bag", FloatType.INSTANCE)
				.addScalar("dry", FloatType.INSTANCE);

		if (!searchTerm.equals("")) {
			query.setParameter("searchTerm", "%" + searchTerm + "%");
		}
		this.setTotalAfterFilter(query.list().size());

		if (amount != -1) {
			query.setMaxResults(amount);
		}

		ArrayList<Object[]> result = (ArrayList<Object[]>) query.list();
		ArrayList<HashMap> list = new ArrayList<>();
		for (Object[] obj : result) {
			HashMap map = new HashMap();
			map.put("id", Common.getStringValue(obj[0]));
			map.put("client", Common.getStringValue(obj[1]));
			map.put("cleanGrade", Common.getStringValue(obj[2]));
			map.put("colorSort", Common.getStringValue(obj[3]));
			map.put("import_", Common.getStringValue(obj[4]));
			map.put("polishing", Common.getStringValue(obj[5]));
			map.put("rebag", Common.getStringValue(obj[6]));
			map.put("reweighing", Common.getStringValue(obj[7]));
			map.put("storage", Common.getStringValue(obj[8]));
			map.put("jute", Common.getStringValue(obj[9]));
			map.put("bigbag", Common.getStringValue(obj[10]));
			map.put("bulk", Common.getStringValue(obj[11]));
			map.put("pp", Common.getStringValue(obj[12]));
			map.put("jumbo", Common.getStringValue(obj[13]));
			map.put("dry", Common.getStringValue(obj[14]));
			list.add(map);
		}
		return list;
	}

	public long countRow() {
		String sql = "select count(id) from price_list";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
	}
	
	public PriceListView getPriceListById(int id){
		return (PriceListView)sessionFactory.getCurrentSession().get(PriceListView.class, id);
	}
}
