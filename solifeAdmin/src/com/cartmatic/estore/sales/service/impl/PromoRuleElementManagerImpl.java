package com.cartmatic.estore.sales.service.impl;

import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.dao.PromoRuleElementDao;
import com.cartmatic.estore.sales.service.PromoRuleElementManager;


/**
 * Manager implementation for PromoRuleElement, responsible for business processing, and communicate between web and persistence layer.
 */
public class PromoRuleElementManagerImpl extends GenericManagerImpl<PromoRuleElement> implements PromoRuleElementManager {

	private PromoRuleElementDao promoRuleElementDao = null;

	/**
	 * @param promoRuleElementDao
	 *            the promoRuleElementDao to set
	 */
	public void setPromoRuleElementDao(PromoRuleElementDao promoRuleElementDao) {
		this.promoRuleElementDao = promoRuleElementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = promoRuleElementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(PromoRuleElement entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(PromoRuleElement entity) {

	}
	public void deleteAllByRuleId(Integer _ruleId){
		promoRuleElementDao.deleteAllByRuleId(_ruleId);
	}

}
