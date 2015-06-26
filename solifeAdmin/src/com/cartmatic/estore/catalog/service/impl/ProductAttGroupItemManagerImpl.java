package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductAttGroupItemDao;
import com.cartmatic.estore.catalog.service.ProductAttGroupItemManager;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductAttGroupItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductAttGroupItemManagerImpl extends GenericManagerImpl<ProductAttGroupItem> implements ProductAttGroupItemManager {

	private ProductAttGroupItemDao productAttGroupItemDao = null;

	/**
	 * @param productAttGroupItemDao
	 *            the productAttGroupItemDao to set
	 */
	public void setProductAttGroupItemDao(ProductAttGroupItemDao productAttGroupItemDao) {
		this.productAttGroupItemDao = productAttGroupItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productAttGroupItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductAttGroupItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductAttGroupItem entity) {

	}
	public List<ProductAttGroupItem> findProductAttGroupItemsByProductTypeId(Integer productTypeId) {
		List<ProductAttGroupItem>productAttGroupItems=findByProperty("productTypeId",productTypeId);
		return productAttGroupItems;
	}

	public ProductAttGroupItem getProductAttGroupItemByProductTypeAndAttribute(Integer productTypeId, Integer attributeId) {
		return productAttGroupItemDao.getProductAttGroupItemByProductTypeAndAttribute(productTypeId, attributeId);
	}
}
