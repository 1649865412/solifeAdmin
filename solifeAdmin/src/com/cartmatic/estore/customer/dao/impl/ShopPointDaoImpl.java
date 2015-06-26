package com.cartmatic.estore.customer.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.customer.dao.ShopPointDao;

/**
 * Dao implementation for ShopPoint.
*/
public class ShopPointDaoImpl extends HibernateGenericDaoImpl<ShopPoint> implements ShopPointDao {

	public ShopPoint getByCustomerId(Integer customerId) {
		return findUniqueByProperty("customer.appuserId", customerId);
	}

	@SuppressWarnings("unchecked")
	public List<ShopPoint> findShopPointListOderbyGainedTotalDesc(PagingBean pagingBean) {
		String query="from ShopPoint sp order by sp.gainedTotal desc";
		List<ShopPoint> shopPointList=find(query, pagingBean);
		return shopPointList;
	}

}
