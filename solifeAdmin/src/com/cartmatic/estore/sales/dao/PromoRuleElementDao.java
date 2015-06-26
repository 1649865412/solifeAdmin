package com.cartmatic.estore.sales.dao;

import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PromoRuleElement.
 */
public interface PromoRuleElementDao extends GenericDao<PromoRuleElement> {
	public void deleteAllByRuleId(Integer _ruleId);
}