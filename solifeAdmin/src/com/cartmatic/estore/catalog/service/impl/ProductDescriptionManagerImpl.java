package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.ProductDescriptionDao;
import com.cartmatic.estore.catalog.service.ProductDescriptionManager;
import com.cartmatic.estore.common.model.catalog.ProductDescription;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductDescription, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductDescriptionManagerImpl extends GenericManagerImpl<ProductDescription> implements ProductDescriptionManager {

	private ProductDescriptionDao productDescriptionDao = null;

	/**
	 * @param productDescriptionDao
	 *            the productDescriptionDao to set
	 */
	public void setProductDescriptionDao(ProductDescriptionDao productDescriptionDao) {
		this.productDescriptionDao = productDescriptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productDescriptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductDescription entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductDescription entity) {

	}

}
