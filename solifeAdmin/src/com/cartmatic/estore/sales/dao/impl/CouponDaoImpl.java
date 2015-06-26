
package com.cartmatic.estore.sales.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.dao.CouponDao;

/**
 * Dao implementation for Coupon.
 */
public class CouponDaoImpl extends HibernateGenericDaoImpl<Coupon> implements
		CouponDao {
	public boolean existCoupon(String couponNo) {
		return getCouponByNo(couponNo) != null;
	}

	public Coupon getCouponByNo(String couponNo) {
		List list = getHibernateTemplate().find(
				"from Coupon coupon where coupon.couponNo='" + StringEscapeUtils.escapeSql(couponNo) + "'");
		if (list.size() == 0)
			return null;
		return (Coupon) list.get(0);
	}
	public List searchCoupons(SearchCriteria _searchCriteria, String _promoRule){
		SearchCriteria searchCriteria = _searchCriteria;
		searchCriteria.setHql("from Coupon coupon where coupon.promoRule.promoRuleId = "+_promoRule+" order by couponId desc");
		List<Product> list=searchByCriteria(searchCriteria);
		return list;
		
	}
	public Coupon getIdleCoupon(Integer couponTypeId){
		List list = getHibernateTemplate().find(
				"from Coupon coupon where coupon.promoRule.promoRuleId='" + couponTypeId + "' and isSent = 0 and remainedTimes > 0");
		if (list.size() == 0)
			return null;
		return (Coupon) list.get(0);
	}
}
