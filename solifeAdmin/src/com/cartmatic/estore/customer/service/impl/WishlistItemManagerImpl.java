package com.cartmatic.estore.customer.service.impl;

import com.cartmatic.estore.common.model.customer.WishlistItem;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.WishlistItemDao;
import com.cartmatic.estore.customer.service.WishlistItemManager;


/**
 * Manager implementation for WishlistItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class WishlistItemManagerImpl extends GenericManagerImpl<WishlistItem> implements WishlistItemManager {

	private WishlistItemDao wishlistItemDao = null;

	/**
	 * @param wishlistItemDao
	 *            the wishlistItemDao to set
	 */
	public void setWishlistItemDao(WishlistItemDao wishlistItemDao) {
		this.wishlistItemDao = wishlistItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = wishlistItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(WishlistItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(WishlistItem entity) {

	}

}
