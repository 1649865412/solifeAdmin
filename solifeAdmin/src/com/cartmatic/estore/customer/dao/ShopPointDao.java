package com.cartmatic.estore.customer.dao;

import java.util.List;

import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.model.PagingBean;
/**
 * Dao interface for ShopPoint.
 */
public interface ShopPointDao extends GenericDao<ShopPoint> {
	public ShopPoint getByCustomerId(Integer customerId);
	public List<ShopPoint> findShopPointListOderbyGainedTotalDesc(PagingBean pagingBean);
}