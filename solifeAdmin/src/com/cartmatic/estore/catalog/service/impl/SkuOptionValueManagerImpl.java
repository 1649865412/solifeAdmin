package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.SkuOptionValueDao;
import com.cartmatic.estore.catalog.service.SkuOptionValueManager;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for SkuOptionValue, responsible for business processing, and communicate between web and persistence layer.
 */
public class SkuOptionValueManagerImpl extends GenericManagerImpl<SkuOptionValue> implements SkuOptionValueManager {

	private SkuOptionValueDao skuOptionValueDao = null;

	/**
	 * @param skuOptionValueDao
	 *            the skuOptionValueDao to set
	 */
	public void setSkuOptionValueDao(SkuOptionValueDao skuOptionValueDao) {
		this.skuOptionValueDao = skuOptionValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = skuOptionValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(SkuOptionValue entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override 
	protected void onSave(SkuOptionValue entity) {

	}

	public List<SkuOptionValue> findSkuOptionValueByOptionCodeAndValueName(String optionCode, String valueName) {
		return skuOptionValueDao.findSkuOptionValueByOptionCodeAndValueName(optionCode, valueName);
	}

	public List<SkuOptionValue> findSkuOptionValueByOptionNameAndValueName(String optionName, String valueName) {
		return skuOptionValueDao.findSkuOptionValueByOptionNameAndValueName(optionName, valueName);
	}

}
