package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.SkuOptionDao;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for SkuOption.
*/
public class SkuOptionDaoImpl extends HibernateGenericDaoImpl<SkuOption> implements SkuOptionDao {

	@SuppressWarnings("unchecked")
	public List<SkuOption> findSkuOptionsByProductType(Integer productTypeId) {
		List<SkuOption> skuOptions=findByHql("select ptso.skuOption from ProductTypeSkuOption ptso where ptso.productType.productTypeId=? order by ptso.sortOrder asc", productTypeId);
		return skuOptions;
	}
	
	@SuppressWarnings("unchecked")
	public List<SkuOption> findActiveSkuOptionsByProductType(Integer productTypeId){
		List<SkuOption> skuOptions=findByHql("select ptso.skuOption from ProductTypeSkuOption ptso where ptso.skuOption.status=1 and ptso.productType.productTypeId=? order by ptso.sortOrder asc", productTypeId);
		return skuOptions;
	}

}
