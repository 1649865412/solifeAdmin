package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductTypeSkuOptionDao;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductTypeSkuOption.
*/
public class ProductTypeSkuOptionDaoImpl extends HibernateGenericDaoImpl<ProductTypeSkuOption> implements ProductTypeSkuOptionDao {

	@SuppressWarnings("unchecked")
	public List<ProductType> findActiveSkuOptionsProductType() {
		String hql="SELECT DISTINCT ptso.productType FROM ProductTypeSkuOption ptso where ptso.skuOption.status=?";
		List<ProductType>productTypes=findByHql(hql,Constants.STATUS_ACTIVE);
		return productTypes;
	}

}
