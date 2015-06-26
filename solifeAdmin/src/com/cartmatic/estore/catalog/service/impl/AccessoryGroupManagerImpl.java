package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.AccessoryGroupDao;
import com.cartmatic.estore.catalog.service.AccessoryGroupManager;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for AccessoryGroup, responsible for business processing, and communicate between web and persistence layer.
 */
public class AccessoryGroupManagerImpl extends GenericManagerImpl<AccessoryGroup> implements AccessoryGroupManager {

	private AccessoryGroupDao accessoryGroupDao = null;

	/**
	 * @param accessoryGroupDao
	 *            the accessoryGroupDao to set
	 */
	public void setAccessoryGroupDao(AccessoryGroupDao accessoryGroupDao) {
		this.accessoryGroupDao = accessoryGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = accessoryGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AccessoryGroup entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AccessoryGroup entity) {

	}

	public List<AccessoryGroup> findAllAccessoryGroup() {
		return getAllOrdered("groupName",true);
	}

	public AccessoryGroup getAccessoryGroupByCode(String code) {
		AccessoryGroup accessoryGroup=dao.findUniqueByProperty("groupCode", code);
		return accessoryGroup;
	}
}
