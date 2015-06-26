package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductPackageItem.
 */
public interface ProductPackageItemDao extends GenericDao<ProductPackageItem> {
	public List<Product> getProductPackageByItemProduct(Integer itemProductId);
}