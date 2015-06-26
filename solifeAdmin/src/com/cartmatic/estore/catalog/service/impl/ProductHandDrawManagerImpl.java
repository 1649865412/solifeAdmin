package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.ProductHandDrawDao;
import com.cartmatic.estore.catalog.service.ProductHandDrawManager;
import com.cartmatic.estore.common.model.catalog.ProductHandDraw;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductDescription, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductHandDrawManagerImpl extends GenericManagerImpl<ProductHandDraw> implements ProductHandDrawManager {

	private ProductHandDrawDao productHandDrawDao = null;


	public void setProductHandDrawDao(ProductHandDrawDao productHandDrawDao) {
		this.productHandDrawDao = productHandDrawDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productHandDrawDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductHandDraw entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductHandDraw entity) {

	}

}
