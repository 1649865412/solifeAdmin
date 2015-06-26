package com.cartmatic.estore.catalog.dao;

import com.cartmatic.estore.common.model.catalog.ProductRateValue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductRateValue.
 */
public interface ProductRateValueDao extends GenericDao<ProductRateValue> {
	public void deleteAllByProductId(Integer productId);
}