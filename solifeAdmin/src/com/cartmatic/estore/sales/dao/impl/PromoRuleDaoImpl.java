
package com.cartmatic.estore.sales.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.dao.PromoRuleDao;
import com.cartmatic.estore.sales.model.PromoRuleSearchCriteria;

/**
 * Dao implementation for PromoRule.
 */
public class PromoRuleDaoImpl extends HibernateGenericDaoImpl<PromoRule>
		implements PromoRuleDao {

	public List<PromoRule> getAllCartPromotionRulesInProcess() {
		String hql = "from PromoRule r where r.status ="+Constants.STATUS_ACTIVE+
		" and r.startTime < now()  and (now() < r.endTime or r.endTime is null) and r.promoType = '"+ 
		PromoRule.PROMOTION_TYPE_SHOPPINGCARTPROMOTION+"' order by r.priority asc,r.promoRuleId asc";
		List<PromoRule> list =  this.findByHql(hql, null);
		return list;
		/*List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where r.status = ").append(
						Constants.STATUS_ACTIVE).append(" and ");
				sb.append("r.startTime < :now  and ");
				sb.append("(:now < r.endTime or r.endTime is null) and ");
				sb.append("r.promoType = '").append(
						PromoRule.PROMOTION_TYPE_SHOPPINGCARTPROMOTION).append(
						"'");
				sb.append(" order by r.priority asc,r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List<PromoRule> rows = (List<PromoRule>) query.list();
				return rows;
			}

		});
		return list;*/

	}

	public List<PromoRule> getAllCatalogPromotionRulesInProcess() {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where r.status = ").append(
						Constants.STATUS_ACTIVE).append(" and ");
				sb.append("r.startTime < :now  and ");
				sb.append("(:now < r.endTime or r.endTime is null) and ");
				sb.append("r.promoType = '").append(
						PromoRule.PROMOTION_TYPE_CATALOGPROMOTION).append("'");
				sb.append(" order by r.priority asc,r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List rows = query.list();
				return rows;
			}
		});
		return list;
	}

	public List<PromoRule> getAllCouponPromotionRulesInProcess() {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where r.status = ").append(
						Constants.STATUS_ACTIVE).append(" and ");
				sb.append("r.startTime < :now  and ");
				sb.append("(:now < r.endTime or r.endTime is null) and ");
				sb.append("r.promoType = '").append(
						PromoRule.PROMOTION_TYPE_COUPONPROMOTION).append("'");
				sb.append(" order by r.priority asc,r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List rows = query.list();
				return rows;
			}
		});
		return list;
	}

	public List<PromoRule> getAllCouponTypesInProcessOrInFuture() {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where ");
				sb.append("r.status = ").append(Constants.STATUS_ACTIVE)
						.append(" and ");
				sb.append("(:now < r.endTime or r.endTime is null) and ");
				sb.append("r.promoType = '").append(
						PromoRule.PROMOTION_TYPE_COUPONPROMOTION).append("'");
				sb.append(" order by r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List rows = query.list();
				
				return rows;
			}
		});

		return list;
	}

	public PromoRule getCouponPromotionRule(Coupon _coupon) {
		return getById(_coupon.getPromoRuleId());
	}

	public List<PromoRule> searchPromotionRules(SearchCriteria _searchCriteria,
			PromoRuleSearchCriteria _promoSearchCriteria) {
		SearchCriteria searchCriteria = _searchCriteria;
		PromoRuleSearchCriteria promoSearchCriteria = _promoSearchCriteria;
		StringBuffer sb = new StringBuffer();
		sb.append("from PromoRule  where 1=1");

		if (null != promoSearchCriteria.getName()) {
			sb.append(" and name like '%" + StringEscapeUtils.escapeSql(promoSearchCriteria.getName())
					+ "%'");
		}
		if (null != promoSearchCriteria.getPromoType()) {
			sb.append(" and promoType like '%"
					+ promoSearchCriteria.getPromoType() + "%'");
		}
		if (null != promoSearchCriteria.getStoreId()) {
			sb.append(" and store.storeId = " + promoSearchCriteria.getStoreId() + "");
		}
		if (null != promoSearchCriteria.getState()) {
			int state = Integer.parseInt(promoSearchCriteria.getState());
			String format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String now = dateFormat.format(new Date());

			switch (state) {

				case PromoRule.STATE_DOING:
					sb.append("and  startTime < '" + now + "'");
					sb.append(" and ('" + now
							+ "' <  endTime  or endTime is null)");
					sb.append("and status =1 ");
					break;
				case PromoRule.STATE_FUTURE:
					sb.append("and  startTime > '" + now + "'");
					sb.append(" and ( endTime > '" + now
							+ "'  or endTime is null)");
					sb.append("and status =1 ");
					break;
				case PromoRule.STATE_PAST:
					sb.append("and  endTime < '" + now + "'");
					sb.append("and status =1 ");
					break;
				case PromoRule.STATE_INVALID:
					sb.append("and status !=1 ");
					break;
				default:
					break;

			}

		}
		sb.append(" order by priority asc,promoRuleId asc");
		searchCriteria.setHql(sb.toString());
		List<PromoRule> list = searchByCriteria(searchCriteria);
		return list;

	}

	public List<PromoRule> getAllPromotionRulesInProcessForFront() {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where r.status = ").append(
						Constants.STATUS_ACTIVE).append(" and ");
				sb.append("r.startTime < :now  and ");
				sb.append("(:now < r.endTime or r.endTime is null) ");
				sb.append(" order by r.priority asc,r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List rows = query.list();
				return rows;
			}
		});

		return list;
	}

	public List<PromoRule> getAllPromotionRulesInFutureForFront() {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from PromoRule r where r.status = ").append(
						Constants.STATUS_ACTIVE).append(" and ");
				sb.append("r.startTime > :now ");
				sb.append(" order by r.priority asc,r.promoRuleId asc");
				Query query = session.createQuery(sb.toString());
				query.setTimestamp("now", now);
				List rows = query.list();
				return rows;
			}
		});

		return list;
	}

	@Override
	public PromoRule getSpecialPromotionRule(Short triggerType) {
		// TODO Auto-generated method stub
		PromoRule promo = null;
		promo = this.findUniqueByProperty("triggerType", triggerType);
		return promo;
	}

}
