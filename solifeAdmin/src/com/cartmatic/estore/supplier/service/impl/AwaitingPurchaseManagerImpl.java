package com.cartmatic.estore.supplier.service.impl;

import com.cartmatic.estore.common.model.supplier.AwaitingPurchase;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.dao.AwaitingPurchaseDao;
import com.cartmatic.estore.supplier.service.AwaitingPurchaseManager;


/**
 * Manager implementation for AwaitingPurchase, responsible for business processing, and communicate between web and persistence layer.
 */
public class AwaitingPurchaseManagerImpl extends GenericManagerImpl<AwaitingPurchase> implements AwaitingPurchaseManager {

	private AwaitingPurchaseDao awaitingPurchaseDao = null;

	/**
	 * @param awaitingPurchaseDao
	 *            the awaitingPurchaseDao to set
	 */
	public void setAwaitingPurchaseDao(AwaitingPurchaseDao awaitingPurchaseDao) {
		this.awaitingPurchaseDao = awaitingPurchaseDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = awaitingPurchaseDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AwaitingPurchase entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AwaitingPurchase entity) {

	}

}
