package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductRateItem, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductRateItemManager extends GenericManager<ProductRateItem> {
	/**
	 * 查找产品类型关联的评论项
	 * 按ProductRateItem的sortOrder排序
	 * @param productTypeId
	 * @return
	 */
	public List<ProductRateItem> findProductRateItemsByProductType(Integer productTypeId);
	
}
