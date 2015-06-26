package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.Carrier;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.CarrierManager;
import com.cartmatic.estore.system.dao.CarrierDao;


/**
 * Manager implementation for Carrier, responsible for business processing, and communicate between web and persistence layer.
 */
public class CarrierManagerImpl extends GenericManagerImpl<Carrier> implements CarrierManager {

	private CarrierDao carrierDao = null;

	/**
	 * @param carrierDao
	 *            the carrierDao to set
	 */
	public void setCarrierDao(CarrierDao carrierDao) {
		this.carrierDao = carrierDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = carrierDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Carrier entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Carrier entity) {

	}

	@Override
	public List<Carrier> findActiveCarriers() {
		return carrierDao.findActiveCarriers();
	}

}
