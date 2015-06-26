package com.cartmatic.estore.sales.service;

import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PromoRuleElement, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface PromoRuleElementManager extends GenericManager<PromoRuleElement> {
	public void deleteAllByRuleId(Integer _ruleId);
}
