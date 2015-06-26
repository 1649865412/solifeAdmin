package com.cartmatic.estore.content.service.impl;

import com.cartmatic.estore.common.model.content.ProductAdvertisement;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.content.service.ProductAdvertisementManager;
import com.cartmatic.estore.content.dao.ProductAdvertisementDao;


/**
 * Manager implementation for ProductAdvertisement, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductAdvertisementManagerImpl extends GenericManagerImpl<ProductAdvertisement> implements ProductAdvertisementManager {

	private ProductAdvertisementDao productAdvertisementDao = null;

	/**
	 * @param productAdvertisementDao
	 *            the productAdvertisementDao to set
	 */
	public void setProductAdvertisementDao(ProductAdvertisementDao productAdvertisementDao) {
		this.productAdvertisementDao = productAdvertisementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productAdvertisementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ProductAdvertisement entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ProductAdvertisement entity) {

	}

}
