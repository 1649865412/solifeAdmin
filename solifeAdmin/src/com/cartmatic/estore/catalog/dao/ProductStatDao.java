package com.cartmatic.estore.catalog.dao;

import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductStat.
 */
public interface ProductStatDao extends GenericDao<ProductStat> {
	public ProductStat getProductStat(Integer storeId,Integer productId);
}