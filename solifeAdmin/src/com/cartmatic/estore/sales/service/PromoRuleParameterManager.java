package com.cartmatic.estore.sales.service;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PromoRuleParameter, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface PromoRuleParameterManager extends GenericManager<PromoRuleParameter> {
	public void deleteAllByElementId(Integer _elementId);
}
