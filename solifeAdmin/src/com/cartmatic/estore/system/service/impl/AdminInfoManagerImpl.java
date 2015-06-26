package com.cartmatic.estore.system.service.impl;

import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.AdminInfoManager;
import com.cartmatic.estore.system.dao.AdminInfoDao;


/**
 * Manager implementation for AdminInfo, responsible for business processing, and communicate between web and persistence layer.
 */
public class AdminInfoManagerImpl extends GenericManagerImpl<AdminInfo> implements AdminInfoManager {

	private AdminInfoDao adminInfoDao = null;

	/**
	 * @param adminInfoDao
	 *            the adminInfoDao to set
	 */
	public void setAdminInfoDao(AdminInfoDao adminInfoDao) {
		this.adminInfoDao = adminInfoDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = adminInfoDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AdminInfo entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AdminInfo entity) {

	}

}
