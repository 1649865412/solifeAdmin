package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.ProductRateValueDao;
import com.cartmatic.estore.catalog.service.ProductRateValueManager;
import com.cartmatic.estore.common.model.catalog.ProductRateValue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductRateValue, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductRateValueManagerImpl extends GenericManagerImpl<ProductRateValue> implements ProductRateValueManager {

	private ProductRateValueDao productRateValueDao = null;

	/**
	 * @param productRateValueDao
	 *            the productRateValueDao to set
	 */
	public void setProductRateValueDao(ProductRateValueDao productRateValueDao) {
		this.productRateValueDao = productRateValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productRateValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductRateValue entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductRateValue entity) {

	}

	@Override
	public void deleteAllByProductId(Integer productId) {
		productRateValueDao.deleteAllByProductId(productId);
	}
}