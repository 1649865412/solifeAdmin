package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductPackageItemDao;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductPackageItem.
*/
public class ProductPackageItemDaoImpl extends HibernateGenericDaoImpl<ProductPackageItem> implements ProductPackageItemDao {

	@SuppressWarnings("unchecked")
	public List<Product> getProductPackageByItemProduct(Integer itemProductId) {
		List<Product> productPackageList=findByHql("select distinct ppi.productSku.product from ProductPackageItem ppi where ppi.itemSku.product.productId=?", itemProductId);
		return productPackageList;
	}

}
