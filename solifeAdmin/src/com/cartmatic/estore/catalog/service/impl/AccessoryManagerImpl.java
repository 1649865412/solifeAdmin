package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.AccessoryDao;
import com.cartmatic.estore.catalog.service.AccessoryManager;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for Accessory, responsible for business processing, and communicate between web and persistence layer.
 */
public class AccessoryManagerImpl extends GenericManagerImpl<Accessory> implements AccessoryManager {

	private AccessoryDao accessoryDao = null;

	/**
	 * @param accessoryDao
	 *            the accessoryDao to set
	 */
	public void setAccessoryDao(AccessoryDao accessoryDao) {
		this.accessoryDao = accessoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = accessoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Accessory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Accessory entity) {

	}

}
