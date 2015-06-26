package com.cartmatic.estore.cart.service.impl;

import com.cartmatic.estore.cart.dao.ShoppingcartItemGcDao;
import com.cartmatic.estore.cart.service.ShoppingcartItemGcManager;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for Attribute, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShoppingcartItemGcManagerImpl extends GenericManagerImpl<ShoppingcartItemGc> implements ShoppingcartItemGcManager {

	private ShoppingcartItemGcDao shoppingcartItemGcDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shoppingcartItemGcDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ShoppingcartItemGc entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ShoppingcartItemGc entity) {

	}

	public ShoppingcartItemGcDao getShoppingcartItemGcDao() {
		return shoppingcartItemGcDao;
	}

	public void setShoppingcartItemGcDao(ShoppingcartItemGcDao shoppingcartItemGcDao) {
		this.shoppingcartItemGcDao = shoppingcartItemGcDao;
	}

}