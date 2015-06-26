package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.Currency;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.CurrencyDao;
import com.cartmatic.estore.system.service.CurrencyManager;


/**
 * Manager implementation for Currency, responsible for business processing, and communicate between web and persistence layer.
 */
public class CurrencyManagerImpl extends GenericManagerImpl<Currency> implements CurrencyManager {

	private CurrencyDao currencyDao = null;

	public List<Currency> loadEnableCurrency()
	{
		return currencyDao.loadEnableCurrency();
	}
	/**
	 * @param currencyDao
	 *            the currencyDao to set
	 */
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = currencyDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Currency entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Currency entity) {

	}

}
