package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductTypeSkuOption, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductTypeSkuOptionManager extends GenericManager<ProductTypeSkuOption> {
	/**
	 * 获取用激活SkuOption的产品类型
	 * @return
	 */
	public List<ProductType> findActiveSkuOptionsProductType();
}
