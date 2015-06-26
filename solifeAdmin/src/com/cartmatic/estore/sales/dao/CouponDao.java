package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for Coupon.
 */
public interface CouponDao extends GenericDao<Coupon> {
	public boolean existCoupon(String couponNo) ;

	public Coupon getCouponByNo(String couponNo) ;
	
	public List searchCoupons(SearchCriteria _searchCriteria, String _promoRule);
	
	public Coupon getIdleCoupon(Integer couponTypeId);
}