package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductAttGroupItem, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductAttGroupItemManager extends GenericManager<ProductAttGroupItem> {
	/**
	 * 获取某产品类型关联的所有的自定义属性（ProductAttGroupItem）
	 * @param productTypeId
	 * @return
	 */
	public List<ProductAttGroupItem> findProductAttGroupItemsByProductTypeId(Integer productTypeId);
	
	
	/**
	 * 根据产品类型及自定义属性获取ProductAttGroupItem
	 * @param productTypeId
	 * @param attributeId
	 * @return
	 */
	public ProductAttGroupItem getProductAttGroupItemByProductTypeAndAttribute(Integer productTypeId,Integer attributeId);
}
