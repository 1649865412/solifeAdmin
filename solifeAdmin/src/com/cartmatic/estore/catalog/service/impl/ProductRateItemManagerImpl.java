package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductRateItemDao;
import com.cartmatic.estore.catalog.service.ProductRateItemManager;
import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductRateItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductRateItemManagerImpl extends GenericManagerImpl<ProductRateItem> implements ProductRateItemManager {

	private ProductRateItemDao productRateItemDao = null;

	/**
	 * @param productRateItemDao
	 *            the productRateItemDao to set
	 */
	public void setProductRateItemDao(ProductRateItemDao productRateItemDao) {
		this.productRateItemDao = productRateItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productRateItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductRateItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductRateItem entity) {

	}

	public List<ProductRateItem> findProductRateItemsByProductType(Integer productTypeId) {
		List<ProductRateItem> productRateItems=findByPropertyOrdered("productType.productTypeId",productTypeId, "sortOrder", true);
		return productRateItems;
	}

}
