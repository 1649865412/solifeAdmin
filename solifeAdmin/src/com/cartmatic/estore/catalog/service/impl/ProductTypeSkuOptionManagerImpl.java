package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductTypeSkuOptionDao;
import com.cartmatic.estore.catalog.service.ProductTypeSkuOptionManager;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductTypeSkuOption, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductTypeSkuOptionManagerImpl extends GenericManagerImpl<ProductTypeSkuOption> implements ProductTypeSkuOptionManager {

	private ProductTypeSkuOptionDao productTypeSkuOptionDao = null;

	/**
	 * @param productTypeSkuOptionDao
	 *            the productTypeSkuOptionDao to set
	 */
	public void setProductTypeSkuOptionDao(ProductTypeSkuOptionDao productTypeSkuOptionDao) {
		this.productTypeSkuOptionDao = productTypeSkuOptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productTypeSkuOptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductTypeSkuOption entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductTypeSkuOption entity) {

	}

	public List<ProductType> findActiveSkuOptionsProductType() {
		return productTypeSkuOptionDao.findActiveSkuOptionsProductType();
	}

}
