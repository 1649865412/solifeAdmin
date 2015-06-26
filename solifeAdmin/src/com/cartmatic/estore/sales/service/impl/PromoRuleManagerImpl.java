package com.cartmatic.estore.sales.service.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.dao.PromoRuleDao;
import com.cartmatic.estore.sales.model.PromoRuleSearchCriteria;
import com.cartmatic.estore.sales.service.PromoRuleManager;


/**
 * Manager implementation for PromoRule, responsible for business processing, and communicate between web and persistence layer.
 */
public class PromoRuleManagerImpl extends GenericManagerImpl<PromoRule> implements PromoRuleManager {

	private PromoRuleDao promoRuleDao = null;

	/**
	 * @param promoRuleDao
	 *            the promoRuleDao to set
	 */
	public void setPromoRuleDao(PromoRuleDao promoRuleDao) {
		this.promoRuleDao = promoRuleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = promoRuleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(PromoRule entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(PromoRule entity) {

	}
	
	public List<PromoRule> getAllCartPromotionRulesInProcess(){
		return promoRuleDao.getAllCartPromotionRulesInProcess(); 
	}
	public List<PromoRule> getAllCatalogPromotionRulesInProcess(){
		return promoRuleDao.getAllCatalogPromotionRulesInProcess();
	}
	
	public List<PromoRule> getAllCouponPromotionRulesInProcess(){
		return promoRuleDao.getAllCouponPromotionRulesInProcess();
	}
	
	public PromoRule getCouponPromotionRule(Coupon _coupon){
		return promoRuleDao.getCouponPromotionRule(_coupon);
	}
	
	public List<PromoRule> getAllCouponTypesInProcessOrInFuture(){
		return promoRuleDao.getAllCouponTypesInProcessOrInFuture();
	}
	public List<PromoRule>  searchPromotionRules(SearchCriteria _searchCriteria,
			PromoRuleSearchCriteria _promoSearchCriteria){
		return promoRuleDao.searchPromotionRules(_searchCriteria, _promoSearchCriteria);
	}
	public List<PromoRule> getAllPromotionRulesInProcessForFront(){
		return promoRuleDao.getAllPromotionRulesInProcessForFront();
	}
	public List<PromoRule> getAllPromotionRulesInFutureForFront(){
		return promoRuleDao.getAllPromotionRulesInFutureForFront();
	}
	public void setState(PromoRule _rule) {
		PromoRule rule = _rule;
		Date now = new Date();
		int i = now.compareTo(rule.getStartTime());
		int j;
		if (rule.getEndTime() != null) {
			j = now.compareTo(rule.getEndTime());
		} else {
			j = -1;
		}
		
		if(rule.getStatus() == 1 ){
			if ((i > 0) && (j < 0)) {
				rule.setState(String.valueOf(PromoRule.STATE_DOING));
			} else if ((i < 0) && (j < 0)) {
				rule.setState(String.valueOf(PromoRule.STATE_FUTURE));
			} else {
				rule.setState(String.valueOf(PromoRule.STATE_PAST));
			}	
		}else{
			rule.setState(String.valueOf(PromoRule.STATE_INVALID));
		}
		return;
	}

	@Override
	public PromoRule getSpecialPromotionRule(Short triggerType) {
		// TODO Auto-generated method stub
		return this.promoRuleDao.getSpecialPromotionRule(triggerType);
	}
	
}
