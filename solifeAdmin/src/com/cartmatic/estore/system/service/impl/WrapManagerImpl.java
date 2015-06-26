package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.WrapManager;
import com.cartmatic.estore.system.dao.WrapDao;


/**
 * Manager implementation for Wrap, responsible for business processing, and communicate between web and persistence layer.
 */
public class WrapManagerImpl extends GenericManagerImpl<Wrap> implements WrapManager {

	private WrapDao wrapDao = null;

	/**
	 * @param wrapDao
	 *            the wrapDao to set
	 */
	public void setWrapDao(WrapDao wrapDao) {
		this.wrapDao = wrapDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = wrapDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Wrap entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Wrap entity) {

	}

	@Override
	public List<Wrap> getWrapsAllDesc() {
		return wrapDao.getAllOrdered("wrapId", false);
	}

}
