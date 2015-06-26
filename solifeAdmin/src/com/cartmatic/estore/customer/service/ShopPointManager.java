package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ShopPoint, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ShopPointManager extends GenericManager<ShopPoint> {

	public void saveChangeTotal(Customer customer,Integer point);
	
	public ShopPoint getByCustomerId(Integer customerId);
	
	public List<ShopPoint> findShopPointListOderbyGainedTotalDesc(PagingBean pagingBean);
}
