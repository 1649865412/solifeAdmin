package com.cartmatic.estore.order.service.impl;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.dao.SalesOrderGeoipDao;
import com.cartmatic.estore.order.service.SalesOrderGeoipManager;


/**
 * Manager implementation for SalesOrderGeoip, responsible for business processing, and communicate between web and persistence layer.
 */
public class SalesOrderGeoipManagerImpl extends GenericManagerImpl<SalesOrderGeoip> implements SalesOrderGeoipManager {

	private SalesOrderGeoipDao salesOrderGeoipDao = null;

	/**
	 * @param salesOrderGeoipDao
	 *            the salesOrderGeoipDao to set
	 */
	public void setSalesOrderGeoipDao(SalesOrderGeoipDao salesOrderGeoipDao) {
		this.salesOrderGeoipDao = salesOrderGeoipDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = salesOrderGeoipDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SalesOrderGeoip entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SalesOrderGeoip entity) {

	}

	public SalesOrderGeoip getSalesOrderGeoipByOrderNoAndActionType(String orderNo, Short actionType) {
		return salesOrderGeoipDao.getSalesOrderGeoipByOrderNoAndActionType(orderNo, actionType);
	}
}
