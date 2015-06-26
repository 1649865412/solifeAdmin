package com.cartmatic.estore.catalog.dao;

import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductAttGroupItem.
 */
public interface ProductAttGroupItemDao extends GenericDao<ProductAttGroupItem> {
	/**
	 * 根据产品类型及自定义属性获取ProductAttGroupItem
	 * @param productTypeId
	 * @param attributeId
	 * @return
	 */
	public ProductAttGroupItem getProductAttGroupItemByProductTypeAndAttribute(Integer productTypeId,Integer attributeId);
}