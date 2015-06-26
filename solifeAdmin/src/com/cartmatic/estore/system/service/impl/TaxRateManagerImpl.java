package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.TaxRateManager;
import com.cartmatic.estore.system.dao.TaxRateDao;


/**
 * Manager implementation for TaxRate, responsible for business processing, and communicate between web and persistence layer.
 */
public class TaxRateManagerImpl extends GenericManagerImpl<TaxRate> implements TaxRateManager {

	private TaxRateDao taxRateDao = null;

	/**
	 * @param taxRateDao
	 *            the taxRateDao to set
	 */
	public void setTaxRateDao(TaxRateDao taxRateDao) {
		this.taxRateDao = taxRateDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = taxRateDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TaxRate entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TaxRate entity) {

	}

	@Override
	public List<TaxRate> findByRegionIds(List<Integer> regionIdList,Integer productTypeId) {
		return taxRateDao.findByRegionIds(regionIdList, productTypeId);
	}

	@Override
	public List<TaxRate> findByTaxProductTypeRegion(Integer taxId,Integer productTypeId, Integer regionId) {
		return taxRateDao.findByTaxProductTypeRegion(taxId, productTypeId, regionId);
	}

}
