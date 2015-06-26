package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemVersion;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.SystemVersionManager;
import com.cartmatic.estore.system.dao.SystemVersionDao;


/**
 * Manager implementation for SystemVersion, responsible for business processing, and communicate between web and persistence layer.
 */
public class SystemVersionManagerImpl extends GenericManagerImpl<SystemVersion> implements SystemVersionManager {

	private SystemVersionDao systemVersionDao = null;

	/**
	 * @param systemVersionDao
	 *            the systemVersionDao to set
	 */
	public void setSystemVersionDao(SystemVersionDao systemVersionDao) {
		this.systemVersionDao = systemVersionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = systemVersionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SystemVersion entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SystemVersion entity) {

	}

	@Override
	public SystemVersion getSystemVersion() {
		List<SystemVersion> systemVersionList=systemVersionDao.getAllOrdered("systemVersionId", false);
		if(systemVersionList!=null&&systemVersionList.size()>0){
			return systemVersionList.get(0);
		}
		return null;
	}
}
