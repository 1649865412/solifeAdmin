package com.cartmatic.estore.system.service.impl;

import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.RoleResDao;
import com.cartmatic.estore.system.service.RoleResManager;


/**
 * Manager implementation for RoleRes, responsible for business processing, and communicate between web and persistence layer.
 */
public class RoleResManagerImpl extends GenericManagerImpl<RoleRes> implements RoleResManager {

	private RoleResDao roleResDao = null;

	/**
	 * @param roleResDao
	 *            the roleResDao to set
	 */
	public void setRoleResDao(RoleResDao roleResDao) {
		this.roleResDao = roleResDao;
	}
	
	public void deleteByRoleId(Integer id)
	{
		roleResDao.deleteByRoleId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = roleResDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(RoleRes entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(RoleRes entity) {

	}

}
