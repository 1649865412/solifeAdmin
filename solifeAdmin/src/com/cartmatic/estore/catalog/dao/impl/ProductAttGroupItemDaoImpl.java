package com.cartmatic.estore.catalog.dao.impl;

import com.cartmatic.estore.catalog.dao.ProductAttGroupItemDao;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductAttGroupItem.
*/
public class ProductAttGroupItemDaoImpl extends HibernateGenericDaoImpl<ProductAttGroupItem> implements ProductAttGroupItemDao {

	public ProductAttGroupItem getProductAttGroupItemByProductTypeAndAttribute(
			Integer productTypeId, Integer attributeId) {
		String hql="select pagi from ProductAttGroupItem pagi where pagi.productTypeId=? and pagi.attribute.attributeId=?";
		ProductAttGroupItem productAttGroupItem=(ProductAttGroupItem)findUnique(hql, new Object[]{productTypeId,attributeId});
		return productAttGroupItem;
	}

}
