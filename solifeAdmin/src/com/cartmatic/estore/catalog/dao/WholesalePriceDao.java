package com.cartmatic.estore.catalog.dao;

import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for WholesalePrice.
 */
public interface WholesalePriceDao extends GenericDao<WholesalePrice> {
	/*hqm*/
	public WholesalePrice getSalePriceBySkuIdByMinQuantity(Integer productSkuId,Integer minQuantity);
}