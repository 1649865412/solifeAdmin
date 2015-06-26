package com.cartmatic.estore.sales.service.impl;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.dao.PromoRuleParameterDao;
import com.cartmatic.estore.sales.service.PromoRuleParameterManager;


/**
 * Manager implementation for PromoRuleParameter, responsible for business processing, and communicate between web and persistence layer.
 */
public class PromoRuleParameterManagerImpl extends GenericManagerImpl<PromoRuleParameter> implements PromoRuleParameterManager {

	private PromoRuleParameterDao promoRuleParameterDao = null;

	/**
	 * @param promoRuleParameterDao
	 *            the promoRuleParameterDao to set
	 */
	public void setPromoRuleParameterDao(PromoRuleParameterDao promoRuleParameterDao) {
		this.promoRuleParameterDao = promoRuleParameterDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = promoRuleParameterDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(PromoRuleParameter entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(PromoRuleParameter entity) {

	}
	
	public void deleteAllByElementId(Integer _elementId) {
		promoRuleParameterDao.deleteAllByElementId(_elementId);
		
	}

}
