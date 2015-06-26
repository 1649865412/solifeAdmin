package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.ProductSkuOptionValueDao;
import com.cartmatic.estore.catalog.service.ProductSkuOptionValueManager;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductSkuOptionValue, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductSkuOptionValueManagerImpl extends GenericManagerImpl<ProductSkuOptionValue> implements ProductSkuOptionValueManager {

	private ProductSkuOptionValueDao productSkuOptionValueDao = null;

	/**
	 * @param productSkuOptionValueDao
	 *            the productSkuOptionValueDao to set
	 */
	public void setProductSkuOptionValueDao(ProductSkuOptionValueDao productSkuOptionValueDao) {
		this.productSkuOptionValueDao = productSkuOptionValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productSkuOptionValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductSkuOptionValue entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductSkuOptionValue entity) {

	}

}
