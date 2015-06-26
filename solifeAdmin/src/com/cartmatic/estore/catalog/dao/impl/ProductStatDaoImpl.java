package com.cartmatic.estore.catalog.dao.impl;

import com.cartmatic.estore.catalog.dao.ProductStatDao;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductStat.
*/
public class ProductStatDaoImpl extends HibernateGenericDaoImpl<ProductStat> implements ProductStatDao {

	@Override
	public ProductStat getProductStat(Integer storeId, Integer productId) {
		return (ProductStat) findUnique("from ProductStat s where s.store.storeId=? and s.product.productId=?", storeId,productId);
	}

}
