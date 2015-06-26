package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.ShippingMethodManager;
import com.cartmatic.estore.system.dao.ShippingMethodDao;


/**
 * Manager implementation for ShippingMethod, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShippingMethodManagerImpl extends GenericManagerImpl<ShippingMethod> implements ShippingMethodManager {

	private ShippingMethodDao shippingMethodDao = null;

	/**
	 * @param shippingMethodDao
	 *            the shippingMethodDao to set
	 */
	public void setShippingMethodDao(ShippingMethodDao shippingMethodDao) {
		this.shippingMethodDao = shippingMethodDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shippingMethodDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ShippingMethod entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ShippingMethod entity) {

	}

	@Override
	public List<ShippingMethod> getShippingMethodsAllOrder() {
		return shippingMethodDao.getShippingMethodsAllOrder();
	}

	@Override
	public List<ShippingMethod> findNormalShippingMethods() {
		return shippingMethodDao.findNormalShippingMethods();
	}

}
