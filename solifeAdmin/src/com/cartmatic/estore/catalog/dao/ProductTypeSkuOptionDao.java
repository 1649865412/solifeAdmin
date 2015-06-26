package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductTypeSkuOption.
 */
public interface ProductTypeSkuOptionDao extends GenericDao<ProductTypeSkuOption> {
	/**
	 * 获取用激活SkuOption的产品类型
	 * @return
	 */
	public List<ProductType> findActiveSkuOptionsProductType();
}